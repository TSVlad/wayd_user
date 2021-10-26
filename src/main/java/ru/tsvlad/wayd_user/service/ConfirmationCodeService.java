package ru.tsvlad.wayd_user.service;

import ru.tsvlad.wayd_user.restapi.dto.ConfirmationCodeDTO;

public interface ConfirmationCodeService {
    void createConfirmationCodeForEmail(String email);
    boolean confirm(ConfirmationCodeDTO codeDTO);
}
