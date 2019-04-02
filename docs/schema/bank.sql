-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema bank-dev
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema bank-dev
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `bank-dev` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci ;
USE `bank-dev` ;

-- -----------------------------------------------------
-- Table `bank-dev`.`member`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bank-dev`.`member` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `social_id` BIGINT NOT NULL,
  `name` VARCHAR(45) NULL,
  `profile_href` VARCHAR(255) NULL,
  PRIMARY KEY (`id`),
  INDEX `social_id_idx` USING BTREE (`social_id`) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bank-dev`.`account`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bank-dev`.`account` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `member_id` BIGINT NOT NULL,
  `name` VARCHAR(255) NULL,
  `account_number` VARCHAR(255) NULL,
  `balance` BIGINT NULL,
  `rate` DOUBLE NULL,
  `created_at` DATETIME NULL,
  PRIMARY KEY (`id`),
  INDEX `member_id_idx` (`member_id` ASC) VISIBLE,
  INDEX `account_number_idx` (`account_number` ASC) VISIBLE,
  CONSTRAINT `member_id`
    FOREIGN KEY (`member_id`)
    REFERENCES `bank-dev`.`member` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bank-dev`.`transcation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bank-dev`.`transcation` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `account_id` BIGINT NOT NULL,
  `amount` BIGINT NULL,
  `date_time` DATETIME NULL,
  `transaction_classify` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  INDEX `account_id_idx` (`account_id` ASC) VISIBLE,
  CONSTRAINT `account_id_idx`
    FOREIGN KEY (`account_id`)
    REFERENCES `bank-dev`.`account` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bank-dev`.`instrument`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bank-dev`.`instrument` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NULL,
  `description` VARCHAR(2000) NULL,
  `expired_at` DATETIME NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bank-dev`.`Attendance`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bank-dev`.`Attendance` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `number_of_attendee` INT NULL,
  `number_of_absentee` INT NULL,
  `session_name` VARCHAR(100) NULL,
  `started_at` DATETIME NULL,
  PRIMARY KEY (`id`),
  INDEX `session_name_idx` (`session_name` ASC) VISIBLE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
