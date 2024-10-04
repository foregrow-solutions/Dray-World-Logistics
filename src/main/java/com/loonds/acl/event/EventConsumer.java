package com.loonds.acl.event;

import com.loonds.acl.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class EventConsumer {
    private final NotificationService notificationService;

    @Async
    @TransactionalEventListener
    public void handle(NotificationEvent event) {
        log.info("Handling Notification Event : {}", event);
        notificationService.createNotification(event.userId(), event.title(), event.message());
    }

}
