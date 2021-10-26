package ru.tsvlad.wayd_user.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsvlad.wayd_user.entity.ConfirmationCodeEntity;
import ru.tsvlad.wayd_user.messaging.producer.UserServiceProducer;
import ru.tsvlad.wayd_user.repo.ConfirmationCodeRepository;
import ru.tsvlad.wayd_user.restapi.dto.ConfirmationCodeDTO;
import ru.tsvlad.wayd_user.service.ConfirmationCodeService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ConfirmationCodeServiceImpl implements ConfirmationCodeService {
    @Value("${wayd.confirmation-code.duration}")
    private int confirmationCodeDuration;
    @Value("${wayd.confirmation-code.length}")
    private int confirmationCodeLength;
    private final Random random = new Random();

    private final ConfirmationCodeRepository confirmationCodeRepository;
    private final UserServiceProducer userServiceProducer;

    @Override
    @Transactional
    public void createConfirmationCodeForEmail(String email) {
        ConfirmationCodeEntity codeEntity = generateCodeEntity(email);
        codeEntity = confirmationCodeRepository.save(codeEntity);
        userServiceProducer.createConfirmationCode(codeEntity);
    }

    @Override
    public boolean confirm(ConfirmationCodeDTO codeDTO) {
        boolean success = confirmationCodeRepository.findAllByEmailAndExpirationAfter(codeDTO.getEmail(),
                LocalDateTime.now()).stream().anyMatch(codeEntity -> codeEntity.getCode().equals(codeDTO.getCode()));
        if (!success) {
            confirmationCodeRepository.deleteAllByEmail(codeDTO.getEmail());
            createConfirmationCodeForEmail(codeDTO.getEmail());
        }
        return success;
    }

    private ConfirmationCodeEntity generateCodeEntity(String email) {
        ConfirmationCodeEntity result = new ConfirmationCodeEntity();
        result.setEmail(email);
        result.setCode(generateCode());
        result.setExpiration(LocalDateTime.now().plus(confirmationCodeDuration, ChronoUnit.MINUTES));
        return result;
    }

    private String generateCode() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < confirmationCodeLength; i++) {
            result.append(random.nextInt(10));
        }
        return result.toString();
    }
}
