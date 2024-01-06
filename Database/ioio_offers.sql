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
  `content` varchar(45) NOT NULL,
  `info` varchar(1000) NOT NULL,
  PRIMARY KEY (`id_offer`),
  UNIQUE KEY `id_UNIQUE` (`id_offer`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `offers`
--

LOCK TABLES `offers` WRITE;
/*!40000 ALTER TABLE `offers` DISABLE KEYS */;
INSERT INTO `offers` VALUES (0,1,'Test1','1','Szukam studenta, ktory za nie wielka sume bedzie pracowal jako lutownik chinskich moudlow elektronicznych'),(1,2,'Test2','10','Szukam studenta, ktory za niezla sume bedzie pracowal jako lutownik chinskich moudlow elektronicznych. Tylko studenci ze zdecydowana reka!'),(2,2,'Test3','100','Szukam studenta, ktory za niezla sume bedzie pracowal jako programista procesorow Z80. Nie szukam osob, ktore umia tylko zrobic stacje pogodowa!'),(3,2,'Test4','1000','Poszukiwany student z umiejetnosciami pozwalajacymi testowanie wszelakich mikrokontrolerow. Przyjmuje tylko osoby, ktore zrobily prace inzynierska na mikrokontrolerach!'),(4,3,'Test5','10000','Poszukiwany student, ktory musi sobie dorobic w weekendy, aby moc przezyc na karmach ©Whiskas'),(5,3,'Test6','100000','Poszukiwany student, ktory musi sobie poradzic z ciezko pracujacymi studentami w nasazych restauracjach. Pensja miesieczna moze ulec zmianie w zaleznosci od wydajnosci realizacji zamowien - aka. obcinanie wynagrodzenia za wolna realizacje zamowien'),(6,4,'Test7','1','Tesin'),(7,4,'Test8','10','Tesin'),(8,4,'Test9','100','Tesin'),(9,5,'Test10','1000','Tesin'),(10,5,'Test11','10000','Tesin'),(11,5,'Test12','1000','Tesin'),(12,6,'Test13','1','Tesin'),(13,6,'Test14','1','Tesin'),(14,6,'Test15','1','Tesin'),(15,7,'Test16','10','Tesin'),(16,7,'Test17','10','Tesin'),(17,8,'Test18','50','Tesin'),(18,9,'Test19','70','Tesin'),(19,10,'Test20','250','Tesin'),(20,10,'Test21','400','Tesin'),(21,1,'Test22','12301','b'),(22,1,'Test23','3892','b'),(23,1,'Test24','832','b'),(24,1,'Test25','32121','b'),(25,1,'Test26','123','b'),(26,1,'Test27','1','b'),(27,1,'Test28','3213','b'),(28,1,'Test29','2131','b'),(29,1,'Test30','121','b'),(30,1,'Test31','12','b');
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

-- Dump completed on 2024-01-06 14:40:04
