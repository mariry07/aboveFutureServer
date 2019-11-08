create table tb_patrol_mark
(
   id                   varchar(40) primary key comment 'ID，同时为巡更点二维码值，前缀：LO',
   name                 varchar(64) comment '名称',
   description          varchar(300) comment '说明',
   pos_x                varchar(20) comment '纬度坐标',
   pos_y                varchar(20) comment '经度坐标',
   company_id           varchar(32) comment '公司id',
   department_id        varchar(32) comment '部门id',
   create_time          datetime comment '建立时间',
   update_time          datetime comment '修改时间',
   del_flag             int(1) not null default 0 comment '是否删除：0未删，1已删',
   create_by            varchar(255) comment '创建人',
   update_by            varchar(255) comment '修改人',
   qr_code              text(0) comment '二维码图片'
) comment '巡更点';

create table tb_patrol_plan_task_line
(
   id                   varchar(32) primary key,
   task_id              varchar(32) comment '任务id',
   mark_id       		varchar(40) comment '巡更点id',
   pos_x                varchar(20) comment '纬度坐标',
   pos_y                varchar(20) comment '经度坐标',
   photo_id             varchar(32) comment '图片ID',
   add_time             datetime comment '提交时间',
   do_user_id           varchar(32) comment '提交用户id'
) comment '巡更任务记录';

create table tb_patrol_user_routes
(
   id                   varchar(32) primary key comment '前缀日期yyyyMMddhhmmss',
   user_id              varchar(32) not null comment '用户ID',
   add_time             datetime comment '开始时间',
   pos_x                varchar(20) comment '纬度坐标',
   pos_y                varchar(20) comment '经度坐标'
) comment '用户轨迹记录';
