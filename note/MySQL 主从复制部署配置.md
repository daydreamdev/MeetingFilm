* content
{:toc}
MySQL 安装、主从复制

Mysql 的主从复制至少是需要两个 Mysql 的服务，当然 Mysql 的服务是可以分布在不同的服务器上，也可以在一台服务器上启动多个服务。

首先确保主从服务器上的 Mysql 版本相同。

## 主从复制
### 复制功能
- 数据分布
- 负载均衡（读）
- 备份
- 高可用和故障切换
- MySQL 升级测试

### 复制原理
Mysql 中有一种日志叫做 bin 日志（二进制日志）。这个日志会记录下所有修改了数据库的 SQL 语句（insert,update,delete,ALTER TABLE,grant 等等）。

主从复制的原理其实就是把主服务器上的 BIN 日志复制到从服务器上执行一遍，这样从服务器上的数据就和主服务器上的数据相同了。

### 复制流程图

![image](https://github.com/wangfei910/wangfei910.github.io/raw/master/_pic/MySQL/1.png)

### 复制过程
- 主节点必须启用二进制日志，记录任何修改数据库数据的事件。
- 从节点开启一个线程 (I/O Thread) 把自己扮演成 mysql 的客户端，通过 mysql 协议，请求主节点的二进制日志文件中的事件
- 主节点启动一个线程 (dump Thread)，检查自己二进制日志中的事件，跟对方请求的位置对比，如果不带请求位置参数，则主节点就会从第一个日志文件中的第一个事件一个一个发送给从节点。
- 从节点接收到主节点发送过来的数据把它放置到中继日志 (Relay log) 文件中。并记录该次请求到主节点的具哪个二进制日志文件的哪个位置。
- 从节点启动另外一个线程 (sql Thread )，把 replaylog 中的事件读取出来，并在本地再执行一次。

### 复制中线程的作用
主节点：
- Dump Thread:为每个 Slave 的 I/O Thread启动一个 dump 线程，用于向从节点发送二进制事件。

从节点：
- I/O Thread:从 Master 请求二进制日志事件，并保存于中继日志中。
- Sql Thread:从中继日志中读取日志事件，在本地完成重放。

### 

## MySQL 安装部署
### 部署环境
本项目使用 Centos6.5 虚拟机作为 linux 服务器，并使用 Xshell 作为 ssh 客户端连接虚拟机进行安装部署和功能测试。

### 安装 mysql
安装服务器端：
```
yum install mysql-server

yum install mysql-devel
```
安装客户端：
```
yum install mysql
```
### 启动服务并允许远程连接到该 mysql 服务器
启动服务

```
service mysqld start
```
获取初始密码

```
grep "password" /var/log/mysqld.log
```
设置新密码

```
set password for 'root'@'localhost' = password('test123');
```
登陆到 mysql: mysql -uroot -ptest123

```
[root@localhost ~]# mysql -uroot -ptest123  #账号 root，密码 test123
Welcome to the MySQL monitor.  Commands end with ; or \g.
Your MySQL connection id is 18
Server version: 5.5.35-log MySQL Community Server (GPL) by Remi

Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.

Oracle is a registered trademark of Oracle Corporation and/or its
affiliates. Other names may be trademarks of their respective
owners.

Type 'help;' or '\h' for help. Type '\c' to clear the current input statement.

mysql>
```
这时通过 SQLyog 连接是是失败的。需要设置下 mysql 库下面的 user 表，允许任意主机连接

```
update mysql.user set host='%' where user='root' and host='localhost';
```
若是进入 mysql 报这个错：Access denied for user (using password: YES)

则如下解决：
- 关闭 mysql

```
 service mysqld stop
```
- 屏蔽权限

```
mysqld_safe --skip-grant-table
```
- 另起一个终端执行如下：

```
[root@localhost ~]# mysql -u root

mysql>delete from user where user='';

mysql>flush privileges;   #这个一定要执行，否则关闭之前的终端错误会重现

mysql>\q
```
## 配置主从复制
开启两个 mysql 服务
```
主 master：192.168.8.10
从 slave：192.168.8.11

```
### 配置主节点：
授权给从数据库服务器

```
mysql>GRANT REPLICATION SLAVE ON *.* to 'rep1'@'192.168.8.11' identified by 'test123456';
mysql>FLUSH PRIVILEGES;
```
修改主库配置文件，开启 binlog，并设置 server-id，每次修改配置文件后都要重启 mysql 服务才会生效

```
vim /etc/my.cnf
```
在该配置文件 [mysqld] 下面添加下面内容：

```
[mysqld]
log-bin=/var/lib/mysql/binlog
server-id=1
binlog-do-db = cmdb

datadir=/var/lib/mysql
socket=/var/lib/mysql/mysql.sock
......
```
修改配置文件后，重启服务：

```
service mysqld restart
```
查看主服务器当前二进制日志名和偏移量，这个操作的目的是为了在从数据库启动后，从这个点开始进行数据的恢复

```mysql
mysql> show master status;
+---------------+----------+--------------+------------------+
| File          | Position | Binlog_Do_DB | Binlog_Ignore_DB |
+---------------+----------+--------------+------------------+
| binlog.000001 |     1304 | cmdb         |                  |
+---------------+----------+--------------+------------------+
row in set (0.00 sec)
```
主节点已配置好。

### 配置从节点
在 /etc/my.cnf 添加下面配置：

```
[mysqld]
server-id=2
master-host=192.168.8.10
master-user=rep1
master-password=test123456
master-port=3306
replicate-do-db=cmdb
......
```
mysql5.5+ 版本主从复制不支持这些变量，需要在从库上用命令来设置：

```
mysql> CHANGE MASTER TO MASTER_HOST='192.168.8.10',
　　MASTER_PORT=3306,
　　MASTER_USER='rep1',
　　MASTER_PASSWORD='test123456',
　　MASTER_LOG_FILE='binlog.000001',
　　MASTER_LOG_POS=1304; #后面两个参数的值与主库保持一致
```
启动 slave 进程

```
mysql> slave start;
Query OK, 0 rows affected (0.04 sec)
```
查看从节点的状态，如果下面两项值为 YES，则表示配置正确：

![image](https://github.com/wangfei910/wangfei910.github.io/raw/master/_pic/MySQL/2.png)

从库正在等待主库更新数据。。。

### 同步主库已有数据到从库
主库操作：
- 停止主库的数据更新操作
```
mysql>flush tables with read lock;
```
- 新开终端，生成主数据库的备份（导出数据库）
```
[root@localhost ~]# mysqldump -uroot -ptest123 cmdb > cmdb.sql
```
- 将备份文件传到从库
```
[root@localhost ~]# scp cmdb.sql root@192.168.8.11:/root/
```
- 主库解锁
```
mysql>unlock tables;
```
从库操作：
- 停止从库 slave
```
mysql>slave stop;
```
- 新建数据库 cmdb
```
mysql> create database cmdb default charset utf8;
```
- 导入数据
```
[root@ops-dev ~]# mysql -uroot -ptest123 cmdb<cmdb.sql
```
- 查看从库已有该数据库和数据 
```mysql
mysql> show databases;
+--------------------+
| Database           |
+--------------------+
| information_schema |
| cmdb               |
| mysql              |
| performance_schema |
| test               |
+--------------------+
```
此时主从库的数据完全一致，如果对主库进行增删改操作，从库会自动同步进行操作。
