# spark_spring_boot_demo
一个基于Spring Boot的Spark开发手脚架，开箱即用！

# Main函数
spark-submit命令指定Main函数位于[com.maxplus1.spark.demo.scala.main](https://github.com/Paleozoic/spark_spring_boot_demo/tree/master/src/main/scala/com/maxplus1/spark/demo/scala/main)。

# Task任务
Task任务位于[此包下](https://github.com/Paleozoic/spark_spring_boot_demo/tree/master/src/main/scala/com/maxplus1/spark/demo/scala/task)，
所有Task需要实现[com.maxplus1.spark.demo.scala.task.Task](https://github.com/Paleozoic/spark_spring_boot_demo/tree/master/src/main/scala/com/maxplus1/spark/demo/scala/task/Task.scala)

# TaskController任务控制
[TaskController](https://github.com/Paleozoic/spark_spring_boot_demo/tree/master/src/main/scala/com/maxplus1/spark/demo/scala/task/TaskController)是object单例，用于顺序调度Task任务。

# TASK_MAPPING
TASK_MAPPING映射了任务名以及任务的类型classOf

# Main函数入参
入参以JSON的形式传入，传入后会被反序列化为：[TaskInput](https://github.com/Paleozoic/spark_spring_boot_demo/tree/master/src/main/scala/com/maxplus1/spark/demo/scala/entity/TaskInput.scala)，
TaskInput由`taskName:String`和`args:java.util.Map[String,String]`组成。

# 持久层
持久层使用Java版本的MyBatis，
一般来说数据库操作用于读取相关配置参数或者写入程序状态。

# Package打包
使用maven打包的时候，注意某些不需要打入jar的包需要标注：`<scope>provided</scope>`

# PS:程序未测试 2017-03-21 23:06