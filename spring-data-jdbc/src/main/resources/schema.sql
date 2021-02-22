CREATE TABLE person(
    id BIGINT NOT NULL AUTO_INCREMENT,
    version INT NOT NULL,
    firstname NVARCHAR(40) NOT NULL,
    lastname NVARCHAR(40) NOT NULL,
    birthdate DATE NOT NULL,
    CONSTRAINT personPK PRIMARY KEY (id ASC)
);

    CREATE TABLE address(
    person BIGINT NOT NULL,
    street NVARCHAR(40) NOT NULL,
    city NVARCHAR(40) NOT NULL
);

    CREATE TABLE nickname(
    person BIGINT NOT NULL,
    nickname NVARCHAR(40) NOT NULL
);