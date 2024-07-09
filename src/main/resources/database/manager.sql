CREATE TABLE MANAGER (
     ID                     INTEGER          NOT NULL IDENTITY (1, 1) ,
     NAME                   VARCHAR(100)    NOT NULL DEFAULT '',
     CPF                    VARCHAR(11)     NOT NULL DEFAULT '',
     PHONE                  VARCHAR(12)     NOT NULL DEFAULT '',
     EMAIL                  VARCHAR(100)    NOT NULL DEFAULT '',
     CITY                   VARCHAR(100)    NOT NULL DEFAULT '',
     STATE                  VARCHAR(100)    NOT NULL DEFAULT '',
     COMMISSION_PERCENTAGE  DECIMAL(14,2)   NULL ,
     HIRING_DATE            DATE            NOT NULL
     PRIMARY KEY (ID)
);

drop table MANAGER;

ALTER TABLE MANAGER ADD DATE_BIRTH DATE;