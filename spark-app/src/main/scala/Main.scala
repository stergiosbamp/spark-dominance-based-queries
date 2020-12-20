import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.col
import org.apache.spark.sql.Row

object Main {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder
      .master("local[*]")
      .appName("Skyline Dominance Spark app")
      .getOrCreate()

    val PARTITIONS = 3
    val sfs = SFS
    var globalSkyline: Set[Row] = Set()

    val df = spark.read.csv("src/main/resources/mock-datapoints.csv")

    val sumDF = df.withColumn("sum", df.columns.map(c => col(c)).reduce((c1, c2) => c1 + c2))
    val sortedSumDF = sumDF.sort(col("sum").asc)

    // First point of the sorted df is in the skyline
    globalSkyline += sortedSumDF.first()

    println(globalSkyline)

  }

}
