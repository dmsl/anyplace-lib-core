package cy.ac.ucy.cs.anyplace.lib.smas.models

import com.google.gson.annotations.SerializedName

data class CvModelsResp(
  @SerializedName("rows")
  val rows: List<CvModelClass>,
  @SerializedName("status")
  val status: String,
  @SerializedName("uid")
  val uid: String,
  /** Set on errors */
  @SerializedName("descr")
  val descr: String?
)

data class CvModelClass(
  /** An additional ID created by the SMAS backend */
  @SerializedName("oid")
  val oid: Int,
  /** ID used by the YOLO-generated model (zero based) */
  @SerializedName("cid")
  val cid: Int,
  @SerializedName("modeldescr")
  val modeldescr: String,
  @SerializedName("modelid")
  val modelid: Int,
  @SerializedName("name")
  val name: String,
)

/*
 * FOR REQUESTS: Only needs a [ChatUserAuth]
 */
