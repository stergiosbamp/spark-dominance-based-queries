import org.apache.spark.sql.{DataFrame, Row}

import scala.math.BigDecimal.double2bigDecimal


object SFS {

  def isCandidateSkyline(r: Row, skylineSet: Seq[Row]): Unit = {
    // for each point compare dimensions and
    // add it to set if it dominates every point in the set
    val seq = r.toSeq
    val x = seq(0)
    val y = seq(1)

    var localSkyline = Seq[Row]()

    if (skylineSet.isEmpty) {
      localSkyline = localSkyline :+ r
    }

    skylineSet.foreach{ element => {
        val xSkyline = element(0)
        val ySkyline = element(1)
//        if ((x <= xSkyline && y < ySkyline) || (x <= ySkyline && y < ySkyline)) {
//          localSkyline = localSkyline :+ r
//        }
      }
    }
  }

  def computeLocalSkyline(df: DataFrame): Seq[Row] = {
    var localSkyline = Seq[Row]()

    df.foreach(row => isCandidateSkyline(row, localSkyline))
    println("INSIDE:", localSkyline)
    localSkyline
  }
}
