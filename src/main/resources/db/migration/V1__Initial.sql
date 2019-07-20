create table whitelist
(
  id     int auto_increment,
  client varchar(128) null,
  regex  varchar(128) not null,
  constraint whitlist_pk
    primary key (id)
);