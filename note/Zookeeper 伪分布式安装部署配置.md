---
layout: post
title:  "Zookeeper 伪分布式安装部署配置"
categories: 分布式
tags:  分布式 zookeeper集群
author: W.Fly
---
* content
{:toc}
zookeeper安装、启动、集群

## 搭建Zookeeper集群

### 1. 搭建要求
真实的集群是需要部署在不同的服务器上的，但是在我们测试时同时启动十几个虚拟机内存会吃不消，所以这里我们搭建伪集群，也就是把所有的服务都搭建在一台虚拟机上，用端口进行区分。

我们这里要求搭建一个三个节点的Zookeeper集群（伪集群）。

### 2. 准备工作
重新部署一台虚拟机作为我们搭建集群的测试服务器。

（1）安装JDK  【此步骤省略】。

（2）Zookeeper压缩包上传到服务器

（3）将Zookeeper解压 ，创建data目录 ，将 conf下zoo_sample.cfg 文件改名为 zoo.cfg【mv zoo_sample.cfg zoo.cfg】

（4）建立/usr/local/zookeeper-cluster目录，将解压后的Zookeeper复制到以下三个目录
```
[root@mini1 ~]# cp -r zookeeper-3.4.5 /usr/local/zookeeper-cluster/zookeeper-1
[root@mini1 ~]# cp -r zookeeper-3.4.5 /usr/local/zookeeper-cluster/zookeeper-2
[root@mini1 ~]# cp -r zookeeper-3.4.5 /usr/local/zookeeper-cluster/zookeeper-3
```
（5） 配置每一个Zookeeper 的dataDir（zoo.cfg） clientPort 分别为2181  2182  2183

修改/usr/local/zookeeper-cluster/zookeeper-1/conf/zoo.cfg

![在这里插入图片描述](https://github.com/wangfei910/wangfei910.github.io/raw/master/_pic/Zookeeper/1.png)

修改/usr/local/zookeeper-cluster/zookeeper-2/conf/zoo.cfg
```
clientPort=2182
dataDir=/usr/local/zookeeper-cluster/zookeeper-2/data
```
修改/usr/local/zookeeper-cluster/zookeeper-3/conf/zoo.cfg
```
clientPort=2183
dataDir=/usr/local/zookeeper-cluster/zookeeper-3/data
```

### 3. 配置集群

（1）在每个zookeeper的 data 目录下创建一个 myid 文件，内容分别是1、2、3 。这个文件就是记录每个服务器的ID

 ![在这里插入图片描述](https://github.com/wangfei910/wangfei910.github.io/raw/master/_pic/Zookeeper/2.png)
 
（2）在每一个zookeeper 的 zoo.cfg配置客户端访问端口（clientPort）和集群服务器IP列表。
```
server.1=192.168.75.10:2881:3881
server.2=192.168.75.10:2882:3882
server.3=192.168.75.10:2883:3883
```

![在这里插入图片描述](https://github.com/wangfei910/wangfei910.github.io/raw/master/_pic/Zookeeper/3.png)

解释：server.服务器ID=服务器IP地址：服务器之间通信端口：服务器之间投票选举端口

### 4. 启动集群
启动集群就是分别启动每个实例。

```
cd  /usr/local/zookeeper-cluster/zookeeper-1/bin/
 ./zkServer.sh start

cd  /usr/local/zookeeper-cluster/zookeeper-2/bin/
 ./zkServer.sh start

cd  /usr/local/zookeeper-cluster/zookeeper-3/bin/
 ./zkServer.sh start
```
启动后我们查询一下每个实例的运行状态

先查询第一个服务

![在这里插入图片描述](https://github.com/wangfei910/wangfei910.github.io/raw/master/_pic/Zookeeper/4.png)

Mode为follower表示是跟随者（从）

再查询第二个服务Mod 为leader表示是领导者（主）

![在这里插入图片描述](https://github.com/wangfei910/wangfei910.github.io/raw/master/_pic/Zookeeper/5.png)

查询第三个为跟随者（从）

![在这里插入图片描述](https://github.com/wangfei910/wangfei910.github.io/raw/master/_pic/Zookeeper/6.png)
