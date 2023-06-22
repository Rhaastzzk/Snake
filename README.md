# Snake
基于Java的贪吃蛇小游戏
首先有一个玩家登录系统，需要连mysql接数据库，数据库名字叫stu
建表语言为：
CREATE TABLE user(
id varchar(20) PRIMARY KEY,
password varchar(16)
 )DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
 注册成功后登录，即可进入游戏界面
