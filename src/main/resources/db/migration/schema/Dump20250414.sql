-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: audio_book
-- ------------------------------------------------------
-- Server version	8.0.40

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
-- Table structure for table `audio_books`
--

DROP TABLE IF EXISTS `audio_books`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `audio_books` (
  `id` binary(16) NOT NULL,
  `author` varchar(50) NOT NULL,
  `cover_image` varchar(125) NOT NULL,
  `description` text,
  `duration` int NOT NULL,
  `female_audio_url` varchar(125) NOT NULL,
  `is_free` bit(1) NOT NULL,
  `male_audio_url` varchar(125) NOT NULL,
  `published_year` int NOT NULL,
  `title` varchar(50) NOT NULL,
  `category_id` binary(16) NOT NULL,
  `user_id` binary(16) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKnlpdrmhckk6qepn0oby3lorxx` (`category_id`),
  KEY `FKqddpnte2fri4a5wl00k513gvg` (`user_id`),
  CONSTRAINT `FKnlpdrmhckk6qepn0oby3lorxx` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`),
  CONSTRAINT `FKqddpnte2fri4a5wl00k513gvg` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `book_chapters`
--

DROP TABLE IF EXISTS `book_chapters`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `book_chapters` (
  `id` binary(16) NOT NULL,
  `chapter_number` int NOT NULL,
  `text_content` text NOT NULL,
  `audio_book_id` binary(16) NOT NULL,
  `title` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKq90q57owxn3s799je4mcupc2l` (`audio_book_id`),
  CONSTRAINT `FKq90q57owxn3s799je4mcupc2l` FOREIGN KEY (`audio_book_id`) REFERENCES `audio_books` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `id` binary(16) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `favorite_list_books`
--

DROP TABLE IF EXISTS `favorite_list_books`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `favorite_list_books` (
  `id` binary(16) NOT NULL,
  `audio_book_id` binary(16) NOT NULL,
  `favorite_list_id` binary(16) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK17q04vdn41udvce612j74p9wu` (`audio_book_id`),
  KEY `FKkikt707c0jiy9yba1e2wm0mf5` (`favorite_list_id`),
  CONSTRAINT `FK17q04vdn41udvce612j74p9wu` FOREIGN KEY (`audio_book_id`) REFERENCES `audio_books` (`id`),
  CONSTRAINT `FKkikt707c0jiy9yba1e2wm0mf5` FOREIGN KEY (`favorite_list_id`) REFERENCES `favorite_lists` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `favorite_lists`
--

DROP TABLE IF EXISTS `favorite_lists`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `favorite_lists` (
  `id` binary(16) NOT NULL,
  `name` varchar(255) NOT NULL,
  `user_id` binary(16) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9lpfmkjj8x0sicirsyhhp48ke` (`user_id`),
  CONSTRAINT `FK9lpfmkjj8x0sicirsyhhp48ke` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `flyway_schema_history`
--

DROP TABLE IF EXISTS `flyway_schema_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `flyway_schema_history` (
  `installed_rank` int NOT NULL,
  `version` varchar(50) DEFAULT NULL,
  `description` varchar(200) NOT NULL,
  `type` varchar(20) NOT NULL,
  `script` varchar(1000) NOT NULL,
  `checksum` int DEFAULT NULL,
  `installed_by` varchar(100) NOT NULL,
  `installed_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `execution_time` int NOT NULL,
  `success` tinyint(1) NOT NULL,
  PRIMARY KEY (`installed_rank`),
  KEY `flyway_schema_history_s_idx` (`success`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `invalidated_token`
--

DROP TABLE IF EXISTS `invalidated_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `invalidated_token` (
  `id` varchar(36) NOT NULL,
  `expiry_time` datetime(6) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `prenium_plans`
--

DROP TABLE IF EXISTS `prenium_plans`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `prenium_plans` (
  `id` binary(16) NOT NULL,
  `description` varchar(100) DEFAULT NULL,
  `duration_days` int NOT NULL,
  `name` varchar(50) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rating`
--

DROP TABLE IF EXISTS `rating`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rating` (
  `id` binary(16) NOT NULL,
  `comment` varchar(75) DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `rating` int NOT NULL,
  `audio_book_id` binary(16) NOT NULL,
  `user_id` binary(16) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKf68lgbsbxl310n0jifwpfqgfh` (`user_id`),
  KEY `FKp4pcson88nkgjpj4f1no915cy` (`audio_book_id`),
  CONSTRAINT `FKf68lgbsbxl310n0jifwpfqgfh` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKp4pcson88nkgjpj4f1no915cy` FOREIGN KEY (`audio_book_id`) REFERENCES `audio_books` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_preniums`
--

DROP TABLE IF EXISTS `user_preniums`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_preniums` (
  `id` binary(16) NOT NULL,
  `end_date` datetime(6) NOT NULL,
  `payment_status` enum('PENDING','COMPLETED','FAILED') NOT NULL,
  `start_date` datetime(6) NOT NULL,
  `prenium_plan_id` binary(16) NOT NULL,
  `user_id` binary(16) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKe4wy1g5aev1tqf07l7po5m133` (`user_id`),
  KEY `FKem85hret5m9aitkdyad4h507q` (`prenium_plan_id`),
  CONSTRAINT `FKe4wy1g5aev1tqf07l7po5m133` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKem85hret5m9aitkdyad4h507q` FOREIGN KEY (`prenium_plan_id`) REFERENCES `prenium_plans` (`id`),
  CONSTRAINT `user_preniums_chk_1` CHECK ((`payment_status` between 0 and 2))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` binary(16) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `email` varchar(50) DEFAULT NULL,
  `hashed_password` varchar(255) NOT NULL,
  `role` varchar(45) NOT NULL,
  `prenium_expiry` datetime(6) DEFAULT NULL,
  `prenium_status` bit(1) NOT NULL,
  `username` varchar(50) DEFAULT NULL,
  `date_of_birth` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-04-14 23:22:46
