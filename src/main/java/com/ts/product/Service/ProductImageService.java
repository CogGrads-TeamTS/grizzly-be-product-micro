package com.ts.product.Service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ProductImageService {
    ResponseEntity singleFileUploadSave(Long id, MultipartFile file);
}
