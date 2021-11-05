package cy.ac.ucy.cs.anyplace.lib.models

import com.google.gson.annotations.SerializedName

data class Floors(
    @SerializedName("floors")
    val floors: List<Floor>
)

// Request
data class ReqFloorAll(val buid: String)
