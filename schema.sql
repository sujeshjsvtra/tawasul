CREATE TABLE User
(
    id    INT          NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name  VARCHAR(100)  NOT NULL,
    email VARCHAR(100) NOT NULL,
    role  VARCHAR(50),
    created_at   TIMESTAMP,
    status VARCHAR(2),

);

CREATE TABLE Sector
(
    id    INT          NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    sector_id   INT         NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    sector_name VARCHAR(100) NOT NULL,
    sector_name_arabic VARCHAR(100) NOT NULL,
    created_at   TIMESTAMP,
    status VARCHAR(2),
    user_id INT
);

CREATE TABLE Consultation
(
    id    INT          NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    role VARCHAR(50) NOT NULL,
    sector_id VARCHAR(50) NOT NULL,
    created_at   TIMESTAMP,
    status VARCHAR(2),
);
