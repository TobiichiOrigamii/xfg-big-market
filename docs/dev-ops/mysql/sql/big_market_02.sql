/*
 Navicat Premium Data Transfer

 Source Server         : DockerMySQL
 Source Server Type    : MySQL
 Source Server Version : 80032 (8.0.32)
 Source Host           : localhost:3306
 Source Schema         : big_market_02

 Target Server Type    : MySQL
 Target Server Version : 80032 (8.0.32)
 File Encoding         : 65001

 Date: 07/11/2024 22:46:13
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for raffle_activity_account
-- ----------------------------
DROP TABLE IF EXISTS `raffle_activity_account`;
CREATE TABLE `raffle_activity_account`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
  `activity_id` bigint NOT NULL COMMENT '活动ID',
  `total_count` int NOT NULL COMMENT '总次数',
  `total_count_surplus` int NOT NULL COMMENT '总次数-剩余',
  `day_count` int NOT NULL COMMENT '日次数',
  `day_count_surplus` int NOT NULL COMMENT '日次数-剩余',
  `month_count` int NOT NULL COMMENT '月次数',
  `month_count_surplus` int NOT NULL COMMENT '月次数-剩余',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_user_id_activity_id`(`user_id` ASC, `activity_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '抽奖活动账户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of raffle_activity_account
-- ----------------------------
INSERT INTO `raffle_activity_account` VALUES (2, 'origami', 100301, 4, 3, 4, 3, 4, 3, '2024-03-23 12:40:56', '2024-03-23 13:16:40');
INSERT INTO `raffle_activity_account` VALUES (3, 'origami1', 100301, 10, 9, 10, 9, 10, 9, '2024-05-03 16:01:44', '2024-05-04 12:51:32');
INSERT INTO `raffle_activity_account` VALUES (4, 'origami2', 100301, 20, 5, 20, 5, 20, 5, '2024-05-04 12:52:58', '2024-05-04 13:57:32');
INSERT INTO `raffle_activity_account` VALUES (5, 'user001', 100301, 100, 91, 100, 91, 100, 91, '2024-05-24 22:30:54', '2024-05-30 07:22:10');
INSERT INTO `raffle_activity_account` VALUES (6, 'user002', 100301, 200, 200, 200, 200, 200, 200, '2024-05-24 22:30:54', '2024-05-27 22:42:17');
INSERT INTO `raffle_activity_account` VALUES (7, 'origami22', 100301, 1, 1, 1, 1, 1, 1, '2024-06-22 09:55:08', '2024-06-22 09:55:08');
INSERT INTO `raffle_activity_account` VALUES (8, 'origami23', 100301, 1, 1, 1, 1, 1, 1, '2024-06-22 09:57:00', '2024-06-22 09:57:00');
INSERT INTO `raffle_activity_account` VALUES (9, 'origami24', 100301, 3, 0, 3, 0, 3, 0, '2024-06-22 09:59:19', '2024-06-22 09:59:55');
INSERT INTO `raffle_activity_account` VALUES (10, 'origami25', 100301, 6, 0, 6, 0, 6, 0, '2024-06-22 10:07:41', '2024-06-22 10:20:54');
INSERT INTO `raffle_activity_account` VALUES (11, 'origami100', 100301, 59, 44, 59, 44, 59, 44, '2024-06-22 11:30:38', '2024-06-22 11:37:25');
INSERT INTO `raffle_activity_account` VALUES (12, 'origami101', 100301, 57, 54, 57, 54, 57, 54, '2024-06-22 11:44:50', '2024-06-22 11:53:25');

-- ----------------------------
-- Table structure for raffle_activity_account_day
-- ----------------------------
DROP TABLE IF EXISTS `raffle_activity_account_day`;
CREATE TABLE `raffle_activity_account_day`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
  `activity_id` bigint NOT NULL COMMENT '活动ID',
  `day` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '日期（yyyy-mm-dd）',
  `day_count` int NOT NULL COMMENT '日次数',
  `day_count_surplus` int NOT NULL COMMENT '日次数-剩余',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_user_id_activity_id_day`(`user_id` ASC, `activity_id` ASC, `day` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '抽奖活动账户表-日次数' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of raffle_activity_account_day
-- ----------------------------
INSERT INTO `raffle_activity_account_day` VALUES (1, 'origami1', 100301, '2024-05-04', 10, 9, '2024-05-04 12:51:32', '2024-05-04 12:51:32');
INSERT INTO `raffle_activity_account_day` VALUES (2, 'origami2', 100301, '2024-05-04', 20, 5, '2024-05-04 12:53:01', '2024-05-04 13:57:32');
INSERT INTO `raffle_activity_account_day` VALUES (3, 'user001', 100301, '2024-05-24', 100, 96, '2024-05-24 22:31:47', '2024-05-24 22:34:37');
INSERT INTO `raffle_activity_account_day` VALUES (4, 'user001', 100301, '2024-05-27', 100, 97, '2024-05-27 22:39:24', '2024-05-27 22:42:17');
INSERT INTO `raffle_activity_account_day` VALUES (5, 'user001', 100301, '2024-05-30', 100, 98, '2024-05-30 07:20:49', '2024-05-30 07:22:10');
INSERT INTO `raffle_activity_account_day` VALUES (6, 'origami24', 100301, '2024-06-22', 3, 0, '2024-06-22 09:59:40', '2024-06-22 09:59:55');
INSERT INTO `raffle_activity_account_day` VALUES (7, 'origami25', 100301, '2024-06-22', 6, 0, '2024-06-22 10:07:54', '2024-06-22 10:20:54');
INSERT INTO `raffle_activity_account_day` VALUES (8, 'origami100', 100301, '2024-06-22', 59, 44, '2024-06-22 11:31:43', '2024-06-22 11:37:25');
INSERT INTO `raffle_activity_account_day` VALUES (9, 'origami101', 100301, '2024-06-22', 57, 54, '2024-06-22 11:44:59', '2024-06-22 11:53:25');

-- ----------------------------
-- Table structure for raffle_activity_account_month
-- ----------------------------
DROP TABLE IF EXISTS `raffle_activity_account_month`;
CREATE TABLE `raffle_activity_account_month`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
  `activity_id` bigint NOT NULL COMMENT '活动ID',
  `month` varchar(7) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '月（yyyy-mm）',
  `month_count` int NOT NULL COMMENT '月次数',
  `month_count_surplus` int NOT NULL COMMENT '月次数-剩余',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_user_id_activity_id_month`(`user_id` ASC, `activity_id` ASC, `month` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '抽奖活动账户表-月次数' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of raffle_activity_account_month
-- ----------------------------
INSERT INTO `raffle_activity_account_month` VALUES (1, 'origami1', 100301, '2024-05', 10, 9, '2024-05-04 12:51:32', '2024-05-04 12:51:32');
INSERT INTO `raffle_activity_account_month` VALUES (2, 'origami2', 100301, '2024-05', 20, 5, '2024-05-04 12:53:01', '2024-05-04 13:57:32');
INSERT INTO `raffle_activity_account_month` VALUES (3, 'user001', 100301, '2024-05', 100, 91, '2024-05-24 22:31:47', '2024-05-30 07:22:10');
INSERT INTO `raffle_activity_account_month` VALUES (4, 'origami24', 100301, '2024-06', 3, 0, '2024-06-22 09:59:40', '2024-06-22 09:59:55');
INSERT INTO `raffle_activity_account_month` VALUES (5, 'origami25', 100301, '2024-06', 6, 0, '2024-06-22 10:07:54', '2024-06-22 10:20:54');
INSERT INTO `raffle_activity_account_month` VALUES (6, 'origami100', 100301, '2024-06', 59, 44, '2024-06-22 11:31:43', '2024-06-22 11:37:25');
INSERT INTO `raffle_activity_account_month` VALUES (7, 'origami101', 100301, '2024-06', 57, 54, '2024-06-22 11:44:59', '2024-06-22 11:53:25');

-- ----------------------------
-- Table structure for raffle_activity_order_000
-- ----------------------------
DROP TABLE IF EXISTS `raffle_activity_order_000`;
CREATE TABLE `raffle_activity_order_000`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
  `sku` bigint NOT NULL COMMENT '商品sku',
  `activity_id` bigint NOT NULL COMMENT '活动ID',
  `activity_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '活动名称',
  `strategy_id` bigint NOT NULL COMMENT '抽奖策略ID',
  `order_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单ID',
  `order_time` datetime NOT NULL COMMENT '下单时间',
  `total_count` int NOT NULL COMMENT '总次数',
  `day_count` int NOT NULL COMMENT '日次数',
  `month_count` int NOT NULL COMMENT '月次数',
  `pay_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '支付金额【积分】',
  `state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'complete' COMMENT '订单状态（complete）',
  `out_business_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '业务仿重ID - 外部透传的，确保幂等',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_order_id`(`order_id` ASC) USING BTREE,
  UNIQUE INDEX `uq_out_business_no`(`out_business_no` ASC) USING BTREE,
  INDEX `idx_user_id_activity_id`(`user_id` ASC, `activity_id` ASC, `state` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '抽奖活动单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of raffle_activity_order_000
-- ----------------------------
INSERT INTO `raffle_activity_order_000` VALUES (1, 'origami1', 9011, 100301, '测试活动', 100006, '831917125310', '2024-05-03 08:01:44', 10, 10, 10, NULL, 'completed', 'origami1_sku_20240503', '2024-05-03 16:01:44', '2024-05-03 16:01:44');
INSERT INTO `raffle_activity_order_000` VALUES (2, 'user001', 9011, 100301, '测试活动', 100006, '923549663927', '2024-05-24 14:30:55', 100, 100, 100, NULL, 'completed', 'user001_sku_20240524', '2024-05-24 22:30:54', '2024-05-24 22:30:54');
INSERT INTO `raffle_activity_order_000` VALUES (3, 'origami25', 9014, 100301, '测试活动', 100006, '155563940984', '2024-06-22 02:07:41', 1, 1, 1, 0.00, 'completed', 'origami25_sku_20240622', '2024-06-22 10:07:41', '2024-06-22 10:07:41');
INSERT INTO `raffle_activity_order_000` VALUES (4, 'origami25', 9013, 100301, '测试活动', 100006, '760862950352', '2024-06-22 02:11:51', 5, 5, 5, 20.00, 'completed', '404066146511', '2024-06-22 10:11:50', '2024-06-22 10:11:50');

-- ----------------------------
-- Table structure for raffle_activity_order_001
-- ----------------------------
DROP TABLE IF EXISTS `raffle_activity_order_001`;
CREATE TABLE `raffle_activity_order_001`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
  `sku` bigint NOT NULL COMMENT '商品sku',
  `activity_id` bigint NOT NULL COMMENT '活动ID',
  `activity_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '活动名称',
  `strategy_id` bigint NOT NULL COMMENT '抽奖策略ID',
  `order_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单ID',
  `order_time` datetime NOT NULL COMMENT '下单时间',
  `total_count` int NOT NULL COMMENT '总次数',
  `day_count` int NOT NULL COMMENT '日次数',
  `month_count` int NOT NULL COMMENT '月次数',
  `pay_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '支付金额【积分】',
  `state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'complete' COMMENT '订单状态（complete）',
  `out_business_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '业务仿重ID - 外部透传的，确保幂等',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_order_id`(`order_id` ASC) USING BTREE,
  UNIQUE INDEX `uq_out_business_no`(`out_business_no` ASC) USING BTREE,
  INDEX `idx_user_id_activity_id`(`user_id` ASC, `activity_id` ASC, `state` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 271 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '抽奖活动单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of raffle_activity_order_001
-- ----------------------------
INSERT INTO `raffle_activity_order_001` VALUES (3, 'origami', 9011, 100301, '测试活动', 100006, '383240888158', '2024-03-23 04:38:23', 1, 1, 1, NULL, 'completed', '700091009111', '2024-03-23 12:38:23', '2024-03-23 12:38:23');
INSERT INTO `raffle_activity_order_001` VALUES (4, 'user002', 9011, 100301, '测试活动', 100006, '165083654323', '2024-05-24 14:30:55', 100, 100, 100, NULL, 'completed', 'user002_sku_20240524', '2024-05-24 22:30:54', '2024-05-24 22:30:54');
INSERT INTO `raffle_activity_order_001` VALUES (261, 'user002', 9011, 100301, '测试活动', 100006, '762873325216', '2024-05-27 14:42:18', 100, 100, 100, NULL, 'completed', 'user002_sku_20240527', '2024-05-27 22:42:17', '2024-05-27 22:42:17');
INSERT INTO `raffle_activity_order_001` VALUES (262, 'origami24', 9014, 100301, '测试活动', 100006, '887306802600', '2024-06-22 01:59:20', 1, 1, 1, 0.00, 'completed', 'origami24_sku_20240622', '2024-06-22 09:59:19', '2024-06-22 09:59:19');
INSERT INTO `raffle_activity_order_001` VALUES (263, 'origami24', 9014, 100301, '测试活动', 100006, '440886795182', '2024-06-22 01:59:25', 1, 1, 1, 5.00, 'completed', '750705533475', '2024-06-22 09:59:24', '2024-06-22 09:59:24');
INSERT INTO `raffle_activity_order_001` VALUES (264, 'origami24', 9014, 100301, '测试活动', 100006, '282163537675', '2024-06-22 01:59:27', 1, 1, 1, 5.00, 'completed', '738796690867', '2024-06-22 09:59:26', '2024-06-22 09:59:26');
INSERT INTO `raffle_activity_order_001` VALUES (265, 'origami100', 9014, 100301, '测试活动', 100006, '197607904157', '2024-06-22 03:30:38', 1, 1, 1, 0.00, 'completed', 'origami100_sku_20240622', '2024-06-22 11:30:38', '2024-06-22 11:30:38');
INSERT INTO `raffle_activity_order_001` VALUES (266, 'origami100', 9014, 100301, '测试活动', 100006, '505791980195', '2024-06-22 03:33:39', 1, 1, 1, 5.00, 'completed', '761630034156', '2024-06-22 11:33:39', '2024-06-22 11:33:39');
INSERT INTO `raffle_activity_order_001` VALUES (267, 'origami100', 9014, 100301, '测试活动', 100006, '958788244292', '2024-06-22 03:33:41', 1, 1, 1, 5.00, 'completed', '698976521368', '2024-06-22 11:33:41', '2024-06-22 11:33:41');
INSERT INTO `raffle_activity_order_001` VALUES (268, 'origami100', 9014, 100301, '测试活动', 100006, '302080018611', '2024-06-22 03:33:42', 1, 1, 1, 5.00, 'completed', '313351791785', '2024-06-22 11:33:42', '2024-06-22 11:33:42');
INSERT INTO `raffle_activity_order_001` VALUES (269, 'origami100', 9013, 100301, '测试活动', 100006, '586835924975', '2024-06-22 03:33:44', 5, 5, 5, 20.00, 'completed', '074754086575', '2024-06-22 11:33:44', '2024-06-22 11:33:44');
INSERT INTO `raffle_activity_order_001` VALUES (270, 'origami100', 9012, 100301, '测试活动', 100006, '333711998605', '2024-06-22 03:34:23', 50, 50, 50, 100.00, 'completed', '776248180705', '2024-06-22 11:34:22', '2024-06-22 11:34:22');

-- ----------------------------
-- Table structure for raffle_activity_order_002
-- ----------------------------
DROP TABLE IF EXISTS `raffle_activity_order_002`;
CREATE TABLE `raffle_activity_order_002`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
  `sku` bigint NOT NULL COMMENT '商品sku',
  `activity_id` bigint NOT NULL COMMENT '活动ID',
  `activity_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '活动名称',
  `strategy_id` bigint NOT NULL COMMENT '抽奖策略ID',
  `order_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单ID',
  `order_time` datetime NOT NULL COMMENT '下单时间',
  `total_count` int NOT NULL COMMENT '总次数',
  `day_count` int NOT NULL COMMENT '日次数',
  `month_count` int NOT NULL COMMENT '月次数',
  `pay_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '支付金额【积分】',
  `state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'complete' COMMENT '订单状态（complete）',
  `out_business_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '业务仿重ID - 外部透传的，确保幂等',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_order_id`(`order_id` ASC) USING BTREE,
  UNIQUE INDEX `uq_out_business_no`(`out_business_no` ASC) USING BTREE,
  INDEX `idx_user_id_activity_id`(`user_id` ASC, `activity_id` ASC, `state` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '抽奖活动单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of raffle_activity_order_002
-- ----------------------------
INSERT INTO `raffle_activity_order_002` VALUES (1, 'origami23', 9014, 100301, '测试活动', 100006, '695621433142', '2024-06-22 01:57:01', 1, 1, 1, 0.00, 'completed', 'origami23_sku_20240622', '2024-06-22 09:57:00', '2024-06-22 09:57:00');
INSERT INTO `raffle_activity_order_002` VALUES (2, 'origami101', 9014, 100301, '测试活动', 100006, '979296847664', '2024-06-22 03:44:50', 1, 1, 1, 0.00, 'completed', 'origami101_sku_20240622', '2024-06-22 11:44:50', '2024-06-22 11:44:50');
INSERT INTO `raffle_activity_order_002` VALUES (3, 'origami101', 9014, 100301, '测试活动', 100006, '260145574246', '2024-06-22 03:45:46', 1, 1, 1, 5.00, 'completed', '647675002899', '2024-06-22 11:45:46', '2024-06-22 11:45:46');
INSERT INTO `raffle_activity_order_002` VALUES (4, 'origami101', 9013, 100301, '测试活动', 100006, '062133331093', '2024-06-22 03:45:49', 5, 5, 5, 20.00, 'completed', '945591704953', '2024-06-22 11:45:48', '2024-06-22 11:45:49');
INSERT INTO `raffle_activity_order_002` VALUES (5, 'origami101', 9012, 100301, '测试活动', 100006, '702656117291', '2024-06-22 03:46:07', 50, 50, 50, 100.00, 'completed', '371514189773', '2024-06-22 11:46:07', '2024-06-22 11:46:07');

-- ----------------------------
-- Table structure for raffle_activity_order_003
-- ----------------------------
DROP TABLE IF EXISTS `raffle_activity_order_003`;
CREATE TABLE `raffle_activity_order_003`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
  `sku` bigint NOT NULL COMMENT '商品sku',
  `activity_id` bigint NOT NULL COMMENT '活动ID',
  `activity_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '活动名称',
  `strategy_id` bigint NOT NULL COMMENT '抽奖策略ID',
  `order_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单ID',
  `order_time` datetime NOT NULL COMMENT '下单时间',
  `total_count` int NOT NULL COMMENT '总次数',
  `day_count` int NOT NULL COMMENT '日次数',
  `month_count` int NOT NULL COMMENT '月次数',
  `pay_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '支付金额【积分】',
  `state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'complete' COMMENT '订单状态（complete）',
  `out_business_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '业务仿重ID - 外部透传的，确保幂等',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_order_id`(`order_id` ASC) USING BTREE,
  UNIQUE INDEX `uq_out_business_no`(`out_business_no` ASC) USING BTREE,
  INDEX `idx_user_id_activity_id`(`user_id` ASC, `activity_id` ASC, `state` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '抽奖活动单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of raffle_activity_order_003
-- ----------------------------
INSERT INTO `raffle_activity_order_003` VALUES (2, 'origami2', 9011, 100301, '测试活动', 100006, '942458887115', '2024-05-04 05:07:53', 10, 10, 10, NULL, 'completed', 'origami2_sku_20240504', '2024-05-04 13:07:53', '2024-05-04 13:07:53');
INSERT INTO `raffle_activity_order_003` VALUES (3, 'origami22', 9014, 100301, '测试活动', 100006, '384557932017', '2024-06-22 01:55:09', 1, 1, 1, 0.00, 'completed', 'origami22_sku_20240622', '2024-06-22 09:55:08', '2024-06-22 09:55:08');

-- ----------------------------
-- Table structure for task
-- ----------------------------
DROP TABLE IF EXISTS `task`;
CREATE TABLE `task`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
  `topic` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '消息主题',
  `message_id` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '消息编号',
  `message` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '消息主体',
  `state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'create' COMMENT '任务状态；create-创建、completed-完成、fail-失败',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_message_id`(`message_id` ASC) USING BTREE,
  INDEX `idx_state`(`state` ASC) USING BTREE,
  INDEX `idx_create_time`(`update_time` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 93 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '任务表，发送MQ' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of task
-- ----------------------------
INSERT INTO `task` VALUES (1, 'origami1', 'send_rebate', '54825531405', '{\"data\":{\"bizId\":\"origami1_sku_20240503\",\"rebateConfig\":\"9011\",\"rebateType\":\"sku\",\"userId\":\"origami1\"},\"id\":\"54825531405\",\"timestamp\":1714723302543}', 'completed', '2024-05-03 16:01:43', '2024-05-03 16:01:43');
INSERT INTO `task` VALUES (2, 'origami1', 'send_rebate', '43189560552', '{\"data\":{\"bizId\":\"origami1_integral_20240503\",\"rebateConfig\":\"10\",\"rebateType\":\"integral\",\"userId\":\"origami1\"},\"id\":\"43189560552\",\"timestamp\":1714723302551}', 'completed', '2024-05-03 16:01:43', '2024-05-03 16:01:43');
INSERT INTO `task` VALUES (3, 'origami1', 'send_award', '68594836687', '{\"data\":{\"awardId\":104,\"awardTitle\":\"小米台灯\",\"userId\":\"origami1\"},\"id\":\"68594836687\",\"timestamp\":1714798292665}', 'completed', '2024-05-04 12:51:32', '2024-05-04 12:51:32');
INSERT INTO `task` VALUES (4, 'origami2', 'send_rebate', '99573670989', '{\"data\":{\"bizId\":\"origami2_sku_20240504\",\"rebateConfig\":\"9011\",\"rebateType\":\"sku\",\"userId\":\"origami2\"},\"id\":\"99573670989\",\"timestamp\":1714798377892}', 'completed', '2024-05-04 12:52:57', '2024-05-04 12:52:57');
INSERT INTO `task` VALUES (5, 'origami2', 'send_rebate', '86991437226', '{\"data\":{\"bizId\":\"origami2_integral_20240504\",\"rebateConfig\":\"10\",\"rebateType\":\"integral\",\"userId\":\"origami2\"},\"id\":\"86991437226\",\"timestamp\":1714798377895}', 'completed', '2024-05-04 12:52:57', '2024-05-04 12:52:57');
INSERT INTO `task` VALUES (6, 'origami2', 'send_award', '81688408328', '{\"data\":{\"awardId\":102,\"awardTitle\":\"OpenAI会员卡\",\"userId\":\"origami2\"},\"id\":\"81688408328\",\"timestamp\":1714798382103}', 'completed', '2024-05-04 12:53:02', '2024-05-04 12:53:02');
INSERT INTO `task` VALUES (7, 'origami2', 'send_award', '42907626790', '{\"data\":{\"awardId\":104,\"awardTitle\":\"小米台灯\",\"userId\":\"origami2\"},\"id\":\"42907626790\",\"timestamp\":1714798722679}', 'completed', '2024-05-04 12:58:42', '2024-05-04 12:58:42');
INSERT INTO `task` VALUES (8, 'origami2', 'send_award', '85155176747', '{\"data\":{\"awardId\":102,\"awardTitle\":\"OpenAI会员卡\",\"userId\":\"origami2\"},\"id\":\"85155176747\",\"timestamp\":1714798808373}', 'completed', '2024-05-04 13:00:08', '2024-05-04 13:00:08');
INSERT INTO `task` VALUES (9, 'origami2', 'send_award', '76843118145', '{\"data\":{\"awardId\":101,\"awardTitle\":\"随机积分\",\"userId\":\"origami2\"},\"id\":\"76843118145\",\"timestamp\":1714799138341}', 'completed', '2024-05-04 13:05:38', '2024-05-04 13:05:38');
INSERT INTO `task` VALUES (10, 'origami2', 'send_award', '60195171213', '{\"data\":{\"awardId\":101,\"awardTitle\":\"随机积分\",\"userId\":\"origami2\"},\"id\":\"60195171213\",\"timestamp\":1714799145979}', 'completed', '2024-05-04 13:05:45', '2024-05-04 13:05:45');
INSERT INTO `task` VALUES (11, 'origami2', 'send_award', '94121994871', '{\"data\":{\"awardId\":106,\"awardTitle\":\"轻奢办公椅\",\"userId\":\"origami2\"},\"id\":\"94121994871\",\"timestamp\":1714799152785}', 'completed', '2024-05-04 13:05:52', '2024-05-04 13:05:52');
INSERT INTO `task` VALUES (12, 'origami2', 'send_award', '84058820142', '{\"data\":{\"awardId\":108,\"awardTitle\":\"暴走玩偶\",\"userId\":\"origami2\"},\"id\":\"84058820142\",\"timestamp\":1714799159912}', 'completed', '2024-05-04 13:05:59', '2024-05-04 13:05:59');
INSERT INTO `task` VALUES (13, 'origami2', 'send_award', '43171330176', '{\"data\":{\"awardId\":107,\"awardTitle\":\"小霸王游戏机\",\"userId\":\"origami2\"},\"id\":\"43171330176\",\"timestamp\":1714799166780}', 'completed', '2024-05-04 13:06:06', '2024-05-04 13:06:06');
INSERT INTO `task` VALUES (14, 'origami2', 'send_award', '84240245488', '{\"data\":{\"awardId\":108,\"awardTitle\":\"暴走玩偶\",\"userId\":\"origami2\"},\"id\":\"84240245488\",\"timestamp\":1714799177736}', 'completed', '2024-05-04 13:06:17', '2024-05-04 13:06:17');
INSERT INTO `task` VALUES (15, 'origami2', 'send_award', '95184575016', '{\"data\":{\"awardId\":103,\"awardTitle\":\"支付优惠券\",\"userId\":\"origami2\"},\"id\":\"95184575016\",\"timestamp\":1714799184841}', 'completed', '2024-05-04 13:06:24', '2024-05-04 13:06:24');
INSERT INTO `task` VALUES (16, 'origami2', 'send_rebate', '18731063175', '{\"data\":{\"bizId\":\"origami2_sku_20240504\",\"rebateConfig\":\"9011\",\"rebateType\":\"sku\",\"userId\":\"origami2\"},\"id\":\"18731063175\",\"timestamp\":1714799273317}', 'completed', '2024-05-04 13:07:53', '2024-05-04 13:07:53');
INSERT INTO `task` VALUES (17, 'origami2', 'send_rebate', '55910936496', '{\"data\":{\"bizId\":\"origami2_integral_20240504\",\"rebateConfig\":\"10\",\"rebateType\":\"integral\",\"userId\":\"origami2\"},\"id\":\"55910936496\",\"timestamp\":1714799273318}', 'completed', '2024-05-04 13:07:53', '2024-05-04 13:07:53');
INSERT INTO `task` VALUES (18, 'origami2', 'send_award', '62395221004', '{\"data\":{\"awardId\":103,\"awardTitle\":\"支付优惠券\",\"userId\":\"origami2\"},\"id\":\"62395221004\",\"timestamp\":1714799286429}', 'completed', '2024-05-04 13:08:06', '2024-05-04 13:08:06');
INSERT INTO `task` VALUES (19, 'origami2', 'send_award', '82372459539', '{\"data\":{\"awardId\":103,\"awardTitle\":\"支付优惠券\",\"userId\":\"origami2\"},\"id\":\"82372459539\",\"timestamp\":1714802223761}', 'completed', '2024-05-04 13:57:03', '2024-05-04 13:57:03');
INSERT INTO `task` VALUES (20, 'origami2', 'send_award', '56572252891', '{\"data\":{\"awardId\":103,\"awardTitle\":\"支付优惠券\",\"userId\":\"origami2\"},\"id\":\"56572252891\",\"timestamp\":1714802231616}', 'completed', '2024-05-04 13:57:11', '2024-05-04 13:57:11');
INSERT INTO `task` VALUES (21, 'origami2', 'send_award', '40606839519', '{\"data\":{\"awardId\":103,\"awardTitle\":\"支付优惠券\",\"userId\":\"origami2\"},\"id\":\"40606839519\",\"timestamp\":1714802240195}', 'completed', '2024-05-04 13:57:20', '2024-05-04 13:57:20');
INSERT INTO `task` VALUES (22, 'origami2', 'send_award', '99508680162', '{\"data\":{\"awardId\":102,\"awardTitle\":\"OpenAI会员卡\",\"userId\":\"origami2\"},\"id\":\"99508680162\",\"timestamp\":1714802252287}', 'completed', '2024-05-04 13:57:32', '2024-05-04 13:57:32');
INSERT INTO `task` VALUES (23, 'user001', 'send_rebate', '73773124323', '{\"data\":{\"bizId\":\"user001_sku_20240524\",\"rebateConfig\":\"9011\",\"rebateType\":\"sku\",\"userId\":\"user001\"},\"id\":\"73773124323\",\"timestamp\":1716560709722}', 'completed', '2024-05-24 22:25:09', '2024-05-24 22:25:10');
INSERT INTO `task` VALUES (24, 'user001', 'send_rebate', '02391103632', '{\"data\":{\"bizId\":\"user001_integral_20240524\",\"rebateConfig\":\"10\",\"rebateType\":\"integral\",\"userId\":\"user001\"},\"id\":\"02391103632\",\"timestamp\":1716560709725}', 'completed', '2024-05-24 22:25:09', '2024-05-24 22:25:10');
INSERT INTO `task` VALUES (25, 'user002', 'send_rebate', '49594929112', '{\"data\":{\"bizId\":\"user002_sku_20240524\",\"rebateConfig\":\"9011\",\"rebateType\":\"sku\",\"userId\":\"user002\"},\"id\":\"49594929112\",\"timestamp\":1716560951288}', 'completed', '2024-05-24 22:29:11', '2024-05-24 22:29:11');
INSERT INTO `task` VALUES (26, 'user002', 'send_rebate', '57121773264', '{\"data\":{\"bizId\":\"user002_integral_20240524\",\"rebateConfig\":\"10\",\"rebateType\":\"integral\",\"userId\":\"user002\"},\"id\":\"57121773264\",\"timestamp\":1716560951292}', 'completed', '2024-05-24 22:29:11', '2024-05-24 22:29:11');
INSERT INTO `task` VALUES (27, 'user001', 'send_award', '97971125221', '{\"data\":{\"awardConfig\":\"0.01,1\",\"awardId\":101,\"awardTitle\":\"随机积分\",\"orderId\":\"391668886086\",\"userId\":\"user001\"},\"id\":\"97971125221\",\"timestamp\":1716561114657}', 'completed', '2024-05-24 22:31:55', '2024-05-24 22:31:55');
INSERT INTO `task` VALUES (28, 'user001', 'send_award', '89552334625', '{\"data\":{\"awardConfig\":\"0.01,1\",\"awardId\":101,\"awardTitle\":\"随机积分\",\"orderId\":\"179191128326\",\"userId\":\"user001\"},\"id\":\"89552334625\",\"timestamp\":1716561182606}', 'completed', '2024-05-24 22:33:02', '2024-05-24 22:33:02');
INSERT INTO `task` VALUES (29, 'user001', 'send_award', '12743392424', '{\"data\":{\"awardConfig\":\"0.01,1\",\"awardId\":101,\"awardTitle\":\"随机积分\",\"orderId\":\"320625987421\",\"userId\":\"user001\"},\"id\":\"12743392424\",\"timestamp\":1716561244275}', 'completed', '2024-05-24 22:34:04', '2024-05-24 22:34:04');
INSERT INTO `task` VALUES (30, 'user001', 'send_award', '50175032521', '{\"data\":{\"awardConfig\":\"0.01,1\",\"awardId\":101,\"awardTitle\":\"随机积分\",\"orderId\":\"290879207548\",\"userId\":\"user001\"},\"id\":\"50175032521\",\"timestamp\":1716561277990}', 'completed', '2024-05-24 22:34:38', '2024-05-24 22:34:38');
INSERT INTO `task` VALUES (31, 'user002', 'send_rebate', '33869647355', '{\"data\":{\"bizId\":\"user002_sku_20240527\",\"rebateConfig\":\"9011\",\"rebateType\":\"sku\",\"userId\":\"user002\"},\"id\":\"33869647355\",\"timestamp\":1716820626795}', 'completed', '2024-05-27 22:37:07', '2024-05-27 22:37:07');
INSERT INTO `task` VALUES (32, 'user002', 'send_rebate', '66382556237', '{\"data\":{\"bizId\":\"user002_integral_20240527\",\"rebateConfig\":\"10\",\"rebateType\":\"integral\",\"userId\":\"user002\"},\"id\":\"66382556237\",\"timestamp\":1716820626797}', 'completed', '2024-05-27 22:37:07', '2024-05-27 22:37:07');
INSERT INTO `task` VALUES (33, 'user001', 'send_award', '61143330592', '{\"data\":{\"awardConfig\":\"0.01,1\",\"awardId\":101,\"awardTitle\":\"随机积分\",\"orderId\":\"539811500095\",\"userId\":\"user001\"},\"id\":\"61143330592\",\"timestamp\":1716820764302}', 'completed', '2024-05-27 22:39:24', '2024-05-27 22:39:24');
INSERT INTO `task` VALUES (34, 'user001', 'send_award', '93461178922', '{\"data\":{\"awardConfig\":\"0.01,1\",\"awardId\":101,\"awardTitle\":\"随机积分\",\"orderId\":\"405619392079\",\"userId\":\"user001\"},\"id\":\"93461178922\",\"timestamp\":1716820886701}', 'completed', '2024-05-27 22:41:26', '2024-05-27 22:41:26');
INSERT INTO `task` VALUES (35, 'user001', 'send_award', '00073222856', '{\"data\":{\"awardConfig\":\"0.01,1\",\"awardId\":101,\"awardTitle\":\"随机积分\",\"orderId\":\"130698860415\",\"userId\":\"user001\"},\"id\":\"00073222856\",\"timestamp\":1716820937997}', 'completed', '2024-05-27 22:42:18', '2024-05-27 22:42:18');
INSERT INTO `task` VALUES (36, 'user001', 'send_award', '79139422874', '{\"data\":{\"awardConfig\":\"0.01,1\",\"awardId\":101,\"awardTitle\":\"随机积分\",\"orderId\":\"777907205658\",\"userId\":\"user001\"},\"id\":\"79139422874\",\"timestamp\":1717024849194}', 'completed', '2024-05-30 07:20:49', '2024-05-30 07:20:49');
INSERT INTO `task` VALUES (37, 'user001', 'send_award', '19870027158', '{\"data\":{\"awardConfig\":\"0.01,1\",\"awardId\":101,\"awardTitle\":\"随机积分\",\"orderId\":\"037106411824\",\"userId\":\"user001\"},\"id\":\"19870027158\",\"timestamp\":1717024930458}', 'completed', '2024-05-30 07:22:10', '2024-05-30 07:22:10');
INSERT INTO `task` VALUES (38, 'origami22', 'send_rebate', '74434256061', '{\"data\":{\"bizId\":\"origami22_sku_20240622\",\"rebateConfig\":\"9014\",\"rebateType\":\"sku\",\"userId\":\"origami22\"},\"id\":\"74434256061\",\"timestamp\":1719021178881}', 'completed', '2024-06-22 09:52:58', '2024-06-22 09:52:58');
INSERT INTO `task` VALUES (39, 'origami22', 'send_rebate', '11615452652', '{\"data\":{\"bizId\":\"origami22_integral_20240622\",\"rebateConfig\":\"10\",\"rebateType\":\"integral\",\"userId\":\"origami22\"},\"id\":\"11615452652\",\"timestamp\":1719021178886}', 'completed', '2024-06-22 09:52:58', '2024-06-22 09:52:58');
INSERT INTO `task` VALUES (40, 'origami23', 'send_rebate', '64531943772', '{\"data\":{\"bizId\":\"origami23_sku_20240622\",\"rebateConfig\":\"9014\",\"rebateType\":\"sku\",\"userId\":\"origami23\"},\"id\":\"64531943772\",\"timestamp\":1719021420428}', 'completed', '2024-06-22 09:57:00', '2024-06-22 09:57:00');
INSERT INTO `task` VALUES (41, 'origami23', 'send_rebate', '32940966305', '{\"data\":{\"bizId\":\"origami23_integral_20240622\",\"rebateConfig\":\"10\",\"rebateType\":\"integral\",\"userId\":\"origami23\"},\"id\":\"32940966305\",\"timestamp\":1719021420431}', 'completed', '2024-06-22 09:57:00', '2024-06-22 09:57:00');
INSERT INTO `task` VALUES (42, 'origami23', 'credit_adjust_success', '24009811387', '{\"data\":{\"amount\":10,\"orderId\":\"724677881464\",\"outBusinessNo\":\"origami23_integral_20240622\",\"userId\":\"origami23\"},\"id\":\"24009811387\",\"timestamp\":1719021420760}', 'completed', '2024-06-22 09:57:00', '2024-06-22 09:57:00');
INSERT INTO `task` VALUES (43, 'origami24', 'send_rebate', '93076650934', '{\"data\":{\"bizId\":\"origami24_sku_20240622\",\"rebateConfig\":\"9014\",\"rebateType\":\"sku\",\"userId\":\"origami24\"},\"id\":\"93076650934\",\"timestamp\":1719021559510}', 'completed', '2024-06-22 09:59:19', '2024-06-22 09:59:19');
INSERT INTO `task` VALUES (44, 'origami24', 'send_rebate', '02279664282', '{\"data\":{\"bizId\":\"origami24_integral_20240622\",\"rebateConfig\":\"10\",\"rebateType\":\"integral\",\"userId\":\"origami24\"},\"id\":\"02279664282\",\"timestamp\":1719021559510}', 'completed', '2024-06-22 09:59:19', '2024-06-22 09:59:19');
INSERT INTO `task` VALUES (45, 'origami24', 'credit_adjust_success', '05811142160', '{\"data\":{\"amount\":10,\"orderId\":\"929908709864\",\"outBusinessNo\":\"origami24_integral_20240622\",\"userId\":\"origami24\"},\"id\":\"05811142160\",\"timestamp\":1719021559672}', 'completed', '2024-06-22 09:59:19', '2024-06-22 09:59:19');
INSERT INTO `task` VALUES (46, 'origami24', 'credit_adjust_success', '28326779079', '{\"data\":{\"amount\":-5.00,\"orderId\":\"209411906995\",\"outBusinessNo\":\"750705533475\",\"userId\":\"origami24\"},\"id\":\"28326779079\",\"timestamp\":1719021564708}', 'completed', '2024-06-22 09:59:24', '2024-06-22 09:59:24');
INSERT INTO `task` VALUES (47, 'origami24', 'credit_adjust_success', '93156684477', '{\"data\":{\"amount\":-5.00,\"orderId\":\"470247343587\",\"outBusinessNo\":\"738796690867\",\"userId\":\"origami24\"},\"id\":\"93156684477\",\"timestamp\":1719021566642}', 'completed', '2024-06-22 09:59:26', '2024-06-22 09:59:26');
INSERT INTO `task` VALUES (48, 'origami24', 'send_award', '96731396756', '{\"data\":{\"awardId\":108,\"awardTitle\":\"暴走玩偶\",\"orderId\":\"837829954453\",\"userId\":\"origami24\"},\"id\":\"96731396756\",\"timestamp\":1719021580424}', 'completed', '2024-06-22 09:59:40', '2024-06-22 09:59:40');
INSERT INTO `task` VALUES (49, 'origami24', 'send_award', '27216247558', '{\"data\":{\"awardId\":108,\"awardTitle\":\"暴走玩偶\",\"orderId\":\"692282780900\",\"userId\":\"origami24\"},\"id\":\"27216247558\",\"timestamp\":1719021588332}', 'completed', '2024-06-22 09:59:48', '2024-06-22 09:59:48');
INSERT INTO `task` VALUES (50, 'origami24', 'send_award', '08301307816', '{\"data\":{\"awardId\":106,\"awardTitle\":\"轻奢办公椅\",\"orderId\":\"998461237887\",\"userId\":\"origami24\"},\"id\":\"08301307816\",\"timestamp\":1719021596087}', 'completed', '2024-06-22 09:59:56', '2024-06-22 09:59:56');
INSERT INTO `task` VALUES (51, 'origami25', 'send_rebate', '99512680174', '{\"data\":{\"bizId\":\"origami25_sku_20240622\",\"rebateConfig\":\"9014\",\"rebateType\":\"sku\",\"userId\":\"origami25\"},\"id\":\"99512680174\",\"timestamp\":1719022060809}', 'completed', '2024-06-22 10:07:40', '2024-06-22 10:07:40');
INSERT INTO `task` VALUES (52, 'origami25', 'send_rebate', '49590114210', '{\"data\":{\"bizId\":\"origami25_integral_20240622\",\"rebateConfig\":\"10\",\"rebateType\":\"integral\",\"userId\":\"origami25\"},\"id\":\"49590114210\",\"timestamp\":1719022060818}', 'completed', '2024-06-22 10:07:40', '2024-06-22 10:07:40');
INSERT INTO `task` VALUES (53, 'origami25', 'credit_adjust_success', '26340747168', '{\"data\":{\"amount\":10,\"orderId\":\"479481818661\",\"outBusinessNo\":\"origami25_integral_20240622\",\"userId\":\"origami25\"},\"id\":\"26340747168\",\"timestamp\":1719022061708}', 'completed', '2024-06-22 10:07:41', '2024-06-22 10:07:41');
INSERT INTO `task` VALUES (54, 'origami25', 'send_award', '40237601404', '{\"data\":{\"awardId\":101,\"awardTitle\":\"随机积分\",\"orderId\":\"067670700350\",\"userId\":\"origami25\"},\"id\":\"40237601404\",\"timestamp\":1719022074559}', 'completed', '2024-06-22 10:07:54', '2024-06-22 10:07:54');
INSERT INTO `task` VALUES (55, 'origami25', 'credit_adjust_success', '13286186376', '{\"data\":{\"amount\":-20.00,\"orderId\":\"981030218405\",\"outBusinessNo\":\"404066146511\",\"userId\":\"origami25\"},\"id\":\"13286186376\",\"timestamp\":1719022310843}', 'completed', '2024-06-22 10:11:50', '2024-06-22 10:11:50');
INSERT INTO `task` VALUES (56, 'origami25', 'send_award', '18165161268', '{\"data\":{\"awardId\":101,\"awardTitle\":\"随机积分\",\"orderId\":\"733298505993\",\"userId\":\"origami25\"},\"id\":\"18165161268\",\"timestamp\":1719022318849}', 'completed', '2024-06-22 10:11:58', '2024-06-22 10:11:58');
INSERT INTO `task` VALUES (57, 'origami25', 'send_award', '40972020012', '{\"data\":{\"awardId\":101,\"awardTitle\":\"随机积分\",\"orderId\":\"148282410120\",\"userId\":\"origami25\"},\"id\":\"40972020012\",\"timestamp\":1719022366759}', 'completed', '2024-06-22 10:12:46', '2024-06-22 10:12:46');
INSERT INTO `task` VALUES (58, 'origami25', 'send_award', '43532053705', '{\"data\":{\"awardId\":101,\"awardTitle\":\"随机积分\",\"orderId\":\"068213439911\",\"userId\":\"origami25\"},\"id\":\"43532053705\",\"timestamp\":1719022642908}', 'completed', '2024-06-22 10:17:22', '2024-06-22 10:17:22');
INSERT INTO `task` VALUES (59, 'origami25', 'send_award', '57020293715', '{\"data\":{\"awardId\":101,\"awardTitle\":\"随机积分\",\"orderId\":\"355946005992\",\"userId\":\"origami25\"},\"id\":\"57020293715\",\"timestamp\":1719022843337}', 'completed', '2024-06-22 10:20:43', '2024-06-22 10:20:43');
INSERT INTO `task` VALUES (60, 'origami25', 'send_award', '83244065969', '{\"data\":{\"awardId\":101,\"awardTitle\":\"随机积分\",\"orderId\":\"010903932739\",\"userId\":\"origami25\"},\"id\":\"83244065969\",\"timestamp\":1719022854058}', 'completed', '2024-06-22 10:20:54', '2024-06-22 10:20:54');
INSERT INTO `task` VALUES (61, 'origami100', 'send_rebate', '38566670630', '{\"data\":{\"bizId\":\"origami100_sku_20240622\",\"rebateConfig\":\"9014\",\"rebateType\":\"sku\",\"userId\":\"origami100\"},\"id\":\"38566670630\",\"timestamp\":1719027037814}', 'completed', '2024-06-22 11:30:37', '2024-06-22 11:30:37');
INSERT INTO `task` VALUES (62, 'origami100', 'send_rebate', '44646981368', '{\"data\":{\"bizId\":\"origami100_integral_20240622\",\"rebateConfig\":\"10\",\"rebateType\":\"integral\",\"userId\":\"origami100\"},\"id\":\"44646981368\",\"timestamp\":1719027037836}', 'completed', '2024-06-22 11:30:37', '2024-06-22 11:30:37');
INSERT INTO `task` VALUES (63, 'origami100', 'credit_adjust_success', '50575383439', '{\"data\":{\"amount\":10,\"orderId\":\"084053486083\",\"outBusinessNo\":\"origami100_integral_20240622\",\"userId\":\"origami100\"},\"id\":\"50575383439\",\"timestamp\":1719027038114}', 'completed', '2024-06-22 11:30:38', '2024-06-22 11:30:38');
INSERT INTO `task` VALUES (64, 'origami100', 'send_award', '93400504062', '{\"data\":{\"awardId\":101,\"awardTitle\":\"随机积分\",\"orderId\":\"438098073325\",\"userId\":\"origami100\"},\"id\":\"93400504062\",\"timestamp\":1719027103972}', 'completed', '2024-06-22 11:31:43', '2024-06-22 11:31:44');
INSERT INTO `task` VALUES (65, 'origami100', 'credit_adjust_success', '25836549674', '{\"data\":{\"amount\":-5.00,\"orderId\":\"615817318859\",\"outBusinessNo\":\"761630034156\",\"userId\":\"origami100\"},\"id\":\"25836549674\",\"timestamp\":1719027219385}', 'completed', '2024-06-22 11:33:39', '2024-06-22 11:33:39');
INSERT INTO `task` VALUES (66, 'origami100', 'credit_adjust_success', '10704990246', '{\"data\":{\"amount\":-5.00,\"orderId\":\"305661362619\",\"outBusinessNo\":\"698976521368\",\"userId\":\"origami100\"},\"id\":\"10704990246\",\"timestamp\":1719027221168}', 'completed', '2024-06-22 11:33:41', '2024-06-22 11:33:41');
INSERT INTO `task` VALUES (67, 'origami100', 'credit_adjust_success', '76886376162', '{\"data\":{\"amount\":-5.00,\"orderId\":\"159597937845\",\"outBusinessNo\":\"313351791785\",\"userId\":\"origami100\"},\"id\":\"76886376162\",\"timestamp\":1719027222129}', 'completed', '2024-06-22 11:33:42', '2024-06-22 11:33:42');
INSERT INTO `task` VALUES (68, 'origami100', 'credit_adjust_success', '48346636873', '{\"data\":{\"amount\":-20.00,\"orderId\":\"482204967835\",\"outBusinessNo\":\"074754086575\",\"userId\":\"origami100\"},\"id\":\"48346636873\",\"timestamp\":1719027224068}', 'completed', '2024-06-22 11:33:44', '2024-06-22 11:33:44');
INSERT INTO `task` VALUES (69, 'origami100', 'send_award', '81839810710', '{\"data\":{\"awardId\":101,\"awardTitle\":\"随机积分\",\"orderId\":\"649778516231\",\"userId\":\"origami100\"},\"id\":\"81839810710\",\"timestamp\":1719027234563}', 'completed', '2024-06-22 11:33:54', '2024-06-22 11:33:54');
INSERT INTO `task` VALUES (70, 'origami100', 'send_award', '80359413706', '{\"data\":{\"awardConfig\":\"1,100\",\"awardId\":101,\"awardTitle\":\"随机积分\",\"orderId\":\"703127392964\",\"userId\":\"origami100\"},\"id\":\"80359413706\",\"timestamp\":1719027244594}', 'completed', '2024-06-22 11:34:04', '2024-06-22 11:34:04');
INSERT INTO `task` VALUES (71, 'origami100', 'send_award', '38835607635', '{\"data\":{\"awardId\":101,\"awardTitle\":\"随机积分\",\"orderId\":\"735180280972\",\"userId\":\"origami100\"},\"id\":\"38835607635\",\"timestamp\":1719027255729}', 'completed', '2024-06-22 11:34:15', '2024-06-22 11:34:15');
INSERT INTO `task` VALUES (72, 'origami100', 'credit_adjust_success', '68488779767', '{\"data\":{\"amount\":-100.00,\"orderId\":\"634804779698\",\"outBusinessNo\":\"776248180705\",\"userId\":\"origami100\"},\"id\":\"68488779767\",\"timestamp\":1719027262797}', 'completed', '2024-06-22 11:34:22', '2024-06-22 11:34:22');
INSERT INTO `task` VALUES (73, 'origami100', 'send_award', '02924268577', '{\"data\":{\"awardId\":101,\"awardTitle\":\"随机积分\",\"orderId\":\"735049174888\",\"userId\":\"origami100\"},\"id\":\"02924268577\",\"timestamp\":1719027270469}', 'completed', '2024-06-22 11:34:30', '2024-06-22 11:34:30');
INSERT INTO `task` VALUES (74, 'origami100', 'send_award', '63987547257', '{\"data\":{\"awardId\":101,\"awardTitle\":\"随机积分\",\"orderId\":\"489474305920\",\"userId\":\"origami100\"},\"id\":\"63987547257\",\"timestamp\":1719027278809}', 'completed', '2024-06-22 11:34:38', '2024-06-22 11:34:38');
INSERT INTO `task` VALUES (75, 'origami100', 'send_award', '30589146577', '{\"data\":{\"awardId\":101,\"awardTitle\":\"随机积分\",\"orderId\":\"366923803771\",\"userId\":\"origami100\"},\"id\":\"30589146577\",\"timestamp\":1719027285737}', 'completed', '2024-06-22 11:34:45', '2024-06-22 11:34:45');
INSERT INTO `task` VALUES (76, 'origami100', 'send_award', '77183962291', '{\"data\":{\"awardId\":101,\"awardTitle\":\"随机积分\",\"orderId\":\"288120745406\",\"userId\":\"origami100\"},\"id\":\"77183962291\",\"timestamp\":1719027292897}', 'completed', '2024-06-22 11:34:52', '2024-06-22 11:34:52');
INSERT INTO `task` VALUES (77, 'origami100', 'send_award', '03028752574', '{\"data\":{\"awardId\":101,\"awardTitle\":\"随机积分\",\"orderId\":\"334527462588\",\"userId\":\"origami100\"},\"id\":\"03028752574\",\"timestamp\":1719027300160}', 'completed', '2024-06-22 11:35:00', '2024-06-22 11:35:00');
INSERT INTO `task` VALUES (78, 'origami100', 'send_award', '94248581780', '{\"data\":{\"awardId\":103,\"awardTitle\":\"支付优惠券\",\"orderId\":\"056158603651\",\"userId\":\"origami100\"},\"id\":\"94248581780\",\"timestamp\":1719027307778}', 'completed', '2024-06-22 11:35:07', '2024-06-22 11:35:07');
INSERT INTO `task` VALUES (79, 'origami100', 'send_award', '55852600106', '{\"data\":{\"awardId\":103,\"awardTitle\":\"支付优惠券\",\"orderId\":\"369341382235\",\"userId\":\"origami100\"},\"id\":\"55852600106\",\"timestamp\":1719027321175}', 'completed', '2024-06-22 11:35:21', '2024-06-22 11:35:21');
INSERT INTO `task` VALUES (80, 'origami100', 'send_award', '16980276024', '{\"data\":{\"awardId\":102,\"awardTitle\":\"OpenAI会员卡\",\"orderId\":\"383018329411\",\"userId\":\"origami100\"},\"id\":\"16980276024\",\"timestamp\":1719027328789}', 'completed', '2024-06-22 11:35:28', '2024-06-22 11:35:28');
INSERT INTO `task` VALUES (81, 'origami100', 'send_award', '36942780204', '{\"data\":{\"awardId\":102,\"awardTitle\":\"OpenAI会员卡\",\"orderId\":\"062927684538\",\"userId\":\"origami100\"},\"id\":\"36942780204\",\"timestamp\":1719027403846}', 'completed', '2024-06-22 11:36:44', '2024-06-22 11:36:44');
INSERT INTO `task` VALUES (82, 'origami100', 'send_award', '99791280875', '{\"data\":{\"awardId\":103,\"awardTitle\":\"支付优惠券\",\"orderId\":\"865157771554\",\"userId\":\"origami100\"},\"id\":\"99791280875\",\"timestamp\":1719027431483}', 'completed', '2024-06-22 11:37:11', '2024-06-22 11:37:11');
INSERT INTO `task` VALUES (83, 'origami100', 'send_award', '53816527649', '{\"data\":{\"awardId\":103,\"awardTitle\":\"支付优惠券\",\"orderId\":\"339567817759\",\"userId\":\"origami100\"},\"id\":\"53816527649\",\"timestamp\":1719027445422}', 'completed', '2024-06-22 11:37:25', '2024-06-22 11:37:25');
INSERT INTO `task` VALUES (84, 'origami101', 'send_rebate', '04851408102', '{\"data\":{\"bizId\":\"origami101_sku_20240622\",\"rebateConfig\":\"9014\",\"rebateType\":\"sku\",\"userId\":\"origami101\"},\"id\":\"04851408102\",\"timestamp\":1719027890200}', 'completed', '2024-06-22 11:44:50', '2024-06-22 11:44:50');
INSERT INTO `task` VALUES (85, 'origami101', 'send_rebate', '84997259654', '{\"data\":{\"bizId\":\"origami101_integral_20240622\",\"rebateConfig\":\"10\",\"rebateType\":\"integral\",\"userId\":\"origami101\"},\"id\":\"84997259654\",\"timestamp\":1719027890206}', 'completed', '2024-06-22 11:44:50', '2024-06-22 11:44:50');
INSERT INTO `task` VALUES (86, 'origami101', 'credit_adjust_success', '04559967533', '{\"data\":{\"amount\":10,\"orderId\":\"425407842825\",\"outBusinessNo\":\"origami101_integral_20240622\",\"userId\":\"origami101\"},\"id\":\"04559967533\",\"timestamp\":1719027890421}', 'completed', '2024-06-22 11:44:50', '2024-06-22 11:44:50');
INSERT INTO `task` VALUES (87, 'origami101', 'send_award', '86675040094', '{\"data\":{\"awardId\":101,\"awardTitle\":\"随机积分\",\"orderId\":\"003840436800\",\"userId\":\"origami101\"},\"id\":\"86675040094\",\"timestamp\":1719027899661}', 'completed', '2024-06-22 11:44:59', '2024-06-22 11:44:59');
INSERT INTO `task` VALUES (88, 'origami101', 'credit_adjust_success', '21099377099', '{\"data\":{\"amount\":-5.00,\"orderId\":\"215791493342\",\"outBusinessNo\":\"647675002899\",\"userId\":\"origami101\"},\"id\":\"21099377099\",\"timestamp\":1719027946359}', 'completed', '2024-06-22 11:45:46', '2024-06-22 11:45:46');
INSERT INTO `task` VALUES (89, 'origami101', 'credit_adjust_success', '41718311389', '{\"data\":{\"amount\":-20.00,\"orderId\":\"022638554686\",\"outBusinessNo\":\"945591704953\",\"userId\":\"origami101\"},\"id\":\"41718311389\",\"timestamp\":1719027949001}', 'completed', '2024-06-22 11:45:49', '2024-06-22 11:45:49');
INSERT INTO `task` VALUES (90, 'origami101', 'send_award', '62652401481', '{\"data\":{\"awardId\":101,\"awardTitle\":\"随机积分\",\"orderId\":\"261982488893\",\"userId\":\"origami101\"},\"id\":\"62652401481\",\"timestamp\":1719027960311}', 'completed', '2024-06-22 11:46:00', '2024-06-22 11:46:00');
INSERT INTO `task` VALUES (91, 'origami101', 'credit_adjust_success', '55874129101', '{\"data\":{\"amount\":-100.00,\"orderId\":\"631098732490\",\"outBusinessNo\":\"371514189773\",\"userId\":\"origami101\"},\"id\":\"55874129101\",\"timestamp\":1719027967263}', 'completed', '2024-06-22 11:46:07', '2024-06-22 11:46:07');
INSERT INTO `task` VALUES (92, 'origami101', 'send_award', '28244124087', '{\"data\":{\"awardId\":101,\"awardTitle\":\"随机积分\",\"orderId\":\"509036893328\",\"userId\":\"origami101\"},\"id\":\"28244124087\",\"timestamp\":1719028405863}', 'completed', '2024-06-22 11:53:25', '2024-06-22 11:53:25');

-- ----------------------------
-- Table structure for user_award_record_000
-- ----------------------------
DROP TABLE IF EXISTS `user_award_record_000`;
CREATE TABLE `user_award_record_000`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
  `activity_id` bigint NOT NULL COMMENT '活动ID',
  `strategy_id` bigint NOT NULL COMMENT '抽奖策略ID',
  `order_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '抽奖订单ID【作为幂等使用】',
  `award_id` int NOT NULL COMMENT '奖品ID',
  `award_title` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '奖品标题（名称）',
  `award_time` datetime NOT NULL COMMENT '中奖时间',
  `award_state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'create' COMMENT '奖品状态；create-创建、completed-发奖完成',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_order_id`(`order_id` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE,
  INDEX `idx_activity_id`(`activity_id` ASC) USING BTREE,
  INDEX `idx_award_id`(`strategy_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户中奖记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_award_record_000
-- ----------------------------
INSERT INTO `user_award_record_000` VALUES (1, 'origami1', 100301, 100006, '386938913572', 104, '小米台灯', '2024-05-04 04:51:33', 'create', '2024-05-04 12:51:32', '2024-05-04 12:51:32');
INSERT INTO `user_award_record_000` VALUES (2, 'user001', 100301, 100006, '391668886086', 101, '随机积分', '2024-05-24 14:31:47', 'create', '2024-05-24 22:31:55', '2024-05-24 22:31:55');
INSERT INTO `user_award_record_000` VALUES (3, 'user001', 100301, 100006, '179191128326', 101, '随机积分', '2024-05-24 14:33:03', 'create', '2024-05-24 22:33:02', '2024-05-24 22:33:02');
INSERT INTO `user_award_record_000` VALUES (4, 'user001', 100301, 100006, '320625987421', 101, '随机积分', '2024-05-24 14:34:02', 'completed', '2024-05-24 22:34:04', '2024-05-24 22:34:19');
INSERT INTO `user_award_record_000` VALUES (5, 'user001', 100301, 100006, '290879207548', 101, '随机积分', '2024-05-24 14:34:38', 'completed', '2024-05-24 22:34:38', '2024-05-24 22:34:38');
INSERT INTO `user_award_record_000` VALUES (6, 'user001', 100301, 100006, '539811500095', 101, '随机积分', '2024-05-27 14:39:24', 'completed', '2024-05-27 22:39:24', '2024-05-27 22:41:26');
INSERT INTO `user_award_record_000` VALUES (7, 'user001', 100301, 100006, '405619392079', 101, '随机积分', '2024-05-27 14:41:27', 'completed', '2024-05-27 22:41:26', '2024-05-27 22:41:26');
INSERT INTO `user_award_record_000` VALUES (8, 'user001', 100301, 100006, '130698860415', 101, '随机积分', '2024-05-27 14:42:18', 'completed', '2024-05-27 22:42:18', '2024-05-27 22:42:18');
INSERT INTO `user_award_record_000` VALUES (9, 'user001', 100301, 100006, '777907205658', 101, '随机积分', '2024-05-29 23:20:49', 'completed', '2024-05-30 07:20:49', '2024-05-30 07:20:49');
INSERT INTO `user_award_record_000` VALUES (10, 'user001', 100301, 100006, '037106411824', 101, '随机积分', '2024-05-29 23:22:10', 'completed', '2024-05-30 07:22:10', '2024-05-30 07:22:10');
INSERT INTO `user_award_record_000` VALUES (11, 'origami25', 100301, 100006, '067670700350', 101, '随机积分', '2024-06-22 02:07:55', 'completed', '2024-06-22 10:07:54', '2024-06-22 10:07:54');
INSERT INTO `user_award_record_000` VALUES (12, 'origami25', 100301, 100006, '733298505993', 101, '随机积分', '2024-06-22 02:11:59', 'completed', '2024-06-22 10:11:58', '2024-06-22 10:11:58');
INSERT INTO `user_award_record_000` VALUES (13, 'origami25', 100301, 100006, '148282410120', 101, '随机积分', '2024-06-22 02:12:47', 'completed', '2024-06-22 10:12:46', '2024-06-22 10:12:46');
INSERT INTO `user_award_record_000` VALUES (14, 'origami25', 100301, 100006, '068213439911', 101, '随机积分', '2024-06-22 02:17:23', 'completed', '2024-06-22 10:17:22', '2024-06-22 10:17:22');
INSERT INTO `user_award_record_000` VALUES (15, 'origami25', 100301, 100006, '355946005992', 101, '随机积分', '2024-06-22 02:20:43', 'completed', '2024-06-22 10:20:43', '2024-06-22 10:20:43');
INSERT INTO `user_award_record_000` VALUES (16, 'origami25', 100301, 100006, '010903932739', 101, '随机积分', '2024-06-22 02:20:54', 'completed', '2024-06-22 10:20:54', '2024-06-22 10:20:54');

-- ----------------------------
-- Table structure for user_award_record_001
-- ----------------------------
DROP TABLE IF EXISTS `user_award_record_001`;
CREATE TABLE `user_award_record_001`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
  `activity_id` bigint NOT NULL COMMENT '活动ID',
  `strategy_id` bigint NOT NULL COMMENT '抽奖策略ID',
  `order_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '抽奖订单ID【作为幂等使用】',
  `award_id` int NOT NULL COMMENT '奖品ID',
  `award_title` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '奖品标题（名称）',
  `award_time` datetime NOT NULL COMMENT '中奖时间',
  `award_state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'create' COMMENT '奖品状态；create-创建、completed-发奖完成',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_order_id`(`order_id` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE,
  INDEX `idx_activity_id`(`activity_id` ASC) USING BTREE,
  INDEX `idx_award_id`(`strategy_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户中奖记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_award_record_001
-- ----------------------------
INSERT INTO `user_award_record_001` VALUES (1, 'origami24', 100301, 100006, '837829954453', 108, '暴走玩偶', '2024-06-22 01:59:40', 'create', '2024-06-22 09:59:40', '2024-06-22 09:59:40');
INSERT INTO `user_award_record_001` VALUES (2, 'origami24', 100301, 100006, '692282780900', 108, '暴走玩偶', '2024-06-22 01:59:48', 'create', '2024-06-22 09:59:48', '2024-06-22 09:59:48');
INSERT INTO `user_award_record_001` VALUES (3, 'origami24', 100301, 100006, '998461237887', 106, '轻奢办公椅', '2024-06-22 01:59:56', 'create', '2024-06-22 09:59:56', '2024-06-22 09:59:56');
INSERT INTO `user_award_record_001` VALUES (4, 'origami100', 100301, 100006, '438098073325', 101, '随机积分', '2024-06-22 03:31:44', 'completed', '2024-06-22 11:31:43', '2024-06-22 11:31:44');
INSERT INTO `user_award_record_001` VALUES (5, 'origami100', 100301, 100006, '649778516231', 101, '随机积分', '2024-06-22 03:33:55', 'completed', '2024-06-22 11:33:54', '2024-06-22 11:33:54');
INSERT INTO `user_award_record_001` VALUES (6, 'origami100', 100301, 100006, '703127392964', 101, '随机积分', '2024-06-22 03:34:05', 'completed', '2024-06-22 11:34:04', '2024-06-22 11:34:04');
INSERT INTO `user_award_record_001` VALUES (7, 'origami100', 100301, 100006, '735180280972', 101, '随机积分', '2024-06-22 03:34:16', 'completed', '2024-06-22 11:34:15', '2024-06-22 11:34:15');
INSERT INTO `user_award_record_001` VALUES (8, 'origami100', 100301, 100006, '735049174888', 101, '随机积分', '2024-06-22 03:34:30', 'completed', '2024-06-22 11:34:30', '2024-06-22 11:34:30');
INSERT INTO `user_award_record_001` VALUES (9, 'origami100', 100301, 100006, '489474305920', 101, '随机积分', '2024-06-22 03:34:39', 'completed', '2024-06-22 11:34:38', '2024-06-22 11:34:38');
INSERT INTO `user_award_record_001` VALUES (10, 'origami100', 100301, 100006, '366923803771', 101, '随机积分', '2024-06-22 03:34:46', 'completed', '2024-06-22 11:34:45', '2024-06-22 11:34:45');
INSERT INTO `user_award_record_001` VALUES (11, 'origami100', 100301, 100006, '288120745406', 101, '随机积分', '2024-06-22 03:34:53', 'completed', '2024-06-22 11:34:52', '2024-06-22 11:34:52');
INSERT INTO `user_award_record_001` VALUES (12, 'origami100', 100301, 100006, '334527462588', 101, '随机积分', '2024-06-22 03:35:00', 'completed', '2024-06-22 11:35:00', '2024-06-22 11:35:00');
INSERT INTO `user_award_record_001` VALUES (13, 'origami100', 100301, 100006, '056158603651', 103, '支付优惠券', '2024-06-22 03:35:08', 'create', '2024-06-22 11:35:07', '2024-06-22 11:35:07');
INSERT INTO `user_award_record_001` VALUES (14, 'origami100', 100301, 100006, '369341382235', 103, '支付优惠券', '2024-06-22 03:35:21', 'create', '2024-06-22 11:35:21', '2024-06-22 11:35:21');
INSERT INTO `user_award_record_001` VALUES (15, 'origami100', 100301, 100006, '383018329411', 102, 'OpenAI会员卡', '2024-06-22 03:35:29', 'create', '2024-06-22 11:35:28', '2024-06-22 11:35:28');
INSERT INTO `user_award_record_001` VALUES (16, 'origami100', 100301, 100006, '062927684538', 102, 'OpenAI会员卡', '2024-06-22 03:36:44', 'create', '2024-06-22 11:36:44', '2024-06-22 11:36:44');
INSERT INTO `user_award_record_001` VALUES (17, 'origami100', 100301, 100006, '865157771554', 103, '支付优惠券', '2024-06-22 03:37:11', 'create', '2024-06-22 11:37:11', '2024-06-22 11:37:11');
INSERT INTO `user_award_record_001` VALUES (18, 'origami100', 100301, 100006, '339567817759', 103, '支付优惠券', '2024-06-22 03:37:25', 'create', '2024-06-22 11:37:25', '2024-06-22 11:37:25');

-- ----------------------------
-- Table structure for user_award_record_002
-- ----------------------------
DROP TABLE IF EXISTS `user_award_record_002`;
CREATE TABLE `user_award_record_002`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
  `activity_id` bigint NOT NULL COMMENT '活动ID',
  `strategy_id` bigint NOT NULL COMMENT '抽奖策略ID',
  `order_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '抽奖订单ID【作为幂等使用】',
  `award_id` int NOT NULL COMMENT '奖品ID',
  `award_title` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '奖品标题（名称）',
  `award_time` datetime NOT NULL COMMENT '中奖时间',
  `award_state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'create' COMMENT '奖品状态；create-创建、completed-发奖完成',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_order_id`(`order_id` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE,
  INDEX `idx_activity_id`(`activity_id` ASC) USING BTREE,
  INDEX `idx_award_id`(`strategy_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户中奖记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_award_record_002
-- ----------------------------
INSERT INTO `user_award_record_002` VALUES (1, 'origami101', 100301, 100006, '003840436800', 101, '随机积分', '2024-06-22 03:45:00', 'completed', '2024-06-22 11:44:59', '2024-06-22 11:44:59');
INSERT INTO `user_award_record_002` VALUES (2, 'origami101', 100301, 100006, '261982488893', 101, '随机积分', '2024-06-22 03:46:00', 'completed', '2024-06-22 11:46:00', '2024-06-22 11:46:00');
INSERT INTO `user_award_record_002` VALUES (3, 'origami101', 100301, 100006, '509036893328', 101, '随机积分', '2024-06-22 03:53:26', 'completed', '2024-06-22 11:53:25', '2024-06-22 11:53:25');

-- ----------------------------
-- Table structure for user_award_record_003
-- ----------------------------
DROP TABLE IF EXISTS `user_award_record_003`;
CREATE TABLE `user_award_record_003`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
  `activity_id` bigint NOT NULL COMMENT '活动ID',
  `strategy_id` bigint NOT NULL COMMENT '抽奖策略ID',
  `order_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '抽奖订单ID【作为幂等使用】',
  `award_id` int NOT NULL COMMENT '奖品ID',
  `award_title` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '奖品标题（名称）',
  `award_time` datetime NOT NULL COMMENT '中奖时间',
  `award_state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'create' COMMENT '奖品状态；create-创建、completed-发奖完成',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_order_id`(`order_id` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE,
  INDEX `idx_activity_id`(`activity_id` ASC) USING BTREE,
  INDEX `idx_award_id`(`strategy_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户中奖记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_award_record_003
-- ----------------------------
INSERT INTO `user_award_record_003` VALUES (1, 'origami2', 100301, 100006, '809920093949', 102, 'OpenAI会员卡', '2024-05-04 04:53:02', 'create', '2024-05-04 12:53:02', '2024-05-04 12:53:02');
INSERT INTO `user_award_record_003` VALUES (2, 'origami2', 100301, 100006, '613575974630', 104, '小米台灯', '2024-05-04 04:58:43', 'create', '2024-05-04 12:58:42', '2024-05-04 12:58:42');
INSERT INTO `user_award_record_003` VALUES (3, 'origami2', 100301, 100006, '632802870481', 102, 'OpenAI会员卡', '2024-05-04 05:00:08', 'create', '2024-05-04 13:00:08', '2024-05-04 13:00:08');
INSERT INTO `user_award_record_003` VALUES (4, 'origami2', 100301, 100006, '972433161890', 101, '随机积分', '2024-05-04 05:05:38', 'create', '2024-05-04 13:05:38', '2024-05-04 13:05:38');
INSERT INTO `user_award_record_003` VALUES (5, 'origami2', 100301, 100006, '877507315775', 101, '随机积分', '2024-05-04 05:05:46', 'create', '2024-05-04 13:05:45', '2024-05-04 13:05:45');
INSERT INTO `user_award_record_003` VALUES (6, 'origami2', 100301, 100006, '517301751417', 106, '轻奢办公椅', '2024-05-04 05:05:53', 'create', '2024-05-04 13:05:52', '2024-05-04 13:05:52');
INSERT INTO `user_award_record_003` VALUES (7, 'origami2', 100301, 100006, '261932434171', 108, '暴走玩偶', '2024-05-04 05:06:00', 'create', '2024-05-04 13:05:59', '2024-05-04 13:05:59');
INSERT INTO `user_award_record_003` VALUES (8, 'origami2', 100301, 100006, '700573379547', 107, '小霸王游戏机', '2024-05-04 05:06:07', 'create', '2024-05-04 13:06:06', '2024-05-04 13:06:06');
INSERT INTO `user_award_record_003` VALUES (9, 'origami2', 100301, 100006, '105697168349', 108, '暴走玩偶', '2024-05-04 05:06:18', 'create', '2024-05-04 13:06:17', '2024-05-04 13:06:17');
INSERT INTO `user_award_record_003` VALUES (10, 'origami2', 100301, 100006, '959233180689', 103, '支付优惠券', '2024-05-04 05:06:25', 'create', '2024-05-04 13:06:24', '2024-05-04 13:06:24');
INSERT INTO `user_award_record_003` VALUES (11, 'origami2', 100301, 100006, '958431976534', 103, '支付优惠券', '2024-05-04 05:08:06', 'create', '2024-05-04 13:08:06', '2024-05-04 13:08:06');
INSERT INTO `user_award_record_003` VALUES (12, 'origami2', 100301, 100006, '014341440837', 103, '支付优惠券', '2024-05-04 05:57:04', 'create', '2024-05-04 13:57:03', '2024-05-04 13:57:03');
INSERT INTO `user_award_record_003` VALUES (13, 'origami2', 100301, 100006, '104619821752', 103, '支付优惠券', '2024-05-04 05:57:12', 'create', '2024-05-04 13:57:11', '2024-05-04 13:57:11');
INSERT INTO `user_award_record_003` VALUES (14, 'origami2', 100301, 100006, '932807566378', 103, '支付优惠券', '2024-05-04 05:57:20', 'create', '2024-05-04 13:57:20', '2024-05-04 13:57:20');
INSERT INTO `user_award_record_003` VALUES (15, 'origami2', 100301, 100006, '623497872684', 102, 'OpenAI会员卡', '2024-05-04 05:57:32', 'create', '2024-05-04 13:57:32', '2024-05-04 13:57:32');

-- ----------------------------
-- Table structure for user_behavior_rebate_order_000
-- ----------------------------
DROP TABLE IF EXISTS `user_behavior_rebate_order_000`;
CREATE TABLE `user_behavior_rebate_order_000`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
  `order_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单ID',
  `behavior_type` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '行为类型（sign 签到、openai_pay 支付）',
  `rebate_desc` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '返利描述',
  `rebate_type` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '返利类型（sku 活动库存充值商品、integral 用户活动积分）',
  `rebate_config` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '返利配置【sku值，积分值】',
  `out_business_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '业务仿重ID - 外部透传，方便查询使用',
  `biz_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '业务ID - 拼接的唯一值。拼接 out_business_no + 自身枚举',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_order_id`(`order_id` ASC) USING BTREE,
  UNIQUE INDEX `uq_biz_id`(`biz_id` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户行为返利流水订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_behavior_rebate_order_000
-- ----------------------------
INSERT INTO `user_behavior_rebate_order_000` VALUES (5, 'origami1', '883971522401', 'sign', '签到返利-sku额度', 'sku', '9011', '20240503', 'origami1_sku_20240503', '2024-05-03 16:01:42', '2024-05-03 16:01:42');
INSERT INTO `user_behavior_rebate_order_000` VALUES (6, 'origami1', '995944930386', 'sign', '签到返利-积分', 'integral', '10', '20240503', 'origami1_integral_20240503', '2024-05-03 16:01:43', '2024-05-03 16:01:43');
INSERT INTO `user_behavior_rebate_order_000` VALUES (7, 'user001', '153309768984', 'sign', '签到返利-sku额度', 'sku', '9011', '20240524', 'user001_sku_20240524', '2024-05-24 22:25:09', '2024-05-24 22:25:09');
INSERT INTO `user_behavior_rebate_order_000` VALUES (8, 'user001', '922397055482', 'sign', '签到返利-积分', 'integral', '10', '20240524', 'user001_integral_20240524', '2024-05-24 22:25:09', '2024-05-24 22:25:09');
INSERT INTO `user_behavior_rebate_order_000` VALUES (9, 'origami25', '049961446285', 'sign', '签到返利-sku额度', 'sku', '9014', '20240622', 'origami25_sku_20240622', '2024-06-22 10:07:40', '2024-06-22 10:07:40');
INSERT INTO `user_behavior_rebate_order_000` VALUES (10, 'origami25', '311903567924', 'sign', '签到返利-积分', 'integral', '10', '20240622', 'origami25_integral_20240622', '2024-06-22 10:07:40', '2024-06-22 10:07:40');

-- ----------------------------
-- Table structure for user_behavior_rebate_order_001
-- ----------------------------
DROP TABLE IF EXISTS `user_behavior_rebate_order_001`;
CREATE TABLE `user_behavior_rebate_order_001`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
  `order_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单ID',
  `behavior_type` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '行为类型（sign 签到、openai_pay 支付）',
  `rebate_desc` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '返利描述',
  `rebate_type` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '返利类型（sku 活动库存充值商品、integral 用户活动积分）',
  `rebate_config` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '返利配置【sku值，积分值】',
  `out_business_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '业务仿重ID - 外部透传，方便查询使用',
  `biz_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '业务ID - 拼接的唯一值。拼接 out_business_no + 自身枚举',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_order_id`(`order_id` ASC) USING BTREE,
  UNIQUE INDEX `uq_biz_id`(`biz_id` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户行为返利流水订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_behavior_rebate_order_001
-- ----------------------------
INSERT INTO `user_behavior_rebate_order_001` VALUES (1, 'user002', '791847185524', 'sign', '签到返利-sku额度', 'sku', '9011', '20240524', 'user002_sku_20240524', '2024-05-24 22:29:11', '2024-05-24 22:29:11');
INSERT INTO `user_behavior_rebate_order_001` VALUES (2, 'user002', '119018837248', 'sign', '签到返利-积分', 'integral', '10', '20240524', 'user002_integral_20240524', '2024-05-24 22:29:11', '2024-05-24 22:29:11');
INSERT INTO `user_behavior_rebate_order_001` VALUES (3, 'user002', '031618502370', 'sign', '签到返利-sku额度', 'sku', '9011', '20240527', 'user002_sku_20240527', '2024-05-27 22:37:06', '2024-05-27 22:37:06');
INSERT INTO `user_behavior_rebate_order_001` VALUES (4, 'user002', '591157608900', 'sign', '签到返利-积分', 'integral', '10', '20240527', 'user002_integral_20240527', '2024-05-27 22:37:07', '2024-05-27 22:37:07');
INSERT INTO `user_behavior_rebate_order_001` VALUES (5, 'origami24', '532714815440', 'sign', '签到返利-sku额度', 'sku', '9014', '20240622', 'origami24_sku_20240622', '2024-06-22 09:59:19', '2024-06-22 09:59:19');
INSERT INTO `user_behavior_rebate_order_001` VALUES (6, 'origami24', '886487876823', 'sign', '签到返利-积分', 'integral', '10', '20240622', 'origami24_integral_20240622', '2024-06-22 09:59:19', '2024-06-22 09:59:19');
INSERT INTO `user_behavior_rebate_order_001` VALUES (7, 'origami100', '114867898811', 'sign', '签到返利-sku额度', 'sku', '9014', '20240622', 'origami100_sku_20240622', '2024-06-22 11:30:37', '2024-06-22 11:30:37');
INSERT INTO `user_behavior_rebate_order_001` VALUES (8, 'origami100', '846316816808', 'sign', '签到返利-积分', 'integral', '10', '20240622', 'origami100_integral_20240622', '2024-06-22 11:30:37', '2024-06-22 11:30:37');

-- ----------------------------
-- Table structure for user_behavior_rebate_order_002
-- ----------------------------
DROP TABLE IF EXISTS `user_behavior_rebate_order_002`;
CREATE TABLE `user_behavior_rebate_order_002`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
  `order_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单ID',
  `behavior_type` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '行为类型（sign 签到、openai_pay 支付）',
  `rebate_desc` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '返利描述',
  `rebate_type` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '返利类型（sku 活动库存充值商品、integral 用户活动积分）',
  `rebate_config` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '返利配置【sku值，积分值】',
  `out_business_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '业务仿重ID - 外部透传，方便查询使用',
  `biz_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '业务ID - 拼接的唯一值。拼接 out_business_no + 自身枚举',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_order_id`(`order_id` ASC) USING BTREE,
  UNIQUE INDEX `uq_biz_id`(`biz_id` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户行为返利流水订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_behavior_rebate_order_002
-- ----------------------------
INSERT INTO `user_behavior_rebate_order_002` VALUES (1, 'origami23', '674540833668', 'sign', '签到返利-sku额度', 'sku', '9014', '20240622', 'origami23_sku_20240622', '2024-06-22 09:57:00', '2024-06-22 09:57:00');
INSERT INTO `user_behavior_rebate_order_002` VALUES (2, 'origami23', '682003973896', 'sign', '签到返利-积分', 'integral', '10', '20240622', 'origami23_integral_20240622', '2024-06-22 09:57:00', '2024-06-22 09:57:00');
INSERT INTO `user_behavior_rebate_order_002` VALUES (3, 'origami101', '016416761475', 'sign', '签到返利-sku额度', 'sku', '9014', '20240622', 'origami101_sku_20240622', '2024-06-22 11:44:50', '2024-06-22 11:44:50');
INSERT INTO `user_behavior_rebate_order_002` VALUES (4, 'origami101', '836048823314', 'sign', '签到返利-积分', 'integral', '10', '20240622', 'origami101_integral_20240622', '2024-06-22 11:44:50', '2024-06-22 11:44:50');

-- ----------------------------
-- Table structure for user_behavior_rebate_order_003
-- ----------------------------
DROP TABLE IF EXISTS `user_behavior_rebate_order_003`;
CREATE TABLE `user_behavior_rebate_order_003`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
  `order_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单ID',
  `behavior_type` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '行为类型（sign 签到、openai_pay 支付）',
  `rebate_desc` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '返利描述',
  `rebate_type` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '返利类型（sku 活动库存充值商品、integral 用户活动积分）',
  `rebate_config` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '返利配置【sku值，积分值】',
  `out_business_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '业务仿重ID - 外部透传，方便查询使用',
  `biz_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '业务ID - 拼接的唯一值。拼接 out_business_no + 自身枚举',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_order_id`(`order_id` ASC) USING BTREE,
  UNIQUE INDEX `uq_biz_id`(`biz_id` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户行为返利流水订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_behavior_rebate_order_003
-- ----------------------------
INSERT INTO `user_behavior_rebate_order_003` VALUES (7, 'origami2', '298513989210', 'sign', '签到返利-sku额度', 'sku', '9011', '20240504', 'origami2_sku_20240504', '2024-05-04 13:07:53', '2024-05-04 13:07:53');
INSERT INTO `user_behavior_rebate_order_003` VALUES (8, 'origami2', '352651244433', 'sign', '签到返利-积分', 'integral', '10', '20240504', 'origami2_integral_20240504', '2024-05-04 13:07:53', '2024-05-04 13:07:53');
INSERT INTO `user_behavior_rebate_order_003` VALUES (9, 'origami22', '769157142538', 'sign', '签到返利-sku额度', 'sku', '9014', '20240622', 'origami22_sku_20240622', '2024-06-22 09:52:58', '2024-06-22 09:52:58');
INSERT INTO `user_behavior_rebate_order_003` VALUES (10, 'origami22', '718932497750', 'sign', '签到返利-积分', 'integral', '10', '20240622', 'origami22_integral_20240622', '2024-06-22 09:52:58', '2024-06-22 09:52:58');

-- ----------------------------
-- Table structure for user_credit_account
-- ----------------------------
DROP TABLE IF EXISTS `user_credit_account`;
CREATE TABLE `user_credit_account`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
  `total_amount` decimal(10, 2) NOT NULL COMMENT '总积分，显示总账户值，记得一个人获得的总积分',
  `available_amount` decimal(10, 2) NOT NULL COMMENT '可用积分，每次扣减的值',
  `account_status` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '账户状态【open - 可用，close - 冻结】',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户积分账户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_credit_account
-- ----------------------------
INSERT INTO `user_credit_account` VALUES (2, 'user001', 3.65, 3.65, 'open', '2024-05-24 22:34:19', '2024-05-30 07:22:10');
INSERT INTO `user_credit_account` VALUES (3, 'origami23', 10.00, 10.00, 'open', '2024-06-22 09:57:00', '2024-06-22 09:57:00');
INSERT INTO `user_credit_account` VALUES (4, 'origami24', 10.00, 0.00, 'open', '2024-06-22 09:59:19', '2024-06-22 09:59:26');
INSERT INTO `user_credit_account` VALUES (5, 'origami25', 363.30, 343.30, 'open', '2024-06-22 10:07:41', '2024-06-22 10:20:54');
INSERT INTO `user_credit_account` VALUES (6, 'origami100', 400.61, 265.61, 'open', '2024-06-22 11:30:38', '2024-06-22 11:35:00');
INSERT INTO `user_credit_account` VALUES (7, 'origami101', 175.80, 50.80, 'open', '2024-06-22 11:44:50', '2024-06-22 11:53:25');

-- ----------------------------
-- Table structure for user_credit_order_000
-- ----------------------------
DROP TABLE IF EXISTS `user_credit_order_000`;
CREATE TABLE `user_credit_order_000`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
  `order_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单ID',
  `trade_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '交易名称',
  `trade_type` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'forward' COMMENT '交易类型；forward-正向、reverse-逆向',
  `trade_amount` decimal(10, 2) NOT NULL COMMENT '交易金额',
  `out_business_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '业务仿重ID - 外部透传。返利、行为等唯一标识',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_order_id`(`order_id` ASC) USING BTREE,
  UNIQUE INDEX `uq_out_business_no`(`out_business_no` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户积分订单记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_credit_order_000
-- ----------------------------
INSERT INTO `user_credit_order_000` VALUES (1, 'origami25', '479481818661', '行为返利', 'forward', 10.00, 'origami25_integral_20240622', '2024-06-22 10:07:41', '2024-06-22 10:07:41');
INSERT INTO `user_credit_order_000` VALUES (2, 'origami25', '981030218405', '兑换抽奖', 'reverse', -20.00, '404066146511', '2024-06-22 10:11:50', '2024-06-22 10:11:50');

-- ----------------------------
-- Table structure for user_credit_order_001
-- ----------------------------
DROP TABLE IF EXISTS `user_credit_order_001`;
CREATE TABLE `user_credit_order_001`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
  `order_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单ID',
  `trade_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '交易名称',
  `trade_type` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'forward' COMMENT '交易类型；forward-正向、reverse-逆向',
  `trade_amount` decimal(10, 2) NOT NULL COMMENT '交易金额',
  `out_business_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '业务仿重ID - 外部透传。返利、行为等唯一标识',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_order_id`(`order_id` ASC) USING BTREE,
  UNIQUE INDEX `uq_out_business_no`(`out_business_no` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户积分订单记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_credit_order_001
-- ----------------------------
INSERT INTO `user_credit_order_001` VALUES (1, 'origami24', '929908709864', '行为返利', 'forward', 10.00, 'origami24_integral_20240622', '2024-06-22 09:59:19', '2024-06-22 09:59:19');
INSERT INTO `user_credit_order_001` VALUES (2, 'origami24', '209411906995', '兑换抽奖', 'reverse', -5.00, '750705533475', '2024-06-22 09:59:24', '2024-06-22 09:59:24');
INSERT INTO `user_credit_order_001` VALUES (3, 'origami24', '470247343587', '兑换抽奖', 'reverse', -5.00, '738796690867', '2024-06-22 09:59:26', '2024-06-22 09:59:26');
INSERT INTO `user_credit_order_001` VALUES (4, 'origami100', '084053486083', '行为返利', 'forward', 10.00, 'origami100_integral_20240622', '2024-06-22 11:30:38', '2024-06-22 11:30:38');
INSERT INTO `user_credit_order_001` VALUES (5, 'origami100', '615817318859', '兑换抽奖', 'reverse', -5.00, '761630034156', '2024-06-22 11:33:39', '2024-06-22 11:33:39');
INSERT INTO `user_credit_order_001` VALUES (6, 'origami100', '305661362619', '兑换抽奖', 'reverse', -5.00, '698976521368', '2024-06-22 11:33:41', '2024-06-22 11:33:41');
INSERT INTO `user_credit_order_001` VALUES (7, 'origami100', '159597937845', '兑换抽奖', 'reverse', -5.00, '313351791785', '2024-06-22 11:33:42', '2024-06-22 11:33:42');
INSERT INTO `user_credit_order_001` VALUES (8, 'origami100', '482204967835', '兑换抽奖', 'reverse', -20.00, '074754086575', '2024-06-22 11:33:44', '2024-06-22 11:33:44');
INSERT INTO `user_credit_order_001` VALUES (9, 'origami100', '634804779698', '兑换抽奖', 'reverse', -100.00, '776248180705', '2024-06-22 11:34:22', '2024-06-22 11:34:22');

-- ----------------------------
-- Table structure for user_credit_order_002
-- ----------------------------
DROP TABLE IF EXISTS `user_credit_order_002`;
CREATE TABLE `user_credit_order_002`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
  `order_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单ID',
  `trade_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '交易名称',
  `trade_type` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'forward' COMMENT '交易类型；forward-正向、reverse-逆向',
  `trade_amount` decimal(10, 2) NOT NULL COMMENT '交易金额',
  `out_business_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '业务仿重ID - 外部透传。返利、行为等唯一标识',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_order_id`(`order_id` ASC) USING BTREE,
  UNIQUE INDEX `uq_out_business_no`(`out_business_no` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户积分订单记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_credit_order_002
-- ----------------------------
INSERT INTO `user_credit_order_002` VALUES (1, 'origami23', '724677881464', '行为返利', 'forward', 10.00, 'origami23_integral_20240622', '2024-06-22 09:57:00', '2024-06-22 09:57:00');
INSERT INTO `user_credit_order_002` VALUES (2, 'origami101', '425407842825', '行为返利', 'forward', 10.00, 'origami101_integral_20240622', '2024-06-22 11:44:50', '2024-06-22 11:44:50');
INSERT INTO `user_credit_order_002` VALUES (3, 'origami101', '215791493342', '兑换抽奖', 'reverse', -5.00, '647675002899', '2024-06-22 11:45:46', '2024-06-22 11:45:46');
INSERT INTO `user_credit_order_002` VALUES (4, 'origami101', '022638554686', '兑换抽奖', 'reverse', -20.00, '945591704953', '2024-06-22 11:45:49', '2024-06-22 11:45:49');
INSERT INTO `user_credit_order_002` VALUES (5, 'origami101', '631098732490', '兑换抽奖', 'reverse', -100.00, '371514189773', '2024-06-22 11:46:07', '2024-06-22 11:46:07');

-- ----------------------------
-- Table structure for user_credit_order_003
-- ----------------------------
DROP TABLE IF EXISTS `user_credit_order_003`;
CREATE TABLE `user_credit_order_003`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
  `order_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单ID',
  `trade_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '交易名称',
  `trade_type` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'forward' COMMENT '交易类型；forward-正向、reverse-逆向',
  `trade_amount` decimal(10, 2) NOT NULL COMMENT '交易金额',
  `out_business_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '业务仿重ID - 外部透传。返利、行为等唯一标识',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_order_id`(`order_id` ASC) USING BTREE,
  UNIQUE INDEX `uq_out_business_no`(`out_business_no` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户积分订单记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_credit_order_003
-- ----------------------------

-- ----------------------------
-- Table structure for user_raffle_order_000
-- ----------------------------
DROP TABLE IF EXISTS `user_raffle_order_000`;
CREATE TABLE `user_raffle_order_000`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
  `activity_id` bigint NOT NULL COMMENT '活动ID',
  `activity_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '活动名称',
  `strategy_id` bigint NOT NULL COMMENT '抽奖策略ID',
  `order_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单ID',
  `order_time` datetime NOT NULL COMMENT '下单时间',
  `order_state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'create' COMMENT '订单状态；create-创建、used-已使用、cancel-已作废',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_order_id`(`order_id` ASC) USING BTREE,
  INDEX `idx_user_id_activity_id`(`user_id` ASC, `activity_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户抽奖订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_raffle_order_000
-- ----------------------------
INSERT INTO `user_raffle_order_000` VALUES (1, 'origami1', 100301, '测试活动', 100006, '386938913572', '2024-05-04 04:51:32', 'used', '2024-05-04 12:51:32', '2024-05-04 12:51:32');
INSERT INTO `user_raffle_order_000` VALUES (2, 'user001', 100301, '测试活动', 100006, '391668886086', '2024-05-24 14:31:46', 'used', '2024-05-24 22:31:47', '2024-05-24 22:31:55');
INSERT INTO `user_raffle_order_000` VALUES (3, 'user001', 100301, '测试活动', 100006, '179191128326', '2024-05-24 14:33:02', 'used', '2024-05-24 22:33:02', '2024-05-24 22:33:02');
INSERT INTO `user_raffle_order_000` VALUES (4, 'user001', 100301, '测试活动', 100006, '320625987421', '2024-05-24 14:34:01', 'used', '2024-05-24 22:34:01', '2024-05-24 22:34:04');
INSERT INTO `user_raffle_order_000` VALUES (5, 'user001', 100301, '测试活动', 100006, '290879207548', '2024-05-24 14:34:37', 'used', '2024-05-24 22:34:37', '2024-05-24 22:34:38');
INSERT INTO `user_raffle_order_000` VALUES (6, 'user001', 100301, '测试活动', 100006, '539811500095', '2024-05-27 14:39:24', 'used', '2024-05-27 22:39:24', '2024-05-27 22:39:24');
INSERT INTO `user_raffle_order_000` VALUES (7, 'user001', 100301, '测试活动', 100006, '405619392079', '2024-05-27 14:41:26', 'used', '2024-05-27 22:41:26', '2024-05-27 22:41:26');
INSERT INTO `user_raffle_order_000` VALUES (8, 'user001', 100301, '测试活动', 100006, '130698860415', '2024-05-27 14:42:18', 'used', '2024-05-27 22:42:17', '2024-05-27 22:42:18');
INSERT INTO `user_raffle_order_000` VALUES (9, 'user001', 100301, '测试活动', 100006, '777907205658', '2024-05-29 23:20:49', 'used', '2024-05-30 07:20:49', '2024-05-30 07:20:49');
INSERT INTO `user_raffle_order_000` VALUES (10, 'user001', 100301, '测试活动', 100006, '037106411824', '2024-05-29 23:22:10', 'used', '2024-05-30 07:22:10', '2024-05-30 07:22:10');
INSERT INTO `user_raffle_order_000` VALUES (11, 'origami25', 100301, '测试活动', 100006, '067670700350', '2024-06-22 02:07:54', 'used', '2024-06-22 10:07:54', '2024-06-22 10:07:54');
INSERT INTO `user_raffle_order_000` VALUES (12, 'origami25', 100301, '测试活动', 100006, '733298505993', '2024-06-22 02:11:59', 'used', '2024-06-22 10:11:58', '2024-06-22 10:11:58');
INSERT INTO `user_raffle_order_000` VALUES (13, 'origami25', 100301, '测试活动', 100006, '148282410120', '2024-06-22 02:12:47', 'used', '2024-06-22 10:12:46', '2024-06-22 10:12:46');
INSERT INTO `user_raffle_order_000` VALUES (14, 'origami25', 100301, '测试活动', 100006, '068213439911', '2024-06-22 02:17:23', 'used', '2024-06-22 10:17:22', '2024-06-22 10:17:22');
INSERT INTO `user_raffle_order_000` VALUES (15, 'origami25', 100301, '测试活动', 100006, '355946005992', '2024-06-22 02:20:43', 'used', '2024-06-22 10:20:43', '2024-06-22 10:20:43');
INSERT INTO `user_raffle_order_000` VALUES (16, 'origami25', 100301, '测试活动', 100006, '010903932739', '2024-06-22 02:20:54', 'used', '2024-06-22 10:20:54', '2024-06-22 10:20:54');

-- ----------------------------
-- Table structure for user_raffle_order_001
-- ----------------------------
DROP TABLE IF EXISTS `user_raffle_order_001`;
CREATE TABLE `user_raffle_order_001`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
  `activity_id` bigint NOT NULL COMMENT '活动ID',
  `activity_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '活动名称',
  `strategy_id` bigint NOT NULL COMMENT '抽奖策略ID',
  `order_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单ID',
  `order_time` datetime NOT NULL COMMENT '下单时间',
  `order_state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'create' COMMENT '订单状态；create-创建、used-已使用、cancel-已作废',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_order_id`(`order_id` ASC) USING BTREE,
  INDEX `idx_user_id_activity_id`(`user_id` ASC, `activity_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户抽奖订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_raffle_order_001
-- ----------------------------
INSERT INTO `user_raffle_order_001` VALUES (1, 'origami24', 100301, '测试活动', 100006, '837829954453', '2024-06-22 01:59:40', 'used', '2024-06-22 09:59:40', '2024-06-22 09:59:40');
INSERT INTO `user_raffle_order_001` VALUES (2, 'origami24', 100301, '测试活动', 100006, '692282780900', '2024-06-22 01:59:48', 'used', '2024-06-22 09:59:48', '2024-06-22 09:59:48');
INSERT INTO `user_raffle_order_001` VALUES (3, 'origami24', 100301, '测试活动', 100006, '998461237887', '2024-06-22 01:59:56', 'used', '2024-06-22 09:59:55', '2024-06-22 09:59:56');
INSERT INTO `user_raffle_order_001` VALUES (4, 'origami100', 100301, '测试活动', 100006, '438098073325', '2024-06-22 03:31:43', 'used', '2024-06-22 11:31:43', '2024-06-22 11:31:43');
INSERT INTO `user_raffle_order_001` VALUES (5, 'origami100', 100301, '测试活动', 100006, '649778516231', '2024-06-22 03:33:54', 'used', '2024-06-22 11:33:54', '2024-06-22 11:33:54');
INSERT INTO `user_raffle_order_001` VALUES (6, 'origami100', 100301, '测试活动', 100006, '703127392964', '2024-06-22 03:34:04', 'used', '2024-06-22 11:34:04', '2024-06-22 11:34:04');
INSERT INTO `user_raffle_order_001` VALUES (7, 'origami100', 100301, '测试活动', 100006, '735180280972', '2024-06-22 03:34:16', 'used', '2024-06-22 11:34:15', '2024-06-22 11:34:15');
INSERT INTO `user_raffle_order_001` VALUES (8, 'origami100', 100301, '测试活动', 100006, '735049174888', '2024-06-22 03:34:30', 'used', '2024-06-22 11:34:30', '2024-06-22 11:34:30');
INSERT INTO `user_raffle_order_001` VALUES (9, 'origami100', 100301, '测试活动', 100006, '489474305920', '2024-06-22 03:34:39', 'used', '2024-06-22 11:34:38', '2024-06-22 11:34:38');
INSERT INTO `user_raffle_order_001` VALUES (10, 'origami100', 100301, '测试活动', 100006, '366923803771', '2024-06-22 03:34:46', 'used', '2024-06-22 11:34:45', '2024-06-22 11:34:45');
INSERT INTO `user_raffle_order_001` VALUES (11, 'origami100', 100301, '测试活动', 100006, '288120745406', '2024-06-22 03:34:53', 'used', '2024-06-22 11:34:52', '2024-06-22 11:34:52');
INSERT INTO `user_raffle_order_001` VALUES (12, 'origami100', 100301, '测试活动', 100006, '334527462588', '2024-06-22 03:35:00', 'used', '2024-06-22 11:35:00', '2024-06-22 11:35:00');
INSERT INTO `user_raffle_order_001` VALUES (13, 'origami100', 100301, '测试活动', 100006, '056158603651', '2024-06-22 03:35:08', 'used', '2024-06-22 11:35:07', '2024-06-22 11:35:07');
INSERT INTO `user_raffle_order_001` VALUES (14, 'origami100', 100301, '测试活动', 100006, '369341382235', '2024-06-22 03:35:21', 'used', '2024-06-22 11:35:21', '2024-06-22 11:35:21');
INSERT INTO `user_raffle_order_001` VALUES (15, 'origami100', 100301, '测试活动', 100006, '383018329411', '2024-06-22 03:35:29', 'used', '2024-06-22 11:35:28', '2024-06-22 11:35:28');
INSERT INTO `user_raffle_order_001` VALUES (16, 'origami100', 100301, '测试活动', 100006, '062927684538', '2024-06-22 03:36:43', 'used', '2024-06-22 11:36:43', '2024-06-22 11:36:44');
INSERT INTO `user_raffle_order_001` VALUES (17, 'origami100', 100301, '测试活动', 100006, '865157771554', '2024-06-22 03:37:11', 'used', '2024-06-22 11:37:11', '2024-06-22 11:37:11');
INSERT INTO `user_raffle_order_001` VALUES (18, 'origami100', 100301, '测试活动', 100006, '339567817759', '2024-06-22 03:37:25', 'used', '2024-06-22 11:37:25', '2024-06-22 11:37:25');

-- ----------------------------
-- Table structure for user_raffle_order_002
-- ----------------------------
DROP TABLE IF EXISTS `user_raffle_order_002`;
CREATE TABLE `user_raffle_order_002`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
  `activity_id` bigint NOT NULL COMMENT '活动ID',
  `activity_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '活动名称',
  `strategy_id` bigint NOT NULL COMMENT '抽奖策略ID',
  `order_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单ID',
  `order_time` datetime NOT NULL COMMENT '下单时间',
  `order_state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'create' COMMENT '订单状态；create-创建、used-已使用、cancel-已作废',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_order_id`(`order_id` ASC) USING BTREE,
  INDEX `idx_user_id_activity_id`(`user_id` ASC, `activity_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户抽奖订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_raffle_order_002
-- ----------------------------
INSERT INTO `user_raffle_order_002` VALUES (1, 'origami101', 100301, '测试活动', 100006, '003840436800', '2024-06-22 03:44:59', 'used', '2024-06-22 11:44:59', '2024-06-22 11:44:59');
INSERT INTO `user_raffle_order_002` VALUES (2, 'origami101', 100301, '测试活动', 100006, '261982488893', '2024-06-22 03:46:00', 'used', '2024-06-22 11:46:00', '2024-06-22 11:46:00');
INSERT INTO `user_raffle_order_002` VALUES (3, 'origami101', 100301, '测试活动', 100006, '509036893328', '2024-06-22 03:53:26', 'used', '2024-06-22 11:53:25', '2024-06-22 11:53:25');

-- ----------------------------
-- Table structure for user_raffle_order_003
-- ----------------------------
DROP TABLE IF EXISTS `user_raffle_order_003`;
CREATE TABLE `user_raffle_order_003`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
  `activity_id` bigint NOT NULL COMMENT '活动ID',
  `activity_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '活动名称',
  `strategy_id` bigint NOT NULL COMMENT '抽奖策略ID',
  `order_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单ID',
  `order_time` datetime NOT NULL COMMENT '下单时间',
  `order_state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'create' COMMENT '订单状态；create-创建、used-已使用、cancel-已作废',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_order_id`(`order_id` ASC) USING BTREE,
  INDEX `idx_user_id_activity_id`(`user_id` ASC, `activity_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户抽奖订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_raffle_order_003
-- ----------------------------
INSERT INTO `user_raffle_order_003` VALUES (1, 'origami2', 100301, '测试活动', 100006, '809920093949', '2024-05-04 04:53:02', 'used', '2024-05-04 12:53:02', '2024-05-04 12:53:02');
INSERT INTO `user_raffle_order_003` VALUES (2, 'origami2', 100301, '测试活动', 100006, '613575974630', '2024-05-04 04:58:43', 'used', '2024-05-04 12:58:42', '2024-05-04 12:58:42');
INSERT INTO `user_raffle_order_003` VALUES (3, 'origami2', 100301, '测试活动', 100006, '632802870481', '2024-05-04 05:00:08', 'used', '2024-05-04 13:00:08', '2024-05-04 13:00:08');
INSERT INTO `user_raffle_order_003` VALUES (4, 'origami2', 100301, '测试活动', 100006, '972433161890', '2024-05-04 05:04:33', 'used', '2024-05-04 13:04:32', '2024-05-04 13:05:38');
INSERT INTO `user_raffle_order_003` VALUES (5, 'origami2', 100301, '测试活动', 100006, '877507315775', '2024-05-04 05:05:46', 'used', '2024-05-04 13:05:45', '2024-05-04 13:05:45');
INSERT INTO `user_raffle_order_003` VALUES (6, 'origami2', 100301, '测试活动', 100006, '517301751417', '2024-05-04 05:05:53', 'used', '2024-05-04 13:05:52', '2024-05-04 13:05:52');
INSERT INTO `user_raffle_order_003` VALUES (7, 'origami2', 100301, '测试活动', 100006, '261932434171', '2024-05-04 05:06:00', 'used', '2024-05-04 13:05:59', '2024-05-04 13:05:59');
INSERT INTO `user_raffle_order_003` VALUES (8, 'origami2', 100301, '测试活动', 100006, '700573379547', '2024-05-04 05:06:07', 'used', '2024-05-04 13:06:06', '2024-05-04 13:06:06');
INSERT INTO `user_raffle_order_003` VALUES (9, 'origami2', 100301, '测试活动', 100006, '105697168349', '2024-05-04 05:06:18', 'used', '2024-05-04 13:06:17', '2024-05-04 13:06:17');
INSERT INTO `user_raffle_order_003` VALUES (10, 'origami2', 100301, '测试活动', 100006, '959233180689', '2024-05-04 05:06:25', 'used', '2024-05-04 13:06:24', '2024-05-04 13:06:24');
INSERT INTO `user_raffle_order_003` VALUES (11, 'origami2', 100301, '测试活动', 100006, '958431976534', '2024-05-04 05:08:06', 'used', '2024-05-04 13:08:06', '2024-05-04 13:08:06');
INSERT INTO `user_raffle_order_003` VALUES (12, 'origami2', 100301, '测试活动', 100006, '014341440837', '2024-05-04 05:57:04', 'used', '2024-05-04 13:57:03', '2024-05-04 13:57:03');
INSERT INTO `user_raffle_order_003` VALUES (13, 'origami2', 100301, '测试活动', 100006, '104619821752', '2024-05-04 05:57:12', 'used', '2024-05-04 13:57:11', '2024-05-04 13:57:11');
INSERT INTO `user_raffle_order_003` VALUES (14, 'origami2', 100301, '测试活动', 100006, '932807566378', '2024-05-04 05:57:20', 'used', '2024-05-04 13:57:20', '2024-05-04 13:57:20');
INSERT INTO `user_raffle_order_003` VALUES (15, 'origami2', 100301, '测试活动', 100006, '623497872684', '2024-05-04 05:57:32', 'used', '2024-05-04 13:57:32', '2024-05-04 13:57:32');

SET FOREIGN_KEY_CHECKS = 1;
