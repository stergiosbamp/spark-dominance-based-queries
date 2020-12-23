import org.apache.spark.sql.{DataFrame, Row}
import org.apache.spark.util.CollectionAccumulator

import scala.collection.mutable.Set
import scala.util.control.Breaks._

object SFS {

  def checkCandidateRowAgainstSkylineRow(candidateRow: Row, skylineRow: Row): Boolean = {
    var isDominated = false
    val x = candidateRow.getDouble(0)
    val y = candidateRow.getDouble(1)

    val xSkyline = skylineRow.getDouble(0)
    val ySkyline = skylineRow.getDouble(1)

    if ((xSkyline < x && ySkyline <= y) || (xSkyline <= x && ySkyline < y)) {
      isDominated = true
    }

    isDominated
  }

  def addOrIgnoreCandidateRow(candidateRow: Row, localSkyline: Set[Row]): Unit = {

    // The following line is just to demonstrate that the CollectionAccumulator can indeed collect the rows.
    // localSkylinesAcc.add(candidateRow)

    // The problem is that the localSkyline variable doesn't keep the state that we need (the rows that belong to the
    // local skyline. This can be seen by the relevant prints.

    println("Called addOrIngoreCandidateRow", candidateRow)

    if (localSkyline.isEmpty) {
      println("LocalSkyline is empty", localSkyline)
      localSkyline += candidateRow
      println("Added best local row to skyline", candidateRow)
      println("Now skyline has one row", localSkyline)
    }

    var result = false

    localSkyline.foreach( r => {
      result = checkCandidateRowAgainstSkylineRow(candidateRow, r)
      if (result) {
        break()
      }
    })

    if (!result) {
      localSkyline += candidateRow
    }
  }

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
        localSkylinesAcc.add(r)
      }
    })

  }
}
