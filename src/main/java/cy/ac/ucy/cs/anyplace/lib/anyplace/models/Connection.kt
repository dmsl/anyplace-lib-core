package cy.ac.ucy.cs.anyplace.lib.anyplace.models

import com.google.gson.annotations.SerializedName

data class Connection(
    @SerializedName("buid")
    val buid: String,
    @SerializedName("buid_a")
    val buidA: String,
    @SerializedName("buid_b")
    val buidB: String,
    @SerializedName("cuid")
    val cuid: String,
    @SerializedName("edge_type")
    val edgeType: String,
    @SerializedName("floor_a")
    val floorA: String,
    @SerializedName("floor_b")
    val floorB: String,
    @SerializedName("is_published")
    val isPublished: String,
    @SerializedName("pois_a")
    val poisA: String,
    @SerializedName("pois_b")
    val poisB: String,
    @SerializedName("weight")
    val weight: String
)

data class ConnectionsResp(
  @SerializedName("connections")
  val objs: List<Connection>
)

data class ReqSpaceConnections(
  @SerializedName("buid")
  val buid: String
)

