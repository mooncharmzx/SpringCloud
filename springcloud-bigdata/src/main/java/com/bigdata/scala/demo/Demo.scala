package com.bigdata.scala.demo

import org.apache.spark.{SparkConf, SparkContext}

object Demo {
  def main(args: Array[String]): Unit = {
    // 创建 Spark 运行配置对象
    System.setProperty("HADOOP_USER_NAME", "root")
    val sparkConf = new SparkConf()//.setMaster("local[*]")
      .setAppName("单词数量统计：")
      // 设置yarn-client模式提交
      .setMaster("yarn")
      .set("yarn.resourcemanager.hostname","master")
      // 设置executor的个数
      .set("spark.executor.instance","2")
      // 设置executor的内存大小
      .set("spark.executor.memory", "1024M")
      // 设置提交任务的yarn队列
      .set("spark.yarn.queue","spark")
      // 设置driver的ip地址
      .set("spark.driver.host","192.168.0.104")
      .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
    // 创建 Spark 上下文环境对象（连接对象）
    val sc  = new SparkContext(sparkConf)
    // 读取文件
    var input=sc.textFile("src/main/java/test.txt");
    // 分词
    var lines=input.flatMap(line=>line.split(" "))
    // 分组计数
    var count=lines.map(word=>(word,1)).reduceByKey{(x,y)=>x+y}
    // 打印结果
    count.foreach(println)
    //关闭 Spark 连接
    sc.stop()
  }
}
