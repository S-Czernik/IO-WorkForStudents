-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: ioio
-- ------------------------------------------------------
-- Server version	8.2.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `employers_profile_details`
--

DROP TABLE IF EXISTS `employers_profile_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employers_profile_details` (
  `id_employers_profile_details` int NOT NULL,
  `id_user` int NOT NULL,
  `company_name` varchar(45) NOT NULL DEFAULT 'Add company name by clicking edit',
  `NIP` varchar(45) NOT NULL DEFAULT 'Add NIP by clicking edit',
  PRIMARY KEY (`id_employers_profile_details`),
  UNIQUE KEY `id_employers_profile_details_UNIQUE` (`id_employers_profile_details`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employers_profile_details`
--

LOCK TABLES `employers_profile_details` WRITE;
/*!40000 ALTER TABLE `employers_profile_details` DISABLE KEYS */;
INSERT INTO `employers_profile_details` VALUES (0,1,'HealthyCompany','4739571759'),(1,1,'HealthyCompany','4739571759'),(2,1,'HealthyCompany','4739571759'),(3,1,'HealthyCompany','4739571759'),(4,1,'HealthyCompany','4739571759'),(5,1,'HealthyCompany','4739571759'),(6,1,'HealthyCompany','4739571759'),(7,1,'HealthyCompany','4739571759'),(8,1,'HealthyCompany','4739571759'),(9,1,'HealthyCompany','4739571759'),(10,1,'HealthyCompany','4739571759'),(11,1,'HealthyCompany','4739571759'),(12,1,'HealthyCompany','4739571759'),(13,1,'HealthyCompany','4739571759'),(14,1,'HealthyCompany','4739571759'),(15,1,'HealthyCompany','4739571759'),(16,1,'HealthyCompany','4739571759'),(17,1,'HealthyCompany','4739571759'),(18,1,'HealthyCompany','4739571759'),(19,1,'HealthyCompany','4739571759'),(20,1,'HealthyCompany','4739571759'),(21,1,'HealthyCompany','4739571759'),(22,1,'HealthyCompany','4739571759'),(23,1,'HealthyCompany','4739571759'),(24,1,'HealthyCompany','4739571759'),(25,1,'HealthyCompany','4739571759'),(26,1,'HealthyCompany','4739571759'),(27,1,'HealthyCompany','4739571759'),(28,1,'HealthyCompany','4739571759'),(29,1,'HealthyCompany','4739571759'),(30,1,'HealthyCompany','4739571759'),(31,1,'HealthyCompany','4739571759'),(32,1,'HealthyCompany','4739571759'),(33,1,'HealthyCompany','4739571759'),(34,1,'HealthyCompany','4739571759'),(35,1,'HealthyCompany','4739571759'),(36,1,'HealthyCompany','4739571759'),(37,1,'HealthyCompany','4739571759'),(38,1,'Add company name by clicking edit','Add NIP by clicking edit'),(39,1,'Add company name by clicking edit','Add NIP by clicking edit'),(40,1,'Add company name by clicking edit','Add NIP by clicking edit'),(41,1,'Add company name by clicking edit','Add NIP by clicking edit'),(42,1,'Add company name by clicking edit','Add NIP by clicking edit'),(43,13,'Add company name by clicking edit','Add NIP by clicking edit'),(44,14,'Add company name by clicking edit','Add NIP by clicking edit'),(45,1,'Add company name by clicking edit','Add NIP by clicking edit'),(46,1,'Add company name by clicking edit','Add NIP by clicking edit'),(47,1,'Add company name by clicking edit','Add NIP by clicking edit'),(48,1,'Add company name by clicking edit','Add NIP by clicking edit'),(49,1,'Add company name by clicking edit','Add NIP by clicking edit'),(50,1,'Add company name by clicking edit','Add NIP by clicking edit'),(51,1,'Add company name by clicking edit','Add NIP by clicking edit');
/*!40000 ALTER TABLE `employers_profile_details` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-02-14 17:32:57
