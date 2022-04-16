package org.example.store.service;

import javax.mail.MessagingException;

public interface SendMailService {
    boolean sendEmail(String to,String code) throws MessagingException;
}
