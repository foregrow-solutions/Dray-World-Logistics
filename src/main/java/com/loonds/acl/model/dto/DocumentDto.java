package com.loonds.acl.model.dto;

import com.loonds.acl.model.entity.Document;

public record DocumentDto(long id, String label) {
    public static DocumentDto of(Document mdl) {
        if (mdl != null) {
            return new DocumentDto(mdl.getId(), mdl.getLabel());
        } else {
            return new DocumentDto(0, null);
        }
    }
}
