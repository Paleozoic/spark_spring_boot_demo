package com.maxplus1.spark.demo.scala.task

import org.slf4j.{Logger, LoggerFactory}

/**
  * Created by xiaolong.qiu on 2017/3/15.
  */
class TestTask1 extends Task{
  /**
    * 具体的业务逻辑
    */
  override def doTask(): Unit = {

    val log:Logger = LoggerFactory.getLogger(classOf[TestTask1])
    log.info("task1 test start")
    // TODO: 具体的业务逻辑
    val paRdd = sparkContext.parallelize(0 to 99,2)
    val count = paRdd.count()
    println(count)
    log.info("task1 test stop")
  }

}
