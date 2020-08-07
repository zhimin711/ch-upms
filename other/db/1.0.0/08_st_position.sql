-- ----------------------------
-- 3、职位信息表
-- ----------------------------
drop table if exists st_position;
create table st_position
(
    id        bigint(20)  not null auto_increment comment 'ID',
    code      varchar(64) not null comment '职位编码',
    name      varchar(50) not null comment '职位名称',
    sort      int(4)      not null comment '显示顺序',
    remark    varchar(500)         default null comment '备注',
    status    char(1)     not null comment '状态（0停用 1正常）',
    deleted   tinyint(1)           default 0 comment '删除标志（0代表存在 1代表删除）',
    create_by varchar(64)          default '' comment '创建者',
    create_at timestamp   not null default CURRENT_TIMESTAMP comment '创建时间',
    update_by varchar(64)          default '' comment '更新者',
    update_at TIMESTAMP            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NULL COMMENT '更新时间',
    primary key (id),
    key idx_code (`code`)
) comment = '职位信息表';
