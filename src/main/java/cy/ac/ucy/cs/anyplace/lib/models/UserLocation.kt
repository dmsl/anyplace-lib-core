package cy.ac.ucy.cs.anyplace.lib.models

import com.google.gson.annotations.SerializedName

data class UserLocation(
  /** User's ID */
  @SerializedName("uid")
  val uid: String,
  /** Whether the user is emitting an alert */
  @SerializedName("alert")
  val alert: Int,

  /** SpaceID */
  @SerializedName("buid")
  val buid: String,
  @SerializedName("deck")
  val deck: Int,

  /** Latitude */
  @SerializedName("x")
  val x: Double,
  /** Longitude */
  @SerializedName("y")
  val y: Double,

  // Time:
  @SerializedName("time")
  val time: Int,
  @SerializedName("timestr")
  val timestr: String,
  @SerializedName("servertime")
  val servertime: String,
)

