package com.loonds.acl.model.dto;

import com.loonds.acl.model.entity.Note;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoteDto {
    Long id;
    String description;

    public static NoteDto of(Note note){
        NoteDto output = new NoteDto();
        output.setId(note.getId());
        output.setDescription(note.getDescription());
        return output;
    }
}
