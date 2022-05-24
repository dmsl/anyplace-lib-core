package cy.ac.ucy.cs.anyplace.lib.anyplace.models

import com.google.gson.annotations.SerializedName

data class Space(
    @SerializedName("buid")
    val id: String,
    @SerializedName("space_type")
    val type: String,
    @SerializedName("bucode")
    val bucode: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("coordinates_lat")
    val coordinatesLat: String,
    @SerializedName("coordinates_lon")
    val coordinatesLon: String,
    @SerializedName("url")
    val url: String,

    // @SerializedName("username_creator")
    // val usernameCreator: String,
    // @SerializedName("is_published")
    // val isPublished: String
)


/**
 * Response containing with many spaces
 */
data class Spaces(
  @SerializedName("spaces")
  val spaces: List<Space>
)

/**
 * Saving User Selections (LastVal) regarding a Space.
 */
data class LastValSpaces(
  @SerializedName("lastFloor")
  var lastFloor: String? = null
)
