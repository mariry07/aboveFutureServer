create table tb_device
(
   id                   varchar(32) primary key,
   name                 varchar(100) comment '设备名称',
   code                 varchar(50) not null comment '设备编码',
   version              varchar(32) comment '版本',
   system_type          int(2) not null comment '系统类型 10消控联网系统 11消防用水系统 12用电安全系统 13动态监测系统 14燃气监测系统',
   device_type			int(4) not null comment '设备类型 1001无线烟感主机',
   running_state        int(4) not null default 0 comment '0未激活，1离线，2正常，3故障，4报警，5禁用', 
   charge_name          varchar(50) comment '负责人',
   charge_phone         varchar(50) comment '负责人电话',
   sim                  varchar(50) comment 'SIM卡ID',
   imei                 national varchar(255) comment '移动设备识别码',
   photo_id             varchar(32) comment '图片文件ID', 
   is_outdoor           int(1) not null comment '是否室外0 室内，1室外',
   position             varchar(255) comment '设备位置信息',
   pos_x                varchar(20) comment '纬度坐标',
   pos_y                varchar(20) comment '经度坐标',
   floor_id             varchar(32) comment '设备所属公司地理位置ID',
   company_id           varchar(32) comment '公司id',
   department_id        varchar(32) comment '部门id',
   camera_id            varchar(32) comment '关联摄像头ID',
   add_time             datetime comment '添加时间',
   update_time          datetime
) comment '设备表';

create table tb_sensor_type
(
   id                   varchar(32) primary key,
   system_type          int(2) not null comment '系统类型 10消控联网系统 11消防用水系统 12用电安全系统 13动态监测系统 14燃气监测系统',
   device_type			int(4) not null comment '设备类型 1001无线烟感主机',
   name                 varchar(50) not null comment '传感器名称',
   fault_name        	varchar(64) comment '故障名称',
   alarm_name        	varchar(64) comment '告警名称',
   unit                 varchar(10) comment '单位',
   alarm_level          int(4) not null default 1 comment '报警等级，0为故障,1,2,3,低中高',
   limit_up             decimal(8,2) comment '上限阈值',
   limit_down           decimal(8,2) comment '下限阈值',
   pre_limit_up         int(2) default 80 comment '预警上限（%）',
   pre_limit_down       int(2) comment '预警下限（%）'
) comment '传感器类型';

create table tb_sensor
(
   id                   varchar(32) primary key,
   device_id            varchar(32) comment '设备ID',
   sensor_type_id       varchar(32) comment '传感器类型ID',
   last_time            datetime not null comment '最后数据传输时间',
   last_value           decimal(8,2) comment '最后采集数据',
   status         		int(4) not null default 1 comment '状态，1正常，2故障'
)comment '传感器表';

create table tb_sensor_log
(
   id                   varchar(32) primary key,
   device_id            varchar(32) comment '设备ID',
   sensor_type_id       varchar(32) comment '传感器类型ID',
   log_time             datetime not null comment '日志时间',
   log_type             int(4) not null default 1 comment '日志类型，0故障，1正常，2报警',
   data_type            int(4) not null default 0 comment '日志类型，0代表双精度浮点型，1代表文本，2代表json文本',
   log_data             varchar(512) not null
) comment '传感器记录表';

create table tb_device_fault
(
   id                   varchar(32) primary key,
   device_id			varchar(32) comment '设备id',
   device_name          varchar(100) comment '主机名称',
   device_code          varchar(50) comment '主机编码',
   device_charge_name   varchar(50) comment '负责人',
   device_version       varchar(32) comment '版本',
   system_type          int(2) not null comment '系统类型 10消控联网系统 11消防用水系统 12用电安全系统 13动态监测系统 14燃气监测系统',
   device_type			int(4) not null comment '设备类型 1001无线烟感主机',
   position          	varchar(255) comment '地理/报警位置',
   status               int(2) not null default 0 comment '0未处理 1已处理 2误报',  
   deal_user_id         varchar(20) comment '处理人id',
   deal_detail          varchar(100) comment '处理详情',
   add_time             datetime comment '故障时间',
   update_time          datetime comment '更新时间',
   deal_time            datetime comment '处理时间',
   fault_count          int(11) not null default 0 comment '故障次数',
   sensor_type_id       varchar(32) default 0 comment '传感器类型id',
   fault_type_name      varchar(64) comment '故障类型名称',
   company_id           varchar(32) comment '公司id',
   department_id        varchar(32) comment '部门id'
) comment '主机故障';

create table tb_server_alarm
(
   id                   varchar(32) primary key,
   device_id			varchar(32) comment '设备id',
   device_name          varchar(100) comment '主机名称',
   device_code          varchar(50) comment '主机编码',
   device_charge_name   varchar(50) comment '负责人',
   device_version       varchar(32) comment '版本',
   system_type          int(2) not null comment '系统类型 10消控联网系统 11消防用水系统 12用电安全系统 13动态监测系统 14燃气监测系统',
   device_type			int(4) not null comment '设备类型 1001无线烟感主机',
   position          	varchar(255) comment '地理/报警位置',
   level                int(4) default 1 comment '报警等级，0为故障,1,2,3,低中高',
   status               int(2) not null default 0 comment '0未处理 1已处理',
   deal_type            int(2) comment '处理类型：0测试 1维保检修 2误报 3 有效处理 4 其它',
   deal_user_id         bigint(20) comment '处理人id',
   deal_detail          varchar(100) comment '报警值',
   add_time             datetime comment '报警时间',
   update_time          datetime comment '更新时间',
   deal_time            datetime comment '处理时间',
   alarm_count          int(11) not null default 0 comment '报警次数',
   alarm_data           decimal(8,2) comment '告警数据',
   limit_up             decimal(8,2) comment '上限阈值',
   limit_down           decimal(8,2) comment '下限阈值',
   unit                 varchar(10) comment '数据单位',
   sensor_type_id       varchar(32) default 0 comment '传感器类型id',
   alarm_type_name      varchar(64) comment '告警类型名称',
   company_id           varchar(32) comment '公司id',
   department_id        varchar(32) comment '部门id'
) comment '报警';


