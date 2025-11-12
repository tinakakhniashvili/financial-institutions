-- Schema for financial_institutions (structure only)

DROP SCHEMA IF EXISTS `financial_institutions`;
CREATE SCHEMA IF NOT EXISTS `financial_institutions`
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_0900_ai_ci;

USE `financial_institutions`;

-- =======================
--  Core reference tables
-- =======================

CREATE TABLE `financial_network` (
  `ID` bigint unsigned NOT NULL AUTO_INCREMENT,
  `NAME` varchar(100) NOT NULL,
  `GENERATED_ON` date DEFAULT NULL,
  `LAST_UPDATED` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `address` (
  `ID` bigint unsigned NOT NULL AUTO_INCREMENT,
  `COUNTRY` varchar(100) NOT NULL,
  `CITY` varchar(120) NOT NULL,
  `LINE1` varchar(150) NOT NULL,
  `ZIP` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `account_type` (
  `ID` bigint unsigned NOT NULL AUTO_INCREMENT,
  `CODE` varchar(20) NOT NULL,
  `NAME` varchar(80) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UQ_ACCOUNT_TYPE_CODE` (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `transaction_type` (
  `ID` bigint unsigned NOT NULL AUTO_INCREMENT,
  `CODE` varchar(20) NOT NULL,
  `NAME` varchar(80) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UQ_TRANSACTION_TYPE_CODE` (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- =======================
--  Bank and organization
-- =======================

CREATE TABLE `bank` (
  `ID` bigint unsigned NOT NULL AUTO_INCREMENT,
  `NAME` varchar(150) NOT NULL,
  `ACTIVE` tinyint(1) NOT NULL DEFAULT '1',
  `FINANCIAL_NETWORK_ID` bigint unsigned DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_BANK_NETWORK` (`FINANCIAL_NETWORK_ID`),
  CONSTRAINT `FK_BANK_NETWORK`
    FOREIGN KEY (`FINANCIAL_NETWORK_ID`)
    REFERENCES `financial_network` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `branch` (
  `ID` bigint unsigned NOT NULL AUTO_INCREMENT,
  `BANK_ID` bigint unsigned NOT NULL,
  `ADDRESS_ID` bigint unsigned NOT NULL,
  `CODE` varchar(45) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UQ_BRANCH_BANK_CODE` (`BANK_ID`, `CODE`),
  KEY `FK_BRANCH_ADDRESS` (`ADDRESS_ID`),
  CONSTRAINT `FK_BRANCH_ADDRESS`
    FOREIGN KEY (`ADDRESS_ID`) REFERENCES `address` (`ID`),
  CONSTRAINT `FK_BRANCH_BANK`
    FOREIGN KEY (`BANK_ID`) REFERENCES `bank` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `employee` (
  `ID` bigint unsigned NOT NULL AUTO_INCREMENT,
  `BRANCH_ID` bigint unsigned NOT NULL,
  `FULL_NAME` varchar(160) NOT NULL,
  `HIRED_DATE` date DEFAULT NULL,
  `MANAGER` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`),
  KEY `FK_EMPLOYEE_BRANCH` (`BRANCH_ID`),
  CONSTRAINT `FK_EMPLOYEE_BRANCH`
    FOREIGN KEY (`BRANCH_ID`) REFERENCES `branch` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- =======================
--  Customers and loans
-- =======================

CREATE TABLE `customer` (
  `ID` bigint unsigned NOT NULL AUTO_INCREMENT,
  `BANK_ID` bigint unsigned NOT NULL,
  `FULL_NAME` varchar(160) NOT NULL,
  `BIRTH_DATE` date DEFAULT NULL,
  `ADDRESS_ID` bigint unsigned NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_CUSTOMER_ADDRESS` (`ADDRESS_ID`),
  KEY `FK_CUSTOMER_BANK` (`BANK_ID`),
  CONSTRAINT `FK_CUSTOMER_ADDRESS`
    FOREIGN KEY (`ADDRESS_ID`) REFERENCES `address` (`ID`),
  CONSTRAINT `FK_CUSTOMER_BANK`
    FOREIGN KEY (`BANK_ID`) REFERENCES `bank` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `loan` (
  `ID` bigint unsigned NOT NULL AUTO_INCREMENT,
  `CUSTOMER_ID` bigint unsigned NOT NULL,
  `BRANCH_ID` bigint unsigned NOT NULL,
  `PRINCIPAL` decimal(18,2) NOT NULL,
  `RATE` decimal(7,4) NOT NULL,
  `START_DATE` date DEFAULT NULL,
  `END_DATE` date DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_LOAN_BRANCH` (`BRANCH_ID`),
  KEY `FK_LOAN_CUSTOMER` (`CUSTOMER_ID`),
  CONSTRAINT `FK_LOAN_BRANCH`
    FOREIGN KEY (`BRANCH_ID`) REFERENCES `branch` (`ID`),
  CONSTRAINT `FK_LOAN_CUSTOMER`
    FOREIGN KEY (`CUSTOMER_ID`) REFERENCES `customer` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- =======================
--  Accounts, cards, tx
-- =======================

CREATE TABLE `account` (
  `ID` bigint unsigned NOT NULL AUTO_INCREMENT,
  `CUSTOMER_ID` bigint unsigned NOT NULL,
  `BRANCH_ID` bigint unsigned NOT NULL,
  `ACCOUNT_TYPE_ID` bigint unsigned NOT NULL,
  `IBAN` varchar(34) NOT NULL,
  `BALANCE` decimal(18,2) NOT NULL DEFAULT '0.00',
  `OPENED_ON` date DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UQ_ACCOUNT_IBAN` (`IBAN`),
  KEY `FK_ACCOUNT_BRANCH` (`BRANCH_ID`),
  KEY `FK_ACCOUNT_CUSTOMER` (`CUSTOMER_ID`),
  KEY `FK_ACCOUNT_TYPE` (`ACCOUNT_TYPE_ID`),
  CONSTRAINT `FK_ACCOUNT_BRANCH`
    FOREIGN KEY (`BRANCH_ID`) REFERENCES `branch` (`ID`),
  CONSTRAINT `FK_ACCOUNT_CUSTOMER`
    FOREIGN KEY (`CUSTOMER_ID`) REFERENCES `customer` (`ID`),
  CONSTRAINT `FK_ACCOUNT_TYPE`
    FOREIGN KEY (`ACCOUNT_TYPE_ID`) REFERENCES `account_type` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `card` (
  `ID` bigint unsigned NOT NULL AUTO_INCREMENT,
  `ACCOUNT_ID` bigint unsigned NOT NULL,
  `PAN_MASKED` varchar(25) NOT NULL,
  `EXPIRY` date DEFAULT NULL,
  `CONTACTLESS` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`),
  KEY `FK_CARD_ACCOUNT` (`ACCOUNT_ID`),
  CONSTRAINT `FK_CARD_ACCOUNT`
    FOREIGN KEY (`ACCOUNT_ID`) REFERENCES `account` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `account_transaction` (
  `ID` bigint unsigned NOT NULL AUTO_INCREMENT,
  `ACCOUNT_ID` bigint unsigned NOT NULL,
  `TRANSACTION_TYPE_ID` bigint unsigned NOT NULL,
  `AMOUNT` decimal(18,2) NOT NULL,
  `HAPPENED_AT` datetime NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `IDX_TX_ACCOUNT_TIME` (`ACCOUNT_ID`, `HAPPENED_AT`),
  KEY `FK_TX_TYPE` (`TRANSACTION_TYPE_ID`),
  CONSTRAINT `FK_TX_ACCOUNT`
    FOREIGN KEY (`ACCOUNT_ID`) REFERENCES `account` (`ID`),
  CONSTRAINT `FK_TX_TYPE`
    FOREIGN KEY (`TRANSACTION_TYPE_ID`) REFERENCES `transaction_type` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
