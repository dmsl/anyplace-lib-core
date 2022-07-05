package cy.ac.ucy.cs.anyplace.lib.anyplace.models

import com.google.gson.annotations.SerializedName
import cy.ac.ucy.cs.anyplace.lib.smas.models.CvDetection

/**
 * Serialized Computer Vision Map of detections
 * It contains a list of [CvLocationOLD]
 *
 * CLR:PM
 */
@Deprecated("Delete this")
data class CvMapRM(
  /** The kind of model used while performing Object Detection.
   * Examples: vessel-model, ucy-model, coco */
  @SerializedName("detectionModel")
  val detectionModel: String,
  @SerializedName("buid")
  val buid: String,
  @SerializedName("floor_number")
  val floorNumber: String,
  @SerializedName("cvMap")
  val locationOLDS: List<CvLocationOLD>,

  // version number of the [CvMap].
  // All previous versions will be dropped
  // [schema] is different than the global [_schema] field of
  // the backend-saved objects (MongoDB schema)
  @SerializedName("schema")
  val schema: Int) {
  companion object {
    const val SCHEMA = 1
  }
}

/**
 * Serialized Computer Vision Location.
 * It contains:
 * - latitude [lat]
 * - longitude [lon]
 * - and a list of [CvDetection]
 * CLR:PM
 */
@Deprecated("DELETE")
data class CvLocationOLD(
  @SerializedName("lat")
  val lat: String,
  @SerializedName("lon")
  val lon: String,
  @SerializedName("cvDetections")
  val detections: List<CvDetection>
)


