package cy.ac.ucy.cs.anyplace.lib

import cy.ac.ucy.cs.anyplace.lib.models.*
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface API {
  @GET("/api/version")
  suspend fun getVersion(): Response<Version>

  // FLOORS TODO IMPLEMENT + BIND
  @POST("api/mapping/floor/all")
  suspend fun floorAll(@Body req: ReqFloorAll): Response<Floors>

  // FLOORPLANS
  // https://ap-dev.cs.ucy.ac.cy:9001/api/floorplans64/vessel_9bdb1052-ff23-4f9b-b9f9-aae5095af468_1634646807927/-2
  // @POST("api/floorplans64/vessel_9bdb1052-ff23-4f9b-b9f9-aae5095af468_1634646807927/-2")
  @POST("api/floorplans64/{buid}/{floorNum}")
  suspend fun floorplanBase64(@Path("buid") buid: String,
                              @Path("floorNum") floorNum: String,
                              @Body body: Any = Object()): Response<ResponseBody>

















  // DEMO CODE:
  // send an empty object in the body: {}
  @POST("/api/mapping/space/public")
  suspend fun getSpacesPublic(@Body body: Any = Object()): Response<Spaces>

  @POST("/api/user/login")
  suspend fun userLoginLocal(@Body userLoginLocal: UserLoginLocalForm): Response<UserLoginResponse>

  @POST("/api/user/login/google")
  suspend fun userLoginGoogle(@Body userLogin: UserLoginGoogleData): Response<UserLoginResponse>
}