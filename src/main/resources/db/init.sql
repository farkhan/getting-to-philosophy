/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Farhan Khan
 * Created: Oct 17, 2017
 */

CREATE TABLE IF NOT EXISTS url (
   url_id int PRIMARY KEY auto_increment,
   data VARCHAR
);

CREATE TABLE `path` (
    `path_id`	INTEGER PRIMARY KEY AUTOINCREMENT,
    `url_id`	INTEGER,
    `data`	VARCHAR,
    `hop`	INTEGER,
    FOREIGN KEY(`url_id`) REFERENCES `url`(`url_id`)
);