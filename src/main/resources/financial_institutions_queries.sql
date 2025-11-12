INSERT INTO financial_network (NAME, GENERATED_ON, LAST_UPDATED) VALUES
  ('Global Banking Network', '2025-01-01', '2025-01-02 10:00:00'),
  ('Retail Banking Network', '2025-02-01', '2025-02-02 11:30:00');

INSERT INTO address (COUNTRY, CITY, LINE1, ZIP) VALUES
  ('Georgia', 'Tbilisi', 'Rustaveli Ave 1', '0108'),
  ('Georgia', 'Batumi', 'Chavchavadze St 5', '6000'),
  ('Germany', 'Berlin', 'Unter den Linden 10', '10117');

INSERT INTO bank (NAME, ACTIVE, FINANCIAL_NETWORK_ID) VALUES
  ('IGUO Bank', 1, (SELECT ID FROM financial_network WHERE NAME = 'Global Banking Network')),
  ('Caucasus Bank', 1, (SELECT ID FROM financial_network WHERE NAME = 'Retail Banking Network'));

INSERT INTO branch (BANK_ID, ADDRESS_ID, CODE) VALUES
  (
    (SELECT ID FROM bank WHERE NAME = 'IGUO Bank'),
    (SELECT ID FROM address WHERE CITY = 'Tbilisi'),
    'TBS-001'
  ),
  (
    (SELECT ID FROM bank WHERE NAME = 'Caucasus Bank'),
    (SELECT ID FROM address WHERE CITY = 'Berlin'),
    'BER-001'
  );

INSERT INTO customer (BANK_ID, FULL_NAME, BIRTH_DATE, ADDRESS_ID) VALUES
  (
    (SELECT ID FROM bank WHERE NAME = 'IGUO Bank'),
    'Gocha Kakhniashvili',
    '1980-01-15',
    (SELECT ID FROM address WHERE CITY = 'Tbilisi')
  ),
  (
    (SELECT ID FROM bank WHERE NAME = 'IGUO Bank'),
    'Tina Kakhniashvili',
    '1985-05-20',
    (SELECT ID FROM address WHERE CITY = 'Tbilisi')
  ),
  (
    (SELECT ID FROM bank WHERE NAME = 'Caucasus Bank'),
    'Hans Müller',
    '1975-09-09',
    (SELECT ID FROM address WHERE CITY = 'Berlin')
  );

INSERT INTO employee (BRANCH_ID, FULL_NAME, HIRED_DATE, MANAGER) VALUES
  (
    (SELECT ID FROM branch WHERE CODE = 'TBS-001'),
    'Nika Manager',
    '2020-03-01',
    1
  ),
  (
    (SELECT ID FROM branch WHERE CODE = 'TBS-001'),
    'Lasha Teller',
    '2021-07-15',
    0
  ),
  (
    (SELECT ID FROM branch WHERE CODE = 'BER-001'),
    'Anna Advisor',
    '2019-11-20',
    0
  );

INSERT INTO account_type (CODE, NAME) VALUES
  ('CUR', 'Current Account'),
  ('SAV', 'Savings Account'),
  ('BUS', 'Business Account');

INSERT INTO transaction_type (CODE, NAME) VALUES
  ('DEP', 'Deposit'),
  ('WDR', 'Withdrawal'),
  ('FEE', 'Service Fee');

INSERT INTO account (CUSTOMER_ID, BRANCH_ID, ACCOUNT_TYPE_ID, IBAN, BALANCE, OPENED_ON) VALUES
  (
    (SELECT ID FROM customer WHERE FULL_NAME = 'Gocha Kakhniashvili'),
    (SELECT ID FROM branch WHERE CODE = 'TBS-001'),
    (SELECT ID FROM account_type WHERE CODE = 'CUR'),
    'GE00IGUO0000000001',
    1000.00,
    '2024-01-10'
  ),
  (
    (SELECT ID FROM customer WHERE FULL_NAME = 'Tina Kakhniashvili'),
    (SELECT ID FROM branch WHERE CODE = 'TBS-001'),
    (SELECT ID FROM account_type WHERE CODE = 'SAV'),
    'GE00IGUO0000000002',
    5000.00,
    '2023-05-01'
  ),
  (
    (SELECT ID FROM customer WHERE FULL_NAME = 'Hans Müller'),
    (SELECT ID FROM branch WHERE CODE = 'BER-001'),
    (SELECT ID FROM account_type WHERE CODE = 'CUR'),
    'DE00CAUC0000000003',
    2000.00,
    '2022-09-15'
  );

