package cy.ac.ucy.cs.anyplace.lib.smas.models

import com.google.gson.annotations.SerializedName

data class CvMapResp(
  @SerializedName("rows")
  val rows: List<CvMapRow>,
  @SerializedName("status")
  val status: String,
  @SerializedName("uid")
  val uid: String,
  @SerializedName("descr")
  val descr: String,
)

/**
 * This is the FINGERPRINT table
 */
data class CvMapRow(
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
  val deck: Int,

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

