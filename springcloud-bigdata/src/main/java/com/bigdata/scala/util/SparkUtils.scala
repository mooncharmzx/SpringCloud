package com.bigdata.scala.util

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.slf4j.{Logger, LoggerFactory}

object SparkUtils {
  val logger: Logger = LoggerFactory.getLogger(SparkUtils.getClass)

  def createSparkSession(appName:String):SparkSession={
    System.setProperty("HADOOP_USER_NAME", "root")
    val conf = new SparkConf().setAppName(appName).setMaster("local[*]")
    println("SparkConf基础配置结束")
    val spark = SparkSession.builder()
      .config(conf)
      .config("spark.shuffle.consolidateFiles", "true")
      .config("spark.debug.maxToStringFields", "2000")
      .config("spark.serializer","org.apache.spark.serializer.KryoSerializer")
      .config("spark.sql.extensions","org.apache.spark.sql.hudi.HoodieSparkSessionExtension")
      .getOrCreate()

    println("下面是配置s3 连接信息")
    val sc = spark.sparkContext

    println("配置s3信息结束，{}"+sc.hadoopConfiguration)

    spark
  }

  def loadTableFlag(tablePath:String,spark:SparkSession):Boolean={
    try{

      spark.read.format("org.apache.hudi")
        .option("hoodie.datasource.query.type","read_optimized")
        .load(tablePath)
      true
    }catch {
      case s:java.io.FileNotFoundException =>{
        println("加载 "+tablePath+" 失败:"+s.getMessage)
        false
      }
      case s:Exception=>{
        println("加载表"+tablePath+"出现其他错误"+s.getMessage)
        false
      }
    }
  }

}