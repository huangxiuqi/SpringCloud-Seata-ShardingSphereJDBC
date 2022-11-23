# 整合SpringCloud+Seata+ShardingSphereJDBC

## 版本
- spring-cloud 2021.0.5
- spring-cloud-alibaba 2021.0.4.0
- shardingsphere 5.2.1
- seata 1.5.2

## 启动
1. 导入sql文件夹下的数据库表
2. 参考 https://seata.io/zh-cn/docs/ops/deploy-guide-beginner.html 启动seata server
3. 修改Order、Account、Storage、Business下的配置文件
4. 启动Order、Account、Storage、Business服务
5. 访问 http://127.0.0.1:8083/business/purchase?userId=1234567&commodityCode=7654321&orderCount=2 验证结果

>userId为偶数会触发Account服务异常
> 
>commodityCode=7654322会触发Storage服务异常