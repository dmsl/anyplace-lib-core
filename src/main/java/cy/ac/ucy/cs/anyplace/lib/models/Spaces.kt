package cy.ac.ucy.cs.anyplace.lib.models

import com.google.gson.annotations.SerializedName

data class Spaces(
    @SerializedName("spaces")
    val spaces: List<Space>
)

// TODO ReqSpaces? (See [Floors.kt])

/**
 * Saving User Selections (LastVal) regarding a Space.
 */
data class LastValSpaces(
    @SerializedName("lastFloor")
    var lastFloor: String? = null
)