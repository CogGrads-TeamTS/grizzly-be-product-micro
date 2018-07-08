package com.ts.product.Service;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.ts.product.Model.Cart;
import com.ts.product.Model.CartProduct;
import com.ts.product.Model.PaymentRequest;
import com.ts.product.Model.Product;
import com.ts.product.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PaypalService {
    @Autowired
    private ProductRepository productRepository;

    @Value("${paypal.clientId}")
    private String clientId;
    @Value("${paypal.clientSecret}")
    private String clientSecret;
    @Value("${paypal.returnUrl}")
    private String returnUrl;
    @Value("${paypal.cancelUrl}")
    private String cancelUrl;
    @Value("${paypal.mode}")
    private String mode;


    private ItemList buildItemList(List<CartProduct> products) {
        ItemList itemList = new ItemList();
        List<Item> items = new ArrayList<>();
        for (CartProduct product : products) {
            Item item = new Item();
            item.setName(product.getName());
            item.setCurrency("AUD");
            item.setPrice(Float.toString(product.getPrice()));
            item.setQuantity(Integer.toString(product.getQty()));
            item.setSku(Long.toString(product.getId()));
            item.setDescription(product.getDescription());
            items.add(item);
        }
        itemList.setItems(items);
        return itemList;
    }

    private ItemList getItemList(List<CartProduct> clientCartProducts) {
        // returns an item list with information retrieved from the database
        // information is retrieved from the database based on the product ids passed to backend.
        // cannot trust the information passed from client such as prices...
        HashMap<Long, CartProduct> clientCartProductHashMap = new HashMap<>();
        for (CartProduct product : clientCartProducts) {
            clientCartProductHashMap.put(product.getId(), product);
        }

        List<Product> dbProducts = productRepository.findBatchProducts(clientCartProductHashMap.keySet().toArray(new Long[clientCartProductHashMap.keySet().size()]));
        List<CartProduct> dbCartProducts = new ArrayList<CartProduct>();
        for (Product product : dbProducts) {
            CartProduct dbCartProduct = new CartProduct(product, clientCartProductHashMap.get(product.getId()).getQty());
            dbCartProducts.add(dbCartProduct);
        }

        return buildItemList(dbCartProducts);
    }

    public Map<String, Object> createPayment(PaymentRequest request){
        Map<String, Object> response = new HashMap<String, Object>();
        Amount amount = new Amount();
        amount.setCurrency(request.getCurrency());

        ItemList itemList = getItemList(request.getItems());

        // calculate total amount based off itemList
        float amountTotal = 0;
        for(Item item : itemList.getItems()) {
            amountTotal =+ (Float.parseFloat(item.getPrice()) * Integer.parseInt(item.getQuantity()));
        }
        amount.setTotal(Float.toString(amountTotal));

        Transaction transaction = new Transaction();

        // assign amount to paypal transaction
        transaction.setAmount(amount);

        // assign product metadata to paypal transaction
        transaction.setItemList(itemList);

        List<Transaction> transactions = new ArrayList<Transaction>();
        transactions.add(transaction);
        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(returnUrl);
        payment.setRedirectUrls(redirectUrls);
        Payment createdPayment;
        try {
            String redirectUrl = "";
            APIContext context = new APIContext(clientId, clientSecret, mode);
            createdPayment = payment.create(context);
            if(createdPayment!=null){
                List<Links> links = createdPayment.getLinks();
                for (Links link:links) {
                    if(link.getRel().equals("approval_url")){
                        redirectUrl = link.getHref();
                        break;
                    }
                }
                response.put("id", createdPayment.getId());
                response.put("status", "success");
                response.put("redirect_url", redirectUrl);
            }
        } catch (PayPalRESTException e) {
            System.out.println("Error happened during payment creation!");
        }
        return response;
    }

    public Map<String, Object> completePayment(HttpServletRequest req){
        Map<String, Object> response = new HashMap();
        Payment payment = new Payment();
        payment.setId(req.getParameter("paymentID"));
        System.out.println(req.getParameter("paymentID"));
        System.out.println(req.getParameter("payerID"));
        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(req.getParameter("payerID"));
        try {
            APIContext context = new APIContext(clientId, clientSecret, mode);
            Payment createdPayment = payment.execute(context, paymentExecution);
            if(createdPayment!=null){
                response.put("status", "success");
                response.put("payment", createdPayment.toJSON());

                // get the items paid for
                // using sku from each item, can construct the order history to store in database.
                List<Item> items = createdPayment.getTransactions().get(0).getItemList().getItems();
                for (Item item : items) {
                    System.out.println(item.getSku());
                }
            }
        } catch (PayPalRESTException e) {
            System.err.println(e.getDetails());
        }
        return response;
    }
}
