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

-- Dump completed on 2025-11-06  3:25:01
