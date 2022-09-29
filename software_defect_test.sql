/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2022/9/28 23:36:37                           */
/*==============================================================*/

/*==============================================================*/
/* Table: list_of_permissions                                   */
/*==============================================================*/
create table list_of_permissions
(
   permission_name      char(20) not null,
   permission_level     int not null,
   primary key (permission_level)
);

/*==============================================================*/
/* Table: list_of_user_permission                               */
/*==============================================================*/
create table list_of_user_permission
(
   permission_id        int not null,
   user_id              int,
   name                 char(100),
   permission_level     int,
   primary key (permission_id)
);

/*==============================================================*/
/* Table: log                                                   */
/*==============================================================*/
create table log
(
   acton_id             bigint not null,
   user_id              int,
   name                 char(100),
   content              char(200),
   date_time            datetime,
   primary key (acton_id)
);

/*==============================================================*/
/* Table: user                                                  */
/*==============================================================*/
create table user
(
   user_id              int not null,
   name                 char(100) not null,
   permission_id        int,
   password             char(10),
   phone_number         char(11),
   primary key (user_id, name)
);

/*==============================================================*/
/* Table: user_dataset                                          */
/*==============================================================*/
create table user_dataset
(
   dataset_name         char(100) not null,
   dataset_location     char(200),
   dataset_id           int not null,
   user_id              int,
   name                 char(100),
   primary key (dataset_name, dataset_id)
);

alter table list_of_user_permission add constraint FK_permission_exploited foreign key (permission_level)
      references list_of_permissions (permission_level) on delete restrict on update restrict;

alter table list_of_user_permission add constraint FK_user_permission2 foreign key (user_id, name)
      references user (user_id, name) on delete restrict on update restrict;

alter table log add constraint FK_log_user_action foreign key (user_id, name)
      references user (user_id, name) on delete restrict on update restrict;

alter table user add constraint FK_user_permission foreign key (permission_id)
      references list_of_user_permission (permission_id) on delete restrict on update restrict;

alter table user_dataset add constraint FK_user_dataset foreign key (user_id, name)
      references user (user_id, name) on delete restrict on update restrict;

