package cy.ac.ucy.cs.anyplace.lib.network

/**
 * A wrapper that is handy for managing the requests:
 * - on init: can be unset, so we can ignore it (in our [Flow] collectors
 * - when we start the request, we set it to [Loading]
 *   - this allows us to adjust our ui, and show that we are working the get/download some resources
 *   - then the result with either be [Success] or [Error], so we can act accordingly
 */
sealed class NetworkResult<T>(
  val data: T?  = null,
  val message: String? = null) {

  companion object {
    const val UP_TO_DATE = "up-to-date"
    const val DB_LOADED = "db-loaded"
  }

  class Success<T> (data: T, message: String?=null) : NetworkResult<T>(data, message)
  class Error<T>(message: String?, data: T?= null): NetworkResult<T>(data, message)
  class Loading<T>: NetworkResult<T>()
  class Unset<T>(message: String?=null, data: T?= null): NetworkResult<T>(data, message)
}
