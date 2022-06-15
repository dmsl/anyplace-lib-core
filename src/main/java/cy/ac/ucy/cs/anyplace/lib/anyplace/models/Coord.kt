package cy.ac.ucy.cs.anyplace.lib.anyplace.models

import com.google.gson.annotations.SerializedName

/**
 * [Coord] holds lat/lon as double.
 *
 * Used by core-lib, so we don't have to add Google Maps as a dependency (for [LatLng])
 */
data class Coord(
  @SerializedName("lat")
  val lat: Double,
  @SerializedName("lon")
  val lon: Double,
  /** Floor or Deck */
  @SerializedName("level")
  val level: Int,
  ) {
  companion object {
    fun get(lat: String, lon: String, level: Int) = Coord(lat.toDouble(), lon.toDouble(), level)
  }
}
