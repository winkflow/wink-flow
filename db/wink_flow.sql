/*
 Navicat MySQL Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 50721
 Source Host           : 127.0.0.1:3306
 Source Schema         : wink_flow

 Target Server Type    : MySQL
 Target Server Version : 50721
 File Encoding         : 65001

 Date: 13/11/2018 22:28:25
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for wink_resource_permission
-- ----------------------------
DROP TABLE IF EXISTS `wink_resource_permission`;
CREATE TABLE `wink_resource_permission` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'primary key',
  `pid` bigint(20) DEFAULT NULL,
  `path` varchar(100) COLLATE utf8mb4_bin NOT NULL,
  `method` varchar(10) COLLATE utf8mb4_bin DEFAULT NULL,
  `permission` json NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of wink_resource_permission
-- ----------------------------
BEGIN;
INSERT INTO `wink_resource_permission` VALUES (1, NULL, '/actuator/**', NULL, '[\"PLAT_ADMIN\"]', '2018-11-07 20:54:12', '2018-11-07 20:54:15', 0);
INSERT INTO `wink_resource_permission` VALUES (2, 1, '/actuator/gateway/routes', NULL, '[\"PLAT_ROUTE_MANAGER\"]', '2018-11-07 20:54:12', '2018-11-07 20:54:15', 0);
INSERT INTO `wink_resource_permission` VALUES (3, NULL, '/wink-server/status', NULL, '[]', '2018-11-13 21:55:30', '2018-11-13 21:55:36', 0);
COMMIT;

-- ----------------------------
-- Table structure for wink_user
-- ----------------------------
DROP TABLE IF EXISTS `wink_user`;
CREATE TABLE `wink_user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'primary key',
  `user_name` varchar(50) COLLATE utf8mb4_bin NOT NULL,
  `password` varchar(100) COLLATE utf8mb4_bin NOT NULL,
  `nickname` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of wink_user
-- ----------------------------
BEGIN;
INSERT INTO `wink_user` VALUES (1, 'admin', '$2a$04$fkZvovrUnlceqHQN1iWws.IpGC.IdMHuEMhTz3wekPGn.pN.t2NI2', 'admin', '2018-11-05 13:58:42', '2018-11-05 13:58:45', 0);
INSERT INTO `wink_user` VALUES (2, 'user', '$2a$04$fkZvovrUnlceqHQN1iWws.IpGC.IdMHuEMhTz3wekPGn.pN.t2NI2', 'admin', '2018-11-05 13:58:42', '2018-11-05 13:58:45', 0);
COMMIT;

-- ----------------------------
-- Table structure for wink_user_permission
-- ----------------------------
DROP TABLE IF EXISTS `wink_user_permission`;
CREATE TABLE `wink_user_permission` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'primary key',
  `user_id` bigint(20) NOT NULL,
  `project_id` bigint(20) DEFAULT NULL,
  `permission` json NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of wink_user_permission
-- ----------------------------
BEGIN;
INSERT INTO `wink_user_permission` VALUES (1, 1, NULL, '[\"PLAT_ADMIN\"]', '2018-11-05 14:00:47', '2018-11-05 14:00:54', 0);
INSERT INTO `wink_user_permission` VALUES (2, 2, NULL, '[\"PLAT_ROUTE_MANAGER\"]', '2018-11-09 14:14:44', '2018-11-09 14:14:48', 0);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
