package ru.tsvlad.wayd_user.enums;

public enum UserStatus {
    NOT_APPROVED_EMAIL,
    NOT_APPROVED_BY_MODERATOR,//for organizations
    INVALID,
    ACTIVE,
    BANNED,
}
