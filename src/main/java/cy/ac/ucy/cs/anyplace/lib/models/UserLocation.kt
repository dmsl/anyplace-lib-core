package cy.ac.ucy.cs.anyplace.lib.models

import com.google.gson.annotations.SerializedName

/** SMAS Response for a [UserLocation] */
data class UserLocation(
  /** User's ID */
  @SerializedName("uid")
  val uid: String,
  /** Whether the user is emitting an alert */
  @SerializedName("alert")
  val alert: Int,

  @SerializedName("name")
  val name: String,
  @SerializedName("surname")
  val surname: String,

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
  val time: Long,
  @SerializedName("timestr")
  val timestr: String,
  @SerializedName("servertime")
  val servertime: String,

  /** Timestamp of the last user msg*/
  @SerializedName("msgts")
  val lastMsgTime: Long,
)


/** A location in a space:
 * - Space ID
 * - Deck/Floor
 * - lat/long
 */
data class UserCoordinates(
  /** Space ID */
  val buid: String,
  /** Floor or Deck */
  val level: Int,
  val lat: Double,
  val lon: Double,
)
