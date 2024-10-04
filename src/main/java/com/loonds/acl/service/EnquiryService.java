package com.loonds.acl.service;

import com.loonds.acl.model.dto.EnquiryDto;
import com.loonds.acl.model.payload.CreateQueryPayload;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EnquiryService {

    void saveQuery(CreateQueryPayload payload);

    Page<EnquiryDto> fetchAllQuery(Pageable pageable);
}
