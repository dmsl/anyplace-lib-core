package cy.ac.ucy.cs.anyplace.lib.models

import com.google.gson.annotations.SerializedName

data class Floor(
    @SerializedName("fuid")
    val fuid: String,

    @SerializedName("buid")
    val buid: String,
    @SerializedName("floor_number")
    val floorNumber: String,

    @SerializedName("floor_name")
    val floorName: String,
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
 * Many floors in a response
 */
data class Floors(
  @SerializedName("floors")
  val floors: List<Floor>
)

// TODO:PMX: Req<Endpoint>
// Request
data class ReqFloorAll(val buid: String)
