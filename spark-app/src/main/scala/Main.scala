import java.util

import org.apache.spark.sql.{DataFrame, Row, SparkSession}
import org.apache.spark.sql.functions.col
import org.apache.spark.util.CollectionAccumulator


object Main {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder
      .master("local[*]")
      .appName("Skyline Dominance Spark app")
      .getOrCreate()

    val df = spark.read.option("inferSchema", "true").csv("src/main/resources/mock-datapoints.csv")

//    val skylineSet = skylineQuery(spark, df)
//    println(s"Skyline set is: ${skylineSet}")

    topKDominating(2, spark, df)
  }

  def skylineQuery(spark: SparkSession, df: DataFrame): util.List[Row] = {
    // In each skyline query accumulator must be re-created
    val skylineAccumulator = spark.sparkContext.collectionAccumulator[Row]("skylineAccumulator")

    val sumDF = df.withColumn("sum", df.columns.map(c => col(c)).reduce((c1, c2) => c1 + c2))
    val sortedSumDF = sumDF.sort(col("sum").asc)

    val sfs = SFS
    sfs.computeLocalSkyline(sortedSumDF, skylineAccumulator)
    skylineAccumulator.value
  }

  def topKDominating(k: Int, spark: SparkSession, df: DataFrame): Unit = {
    val skylinePoints = skylineQuery(spark, df)
    val domination = Domination
    val scoreAcc = spark.sparkContext.longAccumulator("Score accumulator")
    var dominatingMap = Map[Row, Long]()
    skylinePoints.forEach( row => {
      scoreAcc.reset()
      domination.dominantScore(row, df, scoreAcc)
      dominatingMap = dominatingMap + (row -> scoreAcc.value)
    })

    // sort points by the dominance score
    val sortedDominatingMap = dominatingMap.toSeq.sortWith(_._2 > _._2)
    println(sortedDominatingMap)

  }

}
