package com.loonds.acl.web;

import com.loonds.acl.service.ImportExportService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Tag(name = "Import/Export APIs", description = "API for manage Bulk Data Operations")
@Slf4j
@RestController
@RequiredArgsConstructor
public class ImportExportController {
    private final ImportExportService importExportService;


    @PostMapping("/import-csv")
    public ResponseEntity<String> importCsvData(@RequestParam("file") MultipartFile file) {
        try {
            importExportService.importFromCsv(file);
            return ResponseEntity.ok("CSV data imported successfully.");
        } catch (IOException e) {
            log.error("Error occurred during CSV import: {}", e.getMessage());
            return ResponseEntity.status(500).body("Failed to import CSV data: " + e.getMessage());
        }
    }

    @GetMapping("/export-csv")
    public ResponseEntity<byte[]> exportCsvData() {
        try {
            byte[] zipBytes = importExportService.exportToCsv();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentDispositionFormData("attachment", "exported_data.zip");
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(zipBytes);
        } catch (IOException e) {
            log.error("Error occurred during CSV export: {}", e.getMessage());
            return ResponseEntity.status(500).body(null);
        }
    }

}
