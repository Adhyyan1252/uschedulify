CREATE DATABASE 'test';
CREATE TABLE 'auth' (
  'userID' int(11) NOT NULL AUTO_INCREMENT,
  'userName' varchar(20) NOT NULL,
  'passHash' varchar(30) NOT NULL,
  PRIMARY KEY ('userID'),
  UNIQUE KEY 'userName_UNIQUE' ('userName')
) ENGINE=InnoDB AUTO_INCREMENT=331 DEFAULT CHARSET=utf8;
CREATE TABLE 'classes' (
  'classID' int(11) NOT NULL AUTO_INCREMENT,
  'major' varchar(5) NOT NULL,
  'class_number' varchar(5) NOT NULL,
  PRIMARY KEY ('classID')
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE 'schedule_section_link' (
  'scheduleID' int(11) NOT NULL,
  'sectionID' varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE 'schedules' (
  'scheduleID' int(11) NOT NULL AUTO_INCREMENT,
  'userID' int(11) NOT NULL,
  'dateCreated' datetime DEFAULT NULL,
  'schedule_name' varchar(45) DEFAULT NULL,
  'saved' tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY ('scheduleID'),
  KEY 'fkUser_idx' ('userID'),
  CONSTRAINT 'fkUser' FOREIGN KEY ('userID') REFERENCES 'auth' ('userID') ON DELETE NO ACTION ON 