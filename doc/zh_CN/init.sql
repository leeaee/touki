-- cs_admin
insert into cs_admin (id, name, password, true_name) values (1, 'admin', '96e79218965eb72c92a549dd5a330112', 'admin');


-- cs_role
insert into cs_role (id, name) values (1, 'Administrator');


-- cs_authority 
insert into cs_authority (id, name, display_name) values (1, 'a_browse_index', 'auth.browse.index');

insert into cs_authority (id, name, display_name) values (2, 'a_browse_admin', 'auth.browse.admin');
insert into cs_authority (id, name, display_name) values (3, 'a_create_admin', 'auth.create.admin');
insert into cs_authority (id, name, display_name) values (4, 'a_modify_admin', 'auth.modify.admin');
insert into cs_authority (id, name, display_name) values (5, 'a_delete_admin', 'auth.delete.admin');

insert into cs_authority (id, name, display_name) values (6, 'a_browse_role', 'auth.browse.role');
insert into cs_authority (id, name, display_name) values (7, 'a_create_role', 'auth.create.role');
insert into cs_authority (id, name, display_name) values (8, 'a_modify_role', 'auth.modify.role');
insert into cs_authority (id, name, display_name) values (9, 'a_delete_role', 'auth.delete.role');


-- cs_resource
insert into cs_resource (id, resource_type, application, value, position) values(1, 'url', 'csadmin', '/index*', 1.9);

insert into cs_resource (id, resource_type, application, value, position) values(2, 'url', 'csadmin', '/admin?method=browse*', 2.0);
insert into cs_resource (id, resource_type, application, value, position) values(3, 'url', 'csadmin', '/admin?method=create*', 2.1);
insert into cs_resource (id, resource_type, application, value, position) values(4, 'url', 'csadmin', '/admin?method=update*', 2.2);
insert into cs_resource (id, resource_type, application, value, position) values(5, 'url', 'csadmin', '/admin?method=delete*', 2.3);
insert into cs_resource (id, resource_type, application, value, position) values(6, 'url', 'csadmin', '/admin*', 2.9);

insert into cs_resource (id, resource_type, application, value, position) values(7, 'url', 'csadmin', '/role?method=browse*', 3.0);
insert into cs_resource (id, resource_type, application, value, position) values(8, 'url', 'csadmin', '/role?method=create*', 3.1);
insert into cs_resource (id, resource_type, application, value, position) values(9, 'url', 'csadmin', '/role?method=update*', 3.2);
insert into cs_resource (id, resource_type, application, value, position) values(10, 'url', 'csadmin', '/role?method=delete*', 3.3);
insert into cs_resource (id, resource_type, application, value, position) values(11, 'url', 'csadmin', '/role*', 3.9);


-- cs_admin_x_role
insert into cs_admin_x_role values(1, 1);


-- cs_role_x_authority
insert into cs_role_x_authority values(1, 1);
insert into cs_role_x_authority values(1, 2);
insert into cs_role_x_authority values(1, 3);
insert into cs_role_x_authority values(1, 4);
insert into cs_role_x_authority values(1, 5);
insert into cs_role_x_authority values(1, 6);
insert into cs_role_x_authority values(1, 7);
insert into cs_role_x_authority values(1, 8);
insert into cs_role_x_authority values(1, 9);


-- cs_resource_x_authority
insert into cs_resource_x_authority values(1, 1);

insert into cs_resource_x_authority values(2, 2);
insert into cs_resource_x_authority values(3, 3);
insert into cs_resource_x_authority values(4, 4);
insert into cs_resource_x_authority values(5, 5);
insert into cs_resource_x_authority values(6, 2);

insert into cs_resource_x_authority values(7, 6);
insert into cs_resource_x_authority values(8, 7);
insert into cs_resource_x_authority values(9, 8);
insert into cs_resource_x_authority values(10, 9);
insert into cs_resource_x_authority values(11, 6);

