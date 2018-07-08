package com.ts.product.Controller;


import com.netflix.discovery.converters.Auto;
import com.ts.product.Model.CartProduct;
import com.ts.product.Model.PaymentRequest;
import com.ts.product.Service.PaypalService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/paypal")
@CrossOrigin
public class PaymentController {
    @Autowired
    private PaypalService paypalService;

    @PostMapping(value = "/make/payment")
    public Map<String, Object> makePayment(@RequestBody PaymentRequest request){
        for (CartProduct item : request.getItems()) {
            System.out.println(item.getId());
        }
        return paypalService.createPayment(request);
    }

    @PostMapping(value = "/complete/payment")
    public Map<String, Object> completePayment(HttpServletRequest request){
        return paypalService.completePayment(request);
    }
}
