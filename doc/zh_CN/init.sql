insert into cs_admin (id, admin_id, password, true_name) values (1, 'admin', '96e79218965eb72c92a549dd5a330112', 'leeaee');
insert into cs_admin (id, admin_id, password, true_name) values (2, 'advis', '96e79218965eb72c92a549dd5a330112', 'roger');


insert into cs_role (id, role_name) values (1, '管理员');
insert into cs_role (id, role_name) values (2, '用户');


insert into cs_authority (id, name, display_name) values (1, 'a_browse_index', '首页浏览');
insert into cs_authority (id, name, display_name) values (2, 'a_browse_admin', '管理员浏览');
insert into cs_authority (id, name, display_name) values (3, 'a_manage_admin', '管理员管理');
insert into cs_authority (id, name, display_name) values (4, 'a_browse_role', '角色浏览');
insert into cs_authority (id, name, display_name) values (5, 'a_modify_role', '角色管理');


insert into cs_resource (id, resource_type, value, position) values(1, 'url', '/index*', 1.0);
insert into cs_resource (id, resource_type, value, position) values(2, 'url', '/admin?method=adminPreCreate*', 2.0);
insert into cs_resource (id, resource_type, value, position) values(3, 'url', '/admin?method=adminDoDelete*', 3.0);
insert into cs_resource (id, resource_type, value, position) values(4, 'url', '/admin*', 4.0);
insert into cs_resource (id, resource_type, value, position) values(5, 'url', '/admin/role?method=rolePreCreate*', 5.0);
insert into cs_resource (id, resource_type, value, position) values(6, 'url', '/admin/role?method=roleDoDelete*', 6.0);
insert into cs_resource (id, resource_type, value, position) values(7, 'url', '/admin/role*', 7.0);


insert into cs_admin_x_role values(1, 1);
insert into cs_admin_x_role values(2, 2);


insert into cs_role_x_authority values(1, 1);
insert into cs_role_x_authority values(1, 2);
insert into cs_role_x_authority values(1, 3);
insert into cs_role_x_authority values(1, 4);
insert into cs_role_x_authority values(1, 5);
insert into cs_role_x_authority values(2, 1);
insert into cs_role_x_authority values(2, 2);


insert into cs_resource_x_authority values(1, 1);
insert into cs_resource_x_authority values(2, 3);
insert into cs_resource_x_authority values(3, 3);
insert into cs_resource_x_authority values(4, 2);
insert into cs_resource_x_authority values(5, 5);
insert into cs_resource_x_authority values(6, 5);
insert into cs_resource_x_authority values(7, 4);