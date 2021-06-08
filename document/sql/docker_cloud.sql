CREATE DATABASE IF NOT EXISTS `docker_cloud` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_bin;

USE `docker_cloud`;

DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details`
(
    `client_id`               varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `resource_ids`            varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL,
    `client_secret`           varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL,
    `scope`                   varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL,
    `authorized_grant_types`  varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL,
    `web_server_redirect_uri` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL,
    `authorities`             varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL,
    `access_token_validity`   int                                                      NULL DEFAULT NULL,
    `refresh_token_validity`  int                                                      NULL DEFAULT NULL,
    `additional_information`  varchar(4096) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `autoapprove`             varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL,
    PRIMARY KEY (`client_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

INSERT INTO `oauth_client_details`
VALUES ('dc-ums', '', '2233qqaazz', 'all', 'password,client_credentials,refresh_token,
authorization_code', '', NULL, 3600, 7200, NULL, 'true');

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `id`                bigint AUTO_INCREMENT COMMENT '用户ID',
    `username`          varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL COMMENT '用户名',
    `password`          varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
    `nickname`          varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '昵称',
    `avatar`            varchar(128) DEFAULT NULL COMMENT '头像地址',
    `create_time`       datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `recent_login_time` datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '最近登录时间',
    `enabled`           tinyint      DEFAULT 1 COMMENT '是否启用',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

INSERT INTO `user`
VALUES (null, 'test1', '{bcrypt}$2a$10$VaphyIrQ7C9aELKTx/Wh1.QqGVvBymhd57NrY/OoQhuAjMgNMoEO6', '测试管理员', NULL, NOW(), NOW(), 1),
       (null, 'test2', '{bcrypt}$2a$10$VaphyIrQ7C9aELKTx/Wh1.QqGVvBymhd57NrY/OoQhuAjMgNMoEO6', '测试用户', NULL, NOW(), NOW(), 1);


DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`
(
    `id`      bigint AUTO_INCREMENT COMMENT '用户角色ID',
    `user_id` bigint NOT NULL COMMENT '用户ID',
    `role_id` bigint NOT NULL COMMENT '角色ID',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

INSERT INTO `user_role`
VALUES (1, 1, 1),
       (2, 1, 2),
       (3, 2, 2);


DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`
(
    `id`       bigint AUTO_INCREMENT COMMENT '角色ID',
    `name`     varchar(32) NOT NULL COMMENT '角色名',
    `nickname` varchar(32) DEFAULT NULL COMMENT '角色昵称',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

INSERT INTO `role`
VALUES (1, 'ROLE_ADMIN', '管理员'),
       (2, 'ROLE_USER', '用户');

DROP TABLE IF EXISTS `resource`;
CREATE TABLE `resource`
(
    `id`     bigint AUTO_INCREMENT COMMENT '资源ID',
    `perms`  varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '权限标识',
    `method` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci        NULL DEFAULT NULL COMMENT '请求方法类型（POST/PUT/DELETE/PATCH）',
    `name`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci      DEFAULT NULL COMMENT '资源名称',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

INSERT INTO `resource`
VALUES (1, '/ums/statistics/**', '*', '统计用户信息'),
       (2, '/ums/user/**', '*', '用户管理'),
       (3, '/dockeradmin/**', '*', 'Docker管理'),
       (4, '/dockerserve/admin/**', '*', 'Docker管理')
;

DROP TABLE IF EXISTS `resource_role`;
CREATE TABLE `resource_role`
(
    `id`          bigint AUTO_INCREMENT COMMENT '资源角色ID',
    `resource_id` bigint NOT NULL COMMENT '资源ID',
    `role_id`     bigint NOT NULL COMMENT '角色ID',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

INSERT INTO `resource_role`
VALUES (1, 1, 2),
       (2, 2, 2),
       (3, 3, 2)
;

DROP TABLE IF EXISTS `image`;
CREATE TABLE `image`
(
    `id`          bigint AUTO_INCREMENT,
    `name`        varchar(64) COMMENT '名称',
    `version`     varchar(16) COMMENT '版本',
    `description` varchar(512) COMMENT '描述',
    `indicate`    varchar(64) NOT NULL COMMENT '标识',
    `user_id`     bigint COMMENT '用户ID',
    `share`       tinyint  DEFAULT 0 COMMENT '是否共享',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

INSERT INTO `image`
VALUES (1, 'test-image', '1.0.0', '测试镜像', '735af156ac7b', 1, 1, NOW());

DROP TABLE IF EXISTS `application`;
CREATE TABLE `application`
(
    `id`          bigint AUTO_INCREMENT,
    `name`        varchar(64) COMMENT '名称',
    `description` varchar(512) COMMENT '描述',
    `user_id`     bigint NOT NULL COMMENT '用户ID',
    `share`       tinyint  DEFAULT 0 COMMENT '是否共享',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

INSERT INTO `application`
VALUES (1, 'test-01', '测试', 1, 1, NOW(), NOW());

DROP TABLE IF EXISTS `serve`;
CREATE TABLE `serve`
(
    `id`             bigint AUTO_INCREMENT,
    `name`           varchar(64) COMMENT '名称',
    `description`    varchar(512) COMMENT '描述',
    `serve_indicate` varchar(64) NOT NULL COMMENT '服务标识',
    `image_indicate` varchar(64) NOT NULL COMMENT '镜像标识',
    `container_name` varchar(64) NOT NULL COMMENT '容器名称',
    `environment`    varchar(512) COMMENT '环境信息（以分号分割）',
    `port`           varchar(64) COMMENT '端口信息（以分号分割）',
    `volume`         varchar(256) COMMENT '挂载目录（以分号分割）',
    `share`          tinyint DEFAULT 0 COMMENT '是否共享',
    `application_id` bigint COMMENT '应用ID',
    `user_id`        bigint COMMENT '用户ID',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

INSERT INTO `serve`
VALUES (1, '测试服务', '测试服务', 1, 'test-01', '735af156ac7b', 'test-01', NULL, '8080', NULL, 1, 1);

DROP TABLE IF EXISTS `serve_link`;
CREATE TABLE `serve_link`
(
    `id`                bigint AUTO_INCREMENT,
    `serve_indicate`    varchar(64) NOT NULL COMMENT '链接服务的标识',
    `be_serve_indicate` varchar(64) NOT NULL COMMENT '被链接服务的标识',
    `alias`             varchar(16) COMMENT '被链接服务的别名',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `serve_depend`;
CREATE TABLE `serve_depend`
(
    `id`                bigint AUTO_INCREMENT,
    `serve_indicate`    varchar(64) NOT NULL COMMENT '依赖服务的标识',
    `be_serve_indicate` varchar(64) NOT NULL COMMENT '被依赖服务的标识',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

