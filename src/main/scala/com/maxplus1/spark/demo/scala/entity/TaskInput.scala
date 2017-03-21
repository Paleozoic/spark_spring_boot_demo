package com.maxplus1.spark.demo.scala.entity

import scala.beans.BeanProperty

/**
  * Created by qxloo on 2017/3/19.
  */
class TaskInput {
  @BeanProperty var taskName:String = _
  @BeanProperty var args:java.util.Map[String,String] = _
}
