-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema Events_db
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema Events_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `Events_db` DEFAULT CHARACTER SET utf8 ;
USE `Events_db` ;

-- --------------------------------------------EventsEventsEventsEventsEvents---------
-- Table `Events_db`.`Events`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Events_db`.`Events` (
  `IoT_device_id` VARCHAR(45) NOT NULL,
  `longitude` INT NOT NULL,
  `latitude` INT NOT NULL,
  `smoke_sensor` VARCHAR(45) NULL,
  `gas_sensor` VARCHAR(45) NULL,
  `temp_sensor` VARCHAR(45) NULL,
  `uv_sensor` VARCHAR(45) NULL,
  `danger_degree` VARCHAR(45) NULL,
  PRIMARY KEY ( `IoT_device_id`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
