package com.loonds.acl.repository;

import com.loonds.acl.model.entity.Notification;
import com.loonds.acl.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, String> {
    Page<Notification> findAllByUser(User user, Pageable pageable);
}
