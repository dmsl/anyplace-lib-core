package cy.ac.ucy.cs.anyplace.lib.smas.models

import com.google.gson.annotations.SerializedName

data class SmasUser(
        val uid: String,
        val sessionkey: String,
        )

data class SmasLoginReq(
        val uid: String,
        val password: String)

data class SmasLoginResp(
        @SerializedName("descr")
        val descr: String?,
        // TODO:DZ: sessionid
        @SerializedName("sessionid")
        val sessionkey: String,
        @SerializedName("status")
        val status: String,
        @SerializedName("uid")
        val uid: String
)
