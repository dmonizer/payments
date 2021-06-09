CREATE TABLE payments
(
    ID           INT AUTO_INCREMENT PRIMARY KEY,
    paymentType  VARCHAR(10),
    amount       NUMBER,
    debtorIban   VARCHAR(100), -- IRL would create index for this
    creditorIban VARCHAR(100),
    currency     VARCHAR(10),
    bic          VARCHAR(100),
    details      VARCHAR(255),
    cancelled    BOOLEAN default false,
    created_by   VARCHAR(100),
    created_at   TIMESTAMP,
    updated_by   VARCHAR(100),
    updated_at   TIMESTAMP
);

CREATE TABLE fees
(
    ID        INT AUTO_INCREMENT PRIMARY KEY,
    currency  VARCHAR(10),
    amount    NUMBER,
    paymentId NUMBER
);


