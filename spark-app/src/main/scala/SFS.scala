import org.apache.spark.sql.{DataFrame, Row}
import org.apache.spark.util.CollectionAccumulator


object SFS {

  def computeLocalSkyline(df: DataFrame, localSkylinesAcc: CollectionAccumulator[Row]): Unit = {

    df.coalesce(1).foreach( r => {

      val x = r.getDouble(0)
      val y = r.getDouble(1)

      val value = localSkylinesAcc.value

      var isDominated = false
      value.forEach(skylineRow => {
        val xSkyline = skylineRow.getDouble(0)
        val ySkyline = skylineRow.getDouble(1)

        if ((xSkyline < x && ySkyline <= y) || (xSkyline <= x && ySkyline < y)) {
          isDominated = true
        }
      })

      if (!isDominated){
        // add the coords of the points excluding the sum
        localSkylinesAcc.add(Row(x, y))
      }
    })

  }
}
