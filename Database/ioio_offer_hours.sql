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
-- Table structure for table `offer_hours`
--

DROP TABLE IF EXISTS `offer_hours`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `offer_hours` (
  `id_hour` int NOT NULL,
  `id_offer` int NOT NULL,
  `begin` int NOT NULL,
  `end` int NOT NULL,
  `day` enum('mon','tue','wed','thu','fri','sat','sun') NOT NULL,
  PRIMARY KEY (`id_hour`),
  UNIQUE KEY `id_hour_UNIQUE` (`id_hour`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `offer_hours`
--

LOCK TABLES `offer_hours` WRITE;
/*!40000 ALTER TABLE `offer_hours` DISABLE KEYS */;
INSERT INTO `offer_hours` VALUES (1,1,60,1080,'mon'),(2,1,244,488,'fri'),(4,22,480,1020,'mon'),(5,22,360,990,'tue'),(6,19,300,1080,'wed'),(7,15,915,1230,'sun'),(10,3,135,150,'sun'),(11,7,1230,1245,'sat'),(12,11,900,960,'tue'),(13,21,1220,1410,'tue'),(14,5,480,930,'mon'),(15,9,480,960,'fri'),(16,9,915,1220,'tue'),(17,25,480,1200,'mon');
/*!40000 ALTER TABLE `offer_hours` ENABLE KEYS */;
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
