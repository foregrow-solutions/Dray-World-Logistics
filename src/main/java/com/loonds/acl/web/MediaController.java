package com.loonds.acl.web;

import com.loonds.acl.security.AuthenticatedUser;
import com.loonds.acl.security.CurrentUser;
import com.loonds.acl.service.MediaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Tag(name = "Upload APIs", description = "API for manage Enquiry Operations")
@Slf4j
@RestController
@RequiredArgsConstructor
public class MediaController {
    private final MediaService mediaService;

    @Operation(summary = "Upload Media Images, Banner")
    @PostMapping("/settings/upload")
    public String uploadMedia(@RequestParam("file") MultipartFile file,
                              @CurrentUser AuthenticatedUser user) throws IOException {
        return mediaService.upload(user.getUsername(), file);
    }

}
