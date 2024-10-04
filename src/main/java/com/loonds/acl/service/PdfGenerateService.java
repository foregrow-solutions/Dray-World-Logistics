package com.loonds.acl.service;

import com.itextpdf.io.source.ByteArrayOutputStream;
import com.loonds.acl.model.dto.ChannelDto;
import com.loonds.acl.model.enums.RateType;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public interface PdfGenerateService {
    ByteArrayOutputStream generateInvoice(String id) throws IOException;
    ByteArrayOutputStream generateConfirmation(String id, RateType type) throws IOException;
}
