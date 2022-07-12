package cy.ac.ucy.cs.anyplace.lib.smas

import cy.ac.ucy.cs.anyplace.lib.smas.models.CvLocalizeReq
import cy.ac.ucy.cs.anyplace.lib.smas.models.CvLocalizeResp
import cy.ac.ucy.cs.anyplace.lib.smas.models.FingerprintSendReq
import cy.ac.ucy.cs.anyplace.lib.smas.models.FingerprintSendResp
import cy.ac.ucy.cs.anyplace.lib.smas.models.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

/** SMAS Chat API */
interface ChatAPI {

  @POST("/{path}/version.php")
  suspend fun version(@Path("path") path: String, @Body empty: Any = Object()): Response<SmasVersion>

  @POST("/{path}/login.php")
  suspend fun login(@Path("path") path: String, @Body req: SmasLoginReq): Response<SmasLoginResp>

  @POST("/{path}/location-get.php")
  suspend fun locationGet(@Path("path") path: String, @Body req: ChatUserAuth): Response<UserLocations>

  @POST("/{path}/location-send.php")
  suspend fun locationSend(@Path("path") path: String, @Body req: LocationSendReq): Response<LocationSendResp>

  @POST("/{path}/msg-get.php")
  suspend fun messagesGet(@Path("path") path: String, @Body req: MsgGetReq): Response<ChatMsgsResp>

  @POST("/{path}/msg-send.php")
  suspend fun messageSend(@Path("path") path: String, @Body req: MsgSendReq): Response<MsgSendResp>

  // TODO fingerprint send?

  @POST("/{path}/db-model-get.php")
  suspend fun cvModelsGet(@Path("path") path: String, @Body req: ChatUserAuth): Response<CvModelsResp>

  @POST("/{path}/fingerprint-send.php")
  suspend fun cvFingerprintSend(@Path("path") path: String, @Body req: FingerprintSendReq): Response<FingerprintSendResp>

  @POST("/{path}/fingerprint-localize.php")
  suspend fun cvLocalization(@Path("path") path: String, @Body req: CvLocalizeReq): Response<CvLocalizeResp>


  @POST("/{path}/db-fingerprint-get.php")
  suspend fun cvFingerprintGet(@Path("path") path: String, @Body req: ChatUserAuth): Response<CvMapResp>
}

/** Authenticated ChatUser */
data class ChatUserAuth(
  val uid: String,
  val sessionkey: String) {
  constructor(user: SmasUser) : this(user.uid, user.sessionkey)
}
