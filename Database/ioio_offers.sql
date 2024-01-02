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
INSERT INTO `offers` VALUES (0,1,'Lutowanie chinskich czesci','Zarobek najnizsza krajowa','Szukam studenta, ktory za nie wielka sume bedzie pracowal jako lutownik chinskich moudlow elektronicznych'),(1,2,'Lutowanie chinskich czesci','Zarobek: najnizsza krajowa','Szukam studenta, ktory za niezla sume bedzie pracowal jako lutownik chinskich moudlow elektronicznych. Tylko studenci ze zdecydowana reka!'),(2,2,'Programowanie procesorow Z80','Zarobek: 5000 zl/msc','Szukam studenta, ktory za niezla sume bedzie pracowal jako programista procesorow Z80. Nie szukam osob, ktore umia tylko zrobic stacje pogodowa!'),(3,2,'Tester urzadzen','Zarobek: do ustalenia','Poszukiwany student z umiejetnosciami pozwalajacymi testowanie wszelakich mikrokontrolerow. Przyjmuje tylko osoby, ktore zrobily prace inzynierska na mikrokontrolerach!'),(4,3,'Pracownik w sieci restauracji McDonald\'s','Zarobek: 23,50zl/h','Poszukiwany student, ktory musi sobie dorobic w weekendy, aby moc przezyc na karmach ©Whiskas'),(5,3,'Stanowisko menadzera w McDonalds','Zarobek: 24,50zl/h','Poszukiwany student, ktory musi sobie poradzic z ciezko pracujacymi studentami w nasazych restauracjach. Pensja miesieczna moze ulec zmianie w zaleznosci od wydajnosci realizacji zamowien - aka. obcinanie wynagrodzenia za wolna realizacje zamowien'),(6,4,'Test11','x','Tesin'),(7,4,'Test12','D','Tesin'),(8,4,'Test13','x','Tesin'),(9,5,'Test14','D','Tesin'),(10,5,'Test15','x','Tesin'),(11,5,'Test16','D','Tesin'),(12,6,'Test17','x','Tesin'),(13,6,'Test18','D','Tesin'),(14,6,'Test19','xD','Tesin'),(15,7,'Test111','x','Tesin'),(16,7,'Test112','D','Tesin'),(17,8,'Test113','x','Tesin'),(18,9,'Test114','D','Tesin'),(19,10,'Test115','xd','Tesin'),(20,10,'Test116','xxxd','Tesin');
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

-- Dump completed on 2023-12-30 19:37:01
