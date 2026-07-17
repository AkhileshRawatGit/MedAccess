package com.medaccess.Service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryService {

    private final Cloudinary cloudinary;
    public String uploadFile(MultipartFile file)  {

        try {
            Map uploadFile = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            return uploadFile.get("secure_url").toString();
        }catch (Exception e){
            throw  new RuntimeException(e.getMessage());
        }

    }
}
