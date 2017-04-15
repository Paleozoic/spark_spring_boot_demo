package com.maxplus1

import java.util.Random

/**
  * Created by qxloo on 2017/4/15.
  */
object Test {
  def main(args: Array[String]): Unit = {
    val sentences = Array[String]("the cow jumped over the moon", "an apple a day keeps the doctor away", "four score and seven years ago", "snow white and the seven dwarfs", "i am at two with nature")
    val random = new Random
    val arr = new Array[String](10)
    for(i<- 0 until  10){
      // 随机选择一个句子
      val sentence = sentences(random.nextInt(sentences.length))
      arr(i) = sentence
    }
    for (elem <- arr) {
      println(elem)
    }

  }

}
