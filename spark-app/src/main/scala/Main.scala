import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.col
import org.apache.spark.sql.Row

import scala.collection.mutable


object Main {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder
      .master("local[*]")
      .appName("Skyline Dominance Spark app")
      .getOrCreate()

    val sfs = SFS

    val df = spark.read.option("inferSchema", "true").csv("src/main/resources/mock-datapoints.csv")

    val skylineAccumulator = spark.sparkContext.collectionAccumulator[Row]("skylineAccumulator")

    val sumDF = df.withColumn("sum", df.columns.map(c => col(c)).reduce((c1, c2) => c1 + c2))
    val sortedSumDF = sumDF.sort(col("sum").asc)

    sfs.computeLocalSkyline(sortedSumDF, skylineAccumulator)

    println("Skyline set is", skylineAccumulator.value)

  }

}
