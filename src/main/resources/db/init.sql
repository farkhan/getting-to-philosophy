/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Farhan Khan
 * Created: Oct 17, 2017
 */

CREATE TABLE `url` (
	`url_id`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`data`	VARCHAR,
	`title`	VARCHAR
);

CREATE TABLE `path` (
    `path_id`	INTEGER PRIMARY KEY AUTOINCREMENT,
    `url_id`	INTEGER,
    `data`	VARCHAR,
    `hop`	INTEGER,
    `title`	VARCHAR,
    FOREIGN KEY(`url_id`) REFERENCES `url`(`url_id`)
);