import org.apache.spark.sql.{DataFrame, Row}
import org.apache.spark.util.{CollectionAccumulator, LongAccumulator}

object Domination {

  def dominantScore(row: Row,
                    dataset: DataFrame,
                    scoreAcc: LongAccumulator): Unit = {
    val pointX = row.getDouble(0)
    val pointY = row.getDouble(1)
    var score = 0

    dataset.foreach(r => {
      val datasetX = r.getDouble(0)
      val datasetY = r.getDouble(1)

      if ((pointX < datasetX && pointY <= datasetY) || (pointX <= datasetX && pointY < datasetY)) {
        score = score + 1
        scoreAcc.add(1)
      }
    })
  }
}
