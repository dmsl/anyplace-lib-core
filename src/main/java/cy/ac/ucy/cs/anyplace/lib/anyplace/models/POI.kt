package cy.ac.ucy.cs.anyplace.lib.anyplace.models


import com.google.gson.annotations.SerializedName

data class POI(
  @SerializedName("buid")
    val buid: String,
  @SerializedName("coordinates_lat")
    val coordinatesLat: String,
  @SerializedName("coordinates_lon")
    val coordinatesLon: String,
  @SerializedName("description")
    val description: String,
  @SerializedName("floor_name")
    val levelName: String,
  @SerializedName("floor_number")
    val levelNumber: String,
  @SerializedName("image")
    val image: String,
  @SerializedName("is_building_entrance")
    val isBuildingEntrance: String,
  @SerializedName("is_door")
    val isDoor: String,
  @SerializedName("is_published")
    val isPublished: String,
  @SerializedName("name")
    val name: String,
  @SerializedName("pois_type")
    val poisType: String,
  @SerializedName("puid")
    val puid: String,
  @SerializedName("username_creator")
    val usernameCreator: Any
)

data class POIsResp(
  @SerializedName("pois")
  val objs: List<POI>
)

data class ReqSpacePOIs(
  @SerializedName("buid")
  val buid: String
)

