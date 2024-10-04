package com.loonds.acl.service;


import com.loonds.acl.model.dto.NotificationDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface NotificationService {
    Page<NotificationDto> getNotification(String userId, Pageable pageable);

    void createNotification(String userId, String title, String message);

    Optional<NotificationDto> readNotification(String userId, String id, Boolean isOpen);

    Page<NotificationDto> readAllNotification(String userId, Boolean isOpen, Pageable pageable);
}
