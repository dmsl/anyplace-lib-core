package cy.ac.ucy.cs.anyplace.lib.utils

import cy.ac.ucy.cs.anyplace.lib.utils.Preferences

// TODO:PM replace Preferences with this
class Prefs {
  companion object {
    val BASE_URL: String
      get() = Preferences.getHost()
    val API_KEY: String
      get() = Preferences.getApiKey()
  }
}