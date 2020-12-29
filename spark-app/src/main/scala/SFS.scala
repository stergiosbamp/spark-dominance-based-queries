import org.apache.spark.sql.{DataFrame, Row}
import org.apache.spark.util.CollectionAccumulator


object SFS {

  def computeLocalSkyline(df: DataFrame, localSkylinesAcc: CollectionAccumulator[Array[Double]]): Unit = {

    df.coalesce(1).foreach( r => {

      val dimensions = r.length - 1
      val pointDimensions = Array.fill(dimensions){0.0}

      for ( i <- 0 until dimensions) {
        pointDimensions(i) += r.getDouble(i)
      }

      val value = localSkylinesAcc.value

      var isDominated = false
      value.forEach(skylineRow => {

        val skylineDimensions = Array.fill(dimensions){0.0}

        for ( i <- 0 until dimensions) {
          skylineDimensions(i) += skylineRow(i)
        }

        isDominated = isMultidimensionalPointDominated(dimensions, pointDimensions, skylineDimensions)

      })

      if (!isDominated){
        // add the coords of the points excluding the sum
        localSkylinesAcc.add(pointDimensions)
      }
    })

  }

  def isMultidimensionalPointDominated(dimensions: Int, point: Array[Double], skylinePoint: Array[Double]): Boolean = {
    var isDominated = true
    var atLeastLess = false

    for ( i <- 0 until dimensions) {
      if (point(i) < skylinePoint(i)) {
        isDominated = false
        atLeastLess = true
      } else if (point(i) == skylinePoint(i)) {
        isDominated = false
        atLeastLess = false
      }

    }

    if (!isDominated && atLeastLess) {
      return false
    }
    true
  }

}
