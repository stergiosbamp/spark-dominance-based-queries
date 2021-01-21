import org.apache.spark.sql.{DataFrame, Row, SparkSession}
import org.apache.spark.sql.functions.col

import scala.collection.mutable.ArrayBuffer

object Main {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder
      .master("local[*]")
      .appName("Skyline Dominance Spark app")
      .getOrCreate()

    experiment(spark, "../data/2d-correlation-1000000.csv", 10)

  }

  /** Function that acts as an experiment and runs the three tasks of the project.
   * For each task takes timestamps per task and prints them.
   *
   * @param spark The SparkSession object.
   * @param filename The filename for the distribution to run the experiment.
   * @param k The user-defined k value for the top-k queries.
   */
  def experiment(spark: SparkSession, filename: String, k: Int): Unit = {
    val df = spark.read.option("inferSchema", "true").csv(filename)

    val startTime = System.nanoTime()

    val skylineSet = skylineQuery(spark, df)
    println(s"Skyline set is: ${skylineSet}")
    val skylineTime = System.nanoTime()
    val skylineTimeSecs = (System.nanoTime() - startTime) / 1e9d

    topKDominating(k, spark, df)
    val topKDominatingTime = System.nanoTime()
    val topKDominatingTimeSecs = (System.nanoTime() - skylineTime) / 1e9d

    topKSkyline(k, spark, df)
    val topKSkylineTime = System.nanoTime()
    val topKSkylineTimeSecs = (System.nanoTime() - topKDominatingTime) / 1e9d

    println(filename)
    println(s"Total execution times are: ${skylineTimeSecs + topKDominatingTimeSecs + topKSkylineTimeSecs}")
    println(s"Skyline query time: $skylineTimeSecs")
    println(s"Top K dominating time: $topKDominatingTimeSecs")
    println(s"Top K skyline time: $topKSkylineTimeSecs")
  }

  /**
   * Function for the skyline query.
   *
   * @param spark The SparkSession object.
   * @param df The DataFrame holding the objects of the dataset.
   * @return The array with the global skyline points of the dataset.
   */
  def skylineQuery(spark: SparkSession, df: DataFrame): ArrayBuffer[Row] = {
    // In each skyline query accumulator must be re-created
    val skylineAccumulator = spark.sparkContext.collectionAccumulator[Row]("skylineAccumulator")

    val sumDF = df.withColumn("sum", df.columns.map(c => col(c)).reduce((c1, c2) => c1 + c2))
    val sortedSumDF = sumDF.sort(col("sum").asc)

    val sfs = SFS
    sfs.computeLocalSkyline(sortedSumDF, skylineAccumulator)
    sfs.computeFinalSkyline(skylineAccumulator)
  }

  /**
   * Function for the top-k dominating query.
   *
   * @param k user-defined k value.
   * @param spark The SparkSession object.
   * @param df The DataFrame holding the objects of the dataset.
   */
  def topKDominating(k: Int, spark: SparkSession, df: DataFrame): Unit = {
    var changingDf = df
    for (i <- 1 to k) {
      val skylinePoints: ArrayBuffer[Row] = skylineQuery(spark, changingDf)
      val domination = Domination
      val scoreAcc = spark.sparkContext.longAccumulator("Score accumulator")
      var dominatingMap = Map[Row, Long]()

      for (row <- skylinePoints) {
        scoreAcc.reset()
        domination.dominantScore(row, changingDf, scoreAcc)
        dominatingMap = dominatingMap + (row -> scoreAcc.value)
      }

      // sort points by the dominance score
      val sortedDominatingMap = dominatingMap.toSeq.sortWith(_._2 > _._2)
      val (topPoint, topValue) = sortedDominatingMap.head
      println(s"Top-$i is point $topPoint with dominance score $topValue")

      // Remove top point from dataset and repeat
      changingDf = changingDf.filter(r => {
        val dimensions = r.length - 1
        val pointDimensions = Array.fill(dimensions){0.0}
        val topPointDimensions = Array.fill(dimensions){0.0}

        for ( i <- 0 until dimensions) {
          pointDimensions(i) += r.getDouble(i)
          topPointDimensions(i) += topPoint.getDouble(i)
        }

        !pointDimensions.sameElements(topPointDimensions)
      })
    }
  }

  /**
   * Function for the top-k skyline query.
   *
   * @param k user-defined k value.
   * @param spark The SparkSession object.
   * @param df The DataFrame holding the objects of the dataset
   */
  def topKSkyline(k: Int, spark: SparkSession, df: DataFrame): Unit = {
    val skylinePoints = skylineQuery(spark, df)
    val domination = Domination
    val scoreAcc = spark.sparkContext.longAccumulator("Score accumulator")
    var dominatingMap = Map[Row, Long]()

    for (row <- skylinePoints) {
      scoreAcc.reset()
      domination.dominantScore(row, df, scoreAcc)
      dominatingMap = dominatingMap + (row -> scoreAcc.value)
    }

    // sort points by the dominance score
    val sortedDominatingMap = dominatingMap.toSeq.sortWith(_._2 > _._2)
    val numOfElements = sortedDominatingMap.size

    var i = 1
    for ((topK, topV) <- sortedDominatingMap) {
      println(s"Top-$i is point $topK with dominance score $topV")
      i = i + 1
      if (i > k){
        return
      }
    }
    if (k > numOfElements) {
      println(s"No other points exist in the skyline. Request a lower k value until $numOfElements")
    }
  }
}
