package com.loonds.acl.service.impl;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.*;
import com.loonds.acl.model.entity.Channel;
import com.loonds.acl.model.entity.Customer;
import com.loonds.acl.model.entity.Driver;
import com.loonds.acl.model.entity.Rate;
import com.loonds.acl.model.enums.RateType;
import com.loonds.acl.repository.ChannelRepository;
import com.loonds.acl.service.PdfGenerateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PdfGenerateServiceImpl implements PdfGenerateService {
    private final ChannelRepository channelRepository;

    @Value("${common.converter.destination-dir}")
    private String pdfGeneratorOutputDir;

        private static final String LOGO_URL = "/home/ubuntu/dray-world-logistics/src/main/resources/static/images/logo.png";
//    private static final String LOGO_URL = "D:\\workspace\\dray-world-logistics\\src\\main\\resources\\static\\images\\logo.png";
//
@Override
@Transactional(readOnly = true)
public ByteArrayOutputStream generateInvoice(String id) throws IOException {
    Channel channel = channelRepository.findById(id).orElse(null);
    Customer customer = channel != null ? channel.getCustomer() : null;

    Set<Rate> rates = channel != null ? channel.getRates() : Collections.emptySet();
    Set<Rate> filteredRates = rates.stream()
            .filter(rate -> rate.getType() == RateType.CUSTOMER)
            .collect(Collectors.toSet());
    String total = calculateTotalAmount(filteredRates);

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfWriter writer = new PdfWriter(baos);
    PdfDocument pdf = new PdfDocument(writer);
    Document document = new Document(pdf);

    PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
    document.setFont(font);

    addHeader(document, "INVOICE");

    addCustomerDetails(document, channel, customer, "BILL TO");

    addItemsTable(document, filteredRates);

    Paragraph totalAmountParagraph = new Paragraph("Total: $" + total)
            .setTextAlignment(TextAlignment.RIGHT)
            .setFontSize(24)
            .setMarginTop(30);
    document.add(totalAmountParagraph);

    ImageData backgroundImageData = ImageDataFactory.create(LOGO_URL);
    Image backgroundImage = new Image(backgroundImageData);

    // Resize the image to fit the page
    float pageWidth = pdf.getDefaultPageSize().getWidth();
    float pageHeight = pdf.getDefaultPageSize().getHeight();
    backgroundImage.scaleToFit(pageWidth, pageHeight);

    // Set the position and opacity
    backgroundImage.setFixedPosition(0, 0);
    backgroundImage.setOpacity(0.5f);

    document.add(backgroundImage);

    // Add your footer note or any other content here

    document.close();
    pdf.close();

    return baos;
}



    @Override
    @Transactional(readOnly = true)
    public ByteArrayOutputStream generateConfirmation(String id, RateType type) throws IOException {
        Channel channel = channelRepository.findById(id).orElse(null);
        Customer customer = channel != null ? channel.getCustomer() : null;
        Driver driver = channel != null ? channel.getDriver() : null;
        Set<Rate> rates = channel != null ? channel.getRates() : Collections.emptySet();
        Set<Rate> filteredRates = rates.stream()
                .filter(rate -> rate.getType() == type)
                .collect(Collectors.toSet());
        String total = calculateTotalAmount(filteredRates);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.getPageEffectiveArea(PageSize.A4);

        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        document.setFont(font);

        addHeader(document, "Rate Confirmation");
        addCustomerDetails(document, channel, customer, "Details");

        addItemsTable2(document,rates, customer,driver,type);

        Paragraph totalAmountParagraph = new Paragraph("Total: $" + total).setTextAlignment(TextAlignment.RIGHT).setFontSize(20).setMarginTop(30);
        document.add(totalAmountParagraph);

        Table table1 = new Table(new float[]{1, 2, 1, 2, 1, 2});
        table1.addCell(new Cell().add(new Paragraph("Accepted By: ")).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph("____________")).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph("Date : ")).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph("____________")).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph("Signature: ")).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph("____________")).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph("Driver Name: ")).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph("____________")).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph("Cell #: ")).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph("____________")).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph("Truck: : ")).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph("____________")).setBorder(Border.NO_BORDER));
        table1.setWidth(UnitValue.createPercentValue(100));
        table1.setMarginBottom(10);
        table1.setMarginTop(10);
        document.add(table1);

        table1.setWidth(UnitValue.createPercentValue(100));
        table1.setBorder(Border.NO_BORDER);
        table1.setMargin(0);
        table1.setPadding(0);

        addFooterNote(document);

        document.close();
        pdf.close();

        return baos;
    }

    @Transactional(readOnly = true)
    String calculateTotalAmount(Set<Rate> rates) {
        double amount = rates.stream()
                .filter(rate -> rate.getAmount() != null)
                .mapToDouble(Rate::getAmount)
                .sum();
        return String.valueOf(amount);
    }

    void addHeader(Document document, String title) throws MalformedURLException {
        Table headerTable = new Table(new float[]{1, 1}).setWidth(UnitValue.createPercentValue(100));
        Paragraph titleParagraph = new Paragraph(title).setFontSize(38).setBold().setTextAlignment(TextAlignment.LEFT);
        Paragraph companyName = new Paragraph("DRAY WORLD LOGISTICS INC").setBold();
        Paragraph companyAddress = new Paragraph("9 Nom Crescent Unit 2\nMarkham, Om L3S 2B3, CA");
        Cell companyCell = new Cell().add(titleParagraph).add(companyName).add(companyAddress).setBorder(Border.NO_BORDER);
        headerTable.addCell(companyCell);

        Image logoImage = null;

        try {
            logoImage = new Image(ImageDataFactory.create(LOGO_URL)).setWidth(150).setHorizontalAlignment(HorizontalAlignment.RIGHT);
        } catch (MalformedURLException e) {
            System.out.println("Invalid logo URL: " + LOGO_URL);
        }

        if (logoImage != null) {
            Cell logoCell = new Cell().add(logoImage).setBorder(Border.NO_BORDER)
                    .setVerticalAlignment(VerticalAlignment.TOP)
                    .setMarginBottom(20);
            headerTable.addCell(logoCell);
        }

        document.add(headerTable);
    }


    private void addFooterNote(Document document) {
        Paragraph footerNote = new Paragraph("Customer is responsible to confirm the actual weight & count received from the shipper prior to commencing any transit, and is  responsible for any shortage on delivery.\n" +
                " Any accessors charges such as loading-unloading fee, entry fee, pallet exchange, etc., are included in the agreed rate. POD must be submitted within 5 days from the date the load is delivered as a condition for payment. By signing this rate confirmation, the customer agrees to be bound by DRAY WORLD LOGISTICS INC. For payment questions or any complaints, call 689-204-1948 or email us at ops@dray-world.com")
                .setFontSize(6);
        document.add(footerNote).getBottomMargin();
    }


    private Paragraph createRow(String leftContent, String rightContent, TabAlignment tabAlignment) {
        return new Paragraph()
                .add(new Text(leftContent).setTextAlignment(TextAlignment.LEFT))
                .addTabStops(new TabStop((float) 500, tabAlignment))
                .add(new Tab())
                .add(new Text(rightContent));
    }

    private void addCustomerDetails(Document document, Channel channel, Customer customer, String subtitle) throws MalformedURLException {
        String companyName = (channel != null && channel.getCustomer() != null) ? channel.getCustomer().getCompanyName() : "";
        String invoiceId = (channel != null) ? channel.getPublicId() : "";
        String customerAddress = (customer != null) ? customer.getCompanyAddress() : "VY-12, New York";
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("d MMMM yyyy"));

        Paragraph sTitle = new Paragraph().add(new Text(subtitle + "\n").setBold()).setMarginTop(20);

        Paragraph companyAndInvoice = createRow(companyName, invoiceId, TabAlignment.RIGHT);

        Paragraph addressAndDate = createRow(customerAddress, "Date: " + currentDate, TabAlignment.RIGHT);

        document.add(sTitle);
        document.add(companyAndInvoice);
        document.add(addressAndDate);
    }

    private void addItemsTable(Document document, Set<Rate> filteredRates) {
        Table itemsTable = new Table(new float[]{4, 1}).setWidth(UnitValue.createPercentValue(100));
        itemsTable.addHeaderCell(new Cell().add(new Paragraph("Description").setBold()).setBorderRight(Border.NO_BORDER).setBorderLeft(Border.NO_BORDER));
        itemsTable.addHeaderCell(new Cell().add(new Paragraph("Amount").setBold()).setBorderRight(Border.NO_BORDER).setBorderLeft(Border.NO_BORDER));
        itemsTable.setMarginTop(30);
        itemsTable.setMarginBottom(10);
        itemsTable.setBorder(Border.NO_BORDER);

        for (Rate rate : filteredRates) {
            Cell descriptionCell = new Cell().add(new Paragraph(rate.getLabel())).setBorder(Border.NO_BORDER);
            Cell amountCell = new Cell().add(new Paragraph(String.valueOf(rate.getAmount()))).setBorder(Border.NO_BORDER);
            itemsTable.addCell(descriptionCell);
            itemsTable.addCell(amountCell);
        }

        document.add(itemsTable);
    }

    private void addItemsTable2(Document document, Set<Rate> rates, Customer customer, Driver driver, RateType type) {
        Table itemsTable = new Table(new float[]{2, 2, 2, 2, 2}).setWidth(UnitValue.createPercentValue(100));
        itemsTable.addHeaderCell(new Cell().add(new Paragraph("Carrier").setBold()));
        itemsTable.addHeaderCell(new Cell().add(new Paragraph("Phone").setBold()));
        itemsTable.addHeaderCell(new Cell().add(new Paragraph("Equipment").setBold()));
        itemsTable.addHeaderCell(new Cell().add(new Paragraph("Agreed Amount").setBold()));
        itemsTable.addHeaderCell(new Cell().add(new Paragraph("Status").setBold()));
        itemsTable.setMarginTop(30);
        itemsTable.setMarginBottom(10);
        itemsTable.setBorder(Border.NO_BORDER);


        for (Rate rate : rates) {
            if (rate.getType() == type) {
                String name, mobile;
                if (type == RateType.CUSTOMER) {
                    name = (customer != null) ? customer.getName() : "Customer Name";
                    mobile = (customer != null) ? customer.getMobile() : "Mobile";
                } else {
                    name = (driver != null) ? driver.getName() : "Driver Name";
                    mobile = (driver != null) ? driver.getMobile() : "Mobile";
                }
                itemsTable.addCell(name);
                itemsTable.addCell(mobile);
                itemsTable.addCell(rate.getLabel());
                itemsTable.addCell(String.valueOf(rate.getAmount()));
                itemsTable.addCell("OPEN");
            }
        }

        itemsTable.setKeepTogether(true);
        document.add(itemsTable);
    }

}
