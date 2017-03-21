package com.maxplus1.spark.demo.scala.main

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
    println(appName)
    log.info(appName)
    log.error(appName)
  }
}
