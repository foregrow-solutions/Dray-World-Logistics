package com.loonds.acl.web;

import com.loonds.acl.model.dto.ChannelDto;
import com.loonds.acl.model.enums.Status;
import com.loonds.acl.model.payload.CreateLoadPayload;
import com.loonds.acl.security.AuthenticatedUser;
import com.loonds.acl.security.CurrentUser;
import com.loonds.acl.service.ChannelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Tag(name = "Channel APIs", description = "API for manage Load Operations")
@Slf4j
@RestController
@RequiredArgsConstructor
public class ChannelController {
    private final ChannelService channelService;

    @RequestMapping(value = "/loads-view", method = RequestMethod.GET)
    public ModelAndView dashboard(@CurrentUser AuthenticatedUser authenticatedUser) {
        ModelAndView vi = new ModelAndView();
        vi.setViewName("loads/loads");
        return vi;
    }

    @RequestMapping(value = "/loads-request-view", method = RequestMethod.GET)
    public ModelAndView loadApproveRequest(@CurrentUser AuthenticatedUser authenticatedUser) {
        ModelAndView vi = new ModelAndView();
        vi.setViewName("loads/load-request");
        return vi;
    }

    @Operation(description = "Create New Load")
    @PostMapping("/loads")
    public ChannelDto create(@RequestBody CreateLoadPayload payload,
                             @CurrentUser AuthenticatedUser authenticatedUser) {
        log.info("Load Payload : {}", payload);
        return channelService.create(authenticatedUser.getUsername(), payload);
    }

    @Operation(description = "Update Given Load Details")
    @PutMapping("/loads/{id}")
    public Optional<ChannelDto> update(@PathVariable String id,
                                       @RequestBody ChannelDto channelDto,
                                       @CurrentUser AuthenticatedUser authenticatedUser) {
        return channelService.update(authenticatedUser.getUsername(), id, channelDto);
    }

    @Operation(description = "Get Load Details")
    @GetMapping("/loads/{id}")
    public Optional<ChannelDto> getLoad(@PathVariable String id,
                                        @CurrentUser AuthenticatedUser authenticatedUser) {
        return channelService.get(id);
    }

    @Deprecated
    @Operation(description = "Update given channel status")
    @PutMapping("/loads/{id}/{status}")
    public Optional<ChannelDto> updateChannelStatus(@PathVariable String id,
                                                    @PathVariable Status status) {
        return channelService.updateStatus(id, status);
    }

    @Operation(description = "Get List of All Loads")
    @GetMapping("/loads")
    List<ChannelDto> getAllLoad(@PageableDefault Pageable pageable,
                                @CurrentUser AuthenticatedUser authenticatedUser) {
        return channelService.getAllUserLoad(authenticatedUser.getUsername());
    }

    @Operation(description = "Get List of All Pending Loads")
    @GetMapping("/admin/pending-loads")
    Page<ChannelDto> getLoadByStatus(@PageableDefault Pageable pageable,
                                     @CurrentUser AuthenticatedUser authenticatedUser) {
        return channelService.getLoadByStatus(Status.PENDING, pageable);
    }

    @Operation(description = "Get All Loads")
    @GetMapping("/admin/loads")
    List<ChannelDto> getAllUserLoad(@CurrentUser AuthenticatedUser authenticatedUser) {
        return channelService.getAllLoad();
    }

    @Operation(description = "Delete Given Channel")
    @DeleteMapping("/admin/channels/{id}/")
    void delete(@PathVariable String id){
        channelService.delete(id);
    }
    @Operation(description = "Delete All Channel")
    @DeleteMapping("/admin/channels")
    void deleteAll(){
        channelService.deleteAll();
    }
}
