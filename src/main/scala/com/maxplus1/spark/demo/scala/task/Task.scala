package com.maxplus1.spark.demo.scala.task

import org.apache.spark.SparkContext

/**
  * Created by xiaolong.qiu on 2017/3/15.
  */
trait Task extends Serializable {

  var sparkContext: SparkContext = _ //spark上下文，TODO 如果同一次调度SparkContext不能共享，应该每次都set新的SparkContext
  var args:java.util.Map[String,String] = _ //spark任务的入参，json类型的Map

  def doTask()
}
