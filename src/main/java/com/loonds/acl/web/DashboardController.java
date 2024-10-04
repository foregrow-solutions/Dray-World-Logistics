package com.loonds.acl.web;

import com.loonds.acl.model.dto.DashboardDto;
import com.loonds.acl.model.dto.EmployeeDashboardDto;
import com.loonds.acl.security.AuthenticatedUser;
import com.loonds.acl.security.CurrentUser;
import com.loonds.acl.service.DashboardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DashboardController {
    private final DashboardService dashboardService;

    @RequestMapping(value = "/admin-dashboard", method = RequestMethod.GET)
    public ModelAndView adminDashboard() {
        ModelAndView vi = new ModelAndView();
        vi.setViewName("admin-dashboard/dashboard");
        return vi;
    }

    @RequestMapping(value = "/e-dashboard", method = RequestMethod.GET)
    public ModelAndView employeeDashboard() {
        ModelAndView vi = new ModelAndView();
        vi.setViewName("employee-dashboard/dashboard");
        return vi;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView vi = new ModelAndView();
//        vi.setViewName("index");
        vi.setViewName("auth/login");
        return vi;
    }

    @GetMapping("/count")
    public DashboardDto getCount() {
        return dashboardService.dashboardCount();
    }

    @GetMapping("/e-count")
    public EmployeeDashboardDto getECount(@CurrentUser AuthenticatedUser user) {
        return dashboardService.employeeDashboardCount(user.getUsername());
    }
}
