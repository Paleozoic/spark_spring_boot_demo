# 进度
Spark Local模式已跑通

# spark_spring_boot_demo
一个基于Spring Boot的Spark开发手脚架，开箱即用！

# Main函数
spark-submit命令指定Main函数位于[com.maxplus1.spark.demo.scala.main](https://github.com/Paleozoic/spark_spring_boot_demo/tree/master/src/main/scala/com/maxplus1/spark/demo/scala/main)。

# Task任务
Task任务位于[此包下](https://github.com/Paleozoic/spark_spring_boot_demo/tree/master/src/main/scala/com/maxplus1/spark/demo/scala/task)，
所有Task需要实现[com.maxplus1.spark.demo.scala.task.Task](https://github.com/Paleozoic/spark_spring_boot_demo/tree/master/src/main/scala/com/maxplus1/spark/demo/scala/task/Task.scala)

# TaskController任务控制
[TaskController](https://github.com/Paleozoic/spark_spring_boot_demo/tree/master/src/main/scala/com/maxplus1/spark/demo/scala/task/TaskController.scala)是object单例，用于顺序调度Task任务。

# TASK_MAPPING
TASK_MAPPING映射了任务名以及任务的类型classOf

# Main函数入参
入参以JSON的形式传入，传入后会被反序列化为：[TaskInput](https://github.com/Paleozoic/spark_spring_boot_demo/tree/master/src/main/scala/com/maxplus1/spark/demo/scala/entity/TaskInput.scala)，
TaskInput由`taskName:String`和`args:java.util.Map[String,String]`组成。
一个参数的例子如下：
```json
{
    "taskName": "testTask1",
    "args": {
        "time": "2017-03-21"
    }
}
```
根据参数的taskName可以从TASK_MAPPING取到相应的Task，
并根据传入的参数taskName顺序获得有序数组`tasks: Array[Task]`，
将`tasks: Array[Task]`传入TaskController可以顺序执行Task。
# 持久层
持久层使用Java版本的MyBatis，
一般来说数据库操作用于读取相关配置参数或者写入程序状态。

# Package打包
请留意[pom.xml](https://github.com/Paleozoic/spark_spring_boot_demo/blob/master/pom.xml)

# 为什么Java和Scala混编？
- Scala语法糖实在太多……这个既是好处也是坏处
- Java更适合于面向对象编程，而Scala函数编程的特性，更适合用于计算，复合数学思维的特性。所以使用Java写面向对象的代码，使用Scala写Spark相关计算代码。
- PS：以上属于个人理解，且听且思。

