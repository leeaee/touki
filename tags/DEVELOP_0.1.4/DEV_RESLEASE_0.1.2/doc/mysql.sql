-- creat tables

/*==============================================================*/
/* Table: admin													*/
/*==============================================================*/
CREATE TABLE admin (
	id bigint AUTO_INCREMENT NOT NULL,
	admin_id varchar(63) NOT NULL,
	password varchar(32) NOT NULL,
	true_name varchar(63),
	phone varchar(20)  NULL,
	mobile varchar(20) NULL,
	email varchar(127) NULL,	
	state int(1) DEFAULT '0',
	description varchar(255) DEFAULT NULL,
	last_login bigint(20) DEFAULT '111111111111',
	create_time bigint(20) DEFAULT '111111111111',
	last_modify bigint(20) DEFAULT '111111111111',
	PRIMARY KEY (id)
) ENGINE = InnoDB ROW_FORMAT = DEFAULT CHARSET=utf8;