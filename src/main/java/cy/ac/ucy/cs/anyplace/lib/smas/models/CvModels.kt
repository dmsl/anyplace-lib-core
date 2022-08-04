package cy.ac.ucy.cs.anyplace.lib.smas.models

import com.google.gson.annotations.SerializedName

/*
 * FOR REQUESTS: Only needs a [ChatUserAuth]
 * Response for CvModels (from remote DB that cotains oids, cid, etc; [CvModelClass])
 */
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

/**
 * Remote database representation of a Model's class
 */
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

/**
 * Request for the cv model files
 */
data class CvModelFilesReq(
  @SerializedName("uid")
  val uid: String,
  @SerializedName("sessionkey")
  val sessionkey: String,
  @SerializedName("modelid")
  val modelid: Int,
)

/**
 * Response for getting the cv model files
 * - the model weights and the class names ([CvModelFiles])
 */
data class CvModelFilesResp(
  @SerializedName("rows")
  val rows: CvModelFiles,
  @SerializedName("status")
  val status: String,
  @SerializedName("uid")
  val uid: String,
  @SerializedName("descr")
  val descr: String
)

/**
 * There is still a manual mapping between [CvModelFiles.modelid] and [DetectionModel] enum class.
 */
data class CvModelFiles(
  /** obj.names: a text file that contains the classes names */
  @SerializedName("classes")
  val classes: String,
  /** the model id (that was requested) */
  @SerializedName("modelid")
  val modelid: Int,
  /** the model itself (YOLO-tiny that was converted to TFlite) */
  @SerializedName("weights")
  val weights: String
)
