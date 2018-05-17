CREATE DATABASE  IF NOT EXISTS `survey_database` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `survey_database`;
-- MySQL dump 10.13  Distrib 5.7.17, for macos10.12 (x86_64)
--
-- Host: 127.0.0.1    Database: survey_database
-- ------------------------------------------------------
-- Server version	5.7.22

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `survey`
--

DROP TABLE IF EXISTS `survey`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `survey` (
  `survey_id` int(11) NOT NULL AUTO_INCREMENT,
  `start_date` datetime NOT NULL,
  `end_date` datetime NOT NULL,
  `is_active` tinyint(4) NOT NULL DEFAULT '1',
  `is_launched` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`survey_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `survey_links`
--

DROP TABLE IF EXISTS `survey_links`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `survey_links` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `link_id` varchar(45) NOT NULL,
  `survey_id` int(11) NOT NULL,
  `email` varchar(45) NOT NULL,
  `is_expired` tinyint(4) NOT NULL DEFAULT '0',
  `create_date` datetime NOT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`,`link_id`,`survey_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-04-25 15:43:02


DROP TABLE IF EXISTS `tsa_section`;

CREATE TABLE `tsa_section` (
  `section_id` int(11) NOT NULL AUTO_INCREMENT,
  `section_title` varchar(100) NOT NULL,
  PRIMARY KEY (`section_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `survey_section`;

CREATE TABLE `survey_section` (
  `survey_id` int(11) NOT NULL,
  `section_id` int(11) NOT NULL,
  PRIMARY KEY (`survey_id`,`section_id`),
  KEY `section_id` (`section_id`),
  CONSTRAINT `survey_section_ibfk_1` 
   FOREIGN KEY (`survey_id`) REFERENCES `survey` (`survey_id`),
  CONSTRAINT `survey_section_ibfk_2` 
   FOREIGN KEY (`section_id`) REFERENCES `tsa_section` (`section_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Run to dummy insert in tsa_section table, add section id that is available in your section table for testing to survey_section table
insert into tsa_section (section_title) values ("Talent Engagement Survey");
insert into tsa_section (section_title) values ("Tell us something about yourself");
insert into tsa_section (section_title) values ("On a scale of 1 to 10 (1 being the lowest and 10th being the highest)");
insert into tsa_section (section_title) values ("Open-ended questions");

insert into survey_section (survey_id, section_id) values (1,1);
insert into survey_section (survey_id, section_id) values (1,2);
insert into survey_section (survey_id, section_id) values (1,3);
insert into survey_section (survey_id, section_id) values (1,4);

*/

DROP TABLE IF EXISTS `tsa_element`;

CREATE TABLE `tsa_element` (
  `element_id` int(11) NOT NULL AUTO_INCREMENT,
  `element` text NOT NULL,
  `element_type`  enum ('TEXT','RADIOGROUP','HTML','BULLET','RATING') NOT NULL,
  `options` text,
  `min_value` varchar(20),
  `max_value` varchar(20),
  PRIMARY KEY (`element_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `section_element`;

CREATE TABLE `section_element` (
  `section_id` int(11) NOT NULL,
  `element_id` int(11) NOT NULL,
  `is_mandatory` boolean default false,
  PRIMARY KEY (`section_id`,`element_id`),
   KEY `element_id` (`element_id`),
  KEY `section_id` (`section_id`),
  CONSTRAINT `section_element_ibfk_1` 
   FOREIGN KEY (`section_id`) REFERENCES `tsa_section` (`section_id`),
  CONSTRAINT `section_element_ibfk_2` 
   FOREIGN KEY (`element_id`) REFERENCES `tsa_element` (`element_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Run to dummy insert in questionnaire_element table;
insert into tsa_element (element, element_type) values 
("Thank you for taking out a moment to answer our Talent Engagement Survey.", 'HTML');

insert into tsa_element (element, element_type) values 
("The office location is the only section that is required. On the other two identifiers-don't feel obligated to fill these out - they are completely optional.", 
'HTML');
insert into tsa_element (element, element_type, options) values 
("Depatment", 'RADIOGROUP', "Admin, IT, Engineering");

insert into tsa_element (element, element_type, options) values 
("Office Location.", 'RADIOGROUP', "India, Cluj, Timisoara, US, Iasi");


insert into tsa_element (element, element_type, options) values 
("Direct Manager (This information will be used strictly to aid your Talent Engagement Partner in sorting data.).", 
'BULLET', "XYZ, ABC, SDF, WER, QIYUI, WWWW");

insert into tsa_element (element, element_type, options, min_value, max_value) values 
("How happy are you at work?", 'RATING', "1,2,3,4,5,6,7,8,9,10", "1", "10");


insert into tsa_element (element, element_type, options , min_value, max_value) values 
("How would you rate 3Pillar's culture?", 'RATING', "1,2,3,4,5,6,7,8,9,10", "1", "10");

insert into tsa_element (element, element_type) values 
("What is the one thing that the organisation could do to improve your overall happiness?", 'TEXT');

insert into tsa_element (element, element_type) values 
("What is the one thing that your manager could do to improve your overall job satisfaction?", 'TEXT');


insert into section_element (section_id, element_id, is_mandatory) values (1,2,true);
insert into section_element (section_id, element_id, is_mandatory) values (1,3,true);
insert into section_element (section_id, element_id, is_mandatory) values (2,4,true);
insert into section_element (section_id, element_id, is_mandatory) values (2,5,true);
insert into section_element (section_id, element_id, is_mandatory) values (2,6,true);
insert into section_element (section_id, element_id, is_mandatory) values (3,7,true);
insert into section_element (section_id, element_id, is_mandatory) values (3,8,true);
insert into section_element (section_id, element_id, is_mandatory) values (4,9,true);
insert into section_element (section_id, element_id, is_mandatory) values (4,10,true);
insert into section_element (section_id, element_id, is_mandatory) values (4,11,false);

 */

DROP TABLE IF EXISTS `tsa_response`;

CREATE TABLE `tsa_response` (
  `response_id` int(11) NOT NULL,
  `question_id` int(11) NOT NULL,
  `timestamp` datetime NOT NULL,
  `answer` text,
  `survey_id` int(11) NOT NULL,
  PRIMARY KEY (`response_id`, `question_id`),
  FOREIGN KEY (`question_id`) REFERENCES tsa_element(`element_id`),
  FOREIGN KEY (`survey_id`) REFERENCES survey(`survey_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;