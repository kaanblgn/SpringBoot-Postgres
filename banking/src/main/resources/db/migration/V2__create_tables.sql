CREATE TABLE IF NOT EXISTS bank
(
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    date_created TIMESTAMP NOT NULL,
    date_updated TIMESTAMP
);

CREATE TABLE IF NOT EXISTS users(
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    phone_number VARCHAR(20) NOT NULL UNIQUE,
    address VARCHAR(255),
    date_created TIMESTAMP NOT NULL,
    date_updated TIMESTAMP,
    identity_type VARCHAR(4) NOT NULL,
    identity_no VARCHAR(11) NOT NULL,
    CONSTRAINT unique_identity_type_no_combination UNIQUE (identity_type, identity_no)
);

CREATE TABLE IF NOT EXISTS account
(
    id UUID PRIMARY KEY,
    balance DECIMAL(11, 2) DEFAULT 0.00,
    date_created TIMESTAMP NOT NULL,
    date_updated TIMESTAMP,
    account_type VARCHAR(20) DEFAULT 'CHECKING',
    account_status VARCHAR(20) DEFAULT 'ACTIVE',
    users_id UUID NOT NULL,
    bank_id UUID NOT NULL,
    CONSTRAINT unique_user_bank_combination UNIQUE (users_id, bank_id),
    FOREIGN KEY (users_id) REFERENCES banking.users(id),
    FOREIGN KEY (bank_id) REFERENCES banking.bank(id)
);

CREATE TABLE IF NOT EXISTS account_transaction
(
    id UUID PRIMARY KEY,
    transaction_type VARCHAR(20) NOT NULL,
    date TIMESTAMP NOT NULL,
    amount DECIMAL(11, 2) NOT NULL,
    account_id UUID NOT NULL,
    FOREIGN KEY (account_id) REFERENCES banking.account(id)
);