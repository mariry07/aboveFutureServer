create table tb_floor
(
   id                   varchar(32) primary key comment '以父ID为前缀，每级4位，2级',
   parent_id			varchar(32) comment '父ID，一级父ID：0，不可修改',
   company_id           varchar(32) comment '公司id',
   name                 national varchar(100) comment '建筑物名称',
   all_name             varchar(300) comment '拼接名称',
   pos_x                varchar(20) comment '纬度坐标',
   pos_y                varchar(20) comment '经度坐标',
   photo_id             varchar(32) comment '图片文件ID',
   sort                 int(5) comment '显示顺序,楼层显示',
   level                int(2) comment '1，2',
   department_id        varchar(32) comment '部门id',
   is_delete            int(1) not null default 0 comment '是否删除：0未删，1已删'
) comment '楼层信息';

