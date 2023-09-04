-- Populate Bank
INSERT INTO bank (id, name, date_created) VALUES ('2bc3cd8e-4dae-430f-8473-ff7023fe4af1', 'Granti', current_timestamp);
INSERT INTO bank (id, name, date_created) VALUES ('417659a4-ecfa-4b40-97cc-7b8b3cf182d4', 'Epara', current_timestamp);
INSERT INTO bank (id, name, date_created) VALUES ('6e6144e5-16f3-4164-a8e9-1fd5c6dd11f4', 'Gin', current_timestamp);
INSERT INTO bank (id, name, date_created) VALUES ('239dc25f-6ead-4216-bd2b-6d992aa42c3b', 'Vkf', current_timestamp);


-- Populate Users
INSERT INTO users (id, name, email, password, phone_number, address, identity_type, identity_no, date_created) VALUES ('b6189152-b981-4a6f-a7dd-0c180657a62e', 'John Doe', 'john.doe@gmail.com', 'mysecretpassword', '5551234567', '123 Main St', 'VKN', '12345678901', current_timestamp);
INSERT INTO users (id, name, email, password, phone_number, address, identity_type, identity_no, date_created) VALUES ('50eb3070-e0e3-4e1b-bfbf-1732eb063274', 'Jane Smith', 'jane.smith@outlook.com', 'anotherpassword', '5559876543', '456 Oak St', 'TCKN', '98765432109', current_timestamp);
INSERT INTO users (id, name, email, password, phone_number, address, identity_type, identity_no, date_created) VALUES ('a4d541ef-6cbd-401c-bd2a-b3fb7e54e976', 'Michael Johnson', 'michael.johnson@gmail.com', 'securepass', '5555555555', '789 Elm St', 'TCKN', '56789012345', current_timestamp);
INSERT INTO users (id, name, email, password, phone_number, address, identity_type, identity_no, date_created) VALUES ('56787bdc-80d5-4e1a-8577-c100477ab4a8', 'Emily Brown', 'emily.brown@outlook.com', 'strongpassword', '5552223333', '101 Pine St', 'VKN', '09876543210', current_timestamp);

-- Populate Account
INSERT INTO account (id, balance, date_created, users_id, bank_id) VALUES ('43f6e3c0-8391-4525-b6d1-7800bd5166e6', 3500.00, current_timestamp, 'b6189152-b981-4a6f-a7dd-0c180657a62e', '2bc3cd8e-4dae-430f-8473-ff7023fe4af1');
INSERT INTO account (id, balance, date_created, users_id, bank_id) VALUES ('1dd798a1-eb91-4ff7-a02b-4f1e1d9cfee8', 2000.00, current_timestamp, '50eb3070-e0e3-4e1b-bfbf-1732eb063274', '417659a4-ecfa-4b40-97cc-7b8b3cf182d4');
INSERT INTO account (id, balance, date_created, users_id, bank_id) VALUES ('d7e24710-6b41-4e25-9b49-f9a13aa46a46', 2500.00, current_timestamp,  'a4d541ef-6cbd-401c-bd2a-b3fb7e54e976', '6e6144e5-16f3-4164-a8e9-1fd5c6dd11f4');
INSERT INTO account (id, balance, date_created, users_id, bank_id) VALUES ('62a77c2c-29cb-4ca9-afdc-8e64f741d9ba', 1500.00, current_timestamp, '56787bdc-80d5-4e1a-8577-c100477ab4a8', '239dc25f-6ead-4216-bd2b-6d992aa42c3b');

-- Populate Account Transactions
INSERT INTO account_transaction (id, transaction_type, date, amount, account_id) VALUES ('1d3fc028-0c91-46ee-b0c9-e1fb55b2664e', 'DEPOSIT', current_timestamp, 1000.00, '43f6e3c0-8391-4525-b6d1-7800bd5166e6');
INSERT INTO account_transaction (id, transaction_type, date, amount, account_id) VALUES ('c77fb11c-32fb-46c9-88c5-43eea9b7b80b', 'WITHDRAW', current_timestamp, 500.00, '1dd798a1-eb91-4ff7-a02b-4f1e1d9cfee8');
INSERT INTO account_transaction (id, transaction_type, date, amount, account_id) VALUES ('f7e87571-8e1f-43ea-9031-2691b43c793f', 'DEPOSIT', current_timestamp, 2000.00, 'd7e24710-6b41-4e25-9b49-f9a13aa46a46');
INSERT INTO account_transaction (id, transaction_type, date, amount, account_id) VALUES ('8c4eea5a-002b-4adc-acac-69178cf60811', 'WITHDRAW', current_timestamp, 750.00, '62a77c2c-29cb-4ca9-afdc-8e64f741d9ba');


