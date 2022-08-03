package cy.ac.ucy.cs.anyplace.lib.smas.models

import com.google.gson.annotations.SerializedName
import cy.ac.ucy.cs.anyplace.lib.anyplace.models.UserCoordinates

/**
 * Request for sending/uploading local fingerprints
 */
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
  val level: Int,
  @SerializedName("x")
  val x: Double,
  @SerializedName("y")
  val y: Double,

  // TIME:
  @SerializedName("time")
  val time: String,

  @SerializedName("cvDetections")
  val cvDetections: List<CvObjectReq>,
  @SerializedName("modelid")
  val modelid: Int
) {
  constructor(user: SmasUser, uc: UserCoordinates, time: String,
              detections: List<CvObjectReq>, modelId: Int):
      this(user.uid, user.sessionkey,
        uc.buid, uc.level, uc.lat, uc.lon,
        time,
        detections, modelId)

  constructor(user: SmasUser, fe: FingerprintScan):
      this(user.uid, user.sessionkey,
        fe.buid, fe.level, fe.x, fe.y,
        fe.time,
        fe.cvDetections, fe.modelid)

}

/**
 * A locally stored fingerprint scan
 * Used for storing in file cache
 * has X,Y,Z location, detections, and model
 */
data class FingerprintScan(
  // LOCATION:
  @SerializedName("buid")
  val buid: String,
  @SerializedName("deck")
  val level: Int,
  @SerializedName("x")
  val x: Double,
  @SerializedName("y")
  val y: Double,

  // TIME:
  @SerializedName("time")
  val time: String,

  // DETECTIONS:
  @SerializedName("cvDetections")
  val cvDetections: List<CvObjectReq>,
  @SerializedName("modelid")
  val modelid: Int
) {
  constructor(uc: UserCoordinates, time: String,
              detections: List<CvObjectReq>, modelId: Int):
      this(uc.buid, uc.level, uc.lat, uc.lon,
        time,
        detections, modelId)
}


/** RESPONSE when sending fingerprints */
data class FingerprintSendResp(
  @SerializedName("rows")
  val rows: Int,
  @SerializedName("status")
  val status: String,
  @SerializedName("uid")
  val uid: String,
  @SerializedName("descr")
  val descr: String,
)


/**
 * Cv Detection information that is used for the SMAS
 * send-fingerprint request
 */
data class CvObjectReq(
  @SerializedName("oid")
  val oid: Int,
  @SerializedName("width")
  val width: Double,
  @SerializedName("height")
  val height: Double,
  @SerializedName("ocr")
  val ocr: String?,
) {
  constructor(cvd: CvObject) : this(cvd.oid, cvd.width, cvd.height, cvd.ocr)
}


/**
 * Serialised Computer Vision Detection
 */
data class CvObject(
  @SerializedName("oid")
  val oid: Int,

  @SerializedName("width")
  val width: Double,
  @SerializedName("height")
  val height: Double,

  /** Name of the detection class */
  @SerializedName("detection")
  val detection: String,

  /** Optical Character Recognition */
  @SerializedName("ocr")
  val ocr: String?,
)


/**
 * A single location generated by the SMAS backend
 */
data class CvLocation(
  @SerializedName("deck")
  val level: Int,
  @SerializedName("dissimilarity")
  val dissimilarity: Float,
  @SerializedName("flid")
  val flid: Int,
  @SerializedName("x")
  val x: Double,
  @SerializedName("y")
  val y: Double
)
