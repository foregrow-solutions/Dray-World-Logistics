package com.loonds.acl.service.impl;

import com.loonds.acl.model.dto.NotificationDto;
import com.loonds.acl.model.entity.Notification;
import com.loonds.acl.model.entity.User;
import com.loonds.acl.repository.NotificationRepository;
import com.loonds.acl.repository.UserRepository;
import com.loonds.acl.service.NotificationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;


    @Override
    @Transactional
    public Page<NotificationDto> getNotification(String userId, Pageable pageable) {
        User user = userRepository.getReferenceById(userId);
        return notificationRepository.findAllByUser(user, pageable).map(NotificationDto::of);
    }

    @Override
    @Transactional
    public void createNotification(String userId, String title, String message) {
        Notification notification = new Notification();
        notification.setUser(userRepository.getReferenceById(userId));
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setIsOpen(false);
        notification.setCreatedDate(Instant.now());
        notification.setModifiedDate(Instant.now());
        Notification save = notificationRepository.save(notification);
        NotificationDto.of(notification);
    }

    @Override
    @Transactional
    public Optional<NotificationDto> readNotification(String userId, String id, Boolean isOpen) {
        return notificationRepository.findById(id).map(notification -> {
            notification.setIsOpen(isOpen);
            return NotificationDto.of(notification);
        });
    }

    @Override
    @Transactional
    public Page<NotificationDto> readAllNotification(String userId, Boolean isOpen, Pageable pageable) {
        return null;
    }
}
