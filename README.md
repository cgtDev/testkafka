# kafka-test<br>
参考了网上雪水的开源代码(地址：https://github.com/xueshui/kafka-test)，并做了部分修改，加了注释
1、去官网下载kafka<br>
我下载的版本是 Scala 2.10  - kafka_2.10-0.10.2.1.tgz /kafka_2.11-0.10.0.1.tgz，下面的实例也是基于该版本。<br>
2、解压安装<br><br>
tar -xzf kafka_2.11-0.10.0.1.tgz<br>
mv kafka_2.11-0.10.0.1    /root<br>
3、修改配置文件<br>
cd /root/kafka_2.11-0.10.0.1/config<br>
cp server.properties server1.properties<br>
cp server.properties server2.properties<br>
cp server.properties server3.properties<br>
修改配置中的三个参数如下：<br>
server1.properties<br>
broker.id=1<br>
listeners=PLAINTEXT://:9092<br>
log.dirs=/tmp/kafka-logs-1<br>
server2.properties<br>
broker.id=2<br>
listeners=PLAINTEXT://:9094<br>
log.dirs=/tmp/kafka-logs-2<br>
<br>
server3.properties<br>
broker.id=3<br>
listeners=PLAINTEXT://:9094<br>
log.dirs=/tmp/kafka-logs-3<br>
4、启动脚本kafkacluster编写<br>
bin/zookeeper-server-start.sh config/zookeeper.properties &<br>
sleep 3s<br>
bin/kafka-server-start.sh config/server1.properties &<br>
bin/kafka-server-start.sh config/server2.properties &<br>
bin/kafka-server-start.sh config/server3.properties &<br>
启动kafka集群只需要执行./kafkacluster<br>
5、创建topic<br>
bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 3 --partitions 3 --topic test<br>
