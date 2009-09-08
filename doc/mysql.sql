-- creat tables

/*==============================================================*/
/* Table: cs_admin											*/
/*==============================================================*/
CREATE TABLE cs_admin (
	id bigint AUTO_INCREMENT NOT NULL,
	name varchar(63) NOT NULL,
	password varchar(32) NOT NULL,
	true_name varchar(63) NULL,
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


/*==============================================================*/
/* Table: cs_role											*/
/*==============================================================*/
CREATE TABLE cs_role (
	id bigint AUTO_INCREMENT NOT NULL,
	name varchar(63) NOT NULL,
	description varchar(255) DEFAULT NULL,
	create_time bigint(20) DEFAULT '111111111111',
	last_modify bigint(20) DEFAULT '111111111111',	
	PRIMARY KEY (id)
) ENGINE = InnoDB ROW_FORMAT = DEFAULT CHARSET=utf8;


/*==============================================================*/
/* Table: cs_authority											*/
/*==============================================================*/
CREATE TABLE cs_authority (
	id bigint AUTO_INCREMENT NOT NULL,
	name varchar(63) NOT NULL,
	display_name varchar(63) NOT NULL,
	PRIMARY KEY (id)
) ENGINE = InnoDB ROW_FORMAT = DEFAULT CHARSET=utf8;


/*==============================================================*/
/* Table: cs_resource											*/
/*==============================================================*/
CREATE TABLE cs_resource (
	id bigint AUTO_INCREMENT NOT NULL,
	resource_type varchar(63) NOT NULL,
	application varchar(63),	
	value varchar(63) NOT NULL,
	position float NOT NULL,
	description varchar(255) DEFAULT NULL,
	PRIMARY KEY (id)
) ENGINE = InnoDB ROW_FORMAT = DEFAULT CHARSET=utf8;


/*==============================================================*/
/* Table: cs_admin_x_role										*/
/*==============================================================*/
CREATE TABLE cs_admin_x_role (
	admin_id bigint NOT NULL,
	role_id bigint NOT NULL,
	PRIMARY KEY (admin_id, role_id)
) ENGINE = InnoDB ROW_FORMAT = DEFAULT CHARSET=utf8;


/*==============================================================*/
/* Table: cs_role_x_authority									*/
/*==============================================================*/
CREATE TABLE cs_role_x_authority (
	role_id bigint NOT NULL,
	authority_id bigint NOT NULL,
	PRIMARY KEY (role_id, authority_id)
) ENGINE = InnoDB ROW_FORMAT = DEFAULT CHARSET=utf8;


/*==============================================================*/
/* Table: cs_resource_x_authority								*/
/*==============================================================*/
CREATE TABLE cs_resource_x_authority (
	resource_id bigint NOT NULL,
	authority_id bigint NOT NULL,
	PRIMARY KEY (resource_id, authority_id)
) ENGINE = InnoDB ROW_FORMAT = DEFAULT CHARSET=utf8;



