import org.apache.spark.sql.{DataFrame, Row}
import org.apache.spark.util.CollectionAccumulator

import scala.collection.mutable.Set
import scala.util.control.Breaks._

object SFS {

  def computeLocalSkyline(df: DataFrame, localSkylinesAcc: CollectionAccumulator[Row]): Unit = {

    // First point based on SFS is in Skyline
    val firstPointX = df.first().getDouble(0)
    val firstPointY = df.first().getDouble(1)

    localSkylinesAcc.add(Row(firstPointX, firstPointY))

    // From the rest of the points excluding the first one
    // check the rest with the skyline set (accumulator) we have
    val dfWithoutFirst = df.filter(r => !r.getDouble(0).equals(firstPointX) && !r.getDouble(1).equals(firstPointY))
    dfWithoutFirst.coalesce(1).foreach( r => {

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
