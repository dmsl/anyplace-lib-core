package cy.ac.ucy.cs.anyplace.lib.smas.models

import com.google.gson.annotations.SerializedName


/** Request for getting a CvFingerprint */
data class CvFingerprintReq(
  val uid: String,
  val sessionkey: String,
  val modelid: Int,
  val buid: String,
  val from: Long) {
  constructor(user: SmasUser, modelid: Int, buid: String, from: Long=0) :
      this(user.uid, user.sessionkey, modelid, buid, from)
}


data class CvFingerprintResp(
  @SerializedName("rows")
  val rows: List<CvFingerprintRow>,
  @SerializedName("status")
  val status: String,
  @SerializedName("uid")
  val uid: String,
  @SerializedName("descr")
  val descr: String,
)


/**
 * This is a row of the FINGERPRINT table
 */
data class CvFingerprintRow(
  @SerializedName("foid")
  val foid: Int,

  @SerializedName("flid")
  val flid: Int,

  @SerializedName("uid")
  val uid: String,

  @SerializedName("time")
  val time: Long,
  @SerializedName("timestr")
  val timestr: String,

  @SerializedName("buid")
  val buid: String,
  @SerializedName("x")
  val x: Double,
  @SerializedName("y")
  val y: Double,
  @SerializedName("deck")
  val level: Int,

  @SerializedName("modelid")
  val modelid: Int,

  @SerializedName("flid:1")
  val flid1: Int,

  @SerializedName("oid")
  val oid: Int,

  @SerializedName("height")
  val height: Double,

  @SerializedName("width")
  val width: Double,

  @SerializedName("ocr")
  val ocr: String?,
)

