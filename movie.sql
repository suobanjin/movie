/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 50712
Source Host           : 127.0.0.1:3306
Source Database       : blog

Target Server Type    : MYSQL
Target Server Version : 50712
File Encoding         : 65001

Date: 2021-11-17 09:41:08
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for blog
-- ----------------------------
DROP TABLE IF EXISTS `blog`;
CREATE TABLE `blog` (
  `id` varchar(100) NOT NULL COMMENT 'id',
  `title` varchar(255) NOT NULL COMMENT '文章标题',
  `header_image_url` varchar(255) NOT NULL COMMENT '首图url',
  `flag` varchar(10) NOT NULL COMMENT '标志（原创等）',
  `views` bigint(255) DEFAULT NULL COMMENT '浏览次数',
  `appreciation` bit(1) DEFAULT NULL COMMENT '是否开启赞赏功能',
  `reprintstatement` bit(1) DEFAULT NULL COMMENT '是否开启转载声明',
  `comment` bit(1) DEFAULT NULL COMMENT '是否开启评论',
  `commend` bit(1) DEFAULT NULL COMMENT '是否推荐',
  `create_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '文章创建时间',
  `update_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '文章更新时间',
  `save` bit(1) NOT NULL COMMENT '判断是保存还是发布',
  `type_id` varchar(255) NOT NULL,
  `comment_count` bigint(20) DEFAULT NULL COMMENT '评论总数',
  `content` longtext NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `like` bigint(20) DEFAULT NULL,
  `author` varchar(15) DEFAULT NULL,
  `from_link` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `blog_type` (`type_id`),
  KEY `blog_id` (`id`) USING BTREE,
  CONSTRAINT `blog_type` FOREIGN KEY (`type_id`) REFERENCES `type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for blog_tags
-- ----------------------------
DROP TABLE IF EXISTS `blog_tags`;
CREATE TABLE `blog_tags` (
  `id` varchar(100) NOT NULL,
  `tag_id` varchar(255) DEFAULT NULL,
  `blog_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` varchar(100) NOT NULL,
  `nickname` varchar(20) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `content` longtext,
  `avatar` varchar(255) DEFAULT NULL,
  `blog_id` varchar(255) DEFAULT NULL,
  `parent_comment_id` varchar(255) DEFAULT NULL,
  `admin_comment` bit(1) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for help
-- ----------------------------
DROP TABLE IF EXISTS `help`;
CREATE TABLE `help` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `phone` varchar(255) NOT NULL,
  `description` text NOT NULL,
  `flag` int(11) NOT NULL,
  `date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `ok` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for movie
-- ----------------------------
DROP TABLE IF EXISTS `movie`;
CREATE TABLE `movie` (
  `id` varchar(255) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `header_image_url` varchar(255) DEFAULT NULL,
  `create_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `description` text,
  `author` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  `movie_link` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tag
-- ----------------------------
DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag` (
  `id` varchar(100) NOT NULL,
  `tag_name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `tag_id` (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for type
-- ----------------------------
DROP TABLE IF EXISTS `type`;
CREATE TABLE `type` (
  `id` varchar(255) NOT NULL,
  `type_name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `type_id` (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` varchar(100) CHARACTER SET utf8mb4 NOT NULL,
  `nickname` varchar(20) NOT NULL,
  `username` varchar(15) NOT NULL,
  `email` varchar(30) NOT NULL,
  `avatar_url` varchar(255) NOT NULL,
  `type` int(11) DEFAULT NULL,
  `create_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idIndex` (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for visit
-- ----------------------------
DROP TABLE IF EXISTS `visit`;
CREATE TABLE `visit` (
  `id` varchar(100) NOT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `visit_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
