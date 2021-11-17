# 基于SpringBoot的电影后台管理系统

# 前言

本项目为郑州轻工业大学软件学院大三上学期实训项目。项目需求为：使用SSH构建电影后台管理系统。我们组使用了SpringBoot进行开发。

# 配置
配置文件修改application-pro中的配置即可 
 mysql配置: 
      application-pro中的配置进行修改即可
 redis安装与配置:
      https://www.jianshu.com/p/e90317668ae2

# 数据库文件
 movie.sql
# 启动
直接启动即可
# 访问
http://localhost:80/admin/login.html

# 初始用户名和密码设置

需要自行在数据库user表中插入一条数据，密码需要md5加密后存入，可以使用test/utils包下MD5UtilsTest将明文转换为密文
