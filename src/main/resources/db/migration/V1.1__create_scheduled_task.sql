CREATE TABLE scheduled_task (
    id              BIGINT        NOT NULL AUTO_INCREMENT,
    task_id         BIGINT        NOT NULL,
    name            VARCHAR(255)  NOT NULL,
    description     TEXT          NULL,
    cron            VARCHAR(100)  NOT NULL,
    request_params  JSON          NULL,
    request_body    JSON          NULL,
    internal        BOOLEAN       NOT NULL DEFAULT FALSE,
    created_at      TIMESTAMP(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at      TIMESTAMP(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    deleted         TIMESTAMP(6)  NULL,

    PRIMARY KEY (id),
    UNIQUE KEY uk_scheduled_task_name (name),
    KEY ix_scheduled_task_task_id (task_id),
    KEY ix_scheduled_task_deleted (deleted),
    CONSTRAINT fk_scheduled_task_task FOREIGN KEY (task_id) REFERENCES task (id)
);