CREATE TABLE IF NOT EXISTS users (
  `id`         INT(6) UNSIGNED UNIQUE PRIMARY KEY AUTO_INCREMENT      NOT NULL,
  `name`       VARCHAR(64) UNIQUE                                     NOT NULL,
  `blocked`    BOOLEAN DEFAULT FALSE                                  NOT NULL,
  `money`      DECIMAL(13, 2) DEFAULT 0                               NOT NULL,
  `lastUpdate` BIGINT DEFAULT 0                                       NOT NULL
)
  ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS wallets (
  `id`         INT(6) UNSIGNED                                        NOT NULL,
  `money`      DECIMAL(13, 2) DEFAULT 0                               NOT NULL,
  `lastUpdate` BIGINT DEFAULT 0                                       NOT NULL
)
  ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS transactions (
  `id`        INT(6) UNSIGNED PRIMARY KEY AUTO_INCREMENT UNIQUE NOT NULL,
  `idFrom`    INT(6) UNSIGNED                                   NOT NULL,
  `idTo`      INT(6) UNSIGNED                                   NOT NULL,
  `blocked`   BOOLEAN DEFAULT FALSE                             NOT NULL,
  `money`     DECIMAL(13, 2)                                    NOT NULL,
  `timestamp` BIGINT                                            NOT NULL
)
  ENGINE = InnoDB;

DROP TABLE users;