INSERT INTO card (ACCOUNT_ID, PAN_MASKED, EXPIRY, CONTACTLESS) VALUES
  (
    (SELECT ID FROM account WHERE IBAN = 'GE00IGUO0000000001'),
    '4111 **** **** 1111',
    '2028-12-31',
    1
  ),
  (
    (SELECT ID FROM account WHERE IBAN = 'GE00IGUO0000000002'),
    '4222 **** **** 2222',
    '2027-06-30',
    1
  ),
  (
    (SELECT ID FROM account WHERE IBAN = 'DE00CAUC0000000003'),
    '4333 **** **** 3333',
    '2026-03-31',
    0
  );

INSERT INTO loan (CUSTOMER_ID, BRANCH_ID, PRINCIPAL, RATE, START_DATE, END_DATE) VALUES
  (
    (SELECT ID FROM customer WHERE FULL_NAME = 'Gocha Kakhniashvili'),
    (SELECT ID FROM branch WHERE CODE = 'TBS-001'),
    10000.00,
    0.0750,
    '2024-01-01',
    '2029-12-31'
  ),
  (
    (SELECT ID FROM customer WHERE FULL_NAME = 'Hans Müller'),
    (SELECT ID FROM branch WHERE CODE = 'BER-001'),
    5000.00,
    0.0650,
    '2023-06-01',
    '2028-05-31'
  );

INSERT INTO account_transaction (ACCOUNT_ID, TRANSACTION_TYPE_ID, AMOUNT, HAPPENED_AT) VALUES
  (
    (SELECT ID FROM account WHERE IBAN = 'GE00IGUO0000000001'),
    (SELECT ID FROM transaction_type WHERE CODE = 'DEP'),
    500.00,
    '2025-01-05 10:15:00'
  ),
  (
    (SELECT ID FROM account WHERE IBAN = 'GE00IGUO0000000001'),
    (SELECT ID FROM transaction_type WHERE CODE = 'WDR'),
    200.00,
    '2025-01-10 09:00:00'
  ),
  (
    (SELECT ID FROM account WHERE IBAN = 'GE00IGUO0000000002'),
    (SELECT ID FROM transaction_type WHERE CODE = 'DEP'),
    1500.00,
    '2025-02-01 14:20:00'
  ),
  (
    (SELECT ID FROM account WHERE IBAN = 'GE00IGUO0000000002'),
    (SELECT ID FROM transaction_type WHERE CODE = 'FEE'),
    2.50,
    '2025-02-02 08:00:00'
  ),
  (
    (SELECT ID FROM account WHERE IBAN = 'DE00CAUC0000000003'),
    (SELECT ID FROM transaction_type WHERE CODE = 'DEP'),
    800.00,
    '2025-03-01 16:45:00'
  ),
  (
    (SELECT ID FROM account WHERE IBAN = 'DE00CAUC0000000003'),
    (SELECT ID FROM transaction_type WHERE CODE = 'WDR'),
    300.00,
    '2025-03-05 11:30:00'
  );

UPDATE bank
SET ACTIVE = 0
WHERE NAME = 'Caucasus Bank';

UPDATE address
SET ZIP = '10118'
WHERE CITY = 'Berlin';

UPDATE employee
SET MANAGER = 1
WHERE FULL_NAME = 'Anna Advisor';

UPDATE account
SET BALANCE = BALANCE + 200.00
WHERE IBAN = 'GE00IGUO0000000002';

UPDATE account
SET BALANCE = BALANCE - 100.00
WHERE IBAN = 'DE00CAUC0000000003';

UPDATE customer
SET FULL_NAME = 'Gocha K.'
WHERE FULL_NAME = 'Gocha Kakhniashvili';

UPDATE transaction_type
SET NAME = 'Cash Deposit'
WHERE CODE = 'DEP';

UPDATE account_type
SET NAME = 'Standard Savings Account'
WHERE CODE = 'SAV';

UPDATE loan
SET RATE = 0.0700
WHERE PRINCIPAL >= 8000.00;

UPDATE financial_network
SET LAST_UPDATED = '2025-11-10 09:00:00'
WHERE NAME = 'Global Banking Network';

DELETE FROM account_transaction
WHERE TRANSACTION_TYPE_ID = (SELECT ID FROM transaction_type WHERE CODE = 'FEE')
  AND AMOUNT < 5.00;

