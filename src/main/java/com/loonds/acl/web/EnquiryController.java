package com.loonds.acl.web;

import com.loonds.acl.model.dto.EnquiryDto;
import com.loonds.acl.model.payload.CreateQueryPayload;
import com.loonds.acl.service.EnquiryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Enquiry APIs", description = "API for manage Enquiry Operations")
@Slf4j
@RestController
@RequiredArgsConstructor
public class EnquiryController {
    private final EnquiryService enquiryService;

    @Operation(summary = "Save Leads Query Response")
    @PostMapping("/contact-us")
    public void saveLeads(@RequestBody CreateQueryPayload payload) {
        enquiryService.saveQuery(payload);
    }

    @Operation(summary = "Get All Queries")
    @GetMapping("/admin/queries")
    public Page<EnquiryDto> getLeads() {
        return enquiryService.fetchAllQuery(Pageable.ofSize(10));
    }
}
