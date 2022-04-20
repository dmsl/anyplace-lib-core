package cy.ac.ucy.cs.anyplace.lib.network

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
  class Unset<T>: NetworkResult<T>()
}
