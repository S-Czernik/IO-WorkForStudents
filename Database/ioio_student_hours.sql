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
-- Table structure for table `student_hours`
--

DROP TABLE IF EXISTS `student_hours`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student_hours` (
  `id_hour` int NOT NULL,
  `id_stud` int NOT NULL,
  `begin` int NOT NULL,
  `end` int NOT NULL,
  `day` enum('mon','tue','wed','thu','fri','sat','sun') NOT NULL,
  PRIMARY KEY (`id_hour`),
  UNIQUE KEY `id_UNIQUE` (`id_hour`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student_hours`
--

LOCK TABLES `student_hours` WRITE;
/*!40000 ALTER TABLE `student_hours` DISABLE KEYS */;
INSERT INTO `student_hours` VALUES (0,1,12,16,'mon'),(1,1,17,21,'mon'),(7,2,495,1110,'thu'),(8,3,610,854,'sun'),(9,4,960,1230,'mon'),(10,5,915,1220,'fri'),(11,7,488,915,'wed'),(12,8,1335,1425,'sat'),(13,9,795,885,'tue'),(14,0,120,180,'mon'),(15,0,915,1110,'fri'),(16,0,480,960,'tue'),(17,0,180,240,'thu'),(18,0,360,420,'mon'),(19,0,495,960,'wed');
/*!40000 ALTER TABLE `student_hours` ENABLE KEYS */;
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
