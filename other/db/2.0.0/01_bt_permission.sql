alter table st_permission add column
  `enable_cookie` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否开始Cookie(0.否 1.是)' after `hidden`;