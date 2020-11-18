/* Create the database if not exists and use it. */
CREATE DATABASE IF NOT EXISTS `gamified_marketing_app_db`
/*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `gamified_marketing_app_db`;

/* Configuration of internal params. */
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

/* Creation of role table. */
DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
	`id` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45) NOT NULL,
    `description` VARCHAR(45),
    PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 272 DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

/* Data inserts for role table. */

/* Creation of registered_user table. */
DROP TABLE IF EXISTS `registered_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `registered_user` (
	`id` INT NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(100) NOT NULL,
    `password` VARCHAR(100) NOT NULL,
    `name` VARCHAR(45),
    `surname` VARCHAR(45),
    `blocked` BOOL NOT NULL,
    `role` INT NOT NULL,
    PRIMARY KEY (`id`),
    KEY (`username`),
    CONSTRAINT `fk_registered_user_role` FOREIGN KEY (`role`) REFERENCES `role` (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 272 DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

/* Data inserts for registered_user table. */

/* Creation of product table. */
DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
	`id` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45) NOT NULL,
    `picture` LONGBLOB,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 272 DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

/* Data inserts for product table. */

/* Creation of questionnaire table. */
DROP TABLE IF EXISTS `questionnaire`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `questionnaire` (
	`id` INT NOT NULL AUTO_INCREMENT,
    `date` DATE NOT NULL,
    `product` INT NOT NULL,
    PRIMARY KEY (`id`),
    KEY (`date`),
    KEY (`product`),
    CONSTRAINT `fk_questionnaire_product` FOREIGN KEY (`product`) REFERENCES `product` (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 272 DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

/* Data inserts for questionnaire table. */

/* Creation of filling table. */
DROP TABLE IF EXISTS `filling`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `filling` (
	`id` INT NOT NULL AUTO_INCREMENT,
    `registered_user` INT NOT NULL,
    `questionnaire` INT NOT NULL,
    `timestamp` TIMESTAMP NOT NULL,
    `points` INT,
    PRIMARY KEY (`id`),
    KEY (`registered_user`, `questionnaire`),
    CONSTRAINT `fk_filling_registered_user` FOREIGN KEY (`registered_user`) REFERENCES `registered_user` (`id`),
    CONSTRAINT `fk_filling_questionnaire` FOREIGN KEY (`questionnaire`) REFERENCES `questionnaire` (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 272 DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

/* Data inserts for filling table. */

/* Creation of question table. */
DROP TABLE IF EXISTS `question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `question` (
	`id` INT NOT NULL AUTO_INCREMENT,
    `text` VARCHAR(500) NOT NULL,
    `optional` BOOL NOT NULL,
    `order` INT NOT NULL,
    `range` INT,
    `questionnaire` INT NOT NULL,
    PRIMARY KEY (`id`),
    KEY (`order`, `questionnaire`),
    CONSTRAINT `fk_question_questionnaire` FOREIGN KEY (`questionnaire`) REFERENCES `questionnaire` (`id`),
    CONSTRAINT `chk_question_range` CHECK ((`range` != NULL AND `range` > 0))
) ENGINE = InnoDB AUTO_INCREMENT = 272 DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

/* Data inserts for question table. */

/* Creation of question_choice table. */
DROP TABLE IF EXISTS `question_choice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `question_choice` (
	`id` INT NOT NULL AUTO_INCREMENT,
    `text` VARCHAR(500) NOT NULL,
    `order` INT NOT NULL,
    `question` INT NOT NULL,
    PRIMARY KEY (`id`),
    KEY (`order`, `question`),
    CONSTRAINT `fk_question_choice_question` FOREIGN KEY (`question`) REFERENCES `question` (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 272 DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

/* Data inserts for question_choice table. */

/* Creation of answer table. */
DROP TABLE IF EXISTS `answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `answer` (
	`id` INT NOT NULL AUTO_INCREMENT,
    `text` VARCHAR(500),
    `range_value` INT,
    `question` INT NOT NULL,
    `filling` INT NOT NULL,
    PRIMARY KEY (`id`),
    KEY (`question`, `filling`),
    CONSTRAINT `fk_answer_question` FOREIGN KEY (`question`) REFERENCES `question` (`id`),
    CONSTRAINT `fk_answer_filling` FOREIGN KEY (`filling`) REFERENCES `filling` (`id`),
    CONSTRAINT `chk_answer_type_fields` CHECK (( (`range_value` != NULL AND `text` = NULL) OR (`range_value` = NULL AND `text` != NULL) ))
) ENGINE = InnoDB AUTO_INCREMENT = 272 DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

/* Data inserts for answer table. */

/* Creation of answer_choice table. */
DROP TABLE IF EXISTS `answer_choice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `answer_choice` (
	`id` INT NOT NULL AUTO_INCREMENT,
    `answer` INT NOT NULL,
    `question_choice` INT NOT NULL,
    PRIMARY KEY (`id`),
    KEY (`answer`, `question_choice`),
    CONSTRAINT `fk_answer_choice_answer` FOREIGN KEY (`answer`) REFERENCES `answer` (`id`),
    CONSTRAINT `fk_answer_choice_question_choice` FOREIGN KEY (`question_choice`) REFERENCES `question_choice` (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 272 DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

/* Data inserts for answer_choice table. */

/* Creation of review table. */
DROP TABLE IF EXISTS `review`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `review` (
	`id` INT NOT NULL AUTO_INCREMENT,
    `title` VARCHAR(250) NOT NULL,
    `text` VARCHAR(500),
    `registered_user` INT NOT NULL,
    `product` INT NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_review_registered_user` FOREIGN KEY (`registered_user`) REFERENCES `registered_user` (`id`),
    CONSTRAINT `fk_review_product` FOREIGN KEY (`product`) REFERENCES `product` (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 272 DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

/* Data inserts for review table. */

/* Data inserts for answer table. */

/* Creation of offensive_word table. */
DROP TABLE IF EXISTS `offensive_word`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `offensive_word` (
	`id` INT NOT NULL AUTO_INCREMENT,
    `word` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 272 DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

/* Data inserts for offensive_word table. */

/* Restoring config params. */
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;