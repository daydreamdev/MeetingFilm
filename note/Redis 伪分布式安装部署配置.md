---
layout: post
title:  "Redis 伪分布式安装部署配置"
categories: 分布式
tags:  分布式
author:W.Fly
---
* content
{:toc}
redis安装、启动、集群

## 测试环境
- redis需要安装在linux服务器上运行测试，本项目使用linux虚拟机及ssh客户端进行功能测试。
- 集群中应该至少有三个节点，每个节点有一备份节点。需要6台服务器。 
- 搭建伪分布式，需要6个redis实例（生产环境搭建只需改下IP地址即可，步骤相似）。

### 虚拟机
- 创建Centos6.5虚拟机作为linux服务器。

### ssh客户端
- 在开发环境(windows)安装ssh客户端，本项目使用Xshell作为ssh客户端连接虚拟机。

## 安装redis
### redis安装环境
- redis是C语言开发，建议在linux上运行，本项目使用Centos6.5作为安装环境。
- 安装redis需要先将官网下载的源码进行编译，编译依赖gcc环境，如果没有gcc环境，需要安装gcc：yum install gcc-c++

### redis安装
- 版本说明：本项目使用redis3.0版本。3.0版本主要增加了redis集群功能，之前2.几的版本不支持集群模式。
- 源码下载：[下载地址](https://github.com/antirez/redis/archive/3.0.0-rc2.tar.gz)
- 上传服务器，解压，编译

```
tar -zxvf redis-3.0.0-rc2.tar.gz 
mv redis-3.0.0-rc2.tar.gz redis3.0
cd /usr/local/redis3.0
make
make install
```
- 创建集群需要的目录

```
mkdir -p /usr.local/cluster
cd /usr.local/cluster
mkdir 7000
mkdir 7001
mkdir 7002
mkdir 7003
mkdir 7004
mkdir 7005
```
- 修改配置文件redis.conf

```
cp /usr/local/redis3.0/redis.conf /usr.local/cluster
vi redis.conf
##修改配置文件中的下面选项
port 7000
daemonize yes
cluster-enabled yes
cluster-config-file nodes.conf
cluster-node-timeout 5000
appendonly yes
##修改完redis.conf配置文件中的这些配置项之后把这个配置文件分别拷贝到7000/7001/7002/7003/7004/7005目录下面
cp /usr/local/cluster/redis.conf /usr/local/cluster/7000
cp /usr/local/cluster/redis.conf /usr/local/cluster/7001
cp /usr/local/cluster/redis.conf /usr/local/cluster/7002
cp /usr/local/cluster/redis.conf /usr/local/cluster/7003
cp /usr/local/cluster/redis.conf /usr/local/cluster/7004
cp /usr/local/cluster/redis.conf /usr/local/cluster/7005

##注意：拷贝完成之后要修改7001/7002/7003/7004/7005目录下面redis.conf文件中的port参数，分别改为对应的文件夹的名称
```
- 分别启动这6个redis实例


```
cd /usr/local/cluster/7000
redis-server redis.conf

cd /usr/local/cluster/7001
redis-server redis.conf

cd /usr/local/cluster/7002
redis-server redis.conf

cd /usr/local/cluster/7003
redis-server redis.conf

cd /usr/local/cluster/7004
redis-server redis.conf

cd /usr/local/cluster/7005
redis-server redis.conf

```

- 需要一个ruby脚本。在redis源码文件夹下的src目录下。redis-trib.rb 
- 把redis-trib.rb文件复制到到redis-cluster目录下。 
- 执行ruby脚本之前，需要安装ruby环境。

```
yum install ruby

yum install rubygems
```
- 安装redis-trib.rb运行依赖的ruby的包。 [redis-3.0.0.gem下载链接](https://download.csdn.net/download/jack85986370/9491462)

```
gem install redis-3.0.0.gem
```
```
[root@localhost redis-cluster]# ./start-all.sh 
```
### 使用redis-trib.rb创建集群

```
cd /usr/local/redis3.0/src
./redis-trib.rb create --replicas 1 127.0.0.1:7000 127.0.0.1:7001 127.0.0.1:7002 127.0.0.1:7003 127.0.0.1:7004 127.0.0.1:7005
```
- 然后配置完成

![image](https://github.com/daydreamdev/MeetingFilm/raw/master/pic/redis1.png) 

至此redis集群即搭建成功！

### 使用redis-cli命令进入集群环境

```
redis-cli -c -p 7000
```
