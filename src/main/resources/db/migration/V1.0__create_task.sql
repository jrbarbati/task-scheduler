CREATE TABLE task (
    id          BIGINT        NOT NULL AUTO_INCREMENT,
    code        VARCHAR(100)  NOT NULL,
    name        VARCHAR(255)  NOT NULL,
    description TEXT          NULL,
    url         VARCHAR(2048) NOT NULL,
    method      VARCHAR(10)   NOT NULL,
    created_at  TIMESTAMP(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at  TIMESTAMP(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    deleted     TIMESTAMP(6)  NULL,

    PRIMARY KEY (id),
    UNIQUE KEY uk_task_code (code),
    KEY ix_task_deleted (deleted)
);
