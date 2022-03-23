package cy.ac.ucy.cs.anyplace.lib.models

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
  val lon: Double) {
  companion object {
    fun get(lat: String, lon: String) = Coord(lat.toDouble(), lon.toDouble())
  }
}
