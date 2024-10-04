package com.loonds.acl.web;

import com.loonds.acl.model.dto.NoteDto;
import com.loonds.acl.service.NoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Note APIs", description = "API for manage Notes Operations")
@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin
public class NoteController {
    private final NoteService noteService;

    @Operation(summary = "Added New Note for Given Channel")
    @PostMapping("/channels/{channelId}/note")
    NoteDto add(@PathVariable String channelId,
                @RequestBody NoteDto noteDto) {
        return noteService.add(channelId, noteDto);
    }

    @Operation(summary = "Get List of All Notes for given Channel")
    @GetMapping("/channel/{channelId}/notes")
    List<NoteDto> getAll(@PathVariable String channelId) {
        return noteService.readAll(channelId);
    }

    @Operation(summary = "Get Notes Details for given Customer")
    @GetMapping("/customers/{customerId}/note")
    Optional<NoteDto> getNote(@PathVariable int customerId) {
        return noteService.read(customerId);
    }

    @Operation(summary = "Delete Given Notes")
    @DeleteMapping("/notes/{id}")
    void delete(@PathVariable long id){
        noteService.delete(id);
    }
}
