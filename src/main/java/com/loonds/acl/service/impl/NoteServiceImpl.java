package com.loonds.acl.service.impl;


import com.loonds.acl.model.dto.NoteDto;
import com.loonds.acl.model.entity.Note;
import com.loonds.acl.repository.ChannelRepository;
import com.loonds.acl.repository.NoteRepository;
import com.loonds.acl.repository.UserRepository;
import com.loonds.acl.service.NoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class NoteServiceImpl implements NoteService {
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;
    private final ChannelRepository channelRepository;

    @Override
    @Transactional
    public NoteDto add(String channelId, NoteDto noteDto) {
        Note note = new Note();
        note.setChannel(channelRepository.getReferenceById(channelId));
        note.setDescription(noteDto.getDescription());
        note.setCreatedAt(Instant.now());
        note.setModifiedDate(Instant.now());
        Note save = noteRepository.save(note);
        return NoteDto.of(save);
    }

    @Override
    @Transactional
    public Optional<NoteDto> update(long id, NoteDto noteDto) {
        return noteRepository.findById(id).map(note -> {
            note.setDescription(noteDto.getDescription());
            return NoteDto.of(note);
        });
    }

    @Override
    public Optional<NoteDto> read(long id) {
        return noteRepository.findById(id).map(NoteDto::of);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NoteDto> readAll(String channelId) {
        return noteRepository.findAllByChannel(channelRepository.getReferenceById(channelId)).stream().map(NoteDto::of).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(long id) {
        if(noteRepository.existsById(id)){
            noteRepository.deleteById(id);
        }
    }

//    @Override
//    @Transactional
//    public Optional<NoteDto> update(int id, NoteDto noteDto) {
//        channelRepository.getReferenceById(c)
//        var result = noteRepositroy.findByCustomer(customer).map(note -> {
//            note.setDescription(noteDto.getDescription());
//            note.setModifiedDate(Instant.now());
//            return noteRepo.save(note);
//        }).orElseGet(() -> {
//            Note note = new Note();
//            note.setCustomer(customer);
//            note.setDescription(noteDto.getDescription());
//            note.setCreatedAt(Instant.now());
//            return noteRepo.save(note);
//        });
//        return Optional.of(NoteDto.of(result));
//    }


}
