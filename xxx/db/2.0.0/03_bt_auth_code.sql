CREATE TABLE bt_auth_code (
  id           BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
  code         VARCHAR(64) NOT NULL UNIQUE COMMENT '授权码',
  content      TEXT        NOT NULL COMMENT '授权内容(JSON)',
  expire_time  DATETIME    NOT NULL COMMENT '过期时间',
  max_uses     INT         DEFAULT 1 COMMENT '最大使用次数',
  used_count   INT         DEFAULT 0 COMMENT '已使用次数',
  status       TINYINT     DEFAULT 1 COMMENT '状态 1有效 0失效 2撤销',
  create_by    VARCHAR(32)      NOT NULL COMMENT '创建人ID',
  create_time  DATETIME    NOT NULL COMMENT '创建时间',
  update_time  DATETIME    NOT NULL COMMENT '更新时间'
) COMMENT='通用授权码表';

CREATE TABLE auth_code_usage_record (
  id           BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
  code         VARCHAR(64) NOT NULL COMMENT '授权码',
  user_id      BIGINT      NOT NULL COMMENT '使用人ID',
  resource_type VARCHAR(32) NOT NULL COMMENT '资源类型',
  resource_id  VARCHAR(64) NOT NULL COMMENT '资源ID',
  action       VARCHAR(32) NOT NULL COMMENT '操作类型',
  use_time     DATETIME    NOT NULL COMMENT '使用时间',
  remark       VARCHAR(255) DEFAULT NULL COMMENT '备注'
) COMMENT='授权码使用记录表';