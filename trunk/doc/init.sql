insert into cs_admin (id, admin_id, password, true_name) values (1, 'admin', '96e79218965eb72c92a549dd5a330112', 'leeaee');
insert into cs_admin (id, admin_id, password, true_name) values (2, 'advis', '96e79218965eb72c92a549dd5a330112', 'roger');


insert into cs_role (id, role_name) values (1, '管理员');
insert into cs_role (id, role_name) values (2, '用户');


insert into cs_authority (id, name, display_name) values (1, 'a_view_admin', '查看用户');
insert into cs_authority (id, name, display_name) values (2, 'a_modify_admin', '管理用户');
insert into cs_authority (id, name, display_name) values (3, 'a_view_role', '查看角色');
insert into cs_authority (id, name, display_name) values (4, 'a_modify_role', '管理角色');


insert into cs_resource (id, resource_type, value, position) values(1, 'url', '/admin?method=adminPreCreate*', 1.0);
insert into cs_resource (id, resource_type, value, position) values(2, 'url', '/admin?method=adminDoDelete*', 2.0);
insert into cs_resource (id, resource_type, value, position) values(3, 'url', '/admin*', 3.0);
insert into cs_resource (id, resource_type, value, position) values(4, 'url', '/security/role!save*', 4.0);
insert into cs_resource (id, resource_type, value, position) values(5, 'url', '/security/role!delete*', 5.0);
insert into cs_resource (id, resource_type, value, position) values(6, 'url', '/security/role*', 6.0);


insert into cs_admin_x_role values(1, 1);
insert into cs_admin_x_role values(1, 2);


insert into cs_role_x_authority values(1, 1);
insert into cs_role_x_authority values(1, 2);
insert into cs_role_x_authority values(1, 3);
insert into cs_role_x_authority values(1, 4);
insert into cs_role_x_authority values(2, 1);
insert into cs_role_x_authority values(2, 3);


insert into cs_resource_x_authority values(1, 2);
insert into cs_resource_x_authority values(2, 2);
insert into cs_resource_x_authority values(3, 1);
insert into cs_resource_x_authority values(4, 4);
insert into cs_resource_x_authority values(5, 4);
insert into cs_resource_x_authority values(6, 3);