import org.apache.spark.sql.{DataFrame, Row}

import scala.math.BigDecimal.double2bigDecimal


object SFS {

  def computeLocalSkyline(df: DataFrame): Seq[Row] = {
    var localSkyline = Seq[Row]()

    df.foreach(row => {
      val seq = row.toSeq
      val x = seq(0)
      val y = seq(1)

      if (localSkyline.isEmpty) {
        localSkyline = localSkyline :+ row
      }

      localSkyline.foreach{ element => {
        val xSkyline = element(0)
        val ySkyline = element(1)
//        if ((x <= xSkyline && y < ySkyline) || (x <= ySkyline && y < ySkyline)) {
//          localSkyline = localSkyline :+ row
//        }
      }}
    })

    println("INSIDE:", localSkyline)
    localSkyline
  }
}
