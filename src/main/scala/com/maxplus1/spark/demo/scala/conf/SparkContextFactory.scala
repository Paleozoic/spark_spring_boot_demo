package com.maxplus1.spark.demo.scala.conf

import com.maxplus1.spark.demo.scala.props.TaskProperties
import org.apache.spark.{SparkConf, SparkContext}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.{Bean, Configuration}

/**
  * Created by xiaolong.qiu on 2017/3/15.
  */
@Configuration
class SparkContextFactory {

  @Autowired
  @transient
  var taskProperties: TaskProperties = _

  @Bean
  def sparkContext(): SparkContext = {
    //sometimes you need to login with Kerberos
    //LoginUtils.login()
    val conf = new SparkConf().setAppName(taskProperties.appName).set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
    val sc = new SparkContext(conf)
    sc
  }
}
