import java.util

import org.apache.spark.sql.{DataFrame, Row, SparkSession}
import org.apache.spark.sql.functions.col
import org.apache.spark.util.CollectionAccumulator


object Main {

  def main(args: Array[String]): Unit = {
    val startTime = System.nanoTime()

    val spark = SparkSession.builder
      .master("local[*]")
      .appName("Skyline Dominance Spark app")
      .getOrCreate()

    val df = spark.read.option("inferSchema", "true").csv("src/main/resources/3d-mock-datapoints.csv")

    val skylineSet = skylineQuery(spark, df)
    println(s"Skyline set is:")
    skylineSet.forEach( e => println(e.mkString(", ")))

//    topKDominating(4, spark, df)

//    topKSkyline(4, spark, df)

    val duration = (System.nanoTime() - startTime) / 1e9d
    println(s"Execution time is: $duration sec")
  }

  def skylineQuery(spark: SparkSession, df: DataFrame): util.List[Array[Double]] = {
    // In each skyline query accumulator must be re-created
    val skylineAccumulator = spark.sparkContext.collectionAccumulator[Array[Double]]("skylineAccumulator")

//    val sumDF = df.withColumn("sum", {df.columns.map(c => col(c)).reduce(_ + _)})
    val sumDF = df.withColumn("sum", df.columns.map(c => col(c)).reduce((c1, c2) => c1 + c2))
    val sortedSumDF = sumDF.sort(col("sum").asc)

    val sfs = SFS
    sfs.computeLocalSkyline(sortedSumDF, skylineAccumulator)
    skylineAccumulator.value
  }

//  def topKDominating(k: Int, spark: SparkSession, df: DataFrame): Unit = {
//    var changingDf = df
//    for (i <- 1 to k) {
//      val skylinePoints = skylineQuery(spark, changingDf)
//      val domination = Domination
//      val scoreAcc = spark.sparkContext.longAccumulator("Score accumulator")
//      var dominatingMap = Map[Row, Long]()
//      skylinePoints.forEach( row => {
//        scoreAcc.reset()
//        domination.dominantScore(row, changingDf, scoreAcc)
//        dominatingMap = dominatingMap + (row -> scoreAcc.value)
//      })
//
//      // sort points by the dominance score
//      val sortedDominatingMap = dominatingMap.toSeq.sortWith(_._2 > _._2)
//      val (topPoint, topValue) = sortedDominatingMap.head
//      println(s"Top-$i is point $topPoint with dominance score $topValue")
//
//      // Remove top point from dataset and repeat
//      changingDf = changingDf.filter(r => !r.getDouble(0).equals(topPoint.getDouble(0)) && !r.getDouble(1).equals(topPoint.getDouble(1)))
//    }
//  }

//  def topKSkyline(k: Int, spark: SparkSession, df: DataFrame): Unit = {
//    val skylinePoints = skylineQuery(spark, df)
//    val domination = Domination
//    val scoreAcc = spark.sparkContext.longAccumulator("Score accumulator")
//    var dominatingMap = Map[Row, Long]()
//    skylinePoints.forEach( row => {
//      scoreAcc.reset()
//      domination.dominantScore(row, df, scoreAcc)
//      dominatingMap = dominatingMap + (row -> scoreAcc.value)
//    })

//    // sort points by the dominance score
//    val sortedDominatingMap = dominatingMap.toSeq.sortWith(_._2 > _._2)
//    val numOfElements = sortedDominatingMap.size
//
//    var i = 1
//    for ((topK, topV) <- sortedDominatingMap) {
//      println(s"Top-$i is point $topK with dominance score $topV")
//      i = i + 1
//    }
//    if (k > numOfElements) {
//      println(s"No other points exist in the skyline. Request a lower k value until $numOfElements")
//    }
//  }
}
