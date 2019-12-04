DROP  table  if exists  user;
create table user (
  id int INTEGER PRIMARY KEY AUTOINCREMENT ,
  name varchar(100) NOT NULL ,
  password varchar(100) NOT NULL DEFAULT  '123',
  role varchar(20) DEFAULT '用户'
);
DROP  table  if exists  trainData;
create table trainData(
  id int INTEGER PRIMARY KEY AUTOINCREMENT ,
  jiQi int not null,
  xiaolv int not null ,
  shuitou real not null ,
  chuli real not null,
  liuLiang real not null ,
  ORDER_TR real not null

)






