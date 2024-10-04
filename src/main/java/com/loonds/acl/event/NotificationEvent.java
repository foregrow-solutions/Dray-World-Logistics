package com.loonds.acl.event;

public record NotificationEvent(String userId, String title, String message) {
}
