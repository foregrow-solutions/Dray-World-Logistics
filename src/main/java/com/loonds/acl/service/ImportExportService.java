package com.loonds.acl.service;

import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImportExportService {
    void importFromCsv(MultipartFile file) throws IOException;

//    InputStreamResource exportToCsv() throws IOException;
    byte[] exportToCsv() throws  IOException;
}
