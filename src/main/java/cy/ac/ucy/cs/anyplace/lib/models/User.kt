package cy.ac.ucy.cs.anyplace.lib.models

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("owner_id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("external")
    val account: String,

    @SerializedName("username")
    val username: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("photo_uri")
    var photoUri: String
)

data class UserLoginLocalForm(
    val username: String,
    val password: String)

data class UserLoginGoogleData(
    val external: String,
    val name: String,
    val access_token: String?)
