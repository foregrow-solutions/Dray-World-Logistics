package com.loonds.acl.model.dto;

import com.loonds.acl.model.entity.Notification;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class NotificationDto {
    String id;
    String title;
    String message;
    Boolean isOpen;
    Instant createdDate;

    public static NotificationDto of(Notification notification){
        NotificationDto output = new NotificationDto();
        output.setId(notification.getId());
        output.setTitle(notification.getTitle());
        output.setMessage(notification.getMessage());
        output.setIsOpen(notification.getIsOpen());
        output.setCreatedDate(notification.getCreatedDate());
        return output;
    }
}
