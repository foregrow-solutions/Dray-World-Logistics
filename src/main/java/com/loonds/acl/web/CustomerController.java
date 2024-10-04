package com.loonds.acl.web;

import com.loonds.acl.model.dto.CustomerDto;
import com.loonds.acl.model.enums.CustomerStatus;
import com.loonds.acl.model.payload.CreateCustomerPayload;
import com.loonds.acl.security.AuthenticatedUser;
import com.loonds.acl.security.CurrentUser;
import com.loonds.acl.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Tag(name = "Customer APIs", description = "API for manage Customer Operations")
@Slf4j
@RestController
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @RequestMapping(value = "/customer", method = RequestMethod.GET)
    public ModelAndView dashboard(@CurrentUser AuthenticatedUser authenticatedUser) {
        ModelAndView vi = new ModelAndView();
        vi.setViewName("customer/customer");
        return vi;
    }

    @RequestMapping(value = "/customer-request-view", method = RequestMethod.GET)
    public ModelAndView loadApproveRequest(@CurrentUser AuthenticatedUser authenticatedUser) {
        ModelAndView vi = new ModelAndView();
        vi.setViewName("customer/customer-request");
        return vi;
    }

    @Operation(description = "Added new Customer")
    @PostMapping("/customers")
    public CustomerDto create(@RequestBody CreateCustomerPayload payload,
                              @CurrentUser AuthenticatedUser authenticatedUser) {
        log.info("Load Payload : {}", payload);
        return customerService.add(authenticatedUser.getUsername(), payload);
    }

    @Operation(description = "Update Given Customer Details")
    @PutMapping("/customers/{id}")
    public Optional<CustomerDto> update(@PathVariable long id,
                                        @RequestBody CustomerDto payload,
                                        @CurrentUser AuthenticatedUser authenticatedUser) {
        log.info("Load Payload : {}", payload);
        return customerService.update(authenticatedUser.getUsername(), id, payload);
    }

    @Operation(description = "Get Details of given Customer")
    @GetMapping("/customers/{id}")
    public Optional<CustomerDto> get(@PathVariable long id,
                                     @CurrentUser AuthenticatedUser authenticatedUser) {
        return customerService.get(id);
    }

    @Operation(description = "Get List of Customer given agents")
    @GetMapping("/customers")
    public List<CustomerDto> get(@CurrentUser AuthenticatedUser authenticatedUser) {
        return customerService.getAllCustomer(authenticatedUser.getUsername());

    }
    @Operation(description = "Get All Customers")
    @GetMapping("/admin/customers")
    public List<CustomerDto> getAll() {
        return customerService.getAll();
    }

    @Operation(description = "Approve given Customer")
    @PutMapping("/admin/customers/{id}/approve")
    void isApproved(@PathVariable long id,
                    @CurrentUser AuthenticatedUser authenticatedUser) {
        customerService.isApproved(authenticatedUser.getUsername(), id, CustomerStatus.APPROVED);
    }

    @Operation(description = "Approve given Customer")
    @DeleteMapping("/admin/customers/{id}/blocked")
    void isBlocked(@PathVariable long id,
                   @CurrentUser AuthenticatedUser authenticatedUser) {
        customerService.isApproved(authenticatedUser.getUsername(), id, CustomerStatus.BLOCKED);
    }

    @Operation(description = "Get All Un-Approved Customers")
    @GetMapping("/admin/customers/un-approved")
    List<CustomerDto> getAllUnApproved(@CurrentUser AuthenticatedUser authenticatedUser) {
        return customerService.getAllUnApproved();
    }
}
