package com.ts.product.Service;

import com.ts.product.Client.CategoryClient;
import com.ts.product.Repository.ProductRepository;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class ProductImageServiceImpl implements ProductImageService {

    public ResponseEntity singleFileUploadSave(Long id, MultipartFile file) {

        if (file.isEmpty()) {
            return new ResponseEntity("The file is invalid. Please try again.", HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        }

        Path path;
        String name;

        try {
            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();

            // Modify the name
            String currentDate = new SimpleDateFormat("yyyyMMddHHmmssSS").format(new Date());
            name = FilenameUtils.getBaseName(id + "-".concat(currentDate)) + "." + FilenameUtils.getExtension(file.getOriginalFilename()).toLowerCase();
            System.out.println(name);

            path = Paths.get("data/images/" + name);
            Files.createFile(path);
            Files.write(path, bytes);

        } catch (FileAlreadyExistsException faee){
            System.out.println(faee);
            return new ResponseEntity("File already exists, please change name and try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IOException e) {
            System.out.println(e);
            return new ResponseEntity("Image could not be added. Try Again at a later time.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity(name, HttpStatus.CREATED);
    }
}
