SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`
(
    `id`          char(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci     NOT NULL COMMENT '主键ID',
    `name`        varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '权限名',
    `key`         varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限唯一KEY',
    `create_time` datetime                                                      NOT NULL COMMENT '创建时间',
    `update_time` datetime                                                      NOT NULL COMMENT '修改时间',
    `create_by`   char(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci     NOT NULL COMMENT '创建人',
    `update_by`   char(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci     NOT NULL COMMENT '修改人',
    `is_deleted`  bit(1)                                                        NOT NULL DEFAULT FALSE COMMENT '是否删除 0-否 1-是',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `uni_key` (`key` ASC) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '系统-权限'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`
(
    `id`          char(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci    NOT NULL COMMENT '主键ID',
    `name`        varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名',
    `create_time` datetime                                                     NOT NULL COMMENT '创建时间',
    `update_time` datetime                                                     NOT NULL COMMENT '修改时间',
    `create_by`   char(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci    NOT NULL COMMENT '创建人',
    `update_by`   char(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci    NOT NULL COMMENT '修改人',
    `is_deleted`  bit(1)                                                       NOT NULL DEFAULT FALSE COMMENT '是否删除 0-否 1-是',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '系统-角色'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission`
(
    `id`            char(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键ID',
    `role_id`       char(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色ID',
    `permission_id` char(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限ID',
    `create_time`   datetime                                                  NOT NULL COMMENT '创建时间',
    `update_time`   datetime                                                  NOT NULL COMMENT '修改时间',
    `create_by`     char(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人',
    `update_by`     char(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '修改人',
    `is_deleted`    bit(1)                                                    NOT NULL DEFAULT FALSE COMMENT '是否删除 0-否 1-是',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_role_id` (`role_id` ASC) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '系统-角色权限关系'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`
(
    `id`          char(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci     NOT NULL COMMENT '主键ID',
    `username`    varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '用户名',
    `password`    varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
    `salt`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '盐值',
    `phone`       char(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci     NULL     DEFAULT '' COMMENT '手机号',
    `email`       varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT '' COMMENT '邮箱',
    `create_time` datetime                                                      NOT NULL COMMENT '创建时间',
    `update_time` datetime                                                      NOT NULL COMMENT '修改时间',
    `create_by`   char(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci     NOT NULL COMMENT '创建人',
    `update_by`   char(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci     NOT NULL COMMENT '修改人',
    `is_deleted`  bit(1)                                                        NOT NULL DEFAULT FALSE COMMENT '是否删除 0-否 1-是',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `uni_phone` (`phone` ASC) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '系统-用户'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`
(
    `id`          char(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键ID',
    `user_id`     char(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户ID',
    `role_id`     char(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色ID',
    `create_time` datetime                                                  NOT NULL COMMENT '创建时间',
    `update_time` datetime                                                  NOT NULL COMMENT '修改时间',
    `create_by`   char(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人',
    `update_by`   char(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '修改人',
    `is_deleted`  bit(1)                                                    NOT NULL DEFAULT FALSE COMMENT '是否删除 0-否 1-是',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_user_id` (`user_id` ASC) USING BTREE,
    INDEX `idx_role_id` (`role_id` ASC) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '系统-用户角色关系'
  ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;