DELETE FROM account_transaction
WHERE ACCOUNT_ID = (SELECT ID FROM account WHERE IBAN = 'DE00CAUC0000000003');

DELETE FROM card
WHERE ACCOUNT_ID = (SELECT ID FROM account WHERE IBAN = 'DE00CAUC0000000003');

DELETE FROM loan
WHERE CUSTOMER_ID = (SELECT ID FROM customer WHERE FULL_NAME = 'Hans Müller');

DELETE FROM account
WHERE CUSTOMER_ID = (SELECT ID FROM customer WHERE FULL_NAME = 'Hans Müller');

DELETE FROM customer
WHERE FULL_NAME = 'Hans Müller';

DELETE FROM employee
WHERE BRANCH_ID = (SELECT ID FROM branch WHERE CODE = 'BER-001');

DELETE FROM branch
WHERE CODE = 'BER-001';

DELETE FROM bank
WHERE NAME = 'Caucasus Bank';

DELETE FROM address
WHERE CITY = 'Batumi';

DELETE FROM financial_network
WHERE NAME = 'Retail Banking Network';

ALTER TABLE customer
ADD COLUMN EMAIL varchar(160) DEFAULT NULL;

ALTER TABLE bank
ADD COLUMN SWIFT_CODE varchar(11) DEFAULT NULL;

ALTER TABLE employee
ADD COLUMN POSITION_TITLE varchar(80) DEFAULT NULL;

ALTER TABLE loan
MODIFY RATE decimal(10,4) NOT NULL;

ALTER TABLE account_transaction
ADD COLUMN DESCRIPTION varchar(255) DEFAULT NULL;

SELECT
  fn.NAME AS network_name,
  b.NAME AS bank_name,
  br.CODE AS branch_code,
  addr_branch.CITY AS branch_city,
  c.FULL_NAME AS customer_name,
  addr_customer.CITY AS customer_city,
  a.IBAN,
  atp.CODE AS account_type_code,
  a.BALANCE,
  cd.PAN_MASKED,
  l.PRINCIPAL AS loan_principal,
  l.RATE AS loan_rate,
  tx.AMOUNT AS transaction_amount,
  tx.HAPPENED_AT,
  tt.CODE AS transaction_type_code,
  e.FULL_NAME AS employee_name
FROM financial_network fn
JOIN bank b ON b.FINANCIAL_NETWORK_ID = fn.ID
JOIN branch br ON br.BANK_ID = b.ID
JOIN address addr_branch ON addr_branch.ID = br.ADDRESS_ID
LEFT JOIN employee e ON e.BRANCH_ID = br.ID
JOIN customer c ON c.BANK_ID = b.ID
JOIN address addr_customer ON addr_customer.ID = c.ADDRESS_ID
LEFT JOIN account a ON a.CUSTOMER_ID = c.ID AND a.BRANCH_ID = br.ID
LEFT JOIN account_type atp ON atp.ID = a.ACCOUNT_TYPE_ID
LEFT JOIN card cd ON cd.ACCOUNT_ID = a.ID
LEFT JOIN loan l ON l.CUSTOMER_ID = c.ID AND l.BRANCH_ID = br.ID
LEFT JOIN account_transaction tx ON tx.ACCOUNT_ID = a.ID
LEFT JOIN transaction_type tt ON tt.ID = tx.TRANSACTION_TYPE_ID;

SELECT
  c.FULL_NAME,
  a.IBAN,
  a.BALANCE
FROM customer c
INNER JOIN account a ON a.CUSTOMER_ID = c.ID;

SELECT
  b.NAME AS bank_name,
  br.CODE AS branch_code
FROM bank b
LEFT JOIN branch br ON br.BANK_ID = b.ID;

SELECT
  e.FULL_NAME AS employee_name,
  br.CODE AS branch_code
FROM branch br
RIGHT JOIN employee e ON e.BRANCH_ID = br.ID;

SELECT
  br.CODE AS branch_code,
  SUM(l.PRINCIPAL) AS total_principal
FROM branch br
LEFT JOIN loan l ON l.BRANCH_ID = br.ID
GROUP BY br.ID, br.CODE;

SELECT
  a.IBAN,
  l.PRINCIPAL
FROM account a
LEFT JOIN loan l ON l.CUSTOMER_ID = a.CUSTOMER_ID
UNION
SELECT
  a.IBAN,
  l.PRINCIPAL
FROM account a
RIGHT JOIN loan l ON l.CUSTOMER_ID = a.CUSTOMER_ID;

