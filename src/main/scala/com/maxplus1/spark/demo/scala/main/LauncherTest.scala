package com.maxplus1.spark.demo.scala.main

import java.util

import com.maxplus1.spark.demo.java.utils.JacksonUtils
import com.maxplus1.spark.demo.scala.entity.TaskInput
import com.maxplus1.spark.demo.scala.props.TaskProperties
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.annotation.ComponentScan

/**
  * Created by xiaolong.qiu on 2017/3/15.
  */
//@SpringBootApplication(exclude= Array(classOf[DataSourceAutoConfiguration]))
@SpringBootApplication
@ComponentScan(basePackages = Array("com.maxplus1.spark.demo.scala.props","com.maxplus1.spark.demo.java"))
@EnableConfigurationProperties(Array(classOf[TaskProperties]))
class LauncherTest {

}

//@Slf4j 在scala代码lombok不起作用
object LauncherTest {
  val log:Logger = LoggerFactory.getLogger(classOf[LauncherTest])

  def main(args: Array[String]): Unit = {
    val context: ConfigurableApplicationContext = new SpringApplicationBuilder(classOf[LauncherTest]).run(args: _*)
    val appName = context.getBeanFactory.getBean(classOf[TaskProperties]).appName
    //入参格式 start
    val list = new util.ArrayList[TaskInput]()
    val input1 = new TaskInput
    input1.taskName = "testTask1"
    input1.args = new util.HashMap[String,String]()
    input1.args.put("time","2017-03-21")
    list.add(input1)
    val input2 = new TaskInput
    input2.taskName = "testTask2"
    input2.args = new util.HashMap[String,String]()
    input2.args.put("time","2017-03-22")
    input2.args.put("index","2")
    list.add(input2)
    println(JacksonUtils.obj2Json(list))
    //入参格式 end
    println(appName)
    log.info(appName)
    log.error(appName)
  }
}
