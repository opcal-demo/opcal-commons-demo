CREATE TABLE user_tb
(
    `id`          bigint NOT NULL AUTO_INCREMENT,
    created_date  timestamp(0) DEFAULT NULL,
    modified_date timestamp(0) DEFAULT NULL,
    first_name    varchar(250) DEFAULT NULL,
    last_name     varchar(250) DEFAULT NULL,
    gender        varchar(6)   DEFAULT NULL,
    age           int          DEFAULT '0',
    PRIMARY KEY (`id`),
    KEY           `IDX_full_name` (`first_name`,`last_name`),
    KEY           `IDX_age` (`age`)
);