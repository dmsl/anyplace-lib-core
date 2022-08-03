package cy.ac.ucy.cs.anyplace.lib.anyplace.models

import com.google.gson.annotations.SerializedName

/**
 * Same response for the Anyplace login and Google login
 */
data class UserLoginResponse(
  @SerializedName("message")
    val message: String,
  @SerializedName("status")
    val status: String,
  @SerializedName("status_code")
    val statusCode: Int,
  @SerializedName("user")
    val userAP: UserAP,
)