SELECT
  c.FULL_NAME,
  COUNT(a.ID) AS account_count
FROM customer c
LEFT JOIN account a ON a.CUSTOMER_ID = c.ID
GROUP BY c.ID, c.FULL_NAME;

SELECT
  b.NAME AS bank_name,
  COUNT(DISTINCT br.ID) AS branch_count
FROM bank b
LEFT JOIN branch br ON br.BANK_ID = b.ID
GROUP BY b.ID, b.NAME;

SELECT
  atp.CODE AS account_type_code,
  AVG(a.BALANCE) AS avg_balance
FROM account_type atp
LEFT JOIN account a ON a.ACCOUNT_TYPE_ID = atp.ID
GROUP BY atp.ID, atp.CODE;

SELECT
  tt.CODE AS transaction_type_code,
  SUM(tx.AMOUNT) AS total_amount
FROM transaction_type tt
LEFT JOIN account_transaction tx ON tx.TRANSACTION_TYPE_ID = tt.ID
GROUP BY tt.ID, tt.CODE;

SELECT
  br.CODE AS branch_code,
  COUNT(DISTINCT e.ID) AS employee_count
FROM branch br
LEFT JOIN employee e ON e.BRANCH_ID = br.ID
GROUP BY br.ID, br.CODE;

SELECT
  c.FULL_NAME,
  SUM(l.PRINCIPAL) AS total_loan_principal
FROM customer c
LEFT JOIN loan l ON l.CUSTOMER_ID = c.ID
GROUP BY c.ID, c.FULL_NAME;

SELECT
  addr.CITY,
  COUNT(DISTINCT c.ID) AS customers_in_city
FROM address addr
LEFT JOIN customer c ON c.ADDRESS_ID = addr.ID
GROUP BY addr.ID, addr.CITY;

SELECT
  c.FULL_NAME,
  COUNT(a.ID) AS account_count
FROM customer c
LEFT JOIN account a ON a.CUSTOMER_ID = c.ID
GROUP BY c.ID, c.FULL_NAME
HAVING COUNT(a.ID) > 1;

SELECT
  br.CODE AS branch_code,
  SUM(l.PRINCIPAL) AS total_principal
FROM branch br
LEFT JOIN loan l ON l.BRANCH_ID = br.ID
GROUP BY br.ID, br.CODE
HAVING SUM(l.PRINCIPAL) IS NOT NULL AND SUM(l.PRINCIPAL) > 0;

SELECT
  atp.CODE AS account_type_code,
  AVG(a.BALANCE) AS avg_balance
FROM account_type atp
LEFT JOIN account a ON a.ACCOUNT_TYPE_ID = atp.ID
GROUP BY atp.ID, atp.CODE
HAVING AVG(a.BALANCE) > 1000.00;

SELECT
  tt.CODE AS transaction_type_code,
  SUM(tx.AMOUNT) AS total_amount
FROM transaction_type tt
LEFT JOIN account_transaction tx ON tx.TRANSACTION_TYPE_ID = tt.ID
GROUP BY tt.ID, tt.CODE
HAVING SUM(tx.AMOUNT) > 500.00;

SELECT
  b.NAME AS bank_name,
  COUNT(DISTINCT c.ID) AS customer_count
FROM bank b
LEFT JOIN customer c ON c.BANK_ID = b.ID
GROUP BY b.ID, b.NAME
HAVING COUNT(DISTINCT c.ID) >= 1;

SELECT
  addr.CITY,
  COUNT(DISTINCT a.ID) AS accounts_in_city
FROM address addr
LEFT JOIN customer c ON c.ADDRESS_ID = addr.ID
LEFT JOIN account a ON a.CUSTOMER_ID = c.ID
GROUP BY addr.ID, addr.CITY
HAVING COUNT(DISTINCT a.ID) > 0;

SELECT
  c.FULL_NAME,
  SUM(CASE WHEN tt.CODE = 'DEP' THEN tx.AMOUNT ELSE 0 END) AS total_deposits
FROM customer c
LEFT JOIN account a ON a.CUSTOMER_ID = c.ID
LEFT JOIN account_transaction tx ON tx.ACCOUNT_ID = a.ID
LEFT JOIN transaction_type tt ON tt.ID = tx.TRANSACTION_TYPE_ID
GROUP BY c.ID, c.FULL_NAME
HAVING SUM(CASE WHEN tt.CODE = 'DEP' THEN tx.AMOUNT ELSE 0 END) > 500.00;
