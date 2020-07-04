-- ----------------------------
-- 11、数据字典表
-- ----------------------------
drop table if exists mt_dict;
create table mt_dict
(
    id        bigint(20) not null auto_increment comment '字典主键',
    pid       varchar(50)         default '' comment '上级id',
    name      varchar(100)        default '' comment '字典名称',
    code      varchar(100)        default '' comment '字典类型',
    sort      int(4)              default 1 comment '显示顺序',
    remark    varchar(500)        default null comment '备注',
    status    char(1)             default '1' comment '状态（0停用 1正常）',
    deleted   tinyint(1)          default 0 comment '删除标志（0代表存在 1代表删除）',
    create_by varchar(64)         default '' comment '创建者',
    create_at timestamp  not null default CURRENT_TIMESTAMP comment '创建时间',
    update_by varchar(64)         default '' comment '更新者',
    update_at TIMESTAMP           DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NULL COMMENT '更新时间',
    primary key (id),
    unique (code)
)  comment = '数据字典表';