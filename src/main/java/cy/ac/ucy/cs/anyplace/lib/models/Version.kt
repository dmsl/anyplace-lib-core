package cy.ac.ucy.cs.anyplace.lib.models

import com.google.gson.annotations.SerializedName

data class Version(
    @SerializedName("address")
    val address: String,
    @SerializedName("port")
    val port: String,
    @SerializedName("variant")
    val variant: String,
    @SerializedName("version")
    val version: String
)
