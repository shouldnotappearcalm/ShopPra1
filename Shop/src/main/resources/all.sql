--创建数据库
create database shop;

create table testuser(
	id int(11) PRIMARY KEY auto_increment,
	name varchar(40),
	create_time timestamp

)ENGINE = INNODB auto_increment = 1 DEFAULT charset = utf8 COMMENT = '测试';

insert into testuser
values(null,'test02',localtimestamp()),
(null,'test02',localtimestamp()),
(null,'test03',localtimestamp()),
(null,'test04',localtimestamp()),
(null,'test05',localtimestamp());

select * from testuser;

------------------以上为建库和测试

---consumer表
CREATE TABLE consumer (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `addr` varchar(255) DEFAULT NULL,
  `stage` int(11) DEFAULT NULL COMMENT '用户状态： 0未激活 1已经激活',
  `code` varchar(64) DEFAULT NULL COMMENT '激活码',
	`role` varchar(64) default 'consumer' COMMENT '账户身份',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;