import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, Row}
import org.apache.spark.util.CollectionAccumulator

import scala.collection.mutable.ArrayBuffer

/**
 * Object for implementing the Skyline Filter Skyline (SFS) algorithm.
 */
object SFS {

  /**
   * Function that computes the global skyline points for the dataset.
   *
   * @param skylineAccumulator Accumulator that holds all the local skyline points.
   * @return The global skyline set
   */
  def computeFinalSkyline(skylineAccumulator: CollectionAccumulator[Row]): ArrayBuffer[Row] = {
    var sortedPoints = new ArrayBuffer[Row]()
    skylineAccumulator.value.forEach( r => {
      sortedPoints += r
    })
    sortedPoints = sortedPoints.sortBy(r => r.getDouble(r.length - 1))

    var finalSkyline = new ArrayBuffer[Row]()

    sortedPoints.foreach( r => {
      val dimensions = r.length - 1

      // Array that represents the dimensions (coordinates) of the examined point
      val pointDimensions = Array.fill(dimensions) {
        0.0
      }
      for (i <- 0 until dimensions) {
        pointDimensions(i) += r.getDouble(i)
      }

      var isDominated = false

      for (x <- finalSkyline) {

        // Array that represents the dimensions (coordinates) of the skyline point
        val skylineDimensions = Array.fill(dimensions) {
          0.0
        }
        for (i <- 0 until dimensions) {
          skylineDimensions(i) += x.getDouble(i)
        }

        if (isMultidimensionalPointDominated(dimensions, pointDimensions, skylineDimensions)) {
          isDominated = true
        }
      }

      if (!isDominated) {
        finalSkyline += r
      }
    })
    finalSkyline
  }

  /**
   * Function that computes the skyline points for each partition using the distributed concept of Spark.
   *
   * For each partition we compute the local skyline points and adds them in an accumulator.
   *
   * @param df The DataFrame holding the objects of the dataset.
   * @param skylineAccumulator The accumulator to be populated with the local skyline points.
   */
  def computeLocalSkyline(df: DataFrame, skylineAccumulator: CollectionAccumulator[Row]): Unit = {
    val rowsRDD: RDD[Row] = df.rdd

    rowsRDD.foreachPartition( iterator => {

      var localSkyline = new ArrayBuffer[Row]()

      iterator.foreach(row => {
        val dimensions = row.length - 1

        // Array that represents the dimensions (coordinates) of the examined point
        val pointDimensions = Array.fill(dimensions) {
          0.0
        }
        for (i <- 0 until dimensions) {
          pointDimensions(i) += row.getDouble(i)
        }

        var isDominated = false

        for (x <- localSkyline) {

          // Array that represents the dimensions (coordinates) of the skyline point
          val skylineDimensions = Array.fill(dimensions) {
            0.0
          }
          for (i <- 0 until dimensions) {
            skylineDimensions(i) += x.getDouble(i)
          }

          if (isMultidimensionalPointDominated(dimensions, pointDimensions, skylineDimensions)) {
            isDominated = true
          }
        }

        if (!isDominated) {
          localSkyline += row
        }
      })
      localSkyline.foreach( r => {
        skylineAccumulator.add(r)
      })
    })
  }

  /***
   * Function that takes a two points and compares them in every dimension.
   * Returns true if the @param point is dominated by @param skylinePoint.
   */
  def isMultidimensionalPointDominated(dimensions: Int, point: Array[Double], skylinePoint: Array[Double]): Boolean = {
    val atLeastAsGood = Array.fill(dimensions){false}

    for ( i <- 0 until dimensions) {
      if (skylinePoint(i) < point(i)) {
        atLeastAsGood(i) = true
      }
    }

    val res = atLeastAsGood.reduce((a, b) => a && b)
    res
  }
}
