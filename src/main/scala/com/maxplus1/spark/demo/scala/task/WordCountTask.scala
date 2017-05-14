package com.maxplus1.spark.demo.scala.task

import org.slf4j.{Logger, LoggerFactory}

/**
  * Created by xiaolong.qiu on 2017/3/15.
  */
class WordCountTask extends Task{
  /**
    * 具体的业务逻辑
    */
  override def doTask(): Unit = {

    val log:Logger = LoggerFactory.getLogger(classOf[WordCountTask])
    log.info("WordCountTask start")

    val sentencesRdd = sparkContext.textFile("hdfs://hdnode:9000/testFiles/sentencesFile")

    val wordsRdd = sentencesRdd.flatMap{sentence=>
        val words = sentence.split(" ")
        val wordWithOne = new Array[(String, Long)](words.length)
        for(i<-0 until  words.length){
          wordWithOne(i) = (words(i),1)
        }
        wordWithOne
    }

    val wordCountRdd = wordsRdd.reduceByKey(_ + _)


    //topN 计算方法1
    val topN_sortBy_take = wordCountRdd.sortBy({e =>
        e._2
    },false).take(3)

    //topN 计算方法2
    val orderingTake = new Ordering[(String, Long)]{
      override def compare(x: (String, Long), y: (String, Long)): Int = {
        (y._2 - x._2).toInt
      }
    }
    val topN_takeOrdered = wordCountRdd.takeOrdered(3)(orderingTake)

    //topN 计算方法3
    val orderingTop = new Ordering[(String, Long)]{
      override def compare(x: (String, Long), y: (String, Long)): Int = {
        (x._2 - y._2).toInt
      }
    }
    val topN_top = wordCountRdd.top(3)(orderingTop)

    val count = wordCountRdd.count()

    println(s"the count of words is : [$count]")
    println("==========Result Starts==========")
    wordCountRdd.collect.foreach{res=>
      val word = res._1
      val count = res._2
      println(s"$word:$count")
    }
    println("==========Result Ends==========")
    println("==========topN_sortBy_take Starts==========")
    topN_sortBy_take.foreach{res=>
      val word = res._1
      val count = res._2
      println(s"$word:$count")
    }
    println("==========topN_sortBy_take Ends==========")
    println("==========topN_takeOrdered Starts==========")
    topN_takeOrdered.foreach{res=>
      val word = res._1
      val count = res._2
      println(s"$word:$count")
    }
    println("==========topN_takeOrdered Ends==========")
    println("==========topN_top Starts==========")
    topN_top.foreach{res=>
      val word = res._1
      val count = res._2
      println(s"$word:$count")
    }
    println("==========topN_top Ends==========")
    log.info("WordCountTask stop")
  }

}
