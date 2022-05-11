alter table st_user add column deleted  tinyint(1)          default 0 comment '删除标志（0代表存在 1代表删除）' after `status`;
alter table st_role add column deleted  tinyint(1)          default 0 comment '删除标志（0代表存在 1代表删除）' after `status`;
alter table st_permission add column deleted  tinyint(1)          default 0 comment '删除标志（0代表存在 1代表删除）' after `status`;