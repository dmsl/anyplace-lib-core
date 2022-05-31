package cy.ac.ucy.cs.anyplace.lib.smas

import cy.ac.ucy.cs.anyplace.lib.anyplace.models.FingerprintSendReq
import cy.ac.ucy.cs.anyplace.lib.anyplace.models.FingerprintSendResp
import cy.ac.ucy.cs.anyplace.lib.smas.models.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/** SMAS Chat API */
interface ChatAPI {

  @POST("/smas/version.php")
  suspend fun version(@Body empty: Any = Object()): Response<ChatVersion>

  @POST("/smas/login.php")
  suspend fun login(@Body req: ChatLoginReq): Response<ChatLoginResp>

  @POST("/smas/location-get.php")
  suspend fun locationGet(@Body req: ChatUserAuth): Response<UserLocations>

  @POST("/smas/location-send.php")
  suspend fun locationSend(@Body req: LocationSendReq): Response<LocationSendResp>

  @POST("/smas/msg-get.php")
  suspend fun messagesGet(@Body req: MsgGetReq): Response<ChatMsgsResp>

  @POST("/smas/msg-send.php")
  suspend fun messageSend(@Body req: MsgSendReq): Response<MsgSendResp>

  // TODO fingerprint send?

  @POST("/smas/db-model-get.php")
  suspend fun cvModelsGet(@Body req: ChatUserAuth): Response<CvModelsResp>

  @POST("/smas/fingerprint-send.php")
  suspend fun cvFingerprintSend(@Body req: FingerprintSendReq): Response<FingerprintSendResp>
}

/** Authenticated ChatUser */
data class ChatUserAuth(
        val uid: String,
        val sessionkey: String) {
  constructor(user: ChatUser) : this(user.uid, user.sessionkey)
}
