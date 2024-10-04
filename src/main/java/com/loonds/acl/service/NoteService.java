package com.loonds.acl.service;


import com.loonds.acl.model.dto.NoteDto;

import java.util.List;
import java.util.Optional;

public interface NoteService {
    NoteDto add(String channelId, NoteDto noteDto);
    Optional<NoteDto> update(long id, NoteDto noteDto);
    Optional<NoteDto> read(long id);

    List<NoteDto> readAll(String channelId);

    void delete(long id);
}
