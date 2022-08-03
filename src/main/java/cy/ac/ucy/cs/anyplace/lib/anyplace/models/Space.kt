package cy.ac.ucy.cs.anyplace.lib.anyplace.models

import com.google.gson.annotations.SerializedName

data class Space(
  /** ID of the space.
   * - buid: used to stand for building unique id
   */
  @SerializedName("buid")
    val buid: String,
  /** type: vessel or buildign */
  @SerializedName("space_type")
    val type: String,
  /** some buildigns have a code name too (like a manually asigned id) */
  @SerializedName("bucode")
    val bucode: String,
  /** Name of the building */
  @SerializedName("name")
    val name: String,
  /** Description of the building */
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

  /** this is done locally */
    @SerializedName("ownerShip")
    val ownerShip: String?=null,

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
