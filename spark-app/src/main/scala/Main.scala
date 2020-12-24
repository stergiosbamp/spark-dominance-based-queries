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
    val skylineAccumulator = spark.sparkContext.collectionAccumulator[Row]("skylineAccumulator")

    skylineQuery(skylineAccumulator, df)

  }

  def skylineQuery(skylineAccumulator: CollectionAccumulator[Row], df: DataFrame): Unit = {
    val sumDF = df.withColumn("sum", df.columns.map(c => col(c)).reduce((c1, c2) => c1 + c2))
    val sortedSumDF = sumDF.sort(col("sum").asc)

    val sfs = SFS
    sfs.computeLocalSkyline(sortedSumDF, skylineAccumulator)

    println(s"Skyline set is: ${skylineAccumulator.value}")
  }

  def topKDominating(k: Int): Unit = {
    Unit
  }

}
