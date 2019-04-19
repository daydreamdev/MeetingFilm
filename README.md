# Meeting 电影网

## 项目已经部署，在线 [Demo](http://www.meetingfilm.xyz)

## 基于微服务架构的在线电影购票平台

- 用户模块：用户注册、登录、退出、信息修改
- 影院模块：影院列表查询，特定电影场次查询
- 电影模块：影片信息，影片多条件查询
- 订单模块：在线选座，订单生成、订单查询、超时关单、应用限流、服务降级
- 支付模块：支付宝沙箱 SDK 集成、二维码生成、扫码支付、TCC 分布式事务

## 项目演示

- 用户登录注册
- 电影搜索
- 在线选座
- 订单生成
- 支付宝沙箱扫码支付
- 订单查看
# 项目演示
# 架构图
![架构图](https://github.com/daydreamdev/MeetingFilm/blob/master/pic/%E6%9E%B6%E6%9E%84%E5%9B%BE.png)
# 项目部署
- [Redis 伪分布式安装部署配置](https://github.com/daydreamdev/MeetingFilm/blob/master/note/Redis%20%E4%BC%AA%E5%88%86%E5%B8%83%E5%BC%8F%E5%AE%89%E8%A3%85%E9%83%A8%E7%BD%B2%E9%85%8D%E7%BD%AE.md) 
- [Zookeeper 伪分布式安装部署配置](https://github.com/daydreamdev/MeetingFilm/blob/master/note/Zookeeper%20%E4%BC%AA%E5%88%86%E5%B8%83%E5%BC%8F%E5%AE%89%E8%A3%85%E9%83%A8%E7%BD%B2%E9%85%8D%E7%BD%AE.md) 
- [MySQL 主从复制部署配置](https://github.com/daydreamdev/MeetingFilm/blob/master/note/MySQL%20%E4%B8%BB%E4%BB%8E%E5%A4%8D%E5%88%B6%E9%83%A8%E7%BD%B2%E9%85%8D%E7%BD%AE.md) 
- [项目部署](https://github.com/daydreamdev/MeetingFilm/blob/master/note/%E9%A1%B9%E7%9B%AE%E9%83%A8%E7%BD%B2.md) 
# 项目总结
- [缓存穿透、缓存雪崩、缓存击穿](https://github.com/daydreamdev/MeetingFilm/blob/master/note/%E7%BC%93%E5%AD%98%E7%A9%BF%E9%80%8F%E3%80%81%E7%BC%93%E5%AD%98%E9%9B%AA%E5%B4%A9%E3%80%81%E7%BC%93%E5%AD%98%E5%87%BB%E7%A9%BF.md)
- [缓存与数据库的一致性](https://github.com/daydreamdev/MeetingFilm/blob/master/note/%E7%BC%93%E5%AD%98%E4%B8%8E%E6%95%B0%E6%8D%AE%E5%BA%93%E7%9A%84%E4%B8%80%E8%87%B4%E6%80%A7.md)
- [限流算法](https://github.com/daydreamdev/MeetingFilm/blob/master/note/%E9%99%90%E6%B5%81%E7%AE%97%E6%B3%95.md)
- [定时任务](https://github.com/daydreamdev/MeetingFilm/blob/master/note/%E5%AE%9A%E6%97%B6%E4%BB%BB%E5%8A%A1.md)
- [分库分表](https://github.com/daydreamdev/MeetingFilm/blob/master/note/%E5%88%86%E5%BA%93%E5%88%86%E8%A1%A8.md)
- [浅析分布式事务](https://github.com/daydreamdev/MeetingFilm/blob/master/note/%E6%B5%85%E6%9E%90%E5%88%86%E5%B8%83%E5%BC%8F%E4%BA%8B%E5%8A%A1.md)
- [如何保证业务的幂等性](https://github.com/daydreamdev/MeetingFilm/blob/master/note/%E5%A6%82%E4%BD%95%E4%BF%9D%E8%AF%81%E4%B8%9A%E5%8A%A1%E7%9A%84%E5%B9%82%E7%AD%89%E6%80%A7.md)
- [布隆过滤器解决缓存穿透](https://github.com/daydreamdev/MeetingFilm/blob/master/note/%E5%B8%83%E9%9A%86%E8%BF%87%E6%BB%A4%E5%99%A8%E8%A7%A3%E5%86%B3%E7%BC%93%E5%AD%98%E7%A9%BF%E9%80%8F.md)
