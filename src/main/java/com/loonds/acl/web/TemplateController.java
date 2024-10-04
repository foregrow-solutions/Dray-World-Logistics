package com.loonds.acl.web;

import com.itextpdf.io.source.ByteArrayOutputStream;
import com.loonds.acl.model.enums.RateType;
import com.loonds.acl.service.PdfGenerateService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@RestController
@Slf4j
@AllArgsConstructor
public class TemplateController {
    private final PdfGenerateService pdfGenerateService;
//    private final PDFGenerator pdfGenerator;


    @GetMapping("/loads/{id}/invoice")
    public void generatePdf(@PathVariable String id,
                            HttpServletResponse response) throws IOException {

        ByteArrayOutputStream baos = pdfGenerateService.generateInvoice(id);

        response.setContentType("application/pdf");
        response.setContentLength(baos.size());
        response.setHeader("Content-Disposition", "attachment; filename=invoice.pdf");

        OutputStream outputStream = response.getOutputStream();
        baos.writeTo(outputStream);
        outputStream.flush();
    }

    @GetMapping("/loads/{id}/rate-conformation")
    public void generatePdf2(@PathVariable String id,
                             @RequestParam RateType type,
                             HttpServletResponse response) throws IOException {

        ByteArrayOutputStream baos = pdfGenerateService.generateConfirmation(id, type);
        String fileName = "confirmation_" + type.toString().toLowerCase() + ".pdf";

        response.setContentType("application/pdf");
        response.setContentLength(baos.size());
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

        OutputStream outputStream = response.getOutputStream();
        baos.writeTo(outputStream);
        outputStream.flush();
    }

}
