delimiter //

insert into METHOD_RIGHTS (ID_SYS_USER, ID_METHOD_RIGHTS, METHOD_NAME, RECORD_STATE) values (0, 1, '*', 'D')
on duplicate key update METHOD_NAME = '*', RECORD_STATE = 'D'; //
insert into METHOD_RIGHTS (ID_SYS_USER, ID_METHOD_RIGHTS, METHOD_NAME, RECORD_STATE) values (0, 2, 'de.mss.applicationauth.call.TestCall', 'A')
on duplicate key update METHOD_NAME = 'de.mss.applicationauth.call.TestCall', RECORD_STATE = 'A'; //


delimiter ;
