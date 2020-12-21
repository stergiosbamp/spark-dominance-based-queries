import org.apache.spark.sql.{DataFrame, Row}

import scala.math.BigDecimal.double2bigDecimal


object SFS {

  def computeLocalSkyline(df: DataFrame): Seq[Row] = {
    var localSkyline = Seq[Row]()

    df.foreach(row => {
      val x = row.getDouble(0)
      val y = row.getDouble(1)

      if (localSkyline.isEmpty) {
        localSkyline = localSkyline :+ row
      }

      localSkyline.foreach{ element => {
        val xSkyline = element.getDouble(0)
        val ySkyline = element.getDouble(1)
        if ((x <= xSkyline && y < ySkyline) || (x <= ySkyline && y < ySkyline)) {
          localSkyline = localSkyline :+ row
        }
      }}
    })

    println("INSIDE:", localSkyline)
    localSkyline
  }
}
