package com.maxplus1.spark.demo.scala.task

import org.slf4j.{Logger, LoggerFactory}

/**
  * Created by xiaolong.qiu on 2017/3/15.
  */
class TestTask extends Task{
  /**
    * 具体的业务逻辑
    */
  override def doTask(): Unit = {

    val log:Logger = LoggerFactory.getLogger(classOf[TestTask])
    log.info("TestTask start")

    val sentencesRdd = sparkContext.textFile("hdfs://hdnode:9000/testFiles/sentencesFile")

    val wordsRdd = sentencesRdd.flatMap{sentence=>
        val words = sentence.split(" ")
        val wordWithOne = new Array[(String, Long)](words.length)
        for(i<-0 until  words.length){
          wordWithOne(i) = (words(i),1)
        }
        wordWithOne
    }


    /**
      * 使用combile进行单词计数
      * http://www.cnblogs.com/guoyunzhe/p/6265632.html
      * createCombiner: V => C ，这个函数把当前的值作为参数，此时我们可以对其做些附加操作(类型转换)并把它返回 (这一步类似于初始化操作)
      * mergeValue: (C, V) => C，该函数把元素V合并到之前的元素C(createCombiner)上 (这个操作在每个分区内进行)
      * mergeCombiners: (C, C) => C，该函数把2个元素C合并 (这个操作在不同分区间进行)
      */
    val combRdd = wordsRdd.combineByKey(
      createCombiner = value => value ,   //定义V到C的转变，即value => value Long => Long 这里没有变化
      mergeValue = (C:Long,V:Long) => (C + V), //C和V合并，这里即相同的单词的计数合并
      mergeCombiners = (C1:Long,C2:Long) => (C1 + C2) //跨分区相同的KEY的单词计数合并
    )

    //不同的做法
    /**
      * 根据K聚合
      */
    val reduceRdd = wordsRdd.reduceByKey(_ + _)

    println("==========combRdd Starts==========")
    combRdd.collect.foreach{res=>
      val word = res._1
      val count = res._2
      println(s"$word:$count")
    }
    println("==========combRdd Ends==========")
    println("==========reduceRdd Starts==========")
    reduceRdd.collect.foreach{res=>
      val word = res._1
      val count = res._2
      println(s"$word:$count")
    }
    println("==========reduceRdd Ends==========")




    /**
      * 聚合整个RDD
      * 这里会输出一个很长的单词以及所有单词的计数聚合
      */
    wordsRdd.reduce{
      (a,b)=>
        (a._1+b._1,a._2 + b._2)
    }

    /**
      * http://www.jianshu.com/p/cd9465a23e3c
      * 先对每个K的V和zeroValue聚合操作，这里是 V + zeroValue
      * 然后在对相同的key执行聚合函数，这里是 + 操作
      * 输出结果：
      * zeroValue：零值，该值不能影响聚合函数结果。比如加法聚合就是zeroValue=0，乘法聚合就是zeroValue=1
      */
    val foldRdd = wordsRdd.foldByKey(0)(_ + _)

    println("==========foldRdd Starts==========")
    foldRdd.collect.foreach{res=>
      val word = res._1
      val count = res._2
      println(s"$word:$count")
    }
    println("==========foldRdd Ends==========")

    /**
      * http://blog.csdn.net/wo334499/article/details/51689587
      * http://www.cnblogs.com/hark0623/p/4496796.html
      * When called on a dataset of (K, V) pairs, returns a dataset of (K, U) pairs where the values
      * for each key are aggregated using the given combine functions and a neutral "zero" value.
      * Allows an aggregated value type that is different than the input value type, while avoiding unnecessary allocations.
      * Like in groupByKey, the number of reduce tasks is configurable through an optional second argument.
      *
      * 根据K聚合，并且可以返回一个类型不同的结果，底层是combineByKeyWithClassTag
      * zeroValue: zeroValue会先代入U执行seqOp
      * seqOp: 表示V与零值zeroValue需要执行的函数 mergeValue, createCombiner
      * combOp: mergeCombiners
      */
    val aggRdd = wordsRdd.aggregateByKey(500L)(
      seqOp = (U:Long,V:Long) => math.max(U,V), //根据Key，在分区内计算最大的值，并且默认值是500 zeroValue。即小于500的默认为500
      combOp = (U1:Long,U2:Long) => U1 + U2 //根据Key，合并分区的值
    )

    println("==========foldRdd Starts==========")
    aggRdd.collect.foreach{res=>
      val word = res._1
      val count = res._2
      println(s"$word:$count")
    }
    println("==========foldRdd Ends==========")

    wordsRdd.groupByKey

    log.info("TestTask stop")

    wordsRdd.mapPartitions({
      eList=>
        eList
    })

    wordsRdd.mapPartitionsWithIndex{
      (index,eList)=>
        eList
    }

  }

}
