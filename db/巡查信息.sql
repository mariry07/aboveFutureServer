create table tb_patrol_device_type
(
   id                   varchar(32) primary key comment '以父ID为前缀，每级4位，3级',
   parent_id            varchar(32) comment '父ID，一级父ID：0，不可修改',
   level                int(1) comment '1，2，3',
   name                 varchar(64) comment '名称',
   description          varchar(300) comment '说明',
   all_name             varchar(300) comment '拼接名称',
   company_id           varchar(32) comment '公司id',
   is_delete            int(1) not null default 0 comment '是否删除：0未删，1已删'
) comment '设备类型';

create table tb_patrol_check_item
(
   id                   varchar(32) primary key,
   name                 varchar(64) comment '名称',
   description          varchar(300) comment '说明',
   device_type_id 		varchar(32)  comment '设备类型ID',
   company_id           varchar(32) comment '公司id',
   is_delete            int(1) not null default 0 comment '是否删除：0未删，1已删'
) comment '设备类型巡查项';

create table tb_patrol_site
(
   id                   varchar(40) primary key comment 'ID，同时为巡查点二维码值，前缀：ST',
   name                 varchar(64) comment '名称',
   description          varchar(300) comment '说明',
   pos_x                varchar(20) comment '纬度坐标',
   pos_y                varchar(20) comment '经度坐标',
   floor_id             varchar(32) not null comment '楼层id',
   photo_id             varchar(32) comment '图片文件ID',
   company_id           varchar(32) comment '公司id',
   department_id        varchar(32) comment '部门id',
   create_time          datetime comment '建立时间',
   update_time          datetime comment '修改时间',
   is_delete            int(1) not null default 0 comment '是否删除：0未删，1已删'
) comment '巡查点';

create table tb_patrol_device
(
   id                   varchar(40) primary key comment 'ID，同时为巡查设备二维码值，前缀：DV',
   name                 varchar(64) comment '名称',
   device_type_id 		varchar(32)  comment '设备类型ID',
   site_id              varchar(40) comment '巡查点ID',
   model                varchar(45) comment '设备型号',
   spec                 varchar(45) comment '规格',
   manufactor           varchar(255) comment '设备厂商',
   provider             varchar(200) comment '供货单位',
   brand                varchar(200) comment '品牌',
   product_time         datetime comment '出厂时间',
   repaire_time         datetime comment '保修时间',
   expire_time          datetime comment '过期时间',
   alarm_days           int(3) comment '过期预警天数',
   description          varchar(300) comment '说明',
   status               int(3) default 1 comment '状态0未使用1使用中2出现问题3维修4报废5到期6即将到期',
   stop_time            datetime comment '报废时间',
   company_id           varchar(32) comment '公司id',
   department_id        varchar(32) comment '部门id',
   code                 varchar(64) comment '设备编码',
   floor_id             varchar(32) not null comment '楼层id',
   create_time          datetime comment '建立时间',
   update_time          datetime comment '修改时间',
   patrol_time          datetime comment '上次巡查时间，默认1970',
   is_delete            int(1) not null default 0 comment '是否删除：0未删，1已删'
) comment '巡查设备';

create table tb_patrol_plan
(
   id                   varchar(32) primary key comment '巡查计划id',
   do_user_id           varchar(32) comment '做任务的用户id',
   name                 varchar(64) comment '名称',
   description          varchar(300) comment '说明',
   type                 int(2) comment '类型:0日检,1周检,2月检,3季检,4半年检,5年检,6临时',
   is_doing             int(1) not null default 1 comment '计划是否正在执行，0否，1是',
   limit_days           int(6) comment '计划下任务的有效天数',
   finished_rate        float default 0 comment '计划完成率',
   is_patrol            int(1) default 1 comment '是巡查计划还是巡更计划,0(false)巡更,1(true)巡查',
   company_id           varchar(32) comment '公司id',
   department_id        varchar(32) comment '部门id',
   create_user_id       bigint(20) comment '创建计划的用户id',
   is_delete            int(1) not null default 0 comment '是否删除：0未删，1已删',
   create_time          datetime comment '建立时间',
   update_time          datetime comment '修改时间'
) comment '巡查计划';

