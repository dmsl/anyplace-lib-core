package cy.ac.ucy.cs.anyplace.lib

import cy.ac.ucy.cs.anyplace.lib.models.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface API {
  @GET("/api/version")
  suspend fun getVersion(): Response<Version>

  // send an empty object in the body: {}
  @POST("/api/mapping/space/public")
  suspend fun getSpacesPublic(@Body body: Any = Object()): Response<Spaces>

  @POST("/api/user/login")
  suspend fun userLoginLocal(@Body userLoginLocal: UserLoginLocalForm): Response<UserLoginResponse>

  @POST("/api/user/login/google")
  suspend fun userLoginGoogle(@Body userLogin: UserLoginGoogleData): Response<UserLoginResponse>

}