CREATE TABLE vehicles
(
    id                              INTEGER         NOT NULL IDENTITY(1,1),
    brand                           VARCHAR(100)    NOT NULL DEFAULT '',
    model                           VARCHAR(100)    NOT NULL DEFAULT '',
    plate                           VARCHAR(10)     NOT NULL DEFAULT '',
    color                           VARCHAR(30)     NOT NULL DEFAULT '',
    year_of_manufacture             INTEGER         NOT NULL,
    daily_cost                      DECIMAL         NOT NULL,
    promotion_description           VARCHAR(500)    NOT NULL DEFAULT '',
    type_fuel                       VARCHAR(100)    NOT NULL DEFAULT '',

    PRIMARY KEY (id)
)

ALTER TABLE vehicles ADD type_vehicle varchar(20);