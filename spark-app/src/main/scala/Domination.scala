import org.apache.spark.sql.{DataFrame, Row}
import org.apache.spark.util.LongAccumulator

/**
 * Object to provide functionality to compute domination scores
 * to all dimensions.
 */
object Domination {
  val sfs = SFS

  /**
   * Function that computes the dominance score for a given point (Row)
   * against the dataset and add the score in an accumulator.
   *
   * @param row The examined point.
   * @param dataset The DataFrame holding the objects of the dataset
   * @param scoreAcc The accumulator to be populated by the score of the point by executors.
   */
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
