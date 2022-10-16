/*
 Navicat Premium Data Transfer

 Source Server         : mysql_8.0.29
 Source Server Type    : MySQL
 Source Server Version : 80029
 Source Host           : localhost:3306
 Source Schema         : sdtest

 Target Server Type    : MySQL
 Target Server Version : 80029
 File Encoding         : 65001

 Date: 16/10/2022 12:44:15
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for dataset
-- ----------------------------
DROP TABLE IF EXISTS `dataset`;
CREATE TABLE `dataset`  (
  `dataset_name` char(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `dataset_location` char(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `dataset_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NULL DEFAULT NULL,
  `name` char(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`dataset_id`, `dataset_name`) USING BTREE,
  INDEX `FK_user_dataset`(`user_id` ASC, `name` ASC) USING BTREE,
  CONSTRAINT `FK_user_dataset` FOREIGN KEY (`user_id`, `name`) REFERENCES `sd_user` (`user_id`, `name`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of dataset
-- ----------------------------
INSERT INTO `dataset` VALUES ('14.png', 'D:\\Application\\Code\\softwaretest\\software-defect-testing\\SD-test\\src\\main\\resources\\csv\\qfs\\14.png', 1, NULL, 'qfs');
INSERT INTO `dataset` VALUES ('$E{AN2}6KYZLMSEJ6W`(V}0.png', 'D:\\Application\\Code\\softwaretest\\software-defect-testing\\SD-test\\src\\main\\resources\\csv\\qfs\\$E{AN2}6KYZLMSEJ6W`(V}0.png', 2, NULL, 'qfs');
INSERT INTO `dataset` VALUES ('$E{AN2}6KYZLMSEJ6W`(V}0.png', 'D:\\Application\\Code\\softwaretest\\software-defect-testing\\SD-test\\src\\main\\resources\\csv\\qfs\\$E{AN2}6KYZLMSEJ6W`(V}0.png', 3, NULL, 'qfs');
INSERT INTO `dataset` VALUES ('._Lucene.csv', 'D:\\Application\\Code\\softwaretest\\software-defect-testing\\SD-test\\src\\main\\resources\\csv\\null\\._Lucene.csv', 4, NULL, NULL);

-- ----------------------------
-- Table structure for sd_user
-- ----------------------------
DROP TABLE IF EXISTS `sd_user`;
CREATE TABLE `sd_user`  (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `name` char(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `phone_number` char(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`, `name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sd_user
-- ----------------------------
INSERT INTO `sd_user` VALUES (1, 'qfs', '12345', '15232263366');
INSERT INTO `sd_user` VALUES (2, 'abc', '12345', '15236948989');
INSERT INTO `sd_user` VALUES (3, 'jiujiu', '12345', '1520');
INSERT INTO `sd_user` VALUES (4, 'www', '12345', '1520');

SET FOREIGN_KEY_CHECKS = 1;
