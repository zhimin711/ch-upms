-- ----------------------------
-- 1、部门表
-- ----------------------------
drop table if exists st_department;
create table st_department
(
    id        bigint(20) not null auto_increment comment '部门id',
    pid       varchar(50)         default '' comment '上级部门id',
    name      varchar(55)         default '' comment '部门名称',
    sort      int(4)              default 1 comment '显示顺序',
    leader    varchar(20)         default null comment '负责人',
    phone     varchar(11)         default null comment '联系电话',
    email     varchar(50)         default null comment '邮箱',
    status    char(1)             default '0' comment '状态（0停用 1正常）',
    deleted  tinyint(1)          default 0 comment '删除标志（0代表存在 1代表删除）',
    create_by varchar(64)         default '' comment '创建者',
    create_at timestamp  not null default CURRENT_TIMESTAMP comment '创建时间',
    update_by varchar(64)         default '' comment '更新者',
    update_at TIMESTAMP           DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NULL COMMENT '更新时间',
    primary key (id),
    key idx_pid (`pid`)
) comment = '部门表';

-- ----------------------------
-- 初始化-部门表数据
-- ----------------------------