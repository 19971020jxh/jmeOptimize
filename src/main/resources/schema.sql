DROP  table  if exists  user;
create table user (
  id   INTEGER PRIMARY KEY AUTOINCREMENT ,
  name varchar(100) NOT NULL ,
  password varchar(100) NOT NULL DEFAULT  '123',
  role varchar(20) DEFAULT '用户'
);
DROP  table  if exists  trainData_1;
create table trainData_1(
  id   INTEGER PRIMARY KEY AUTOINCREMENT ,
  xiaolv int   ,
  shuitou real   ,
  chuli real  ,
  liuLiang real   ,
  ORDER_TR real not null
);
DROP  table  if exists  trainData_2;
create table trainData_2(
  id   INTEGER PRIMARY KEY AUTOINCREMENT ,
  xiaolv int   ,
  shuitou real   ,
  chuli real  ,
  liuLiang real   ,
  ORDER_TR real not null
)






