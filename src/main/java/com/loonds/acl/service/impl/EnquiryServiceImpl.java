package com.loonds.acl.service.impl;

import com.loonds.acl.model.dto.EnquiryDto;
import com.loonds.acl.model.payload.CreateQueryPayload;
import com.loonds.acl.repository.QueryRepository;
import com.loonds.acl.service.EnquiryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EnquiryServiceImpl implements EnquiryService {
    private final QueryRepository queryRepository;

    @Override
    public void saveQuery(CreateQueryPayload payload) {

    }

    @Override
    public Page<EnquiryDto> fetchAllQuery(Pageable pageable) {
        return null;
    }
}
