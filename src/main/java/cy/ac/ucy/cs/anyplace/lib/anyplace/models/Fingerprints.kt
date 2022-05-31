package cy.ac.ucy.cs.anyplace.lib.anyplace.models


import com.google.gson.annotations.SerializedName
import cy.ac.ucy.cs.anyplace.lib.smas.models.ChatUser

data class FingerprintSendReq(
  // USER:
  @SerializedName("uid")
  val uid: String,
  @SerializedName("sessionkey")
  val sessionkey: String,

  // LOCATION:
  @SerializedName("buid")
  val buid: String,
  @SerializedName("deck")
  val deck: Int,
  @SerializedName("x")
  val x: Double,
  @SerializedName("y")
  val y: Double,

  // TIME:
  @SerializedName("time")
  val time: String,

  // DETECTIONS:
  @SerializedName("cvDetections")
  val cvDetections: List<CvDetectionREQ>,
  @SerializedName("modelid")
  val modelid: Int
) {
  constructor(user: ChatUser, uc: UserCoordinates, time: String,
              detections: List<CvDetectionREQ>, modelId: Int):
      this(user.uid, user.sessionkey,
        uc.buid, uc.level, uc.lat, uc.lon,
        time,
        detections, modelId)
}

/** RESPONSE when sending a request */
data class FingerprintSendResp(
  @SerializedName("rows")
  val rows: Int,
  @SerializedName("status")
  val status: String,
  @SerializedName("uid")
  val uid: String,
  @SerializedName("descr") // CHECK:DZ
  val descr: String,
)


/**
 * Cv Detection information that is used for the SMAS
 * send-fingerprint request
 */
data class CvDetectionREQ(
  @SerializedName("oid")
  val oid: Int,
  @SerializedName("width")
  val width: Double,
  @SerializedName("height")
  val height: Double,
)


/**
 * Serialised Computer Vision Detection
 */
data class CvDetection(
  @SerializedName("oid")
  val oid: Int,

  // CHECK in [NMS] method, that uses a heap to discard duplicate detections,
  // we are considering the Width/Height of just the surviving detection
  // NOTE: duplicates due to YOLO's internal design
  @SerializedName("width")
  val width: Double,
  @SerializedName("height")
  val height: Double,

  /** Optical Character Recognition */
  @SerializedName("ocr")
  val ocr: String? = null,

  /** Name of the detection class */
  @SerializedName("detection")
  val detection: String?,
)
