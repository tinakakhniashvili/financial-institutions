-- MySQL dump 10.13  Distrib 9.2.0, for macos14.7 (arm64)
--
-- Host: 127.0.0.1    Database: financial_institutions
-- ------------------------------------------------------
-- Server version	9.2.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account` (
  `ID` bigint unsigned NOT NULL AUTO_INCREMENT,
  `CUSTOMER_ID` bigint unsigned NOT NULL,
  `BRANCH_ID` bigint unsigned NOT NULL,
  `ACCOUNT_TYPE_ID` bigint unsigned NOT NULL,
  `IBAN` varchar(34) NOT NULL,
  `BALANCE` decimal(18,2) NOT NULL DEFAULT '0.00',
  `OPENED_ON` date DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `IBAN` (`IBAN`),
  UNIQUE KEY `ID` (`ID`),
  KEY `FK_ACCOUNT_BRANCH` (`BRANCH_ID`),
  KEY `FK_ACCOUNT_CUSTOMER` (`CUSTOMER_ID`),
  KEY `FK_ACCOUNT_TYPE` (`ACCOUNT_TYPE_ID`),
  CONSTRAINT `FK_ACCOUNT_BRANCH` FOREIGN KEY (`BRANCH_ID`) REFERENCES `branch` (`ID`),
  CONSTRAINT `FK_ACCOUNT_CUSTOMER` FOREIGN KEY (`CUSTOMER_ID`) REFERENCES `customer` (`ID`),
  CONSTRAINT `FK_ACCOUNT_TYPE` FOREIGN KEY (`ACCOUNT_TYPE_ID`) REFERENCES `account_type` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `account_transaction`
--

DROP TABLE IF EXISTS `account_transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account_transaction` (
  `ID` bigint unsigned NOT NULL AUTO_INCREMENT,
  `ACCOUNT_ID` bigint unsigned NOT NULL,
  `TRANSACTION_TYPE_ID` bigint unsigned NOT NULL,
  `AMOUNT` decimal(18,2) NOT NULL,
  `HAPPENED_AT` datetime NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID` (`ID`),
  KEY `idx_tx_account_time` (`ACCOUNT_ID`,`HAPPENED_AT`),
  KEY `FK_TX_TYPE` (`TRANSACTION_TYPE_ID`),
  CONSTRAINT `FK_TX_ACCOUNT` FOREIGN KEY (`ACCOUNT_ID`) REFERENCES `account` (`ID`),
  CONSTRAINT `FK_TX_TYPE` FOREIGN KEY (`TRANSACTION_TYPE_ID`) REFERENCES `transaction_type` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account_transaction`
--

LOCK TABLES `account_transaction` WRITE;
/*!40000 ALTER TABLE `account_transaction` DISABLE KEYS */;
/*!40000 ALTER TABLE `account_transaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `account_type`
--

