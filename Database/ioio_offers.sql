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
-- Table structure for table `offers`
--

DROP TABLE IF EXISTS `offers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `offers` (
  `id_offer` int NOT NULL,
  `id_empl` int NOT NULL,
  `title` varchar(45) NOT NULL,
  `content` varchar(1000) NOT NULL,
  `salary` decimal(10,2) NOT NULL,
  `tags` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id_offer`),
  UNIQUE KEY `id_UNIQUE` (`id_offer`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `offers`
--

LOCK TABLES `offers` WRITE;
/*!40000 ALTER TABLE `offers` DISABLE KEYS */;
INSERT INTO `offers` VALUES (2,19,'Accountant','We invite an accountant to collaborate with us. We offer stable employment and competitive salary.',4800.00,'#job'),(3,1,'Civil Engineer','Job opportunity for a civil engineer with experience in design and supervision.',6000.00,'#civil,job'),(4,14,'Office Assistant','We are looking for an office assistant with organizational skills.',4000.00,'#office'),(5,13,'Python Programmer','We are hiring an experienced Python programmer for project participation.',10000.00,'#programmer,programming'),(6,20,'Financial Advisor','Job opportunity for a financial advisor with experience. We offer flexible hours and attractive salary.',5500.00,'#job'),(7,1,'Logistics Specialist','We invite a logistics specialist to join our team. Stable employment and competitive salary.',5200.00,'#specialist'),(8,15,'Software Engineer','We are looking for a software engineer to work on new projects. Salary negotiable.',6500.00,'#soft,job,it'),(9,14,'Graphic Designer','We seek a creative graphic designer for creating advertising materials. Salary negotiable.',5800.00,'#graphic, program'),(10,21,'HR Specialist','Job opportunity in the HR department. Experience is welcomed. Salary negotiable.',5000.00,'#job'),(11,1,'Business Analyst','We invite a business analyst to participate in data analysis projects. Attractive employment conditions.',7000.00,'#buisness'),(12,16,'Software Tester','We are hiring an experienced software tester. Stable employment and competitive salary.',5500.00,'#soft,program,it'),(13,15,'Customer Service Specialist','Job in customer service area. Salary negotiable.',4800.00,'#custom'),(14,22,'Electronics Engineer','We invite an electronics engineer to work on new projects. Stable employment and attractive salary.',7000.00,'#job'),(15,1,'Patient Caregiver','We are looking for a caregiver to work in a care home. Salary negotiable.',4500.00,'#job'),(17,16,'Marketing Assistant','Job for a marketing assistant with copywriting skills. Salary negotiable.',5200.00,'#market'),(18,23,'Process Engineer','We invite a process engineer to participate in optimizing company operations. Attractive employment conditions.',5800.00,'#job'),(19,1,'IT Security Specialist','We are looking for an IT security specialist. Stable employment and competitive salary.',6500.00,'#IT'),(20,17,'Java Programmer','We are seeking a Java programmer for work on new projects. Competitive salary.',7000.00,'#job'),(21,13,'Landscape Architect','We invite a landscape architect to participate in urban planning projects. Stable employment and attractive salary.',5800.00,'#architect'),(22,1,'HR Specialist','Job in the HR department. Experience is welcomed. Salary negotiable.',2000.00,'#HR'),(23,17,'Data Analyst','We are looking for a data analyst with analysis and reporting skills. We offer an attractive salary.',6500.00,'#job'),(24,18,'Systems Administrator','Job for a systems administrator with experience. Stable employment and competitive salary.',5500.00,'#job');
/*!40000 ALTER TABLE `offers` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-02-14 17:32:58
