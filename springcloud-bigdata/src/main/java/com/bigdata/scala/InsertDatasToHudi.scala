package com.bigdata.scala

import java.util

import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}
/**
 * @author oyl
 * @create 2022-05-29 14:15
 * @Description 官方案例：模拟产生数据，插入Hudi表，表的类型COW
 */
object InsertDatasToHudi {

  def main(args: Array[String]): Unit = {

    val tableName : String = "tb1_trips_cow2"

    // 第1步、模拟乘车数据
    import org.apache.hudi.QuickstartUtils._
    val generator: DataGenerator = new DataGenerator()
    val insertDatas = convertToStringList(generator.generateInserts(100))
    insertDatasToHudi(tableName,insertDatas)
  }

  def insertDatasToHudi(tableName:String,insertDatas :util.List[scala.Predef.String]):Unit={
    System.setProperty("HADOOP_USER_NAME", "root")
    // 创建sparkSQL的运行环境
    val conf = new SparkConf().setAppName("insertDatasToHudi").setMaster("local[2]")
      .set("spark.ui.enabled", "false")
    val spark = SparkSession.builder().config(conf)
      // 设置序列化方式：Kryo
      .config("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
      .getOrCreate()

    //定义变量：表名，数据存储路径

    val tablePath : String = "/datas/hudi-warehouse/" + tableName

    //引入相关包
    import spark.implicits._
    import scala.collection.JavaConversions._

    // 第1步、模拟乘车数据
    import org.apache.hudi.QuickstartUtils._
    val generator: DataGenerator = new DataGenerator()
    //    val insertDatas = convertToStringList(generator.generateInserts(100))

    val df : DataFrame = spark.read.json(spark.sparkContext.parallelize(insertDatas,2).toDS())

    df.printSchema()
    df.show(2)

    //第2步、将数据插入到hudi表
    import org.apache.hudi.DataSourceWriteOptions._
    import org.apache.hudi.config.HoodieWriteConfig._

    df.write.mode(SaveMode.Overwrite)
      .format("hudi")
      .options(getQuickstartWriteConfigs)
      .option(PRECOMBINE_FIELD.key(), "ts")
      .option(RECORDKEY_FIELD.key(), "uuid")
      .option("hoodie.insert.shuffle.parallelism", "2")
      .option("hoodie.upsert.shuffle.parallelism", "2")
      .option("PARTITIONPATH_FIELD_OPT_KEY","partitionpath").option(TBL_NAME.key(),tableName).save(tablePath)

    //关闭
    spark.stop()
  }
}
