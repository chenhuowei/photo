/*
Navicat MySQL Data Transfer

Source Server         : 1
Source Server Version : 50712
Source Host           : localhost:3306
Source Database       : photograph

Target Server Type    : MYSQL
Target Server Version : 50712
File Encoding         : 65001

Date: 2017-05-25 21:08:39
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `photo_id` int(11) DEFAULT NULL,
  `photo_title` char(255) DEFAULT NULL,
  `course_id` int(11) DEFAULT NULL,
  `course_title` char(255) DEFAULT NULL,
  `author_id` int(11) DEFAULT NULL,
  `author` char(255) DEFAULT NULL,
  `reply_comment_id` int(11) DEFAULT NULL,
  `reply_name` char(255) DEFAULT NULL,
  `status` bit(1) DEFAULT b'1',
  `create_time` char(20) DEFAULT NULL,
  `user` char(30) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `is_read` bit(1) DEFAULT NULL,
  `content` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES ('15', '8', 'test', null, null, '2', 'admin', null, null, '', '2017-05-24 13:13:33', 'admin', '2', null, 'test');
INSERT INTO `comment` VALUES ('16', null, null, '43', 'test', '2', 'admin', null, null, '', '2017-05-24 13:13:42', 'admin', '2', null, 'test');

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` char(50) DEFAULT NULL,
  `author` char(30) DEFAULT NULL,
  `author_id` int(11) DEFAULT NULL,
  `content` text,
  `love` int(11) DEFAULT NULL,
  `photo_url` varchar(100) DEFAULT NULL,
  `create_time` char(20) DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  `type` char(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES ('43', 'test', 'admin', '2', 'ddddddd', null, null, '2017-05-24 13:13:54', '', '摄影技巧');

-- ----------------------------
-- Table structure for love
-- ----------------------------
DROP TABLE IF EXISTS `love`;
CREATE TABLE `love` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `course_id` int(11) DEFAULT NULL,
  `photo_id` int(11) DEFAULT NULL,
  `author_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `love` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of love
-- ----------------------------
INSERT INTO `love` VALUES ('1', null, '8', null, '2', '');

-- ----------------------------
-- Table structure for photo
-- ----------------------------
DROP TABLE IF EXISTS `photo`;
CREATE TABLE `photo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `theme` char(30) DEFAULT NULL,
  `type` char(30) DEFAULT NULL,
  `camera_type` char(50) DEFAULT NULL,
  `title` char(50) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `info` varchar(300) DEFAULT NULL,
  `love` int(11) DEFAULT NULL,
  `upload_time` char(20) DEFAULT NULL,
  `author` char(30) DEFAULT NULL,
  `author_id` int(11) DEFAULT NULL,
  `create_time` char(20) DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of photo
-- ----------------------------
INSERT INTO `photo` VALUES ('8', '风景', null, null, 'test', '/photograph/photo/20170524131442-backimage.jpg', 'testddddddddddddddd', null, null, 'admin', '2', '2017-05-24 13:14:42', null);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` char(20) DEFAULT NULL,
  `sex` bit(1) DEFAULT NULL,
  `age` int(3) DEFAULT NULL,
  `phone` char(20) DEFAULT NULL,
  `email` char(20) DEFAULT NULL,
  `account` char(20) DEFAULT NULL,
  `password` char(30) DEFAULT NULL,
  `is_admin` bit(1) DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  `point` int(11) DEFAULT NULL,
  `create_time` char(20) DEFAULT NULL,
  `modify_time` char(20) DEFAULT NULL,
  `last_login_time` char(20) DEFAULT NULL,
  `use_device` char(50) DEFAULT NULL,
  `photo_type` char(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('2', 'admin', '', '16', '13068501300', 'admin', 'admin', 'admin', null, '', null, '2017-05-06 22:23:23', null, '2017-05-24 16:26:59', 'sky', 'te');
INSERT INTO `user` VALUES ('3', 'test', '\0', '17', '2324', '123', 'test', 'test', null, '', null, '2017-05-06 22:25:42', null, null, 'person', 'te');
INSERT INTO `user` VALUES ('4', '1', '', '20', '1', '1', '1', '1', null, '', null, '2017-05-06 22:28:17', null, null, '1', 'te');
INSERT INTO `user` VALUES ('5', '789', '', '4564', '46', '4564', '456', '456', null, '', null, '2017-05-25 20:45:47', null, '2017-05-25 20:45:58', '464646', null);
