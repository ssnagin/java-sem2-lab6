package com.ssnagin.collectionmanager.session;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@EqualsAndHashCode
@Getter
class SessionInfo {
    LocalDateTime lastActivity;
    Long userId;

    SessionInfo(Long userId) {
        this.userId = userId;
        this.lastActivity = LocalDateTime.now();
    }

    void updateActivity() {
        this.lastActivity = LocalDateTime.now();
    }

    boolean isExpired() {
        return LocalDateTime.now().isAfter(lastActivity.plusMinutes(20));
    }
}