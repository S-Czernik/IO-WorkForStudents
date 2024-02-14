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
-- Table structure for table `students_profile_details`
--

DROP TABLE IF EXISTS `students_profile_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `students_profile_details` (
  `id_students_profile_details` int NOT NULL,
  `id_user` int NOT NULL,
  `name` varchar(45) NOT NULL DEFAULT 'Add name by clicking edit',
  `surname` varchar(45) NOT NULL DEFAULT 'Add surname by clicking edit',
  `title` varchar(45) NOT NULL DEFAULT 'Add title by clicking edit',
  PRIMARY KEY (`id_students_profile_details`),
  UNIQUE KEY `id_students_profile_details_UNIQUE` (`id_students_profile_details`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `students_profile_details`
--

LOCK TABLES `students_profile_details` WRITE;
/*!40000 ALTER TABLE `students_profile_details` DISABLE KEYS */;
INSERT INTO `students_profile_details` VALUES (0,0,'Mariusz','Kowalski','Looking for a job in IT'),(1,5,'Add name by clicking edit','Add surname by clicking edit','Add title by clicking edit'),(2,4,'Add name by clicking edit','Add surname by clicking edit','Add title by clicking edit'),(3,2,'Add name by clicking edit','Add surname by clicking edit','Add title by clicking edit'),(4,3,'Add name by clicking edit','Add surname by clicking edit','Add title by clicking edit'),(5,7,'Add name by clicking edit','Add surname by clicking edit','Add title by clicking edit'),(6,8,'Add name by clicking edit','Add surname by clicking edit','Add title by clicking edit'),(7,9,'Add name by clicking edit','Add surname by clicking edit','Add title by clicking edit'),(8,12,'Add name by clicking edit','Add surname by clicking edit','Add title by clicking edit');
/*!40000 ALTER TABLE `students_profile_details` ENABLE KEYS */;
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
