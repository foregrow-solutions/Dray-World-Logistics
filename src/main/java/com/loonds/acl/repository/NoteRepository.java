package com.loonds.acl.repository;

import com.loonds.acl.model.entity.Channel;
import com.loonds.acl.model.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findAllByChannel(Channel channel);

    Optional<Note> findByChannel(Channel channel);
}
