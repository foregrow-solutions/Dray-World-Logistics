package com.loonds.acl.web;

import com.loonds.acl.model.dto.DriverDto;
import com.loonds.acl.model.enums.DriverStatus;
import com.loonds.acl.model.payload.CreateDriverPayload;
import com.loonds.acl.security.AuthenticatedUser;
import com.loonds.acl.security.CurrentUser;
import com.loonds.acl.service.DriverService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Tag(name = "Driver APIs", description = "API for manage Driver Operations")
@RestController
@RequiredArgsConstructor
public class DriverController {
    private final DriverService driverService;

    @RequestMapping(value = "/driver", method = RequestMethod.GET)
    public ModelAndView dashboard(@CurrentUser AuthenticatedUser authenticatedUser) {
        ModelAndView vi = new ModelAndView();
        vi.setViewName("driver/driver");
        return vi;
    }

    @RequestMapping(value = "/driver-request-view", method = RequestMethod.GET)
    public ModelAndView loadApproveRequest(@CurrentUser AuthenticatedUser authenticatedUser) {
        ModelAndView vi = new ModelAndView();
        vi.setViewName("driver/driver-request");
        return vi;
    }

    @Operation(description = "Added new Driver")
    @PostMapping("/drivers")
    public DriverDto create(@RequestBody CreateDriverPayload payload,
                            @CurrentUser AuthenticatedUser authenticatedUser) {
        return driverService.addDriver(authenticatedUser.getUsername(), payload);
    }

    @Operation(description = "Added new Driver")
    @PutMapping("/drivers/{id}")
    public Optional<DriverDto> update(@PathVariable long id,
                                      @RequestBody DriverDto payload,
                                      @CurrentUser AuthenticatedUser authenticatedUser) {
        return driverService.updateDriver(authenticatedUser.getUsername(), id, payload);
    }

    @Operation(description = "Get Given Driver Details")
    @GetMapping("/drivers/{id}")
    public Optional<DriverDto> get(@PathVariable long id,
                                   @CurrentUser AuthenticatedUser authenticatedUser) {
        return driverService.get(id);
    }

    @Operation(description = "Get Given Agent Driver List")
    @GetMapping("/drivers")
    public List<DriverDto> getAll(@CurrentUser AuthenticatedUser authenticatedUser) {
        return driverService.getAllDriver(DriverStatus.APPROVED);
    }

    @Deprecated
    @Operation(description = "Approve given Driver")
    @PutMapping("/admin/drivers/{id}/approve")
    void isApproved(@PathVariable long id,
                    @CurrentUser AuthenticatedUser authenticatedUser) {
        driverService.isApproved(authenticatedUser.getUsername(), id, DriverStatus.APPROVED);
    }

    @Deprecated
    @Operation(description = "Block given Driver")
    @DeleteMapping("/admin/drivers/{id}/blocked")
    void isBlocked(@PathVariable long id,
                   @CurrentUser AuthenticatedUser authenticatedUser) {
        driverService.isApproved(authenticatedUser.getUsername(), id, DriverStatus.BLOCKED);
    }

    @Operation(description = "Get All Un-Approved Driver")
    @GetMapping("/admin/drivers/un-approved")
    List<DriverDto> getAllUnApproved(@CurrentUser AuthenticatedUser authenticatedUser) {
        return driverService.getAllDriver(DriverStatus.PENDING);
    }

    @Operation(description = "Get All Block Driver")
    @GetMapping("/admin/drivers/blocked")
    List<DriverDto> getAllBlocked(@CurrentUser AuthenticatedUser authenticatedUser) {
        return driverService.getAllDriver(DriverStatus.BLOCKED);
    }

}
