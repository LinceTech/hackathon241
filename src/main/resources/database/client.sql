CREATE TABLE client
(
    id              INTEGER         NOT NULL IDENTITY(1,1),
    name            VARCHAR(100)    NOT NULL DEFAULT '',
    cpf             VARCHAR(11)     NOT NULL DEFAULT '',
    birth_date      DATE            NOT NULL,
    phone           VARCHAR(14)     NOT NULL DEFAULT '',
    email           VARCHAR(100)    NOT NULL DEFAULT '',
    cep             VARCHAR(9)      NOT NULL DEFAULT '',
    city            VARCHAR(100)    NOT NULL DEFAULT '',
    state           VARCHAR(2)      NOT NULL DEFAULT '',
    neighborhood    VARCHAR(100)    NOT NULL DEFAULT '',
    street          VARCHAR(100)    NOT NULL DEFAULT '',
    number          INTEGER         NOT NULL ,

    PRIMARY KEY (id)
)