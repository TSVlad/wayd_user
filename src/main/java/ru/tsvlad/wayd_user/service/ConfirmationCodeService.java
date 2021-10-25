package ru.tsvlad.wayd_user.service;

public interface ConfirmationCodeService {
    void createConfirmationCodeForEmail(String email);
}
