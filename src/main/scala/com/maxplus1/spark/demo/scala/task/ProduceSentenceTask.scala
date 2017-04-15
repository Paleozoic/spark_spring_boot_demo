package com.maxplus1.spark.demo.scala.task

import java.util.Random

import org.slf4j.{Logger, LoggerFactory}

/**
  * Created by xiaolong.qiu on 2017/3/15.
  */
class ProduceSentenceTask extends Task{
  /**
    * 具体的业务逻辑
    */
  override def doTask(): Unit = {

    val log:Logger = LoggerFactory.getLogger(classOf[ProduceSentenceTask])
    log.info("ProduceSentenceTask start")
    val random = new Random
    // 句子数组
    val sentences = Array[String]("the cow jumped over the moon", "an apple a day keeps the doctor away", "four score and seven years ago", "snow white and the seven dwarfs", "i am at two with nature")
    val arr = new Array[String](1000)
    // TODO: 具体的业务逻辑
    val sentencesRdd = sparkContext.parallelize({
      for(i<- 0 until  1000){
        // 随机选择一个句子
        val sentence = sentences(random.nextInt(sentences.length))
        arr(i) = sentence
      }
      arr
    },3) //创建1000个句子，并且分为3个Partition 的 RDD
    val count = sentencesRdd.count()
    val partitionNum = sentencesRdd.getNumPartitions
    sentencesRdd.saveAsTextFile("hdfs://hdnode:9000/testFiles/sentencesFile")

    println(s"the count of sentencesRdd is : [$count]")
    println(s"the num of Partitions is : [$partitionNum]")
    log.info("ProduceSentenceTask stop")
  }

}
