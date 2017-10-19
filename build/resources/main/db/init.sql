/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Farhan Khan
 * Created: Oct 17, 2017
 */

CREATE TABLE `bento`.`url` (
  `url_id` INT NOT NULL AUTO_INCREMENT,
  `data` VARCHAR(255) NULL,
  `title` VARCHAR(100) NULL,
  `urlcol` VARCHAR(45) NULL,
  PRIMARY KEY (`url_id`));

CREATE TABLE `bento`.`path` (
  `path_id` INT NOT NULL AUTO_INCREMENT,
  `url_id` INT NULL,
  `data` VARCHAR(255) NULL,
  `hop` INT NULL,
  `title` VARCHAR(100) NULL,
  PRIMARY KEY (`path_id`),
  INDEX `url_id_idx` (`url_id` ASC),
  CONSTRAINT `url_id`
    FOREIGN KEY (`url_id`)
    REFERENCES `bento`.`url` (`url_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);