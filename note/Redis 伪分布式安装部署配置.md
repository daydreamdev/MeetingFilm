* content
{:toc}
redis 安装、启动、集群

## 测试环境
- redis 需要安装在 linux 服务器上运行测试，本项目使用 linux 虚拟机及 ssh 客户端进行功能测试。
- 集群中应该至少有三个节点，每个节点有一备份节点。需要 6 台服务器。 
- 搭建伪分布式，需要 6 个 redis 实例（生产环境搭建只需改下 IP 地址即可，步骤相似）。

### 虚拟机
- 创建 Centos6.5 虚拟机作为 linux 服务器。

### ssh 客户端
- 在开发环境(windows)安装ssh客户端，本项目使用 Xshell 作为 ssh 客户端连接虚拟机。

## 安装 redis
### redis 安装环境
- redis 是 C 语言开发，建议在 linux上 运行，本项目使用 Centos6.5 作为安装环境。
- 安装 redis 需要先将官网下载的源码进行编译，编译依赖 gcc 环境，如果没有 gcc 环境，需要安装 gcc：yum install gcc-c++

### redis 安装
- 版本说明：本项目使用 redis3.0 版本。3.0 版本主要增加了 redis 集群功能，之前2.几的版本不支持集群模式。
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
- 修改配置文件 redis.conf

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
##修改完 redis.conf 配置文件中的这些配置项之后把这个配置文件分别拷贝到 7000/7001/7002/7003/7004/7005 目录下面
cp /usr/local/cluster/redis.conf /usr/local/cluster/7000
cp /usr/local/cluster/redis.conf /usr/local/cluster/7001
cp /usr/local/cluster/redis.conf /usr/local/cluster/7002
cp /usr/local/cluster/redis.conf /usr/local/cluster/7003
cp /usr/local/cluster/redis.conf /usr/local/cluster/7004
cp /usr/local/cluster/redis.conf /usr/local/cluster/7005

##注意：拷贝完成之后要修改 7001/7002/7003/7004/7005 目录下面 redis.conf 文件中的 port 参数，分别改为对应的文件夹的名称
```
- 分别启动这 6 个 redis 实例


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
- 需要一个 ruby 脚本。在 redis 源码文件夹下的 src 目录下。 
- 把 redis-trib.rb 文件复制到 redis-cluster 目录下。 
- 执行 ruby 脚本之前，需要安装 ruby 环境。

```
yum install ruby
yum install rubygems
```
- 安装 redis-trib.rb 运行依赖的 ruby 的包。 [redis-3.0.0.gem 下载链接](https://download.csdn.net/download/jack85986370/9491462)

```
gem install redis-3.0.0.gem
```

### 使用 redis-trib.rb 创建集群

```
cd /usr/local/redis3.0/src
./redis-trib.rb create --replicas 1 127.0.0.1:7000 127.0.0.1:7001 127.0.0.1:7002 127.0.0.1:7003 127.0.0.1:7004 127.0.0.1:7005
```
- 然后配置完成

![image](https://github.com/daydreamdev/MeetingFilm/raw/master/pic/Redis/1.png) 

至此 redis 集群即搭建成功！
### 使用 redis-cli 命令进入集群环境
```
redis-cli -c -p 7000
```
