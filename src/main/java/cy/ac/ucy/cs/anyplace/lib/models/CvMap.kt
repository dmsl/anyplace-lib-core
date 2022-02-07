package cy.ac.ucy.cs.anyplace.lib.models

import com.google.gson.annotations.SerializedName

/**
 * Serialized Computer Vision Map of detections
 * It contains a list of [CvLocation]
 */
data class CvMap(
  /** The kind of model used while performing Object Detection.
   * Examples: vessel-model, ucy-model, coco */
  @SerializedName("detectionModel")
  val detectionModel: String,
  @SerializedName("buid")
  val buid: String,
  @SerializedName("floor_number")
  val floorNumber: String,
  @SerializedName("cvMap")
  val locations: List<CvLocation>,

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
 */
data class CvLocation(
  @SerializedName("lat")
  val lat: String,
  @SerializedName("lon")
  val lon: String,
  @SerializedName("cvDetections")
  val detections: List<CvDetection>
)


/**
 * Serialised Computer Vision Detection
 */
data class CvDetection(
  @SerializedName("detection")
  val detection: String,

  // CHECK in [NMS] method, that uses a heap to discard duplicate detections,
  // we are considering the Width/Height of just the surviving detection
  // NOTE: duplicates due to YOLO's internal design
  @SerializedName("width")
  val width: Float,
  @SerializedName("height")
  val height: Float,

  /** Optical Character Recognition */
  @SerializedName("ocr")
  val ocr: String? = null,
)


// LEFTHERE:
// 1. materialize map and store to file
// 2. inspect file
// 3. read map, create detections again...
// 4. parse it and place it on map
// 5. TODO FUTURE: allow editing the map..

// TODO PARSE CV MAP:
// array of: locations x classes.size

// HASHMAP: OCR: "class_location"
