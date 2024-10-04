package com.loonds.acl.web;

import com.loonds.acl.model.dto.NotificationDto;
import com.loonds.acl.security.AuthenticatedUser;
import com.loonds.acl.security.CurrentUser;
import com.loonds.acl.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@Tag(name = "Notification APIs", description = "API for manage Notification Operations")
@Slf4j
@RestController
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @RequestMapping(value = "/notification", method = RequestMethod.GET)
    public ModelAndView getTemplate() {
        ModelAndView vi = new ModelAndView();
        vi.setViewName("notification/notification");
        return vi;
    }

    @Operation(summary = "Get All Notification")
    @GetMapping("/notifications")
    public Page<NotificationDto> getNotification(@CurrentUser AuthenticatedUser authenticatedUser,
                                                 @PageableDefault Pageable pageable) {
        return notificationService.getNotification(authenticatedUser.getUsername(),pageable);
    }


}
