package com.maxplus1.spark.demo.scala.task

/**
  * Created by xiaolong.qiu on 2017/3/15.
  */
object TaskController extends Serializable {

  def doTaskList(tasks: Array[Task]) = {
    tasks.foreach(task => {
      task.doTask()
    })
  }

  def doTaskList(tasks: Task*) = {
    tasks.foreach(task => {
      task.doTask()
    })
  }
}