create table tb_patrol_plan_site
(
	id					varchar(32) primary key,
	plan_id				varchar(32) comment '任务id',
	site_id				varchar(40) comment '巡查/巡更点id',
	index idx_plansite_planid(plan_id)
) comment '巡查计划巡查点';


create table tb_patrol_plan_task
(
   id                   varchar(32) primary key comment '巡查任务id',
   plan_id              varchar(32) comment '巡查计划id',
   name                 varchar(128) comment '任务名称',
   type                 int(2) comment '类型:0日检,1周检,2月检,3季检,4半年检,5年检,6临时',
   do_user_id           varchar(32) comment '做任务的用户id',
   start_time           datetime comment '任务开始时间',
   end_time             datetime comment '任务截止时间',
   finished_rate        float default 0 comment '任务完成率',
   finished_time        datetime comment '任务完成时间',
   status               int(2) comment '任务状态：1进行中，2完成，3未完成',
   update_time          datetime comment '最新修改时间',
   company_id           varchar(32) comment '公司id',
   department_id        varchar(32) comment '部门id',
   is_patrol            int(1) default 1 comment '是巡查计划还是巡更计划,0(false)巡更,1(true)巡查',
   is_delete            int(1) not null default 0 comment '是否删除：0未删，1已删'
) comment '巡查任务表';

create table tb_patrol_plan_task_result
(
   id                   varchar(32) primary key,
   task_id              varchar(32) comment '任务id',
   site_id       		varchar(40) comment '巡查点id',
   device_id     		varchar(40) comment '巡查设备id',
   check_item_id        varchar(32) comment '检查项id',
   remark               varchar(512) comment '任务提交的备注',
   photo_id             varchar(32) comment '图片ID',
   add_time             datetime comment '结果提交时间',
   do_user_id           varchar(32) comment '提交结果用户id',
   check_result         int(1) default 1 comment '检查结果：1正常，2异常'
) comment '巡查任务结果表';


create table tb_patrol_device_repair
(
   id                   varchar(32) primary key,
   task_id              varchar(32) comment '任务的id',
   device_id            varchar(40) comment '设备的ID',
   device_name          varchar(200) comment '设备名称',
   address              varchar(200) comment '所在位置',  
   repair_user_id       varchar(32) comment '维修人员的id',
   patrol_user_id       varchar(32) comment '设备巡查人员的id',  
   check_user_id        varchar(32) comment '设备检查人员id',
   before_photo_id      varchar(32) comment '维修之前的图片ID',
   before_description   varchar(300) comment '故障隐患的描述',
   after_photo_id       varchar(300) comment '维修之后的图片ID',
   after_description    varchar(300) comment '维修结果的描述',
   start_time           datetime comment '维修的开始时间',
   end_time             datetime comment '规定维修的结束时间',
   status               int(10) comment '0未派发1未完成2未审核3完成4超时完成',
   finished_time        datetime comment '完成的维修设备时间',
   period_time          datetime comment '周期时间(有效时间)',
   name                 varchar(300) comment '修理任务的名称',
   create_time          datetime comment '创建时间',
   is_back              int(10) default 0 comment '0:未审核，1:未通过，2:已通过',
   check_description    varchar(300) comment '检测人员检测后的评语',
   check_time           datetime comment '管理员检测维修的时间',
   is_repair            int(10) comment '是否需要维修(0需要维修1不需要维修)维修后不改变',
   company_id           varchar(32) comment '公司id',
   department_id        varchar(32) comment '部门id'
) comment '巡查设备隐患记录表';


create table tb_patrol_work_remind
(
   id                   varchar(32) primary key comment '工作提醒信息id',
   content              varchar(255) not null comment '工作提醒信息内容',
   create_user_id       varchar(32) not null comment '发布人',
   create_time          datetime not null comment '发布时间',
   company_id           varchar(32) comment '公司id',
   department_id        varchar(32) comment '部门id'
) comment '工作提醒信息表';

create table tb_patrol_work_remind_user
(
   id                   varchar(32) primary key comment '主键id',
   remind_id            varchar(32) not null comment '信息id',
   user_id              varchar(32) not null comment '被发布人id',
   is_read              int(2) not null default 0 comment '阅读状态(0：未阅读，1已阅读)',
   read_time          	datetime  comment '发布时间'
) comment '被发布人与信息关系表';














