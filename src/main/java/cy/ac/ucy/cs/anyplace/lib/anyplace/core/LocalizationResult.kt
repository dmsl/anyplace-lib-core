package cy.ac.ucy.cs.anyplace.lib.anyplace.core

import cy.ac.ucy.cs.anyplace.lib.anyplace.models.Coord


enum class LocalizationMethod {
  anyplaceIMU,             // set by Anyplace: IMU
  anyplaceCvQueryOffline,  // set by Anyplace CV: Offline query (SQLite)
  anyplaceCvQueryOnline,   // set by Anyplace CV: Online query (remote API call)
  unknownMethod,                // set by the Computer Vision engine
  manualByUser,            // forced by the user (most likely w/ a long-click)
  autoMostRecent,          // auto-set to last, recent location (with some bounds on recency)
  NA                       // NotApplicable: localization method is only for own user's location
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
    /** Location forced manually by user */
    const val MANUAL = "loc.manual"
    /** Location auto restored automatically, as it was recent enough (user setting can disable this)
     * recency is fixed to 5 mins */
    const val AUTOSET_RECENT = "loc.last-recent"
    /** Localization was set by IMU (experimental) */
    const val ENGINE_IMU = "loc.engine.imu"
    /** Localization was set by an offline SMAS query */
    const val ENGINE_QUERY_ONLINE = "loc.engine.query.online"
    /** Localization was set by an offline SMAS query */
    const val ENGINE_QUERY_OFFLINE = "loc.engine.query.offline"


    fun getUsedMethod(lr: LocalizationResult) : LocalizationMethod {
      return when (lr.details.toString()) {
        MANUAL -> LocalizationMethod.manualByUser
        AUTOSET_RECENT -> LocalizationMethod.autoMostRecent
        ENGINE_IMU -> LocalizationMethod.anyplaceIMU
        ENGINE_QUERY_ONLINE-> LocalizationMethod.anyplaceCvQueryOnline
        ENGINE_QUERY_OFFLINE-> LocalizationMethod.anyplaceCvQueryOffline
        else -> LocalizationMethod.unknownMethod
      }
    }
  }

  class Success(coord: Coord, details: String) : LocalizationResult(coord=coord, details=details)
  class Error(msg: String, details: String?=null): LocalizationResult(message=msg, details=details)
  class Unset: LocalizationResult()
}
