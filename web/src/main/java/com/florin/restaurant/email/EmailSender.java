package com.florin.restaurant.email;

public interface EmailSender {
    void sendEmail(String to, String content);
}
