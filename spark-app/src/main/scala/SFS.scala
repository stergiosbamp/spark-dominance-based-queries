import org.apache.spark.sql.{DataFrame, Row}
import org.apache.spark.util.CollectionAccumulator


object SFS {

  def computeLocalSkyline(df: DataFrame, localSkylinesAcc: CollectionAccumulator[Row]): Unit = {

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
          skylineDimensions(i) += skylineRow.getDouble(i)
        }

        if (isMultidimensionalPointDominated(dimensions, pointDimensions, skylineDimensions)){
          isDominated = true
        }

      })

      if (!isDominated){
        // add the coords of the points excluding the sum
        localSkylinesAcc.add(r)
      }
    })

  }

  def isMultidimensionalPointDominated(dimensions: Int, point: Array[Double], skylinePoint: Array[Double]): Boolean = {
//    var isDominated = true
    val atLeastAsGood = Array.fill(dimensions){false}

    for ( i <- 0 until dimensions) {
      if (skylinePoint(i) <= point(i)) {
        atLeastAsGood(i) = true
      }
    }

    val res = atLeastAsGood.reduce((a, b) => a && b)
    res
  }

}
