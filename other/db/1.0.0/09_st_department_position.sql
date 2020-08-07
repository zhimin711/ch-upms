-- ----------------------------
-- 8、组织和职位关联表  角色1-N组织
-- ----------------------------
drop table if exists st_department_position;
create table st_department_position
(
    department_id bigint(20) not null comment '组织ID',
    position_id   bigint(20) not null comment '职位ID',
    position_amount        int(6) default 1 comment '职位配额',
    primary key (department_id, position_id)
) engine = innodb comment = '组织和职位关联表';
