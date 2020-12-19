import org.apache.spark.sql.SparkSession

object Main {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder
      .master("local[*]")
      .appName("Skyline Dominance Spark app")
      .getOrCreate()

    val df = spark.read.csv("../data/correlation.csv")
    df.show()
  }
}
