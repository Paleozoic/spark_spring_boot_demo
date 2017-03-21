package com.maxplus1.spark.demo.scala.props

import org.springframework.boot.context.properties.ConfigurationProperties

import scala.beans.BeanProperty

/**
  * Created by xiaolong.qiu on 2017/3/15.
  */
@ConfigurationProperties(prefix = "task.demo")
class TaskProperties {

    @BeanProperty var appName:String = _

}