DROP TABLE IF EXISTS `account_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account_type` (
  `ID` bigint unsigned NOT NULL AUTO_INCREMENT,
  `CODE` varchar(20) NOT NULL,
  `NAME` varchar(80) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `CODE` (`CODE`),
  UNIQUE KEY `ID` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account_type`
--

LOCK TABLES `account_type` WRITE;
/*!40000 ALTER TABLE `account_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `account_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `address` (
  `ID` bigint unsigned NOT NULL AUTO_INCREMENT,
  `COUNTRY` varchar(100) NOT NULL,
  `CITY` varchar(120) NOT NULL,
  `LINE1` varchar(150) NOT NULL,
  `ZIP` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bank`
--

DROP TABLE IF EXISTS `bank`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bank` (
  `ID` bigint unsigned NOT NULL AUTO_INCREMENT,
  `NAME` varchar(150) NOT NULL,
  `ACTIVE` tinyint(1) NOT NULL DEFAULT '1',
  `FINANCIAL_NETWORK_ID` bigint unsigned DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID` (`ID`),
  KEY `fk_bank_network` (`FINANCIAL_NETWORK_ID`),
  CONSTRAINT `fk_bank_network` FOREIGN KEY (`FINANCIAL_NETWORK_ID`) REFERENCES `financial_network` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bank`
--

LOCK TABLES `bank` WRITE;
/*!40000 ALTER TABLE `bank` DISABLE KEYS */;
/*!40000 ALTER TABLE `bank` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `branch`
--

DROP TABLE IF EXISTS `branch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `branch` (
  `ID` bigint unsigned NOT NULL AUTO_INCREMENT,
  `BANK_ID` bigint unsigned NOT NULL,
  `ADDRESS_ID` bigint unsigned NOT NULL,
  `CODE` varchar(45) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `BANK_ID` (`BANK_ID`,`CODE`),
  UNIQUE KEY `ID` (`ID`),
  KEY `FK_BRANCH_ADDRESS` (`ADDRESS_ID`),
  CONSTRAINT `FK_BRANCH_ADDRESS` FOREIGN KEY (`ADDRESS_ID`) REFERENCES `address` (`ID`),
  CONSTRAINT `FK_BRANCH_BANK` FOREIGN KEY (`BANK_ID`) REFERENCES `bank` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `branch`
--

LOCK TABLES `branch` WRITE;
/*!40000 ALTER TABLE `branch` DISABLE KEYS */;
/*!40000 ALTER TABLE `branch` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `card`
--

DROP TABLE IF EXISTS `card`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `card` (
  `ID` bigint unsigned NOT NULL AUTO_INCREMENT,
  `ACCOUNT_ID` bigint unsigned NOT NULL,
  `PAN_MASKED` varchar(25) NOT NULL,
  `EXPIRY` date DEFAULT NULL,
  `CONTACTLESS` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID` (`ID`),
  KEY `FK_CARD_ACCOUNT` (`ACCOUNT_ID`),
  CONSTRAINT `FK_CARD_ACCOUNT` FOREIGN KEY (`ACCOUNT_ID`) REFERENCES `account` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `card`
--

LOCK TABLES `card` WRITE;
/*!40000 ALTER TABLE `card` DISABLE KEYS */;
/*!40000 ALTER TABLE `card` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `ID` bigint unsigned NOT NULL AUTO_INCREMENT,
  `BANK_ID` bigint unsigned NOT NULL,
  `FULL_NAME` varchar(160) NOT NULL,
  `BIRTH_DATE` date DEFAULT NULL,
  `ADDRESS_ID` bigint unsigned NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID` (`ID`),
  KEY `FK_CUSTOMER_ADDRESS` (`ADDRESS_ID`),
  KEY `FK_CUSTOMER_BANK` (`BANK_ID`),
  CONSTRAINT `FK_CUSTOMER_ADDRESS` FOREIGN KEY (`ADDRESS_ID`) REFERENCES `address` (`ID`),
  CONSTRAINT `FK_CUSTOMER_BANK` FOREIGN KEY (`BANK_ID`) REFERENCES `bank` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee` (
  `ID` bigint unsigned NOT NULL AUTO_INCREMENT,
  `BRANCH_ID` bigint unsigned NOT NULL,
  `FULL_NAME` varchar(160) NOT NULL,
  `HIRED_DATE` date DEFAULT NULL,
  `MANAGER` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID` (`ID`),
  KEY `FK_EMPLOYEE_BRANCH` (`BRANCH_ID`),
  CONSTRAINT `FK_EMPLOYEE_BRANCH` FOREIGN KEY (`BRANCH_ID`) REFERENCES `branch` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `financial_network`
--

DROP TABLE IF EXISTS `financial_network`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `financial_network` (
  `ID` bigint unsigned NOT NULL AUTO_INCREMENT,
  `NAME` varchar(100) NOT NULL,
  `GENERATED_ON` date DEFAULT NULL,
  `LAST_UPDATED` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `financial_network`
--

LOCK TABLES `financial_network` WRITE;
/*!40000 ALTER TABLE `financial_network` DISABLE KEYS */;
/*!40000 ALTER TABLE `financial_network` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loan`
--

DROP TABLE IF EXISTS `loan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `loan` (
  `ID` bigint unsigned NOT NULL AUTO_INCREMENT,
  `CUSTOMER_ID` bigint unsigned NOT NULL,
  `BRANCH_ID` bigint unsigned NOT NULL,
  `PRINCIPAL` decimal(18,2) NOT NULL,
  `RATE` decimal(7,4) NOT NULL,
  `START_DATE` date DEFAULT NULL,
  `END_DATE` date DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID` (`ID`),
  KEY `FK_LOAN_BRANCH` (`BRANCH_ID`),
  KEY `FK_LOAN_CUSTOMER` (`CUSTOMER_ID`),
  CONSTRAINT `FK_LOAN_BRANCH` FOREIGN KEY (`BRANCH_ID`) REFERENCES `branch` (`ID`),
  CONSTRAINT `FK_LOAN_CUSTOMER` FOREIGN KEY (`CUSTOMER_ID`) REFERENCES `customer` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loan`
--

LOCK TABLES `loan` WRITE;
/*!40000 ALTER TABLE `loan` DISABLE KEYS */;
/*!40000 ALTER TABLE `loan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaction_type`
--

DROP TABLE IF EXISTS `transaction_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transaction_type` (
  `ID` bigint unsigned NOT NULL AUTO_INCREMENT,
  `CODE` varchar(20) NOT NULL,
  `NAME` varchar(80) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `CODE` (`CODE`),
  UNIQUE KEY `ID` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction_type`
--

LOCK TABLES `transaction_type` WRITE;
/*!40000 ALTER TABLE `transaction_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `transaction_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'financial_institutions'
--

--
-- Dumping routines for database 'financial_institutions'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-11-06  3:25:01_ DO not change comments or so on, do not add comments also, just add queries for hw

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
