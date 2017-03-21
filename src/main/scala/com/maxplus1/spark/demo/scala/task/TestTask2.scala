package com.maxplus1.spark.demo.scala.task

import org.slf4j.{Logger, LoggerFactory}

/**
  * Created by xiaolong.qiu on 2017/3/15.
  */
//@Slf4j 在scala代码lombok不起作用
class TestTask2 extends Task{

  /**
    * 具体的业务逻辑
    */
  override def doTask(): Unit = {
    val log:Logger = LoggerFactory.getLogger(classOf[TestTask2])
    log.info("task2 test start")
    // TODO: 具体的业务逻辑
    val paRdd = sparkContext.parallelize(99 to 199,2)
    val count = paRdd.count()
    println(count)
    log.info("task2 test stop")
  }

}
