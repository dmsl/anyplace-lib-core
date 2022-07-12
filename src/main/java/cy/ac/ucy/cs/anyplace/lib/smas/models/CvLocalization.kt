package cy.ac.ucy.cs.anyplace.lib.smas.models

import com.google.gson.annotations.SerializedName
import cy.ac.ucy.cs.anyplace.lib.anyplace.models.Coord

/**
 * Request for CV Localization
 */
data class CvLocalizeReq(
  @SerializedName("uid")
  val uid: String,
  @SerializedName("sessionkey")
  val sessionkey: String,

  @SerializedName("time")
  val time: String,

  @SerializedName("buid")
  val buid: String,
  @SerializedName("modelid")
  val modelid: Int,

  @SerializedName("cvDetections")
  val cvDetections: List<CvObjectReq>,

  @SerializedName("algorithm")
  val algorithm: Int,

  /** Optional: might be used to improve localization algo */
  @SerializedName("prevX")
  val prevX: Double?=null,

  /** Optional: might be used to improve localization algo */
  @SerializedName("prevY")
  val prevY: Double?=null,

  /** Optional: might be used to improve localization algo
   * (or for experimental evaluation)
   */
  @SerializedName("prevDeck")
  val prevDeck: Int?=null,

) {
  constructor(u: SmasUser, time: String,
              buid: String, modelId: Int, cvds: List<CvObjectReq>, algorithm: Int, prevCoord: Coord):
      this(u.uid, u.sessionkey, time, buid, modelId, cvds, algorithm,
        prevCoord.lat, prevCoord.lon, prevCoord.level)
  constructor(u: SmasUser, time: String,
              buid: String, modelId: Int, cvds: List<CvObjectReq>, algorithm: Int):
      this(u.uid, u.sessionkey, time, buid, modelId, cvds, algorithm)

}



/**
 * Response after asking the backend for a user's locations.
 *
 * It is possible that it contains multiple results [rocws]
 */
data class CvLocalizeResp(
  @SerializedName("uid")
  val uid: String,
  @SerializedName("rows")
  val rows: List<CvLocation>,
  @SerializedName("status")
  val status: String,
  @SerializedName("descr")
  val descr: String?=null,
)
