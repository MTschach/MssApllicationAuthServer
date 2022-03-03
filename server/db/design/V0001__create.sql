delimiter //

/* ************************************************************ */
/* Applicationid                                                */
/* ************************************************************ */
create table if not exists SYS_USER_APPLICATION_ID (
   ID_SYS_USER                   int            not null primary key,
   APPLICATION_ID                varchar(100)   not null,
   RECORD_STATE                  char(1)        not null default 'A'
) ENGINE=InnoDB DEFAULT CHARSET=utf8; //

set @sql = (select if (
   (select count(*) from information_schema.STATISTICS where TABLE_SCHEMA = DATABASE() and TABLE_NAME = 'SYS_USER_APPLICATION_ID' and INDEX_NAME = 'IDX_APPLICATION_ID') = 0,
     'create unique index IDX_APPLICATION_ID on SYS_USER_APPLICATION_ID (APPLICATION_ID)', 
     'select ''Index IDX_APPLICATION_ID already exists'' '
     )); //
prepare stmt from @sql; //
execute stmt; //
deallocate prepare stmt; //

/* ************************************************************************* */
/* MethodRights                                                              */
/* ************************************************************************* */
create table if not exists METHOD_RIGHTS (
   ID_SYS_USER                   int            not null,
   ID_METHOD_RIGHTS              int            not null,
   METHOD_NAME                   varchar(255)   not null,
   RECORD_STATE                  char(1)        not null default 'A',
   primary key (ID_SYS_USER, ID_METHOD_RIGHTS),
   foreign key (ID_SYS_USER) references SYS_USER_APPLICATION_ID (ID_SYS_USER)
) ENGINE=InnoDB DEFAULT CHARSET=utf8; //


delimiter ;
