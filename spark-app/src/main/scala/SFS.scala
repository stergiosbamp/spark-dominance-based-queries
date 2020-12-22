import org.apache.spark.sql.{DataFrame, Row}
import org.apache.spark.util.CollectionAccumulator
import scala.collection.mutable.Set

object SFS {

  def computeLocalSkyline(df: DataFrame, localSkylinesAcc: CollectionAccumulator[Row]): Set[Row] = {

    println("Called computeLocalSkyline", df)

    var localSkyline = Set[Row]()

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

      var isDominated = false

      def checkCandidateRowAgainstSkylineRow(candidateRow: Row, skylineRow: Row): Unit = {
        val x = candidateRow.getDouble(0)
        val y = candidateRow.getDouble(1)

        val xSkyline = skylineRow.getDouble(0)
        val ySkyline = skylineRow.getDouble(1)

        if ((xSkyline < x && ySkyline <= y) || (xSkyline <= x && ySkyline < y)) {
          isDominated = true
        }
      }

      localSkyline.foreach{skylineRow => checkCandidateRowAgainstSkylineRow(candidateRow, skylineRow)}

      if (!isDominated) {
        localSkyline += candidateRow
      }
    }

    df.foreach(row => addOrIgnoreCandidateRow(row, localSkyline))

    localSkyline.foreach(row => localSkylinesAcc.add(row))

    println("INSIDE:", localSkyline)

    localSkyline
  }
}
