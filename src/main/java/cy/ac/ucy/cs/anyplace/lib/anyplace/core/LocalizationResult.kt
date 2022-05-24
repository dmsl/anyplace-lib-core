package cy.ac.ucy.cs.anyplace.lib.anyplace.core

import cy.ac.ucy.cs.anyplace.lib.anyplace.models.Coord


/**
 * Sets [coord] when a location is found, and error [message] when not.
 * [Unset] is used when [LocalizationResult] is not yet initialized, or
 * for some reason the outcome must be ignored.
 *
 * TODO: could add method here:
 * - CV, BLE, WiFi, Hybrid
 */
sealed class LocalizationResult(
  val coord: Coord?  = null,
  val message: String? = null,
  val details: String? = null) {

  class Success(coord: Coord, details: String?=null) : LocalizationResult(coord=coord, details=details)
  class Error(msg: String, details: String?=null): LocalizationResult(message=msg, details=details)
  class Unset: LocalizationResult()
}
