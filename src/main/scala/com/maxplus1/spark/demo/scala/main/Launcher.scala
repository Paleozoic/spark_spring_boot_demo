package com.maxplus1.spark.demo.scala.main

import com.maxplus1.spark.demo.java.utils.JacksonUtils
import com.maxplus1.spark.demo.scala.entity.TaskInput
import com.maxplus1.spark.demo.scala.props.TaskProperties
import com.maxplus1.spark.demo.scala.task.{Task, TaskController, TestTask1, TestTask2}
import org.apache.spark.SparkContext
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.annotation.ComponentScan

/**
  * Created by xiaolong.qiu on 2017/3/15.
  */
@SpringBootApplication
@ComponentScan(basePackages = Array("com.maxplus1.spark.demo"))
@EnableConfigurationProperties(Array(classOf[TaskProperties]))
class Launcher {
}

//@Slf4j 在scala代码lombok不起作用
object Launcher {
  val log:Logger = LoggerFactory.getLogger(classOf[Launcher])

  /*@Autowired
  @transient
  var sparkContext: SparkContext = _*/

  private val TASK_MAPPING = Map("testTask1" -> classOf[TestTask1],"testTask2" -> classOf[TestTask2])

  def main(args: Array[String]): Unit = {
    if (args.length > 0) {
      log.error("[ERROR===>>>]请输入任务名列表，可选任务如下：")
      log.error("======task name start=======")
      TASK_MAPPING.keys.foreach{
        taskName=>
          log.error(taskName)
      }
      log.error("======task name end=========")
    } else {

      val context: ConfigurableApplicationContext = new SpringApplicationBuilder(classOf[Launcher]).run(args: _*)
      val taskArr:Array[Task] = new Array(args.length)

      val sparkContext = context.getBean(classOf[SparkContext])
      try {
        for(i<-0 until args.length){
          val taskInput = JacksonUtils.json2Obj(args(i),classOf[TaskInput])
          if(taskInput==null){
            log.error("[ERROR===>>>]入参无法反序列化为TaskInput！入参为：{}",args(i))
            return;
          }
          val taskName = taskInput.taskName

          if(!TASK_MAPPING.contains(taskName)){
            log.error("[ERROR===>>>]任务{}不存在！",taskName)
            return;
          }
          val task = TASK_MAPPING(taskName).newInstance()
          task.sparkContext = sparkContext
          task.args = taskInput.args
          taskArr(i) = task
        }
        TaskController.doTaskList(taskArr)
      }finally {
        sparkContext.stop()
      }
    }
  }
}
