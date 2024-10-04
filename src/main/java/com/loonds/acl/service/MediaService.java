package com.loonds.acl.service;

import com.loonds.acl.model.enums.UploadType;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface MediaService {
    public String upload(String userId, MultipartFile file) throws IOException;

}
