package com.loonds.acl.web;

import com.loonds.acl.common.ApplicationException;
import com.loonds.acl.common.ErrorCode;
import com.loonds.acl.model.payload.UserLoginPayload;
import com.loonds.acl.security.TokenService;
import com.loonds.acl.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Tag(name = "Auth APIs", description = "API for manage User Auth Operations")
@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserService service;
    private final TokenService tokenService;

    @RequestMapping(value="/login",method= RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView vi = new ModelAndView();
        vi.setViewName("auth/login");
        return vi;
    }

    @Operation(summary = "User Login End-Point")
    @PostMapping("/auth/login")
    public ResponseEntity<?> token(@Valid @RequestBody UserLoginPayload request) {
        log.debug("Login request from user: {}", request.getUsername());
        var user = service.findByUsername(request.getUsername()).orElseThrow(() -> new ApplicationException(ErrorCode.BAD_CRED, "Username not found"));
        var success = service.validateCredentials(request.getPassword(), user.getPassword());
        if (success) {
            var token = tokenService.generate(String.valueOf(user.getId()),365);
            Map<String, Object> tokenMap = new HashMap<>();
            tokenMap.put("access_token", token);
            tokenMap.put("first_name", user.getFirstName());
            tokenMap.put("role", user.getRole());
            tokenMap.put("expires_in", 360000000);
            return ResponseEntity.ok(tokenMap);
        } else {
            throw new ApplicationException(ErrorCode.BAD_CRED, "Invalid credentials");
        }
    }
}
