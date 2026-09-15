package com.vaibhavbansode.connectionService.auth;

public class AuthContextHolder {

    private static final ThreadLocal<Long> currentUserId = new ThreadLocal<>();

    public static Long getCurrentUserId() {
        return currentUserId.get();
    }
    static void setCurrentUserId(Long currentUserId) {
        AuthContextHolder.currentUserId.set(currentUserId);
    }
    static void clearUserId() {
        AuthContextHolder.currentUserId.remove();
    }
}
