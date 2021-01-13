import org.apache.spark.sql.{DataFrame, Row}
import org.apache.spark.util.{CollectionAccumulator, LongAccumulator}

object Domination {
  val sfs = SFS

  def dominantScore(row: Row,
                    dataset: DataFrame,
                    scoreAcc: LongAccumulator): Unit = {
    val dimensions = row.length - 1

    // Array that represents the dimensions (coordinates) of the examined point
    val pointDimensions = Array.fill(dimensions){0.0}
    for ( i <- 0 until dimensions) {
      pointDimensions(i) += row.getDouble(i)
    }

    dataset.foreach(r => {

      val otherPointDimensions = Array.fill(dimensions){0.0}
      for ( i <- 0 until dimensions) {
        otherPointDimensions(i) += r.getDouble(i)
      }

      if (sfs.isMultidimensionalPointDominated(dimensions, otherPointDimensions, pointDimensions)) {
        scoreAcc.add(1)
      }
    })
  }
}
