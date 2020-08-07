-- ----------------------------
-- 9、用户与组织和职位关联表  用户1-N组织-N职位
-- ----------------------------
drop table if exists st_user_department_position;
create table st_user_department_position
(
    user_id       bigint(20) not null comment '用户ID',
    department_id varchar(100) not null comment '组织ID',
    position_id   bigint(20) not null comment '职位ID',
    primary key (user_id, department_id, position_id),
    key idx_dept_id(department_id)
) engine = innodb comment = '用户与组织和职位关联表';