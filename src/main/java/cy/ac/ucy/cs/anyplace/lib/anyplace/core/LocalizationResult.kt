package cy.ac.ucy.cs.anyplace.lib.anyplace.core

import cy.ac.ucy.cs.anyplace.lib.anyplace.models.Coord


enum class LocalizationMethod {
  cvEngine,        // set by the Computer Vision engine
  manualByUser,    // forced by the user (most likely w/ a long-click)
  autoMostRecent,  // auto-set to last, recent location (with some bounds on recency)
  NA               // NotApplicable: localization method is only for own user's location
}

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

  companion object {
    const val MANUAL = "loc.manual"
    const val AUTOSET_RECENT = "loc.last-recent"

    fun getUsedMethod(lr: LocalizationResult) : LocalizationMethod {
      return when (lr.details.toString()) {
        MANUAL -> LocalizationMethod.manualByUser
        AUTOSET_RECENT -> LocalizationMethod.autoMostRecent
        else -> LocalizationMethod.cvEngine
      }
    }
  }

  class Success(coord: Coord, details: String?=null) : LocalizationResult(coord=coord, details=details)
  class Error(msg: String, details: String?=null): LocalizationResult(message=msg, details=details)
  class Unset: LocalizationResult()
}
