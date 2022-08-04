package cy.ac.ucy.cs.anyplace.lib.anyplace.models

import com.google.gson.annotations.SerializedName

/**
 * Level:
 * - some times referred to as: floor, or deck
 * - it's a floor or a deck
 */
data class Level(
  @SerializedName("fuid")
    val fuid: String,

  @SerializedName("buid")
    val buid: String,
  @SerializedName("floor_number")
    val number: String,

  @SerializedName("floor_name")
    val name: String,
  @SerializedName("description")
    val description: String,

    // Coordinates
  @SerializedName("top_right_lat")
    val topRightLat: String,
  @SerializedName("top_right_lng")
    val topRightLng: String,
  @SerializedName("bottom_left_lat")
    val bottomLeftLat: String,
  @SerializedName("bottom_left_lng")
    val bottomLeftLng: String,

  @SerializedName("is_published")
    val isPublished: String,

  @SerializedName("zoom")
    val zoom: String
)

/**
 * Many Levels (floors/decks) in a response
 */
data class Levels(
  @SerializedName("floors")
  val levels: List<Level>
)
