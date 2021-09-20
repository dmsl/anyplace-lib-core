/*
 * Anyplace: A free and open Indoor Navigation Service with superb accuracy!
 *
 * Anyplace is a first-of-a-kind indoor information service offering GPS-less
 * localization, navigation and search inside buildings using ordinary smartphones.
 *
 * Author(s): Christakis Achilleos, Constandinos Demetriou, Marcos Antonios Charalambous
 *
 * Co-supervisor: Paschalis Mpeis
 *
 * Supervisor: Demetrios Zeinalipour-Yazti
 *
 * URL: http://anyplace.cs.ucy.ac.cy
 * Contact: anyplace@cs.ucy.ac.cy
 *
 * Copyright (c) 2019, Data Management Systems Lab (DMSL), University of Cyprus.
 * All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in the
 * Software without restriction, including without limitation the rights to use, copy,
 * modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the
 * following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 *
 */
package cy.ac.ucy.cs.anyplace.lib

import cy.ac.ucy.cs.anyplace.lib.core.Algorithms
import cy.ac.ucy.cs.anyplace.lib.core.LogRecord
import cy.ac.ucy.cs.anyplace.lib.core.RadioMap
import cy.ac.ucy.cs.anyplace.lib.utils.JsonHelper
import cy.ac.ucy.cs.anyplace.lib.utils.Preferences
import org.json.JSONException
import org.json.JSONObject
import java.awt.image.BufferedImage
import java.io.*
import java.util.*
import javax.imageio.ImageIO

class Anyplace {
  enum class OS { Android, Unknown }

  companion object {

    private fun setCache(c: String): cache=c;

    fun Initialize(host: String, port: String?, cache: String) {
      setCache(cache)
      setHost(host)
      port = port
    }
    fun Initialize(host: String, port: String?, cache: String, os: OS) {
      setCache(cache)
      setHost(host)
      port = port
      this.os = os
    }

    /**
     * Gets the details of a specific Point Of Interest
     *
     * @param access_token The users access token (api key)
     * @param pois         The POI that is being specified
     * @return The response JSON as a String
     */
    fun poiDetails(access_token: String?, pois: String?): String {
      val client = RestClient()
      setPath("/anyplace/navigation/pois/id")
      val params: MutableMap<String, String?> = HashMap()
      params["access_token"] = access_token
      params["pois"] = pois
      return JsonHelper.jsonResponse(STATUS_OK, client.doPost(params, host, path))
    }

    fun poiDetails(pois: String?): String {
      return if (Companion.token!!.isEmpty() || Companion.token == null) {
        JsonHelper.jsonResponse(STATUS_ERR, "{}")
      } else poiDetails(
        Companion.token,
        pois
      )
    }

    /**
     * Navigation instructions given from a given location to a POI.
     *
     * @param access_token   The users access token (api key)
     * @param pois_to        The target POI
     * @param buid           The building ID
     * @param floor          The floor number
     * @param coordinates_lat The latitude of position
     * @param coordinates_lon The longitude of position
     * @return A JSON in String form
     */
    fun navigationXY(
      access_token: String?,
      pois_to: String?,
      buid: String?,
      floor: String?,
      coordinates_lat: String?,
      coordinates_lon: String?
    ): String {
      val client = RestClient()
      setPath("/anyplace/navigation/route_xy")
      val params: MutableMap<String, String?> = HashMap()
      params["access_token"] = access_token
      params["pois_to"] = pois_to
      params["buid"] = buid
      params["floor_number"] = floor
      params["coordinates_lat"] = coordinates_lat
      params["coordinates_lon"] = coordinates_lon
      val response = client.doPost(params, host, path)
      val obj: JSONObject
      val statusCode: Int
      try {
        obj = JSONObject(response)
        statusCode = obj.getInt("status_code")
        if (statusCode != 200) {
          return JsonHelper.printError(Exception(), "navigationXY")
        }
      } catch (e: JSONException) {
        return JsonHelper.printError(e, "navigationXY")
      }
      return JsonHelper.jsonResponse(STATUS_OK, response)
    }

    /**
     * Navigation instructions given from a given location to a POI
     *
     * @param pois_to        The target POI
     * @param buid           The building ID
     * @param floor          The floor number
     * @param coordinates_lat The latitude of position
     * @param coordinates_lon The longitude of position
     * @return A JSON in String form
     */
    fun navigationXY(
      pois_to: String?,
      buid: String?,
      floor: String?,
      coordinates_lat: String?,
      coordinates_lon: String?
    ): String {
      return navigationXY(Companion.token, pois_to, buid, floor, coordinates_lat, coordinates_lon)
    }

    /**
     * Navigation instructions between 2 POIs.
     *
     * @param access_token The users access token (api key)
     * @param pois_to      The target POI
     * @param pois_from    The starting POI
     * @return The navigation path in the form of a JSON string as sent by the
     * server
     */
    fun navigationPoiToPoi(access_token: String?, pois_to: String?, pois_from: String?): String {
      val client = RestClient()
      setPath("/anyplace/navigation/route")
      val params: MutableMap<String, String?> = HashMap()
      params["access_token"] = access_token
      params["pois_from"] = pois_from
      params["pois_to"] = pois_to
      val response = client.doPost(params, host, path)
      val obj: JSONObject
      val statusCode: Int
      try {
        obj = JSONObject(response)
        statusCode = obj.getInt("status_code")
      } catch (e: JSONException) {
        return JsonHelper.printError(e, "navigationPoiToPoi")
      }
      return if (statusCode != 200) {
        JsonHelper.printError(Exception(), "navigationPoiToPoi")
      } else JsonHelper.jsonResponse(STATUS_OK, response)
    }

    /**
     * Navigation instructions between 2 POIs.
     *
     * @param pois_to      The target POI
     * @param pois_from    The starting POI
     * @return The navigation path in the form of a JSON string as sent by the
     * server
     */
    fun navigationPoiToPoi(pois_to: String?, pois_from: String?): String {
      return navigationPoiToPoi(Companion.token, pois_to, pois_from)
    }

    /**
     * Get all annotated buildings
     *
     * @return The response JSON as a String
     */
    fun buildingAll(): String {
      val client = RestClient()
      setPath("/anyplace/mapping/building/all")
      return JsonHelper.jsonResponse(STATUS_OK, client.doPost(null, host, path))
    }

    /**
     * Get all buildings for a campus
     *
     * @param cuid The campus ID
     * @return JSON String response
     */
    fun buildingsByCampus(cuid: String): String {
      val client = RestClient()
      setPath("/anyplace/mapping/campus/all_cucode")
      val params: MutableMap<String, String> = HashMap()
      params["cuid"] = cuid
      return JsonHelper.jsonResponse(STATUS_OK, client.doPost(params, host, path))
    }

    /**
     * Get all buildings with the same code
     *
     * @param bucode The building code
     * @return JSON String response
     */
    fun buildingsByBuildingCode(bucode: String): String {
      val client = RestClient()
      setPath("/anyplace/mapping/building/all_bucode")
      val params: MutableMap<String, String> = HashMap()
      params["bucode"] = bucode
      return JsonHelper.jsonResponse(STATUS_OK, client.doPost(params, host, path))
    }

    /**
     * Get all nearby buildings - 50 meter radius
     *
     * @param access_token    The users access token (api key)
     * @param coordinates_lat The latitude
     * @param coordinates_lon The longitude
     * @return Gives the JSON String response of the server
     */
    fun nearbyBuildings(
      access_token: String?,
      coordinates_lat: String?,
      coordinates_lon: String?
    ): String {
      val client = RestClient()
      setPath("/anyplace/mapping/building/coordinates")
      val params: MutableMap<String, String?> = HashMap()
      params["access_token"] = access_token
      params["coordinates_lat"] = coordinates_lat
      params["coordinates_lon"] = coordinates_lon
      return JsonHelper.jsonResponse(STATUS_OK, client.doPost(params, host, path))
    }

    /**
     * Get all nearby buildings - 50 meter radius
     *
     * @param coordinates_lat The latitude
     * @param coordinates_lon The longitude
     * @return Gives the JSON String response of the server
     */
    fun nearbyBuildings(coordinates_lat: String?, coordinates_lon: String?): String {
      return nearbyBuildings(Companion.token, coordinates_lat, coordinates_lon)
    }

    /**
     * Get all floors of a building
     *
     * @param buid Building ID
     * @return A JSON String containing all the floors of the building
     */
    fun allBuildingFloors(buid: String): String {
      val client = RestClient()
      setPath("/anyplace/mapping/floor/all")
      val params: MutableMap<String, String> = HashMap()
      params["buid"] = buid
      return JsonHelper.jsonResponse(STATUS_OK, client.doPost(params, host, path))
    }

    /**
     * Upload RSS log
     *
     * @param access_token Your access token
     * @return A JSON String containing all the floors of the building
     */
    fun uploadRssLog(access_token: String?, rsslog: String?): String {
      val file = File(rsslog)
      val client = RestClient()
      setPath("/anyplace/position/radio_upload")
      val response = client.uploadFile(host, path, file, access_token)
      return JsonHelper.jsonResponse(
        if (response != null) STATUS_OK else STATUS_ERR,
        response ?: "{\"message\":\"ERROR in Uploading RSS log\"}"
      )
    }

    /**
     * Get all POIs inside of a building
     *
     * @param buid The building ID
     * @return JSON string with all the POIs in the building
     */
    fun allBuildingPOIs(buid: String): String {
      val client = RestClient()
      setPath("/anyplace/mapping/pois/all_building")
      val params: MutableMap<String, String> = HashMap()
      params["buid"] = buid
      return JsonHelper.jsonResponse(STATUS_OK, client.doPost(params, host, path))
    }

    /**
     * Get all POIs inside of a floor of a building
     *
     * @param buid  The building ID
     * @param floor The floor number
     * @return JSON String with all the POIs of a floor
     */
    fun allBuildingFloorPOIs(buid: String, floor: String): String {
      val client = RestClient()
      setPath("/anyplace/mapping/pois/all_floor")
      val params: MutableMap<String, String> = HashMap()
      params["buid"] = buid
      params["floor_number"] = floor
      return JsonHelper.jsonResponse(STATUS_OK, client.doPost(params, host, path))
    }

    /**
     * Get all connections between POIs inside of a floor of a building
     *
     * @param buid  The building ID
     * @param floor The floor number
     * @return JSON String with all the connections in a floor
     */
    fun connectionsByFloor(buid: String, floor: String): String {
      val client = RestClient()
      setPath("/anyplace/mapping/connection/all_floor")
      val params: MutableMap<String, String> = HashMap()
      params["buid"] = buid
      params["floor_number"] = floor
      return JsonHelper.jsonResponse(STATUS_OK, client.doPost(params, host, path))
    }

    /**
     * Get all positions with their respective Wi-Fi radio measurements.
     *
     * @param buid  The building ID
     * @param floor The floor number
     * @return JSON String with the wifi intensities on a floor
     */
    fun radioheatMapBuildingFloor(buid: String, floor: String): String {
      val client = RestClient()
      val params: MutableMap<String, String> = HashMap()
      params["buid"] = buid
      params["floor"] = floor
      setPath("/anyplace/mapping/radio/heatmap_building_floor")
      return JsonHelper.jsonResponse(STATUS_OK, client.doPost(params, host, path))
    }

    /**
     * Download the floor plan in base64 png format. It also stores the file locally
     * as a png file
     *
     * @param access_token The access token(api key)
     * @param buid         The building ID
     * @param floor        The floor number
     * @return JSON String containing the floor plan in a base64 format
     */
    fun floorplans(access_token: String, buid: String, floor: String, f: File?): String {
      var output: OutputStream? = null
      val client = RestClient()
      setPath("/anyplace/floorplans64/$buid/$floor")
      val params: MutableMap<String, String> = HashMap()
      params["access_token"] = access_token
      params["buid"] = buid
      params["floor"] = floor
      val response = client.doPost(params, host, path)
      try {
        output = FileOutputStream(f)
        output.write(response.toByteArray())
        output.close()
      } catch (e: Exception) {
        return JsonHelper.printError(e, "floorplans")
      }
      return response
    }

    /**
     * Download the floor plan in base64 png format. It also stores the file locally
     * as a png file
     *
     * @param access_token The access token(api key)
     * @param buid         The building ID
     * @param floor        The floor number
     * @return JSON String containing the floor plan in a base64 format
     */
    fun floorplans64(access_token: String?, buid: String, floor: String): String {
      val client = RestClient()
      setPath("/anyplace/floorplans64/$buid/$floor")
      val params: MutableMap<String, String?> = HashMap()
      params["access_token"] = access_token
      params["buid"] = buid
      params["floor"] = floor
      val response = client.doPost(params, host, path)
      val filename = Companion.cache + buid + "/" + floor + "/" + "floorplan.png"
      try {
        val outputfile = File(filename)
        ImageIO.write(decodeToImage(response), "png", outputfile)
      } catch (e: Exception) {
        return JsonHelper.printError(e, "floorplan64")
      }
      return JsonHelper.jsonResponse(STATUS_OK, response)
    }

    /**
     * Download the floor plan in base64 png format. It also stores the file locally
     * as a png file
     *
     * @param buid         The building ID
     * @param floor        The floor number
     * @return JSON String containing the floor plan in a base64 format
     */
    fun floorplans64(buid: String, floor: String): String {
      return floorplans64(token, buid, floor)
    }

    /**
     * Download the floor plan tiles in a zip file
     *
     * @param access_token The access token(api key)
     * @param buid         The building ID
     * @param floor        The floor number
     * @return JSON String containing the floor tile zip download url
     */
    fun floortiles(access_token: String?, buid: String, floor: String): String {
      val client = RestClient()
      setPath("/anyplace/floortiles/$buid/$floor")
      val params: MutableMap<String, String?> = HashMap()
      params["access_token"] = access_token
      params["buid"] = buid
      params["floor"] = floor
      val response = client.doPost(params, host, path)
      val obj: JSONObject
      val statusCode: Int
      try {
        obj = JSONObject(response)
        statusCode = obj.getInt("status_code")
      } catch (e: JSONException) {
        return JsonHelper.printError(e, "floortiles")
      }
      if (statusCode == 200) {
        val tiles_archive: String
        tiles_archive = try {
          obj.getString("tiles_archive")
        } catch (e: JSONException) {
          return JsonHelper.printError(e, "floortiles")
        }
        val zip = client.getFileWithGet(host, tiles_archive)
        val filename = "res/$buid/$floor/floorPlanTiles.zip"
        try {
          val outputStream = FileOutputStream(filename)
          outputStream.write(zip)
          outputStream.close()
        } catch (e: IOException) {
          return JsonHelper.printError(e, "floortiles")
        }
      } else {
        println("Bad response: $statusCode")
        println(response)
        return response
      }
      return JsonHelper.jsonResponse(STATUS_OK, response)
    }

    /**
     * Download the floor plan tiles in a zip file
     *
     * @param access_token The access token(api key)
     * @param buid         The building ID
     * @param floor        The floor number
     * @return JSON String containing the floor tile zip download url
     */
    fun floortilesByte(access_token: String, buid: String, floor: String): ByteArray? {
      val client = RestClient()
      setPath("/anyplace/floortiles/$buid/$floor")
      val params: MutableMap<String, String> = HashMap()
      params["access_token"] = access_token
      params["buid"] = buid
      params["floor"] = floor
      val response = client.doPost(params, host, path)
      val obj: JSONObject
      val statusCode: Int
      try {
        obj = JSONObject(response)
        statusCode = obj.getInt("status_code")
      } catch (e: JSONException) {
        return null
      }
      if (statusCode == 200) {
        val tiles_archive: String
        tiles_archive = try {
          obj.getString("tiles_archive")
        } catch (e: JSONException) {
          return null
        }
        return client.getFileWithGet(host, tiles_archive)
      } else {
        println("Bad response floortiles")
      }
      return null
    }

    /**
     * Download the floor plan tiles in a zip file
     *
     * @param buid         The building ID
     * @param floor        The floor number
     * @return JSON String containing the floor tile zip download url
     */
    fun floortiles(buid: String, floor: String): String {
      return floortiles(Companion.token, buid, floor)
    }

    /**
     * Radio map using all entries near the location
     *
     * @param access_token    The access token(api key)
     * @param coordinates_lat Latitude
     * @param coordinates_lon Longitude
     * @param floor           The floor number
     * @return JSON String with all the radio measurements of a floor
     */
    fun radioByCoordinatesFloor(
      access_token: String?, coordinates_lat: String?, coordinates_lon: String?,
      floor: String?
    ): String {
      val client = RestClient()
      setPath("/anyplace/position/radio_download_floor")
      val params: MutableMap<String, String?> = HashMap()
      params["access_token"] = access_token
      params["coordinates_lat"] = coordinates_lat
      params["coordinates_lon"] = coordinates_lon
      params["floor_number"] = floor
      params["mode"] = "foo"
      return JsonHelper.jsonResponse(STATUS_OK, client.doPost(params, host, path))
    }

    /**
     * Radio map using all entries near the location
     *
     * @param coordinates_lat Latitude
     * @param coordinates_lon Longitude
     * @param floor           The floor number
     * @return JSON String with all the radio measurements of a floor
     */
    fun radioByCoordinatesFloor(
      coordinates_lat: String?, coordinates_lon: String?,
      floor: String?
    ): String {
      return radioByCoordinatesFloor(Companion.token, coordinates_lat, coordinates_lon, floor)
    }

    /**
     * Get a radio map in a floor with a range
     *
     * @param buid            The building ID
     * @param floor           The floor number
     * @param coordinates_lat The Latitude
     * @param coordinates_lon The Longitude
     * @param range           The desired range
     * @return JSON String with the radiomap measurements of the floor
     */
    fun radioByBuildingFloorRange(
      buid: String, floor: String, coordinates_lat: String, coordinates_lon: String,
      range: String
    ): String {
      val client = RestClient()
      setPath("/anyplace/position/radio_by_floor_bbox")
      val params: MutableMap<String, String> = HashMap()
      params["buid"] = buid
      params["floor_number"] = floor
      params["coordinates_lat"] = coordinates_lat
      params["coordinates_lon"] = coordinates_lon
      params["range"] = range
      return JsonHelper.jsonResponse(STATUS_OK, client.doPost(params, host, path))
    }

    /**
     * Radiomap using all the entries of an entire floor of a building. The
     * measurements are also stored locally for use by estimatePositionOffline
     *
     * @param access_token The access token(api key)
     * @param buid         The building ID
     * @param floor        The floor number
     * @return JSON String with the radio measurement of the floor
     */
    fun radioByBuildingFloor(access_token: String?, buid: String, floor: String): String {
      val client = RestClient()
      setPath("/anyplace/position/radio_by_building_floor")
      val params: MutableMap<String, String?> = HashMap()
      params["access_token"] = access_token
      params["buid"] = buid
      params["floor"] = floor
      val response = client.doPost(params, host, path)
      val obj: JSONObject
      val statusCode: Int
      try {
        obj = JSONObject(response)
        statusCode = obj.getInt("status_code")
      } catch (e: JSONException) {
        return JsonHelper.printError(e, "radioByBuildingFloor")
      }
      if (statusCode == 200) {
        val map_url_parameters: String
        map_url_parameters = try {
          obj.getString("map_url_parameters")
        } catch (e: JSONException) {
          return JsonHelper.printError(e, "radioByBuildingFloor")
        }
        val parameters = client.getFileWithPost(host, map_url_parameters)
        val map_url_mean: String
        map_url_mean = try {
          obj.getString("map_url_mean")
        } catch (e: JSONException) {
          return JsonHelper.printError(e, "radioByBuildingFloor")
        }
        val mean = client.getFileWithPost(host, map_url_mean)
        val map_url_weights: String
        map_url_weights = try {
          obj.getString("map_url_weights")
        } catch (e: JSONException) {
          return JsonHelper.printError(e, "radioByBuildingFloor")
        }
        val weights = client.getFileWithPost(host, map_url_weights)
        val temp = Companion.cache + buid + "/" + floor
        val dir = File(temp)
        val t = dir.mkdirs()
        if (!t) {
          return JsonHelper.printError(
            AnyplaceException("Couldn't create directory.", Exception()),
            "radioByBuildingFloor"
          )
        }
        val indoor_radiomap_parameters =
          Companion.cache + buid + "/" + floor + "/indoor_radiomap_parameters.txt"
        val indoor_radiomap_mean = Companion.cache + buid + "/" + floor + "/indoor_radiomap_mean.txt"
        val indoor_radiomap_weights =
          Companion.cache + buid + "/" + floor + "/indoor_radiomap_weights.txt"
        val f1 = File(indoor_radiomap_mean)
        val f2 = File(indoor_radiomap_parameters)
        val f3 = File(indoor_radiomap_weights)
        try {
          val t1 = f1.createNewFile()
          val t2 = f2.createNewFile()
          val t3 = f3.createNewFile()
          if (!t1 || !t2 || !t3) {
            return JsonHelper.printError(
              AnyplaceException("Couldn't create file.", Exception()),
              "radioByBuildingFloor"
            )
          }
        } catch (e1: IOException) {
          return JsonHelper.printError(e1, "radioByBuildingFloor")
        }
        try {
          var outputStream = FileOutputStream(indoor_radiomap_parameters)
          outputStream.write(parameters)
          outputStream.close()
          outputStream = FileOutputStream(indoor_radiomap_mean)
          outputStream.write(mean)
          outputStream.close()
          outputStream = FileOutputStream(indoor_radiomap_weights)
          outputStream.write(weights)
          outputStream.close()
        } catch (e: IOException) {
          return JsonHelper.printError(e, "radioByBuildingFloor")
        }
      } else {
        println("Bad response: $statusCode")
        println(response)
        return response
      }
      return JsonHelper.jsonResponse(STATUS_OK, response)
    }

    /**
     * Radiomap using all the entries of an entire floor of a building. The
     * measurements are also stored locally for use by estimatePositionOffline
     *
     * @param access_token The access token(api key)
     * @param buid         The building ID
     * @param floor        The floor number
     * @return JSON String with the radio measurement of the floor
     */
    fun radiomapMeanByBuildingFloor(access_token: String, buid: String, floor: String): String {
      val client = RestClient()
      var res: String? = "Error"
      setPath("/anyplace/position/radio_by_building_floor")
      val params: MutableMap<String, String> = HashMap()
      params["access_token"] = access_token
      params["buid"] = buid
      params["floor"] = floor
      val response = client.doPost(params, host, path)
      val obj: JSONObject
      val statusCode: Int
      try {
        obj = JSONObject(response)
        statusCode = obj.getInt("status_code")
      } catch (e: JSONException) {
        return JsonHelper.printError(e, "radioByBuildingFloor")
      }
      if (statusCode == 200) {
        val map_url_mean: String
        map_url_mean = try {
          obj.getString("map_url_mean")
        } catch (e: JSONException) {
          return JsonHelper.printError(e, "radioByBuildingFloor")
        }
        val mean = client.getFileWithPost(host, map_url_mean)
        res = String(mean)
      } else {
        println(response)
        return response
      }
      return res
    }

    /**
     * Radiomap using all the entries of an entire floor of a building. The
     * measurements are also stored locally for use by estimatePositionOffline
     *
     * @param buid         The building ID
     * @param floor        The floor number
     * @return JSON String with the radio measurement of the floor
     */
    fun radioByBuildingFloor(buid: String, floor: String): String {
      return radioByBuildingFloor(Companion.token, buid, floor)
    }

    /**
     * Get an estimation on the user's position based on the APs.
     *
     * @param buid      The building ID
     * @param floor     The floor number
     * @param aps       A table of bssid and rss fingerprints in the form of a JSON
     * @param algorithm The number of the desired algorithm
     * @return JSON String containing the lat and lon
     */
    fun estimatePosition(
      buid: String,
      floor: String,
      aps: Array<String?>,
      algorithm: String
    ): String {
      val client = RestClient()
      setPath("/anyplace/position/estimate_position")
      val params: MutableMap<String, String> = HashMap()
      params["buid"] = buid
      params["floor"] = floor
      val addb = "\\\"bssid\\\""
      val addr = "\\\"rss\\\""
      val addq = "\\\""
      val apBuilder = StringBuilder("[")
      for (s in aps) {
        var obj: JSONObject
        var bssid: String?
        var rss: Int
        try {
          obj = JSONObject(s)
          bssid = obj.getString("bssid")
          rss = obj.getInt("rss")
        } catch (e: JSONException) {
          return JsonHelper.printError(e, "estimatePosition")
        }
        apBuilder.append("{").append(addb).append(":").append(addq).append(bssid).append(addq)
            .append(",").append(addr).append(":").append(rss).append("},")
      }
      var ap = apBuilder.toString()
      ap = ap.substring(0, ap.length - 1) + "]"
      params["APs"] = ap
      params["algorithm_choice"] = algorithm
      return JsonHelper.jsonResponse(STATUS_OK, client.doPost(params, host, path))
    }

    /**
     * Get an estimation on the user's position based on the APs while offline.
     * Needs the radiomap to be stored locally.
     *
     * @param buid      The building ID
     * @param floor     The floor number
     * @param aps       A table of bssid and rss fingerprints in the form of a JSON
     * @param algorithm The number of the desired algorithm
     * @return String with the lat and lon
     */
    fun estimatePositionOffline(
      buid: String,
      floor: String,
      aps: Array<String?>,
      algorithm: String
    ): String {
      val list = ArrayList<LogRecord>()
      for (ap in aps) {
        var obj: JSONObject
        var bssid: String?
        var rss: Int
        try {
          obj = JSONObject(ap)
          bssid = obj.getString("bssid")
          rss = obj.getInt("rss")
        } catch (e: JSONException) {
          return JsonHelper.printError(e, "estimatePositionOffline")
        }
        list.add(LogRecord(bssid, rss))
      }
      val al = algorithm.toInt()
      val file = File(Companion.cache + buid + "/" + floor + "/indoor_radiomap_mean.txt")
      val radio: RadioMap
      radio = try {
        RadioMap(file)
      } catch (e: Exception) {
        return JsonHelper.printError(e, "estimatePositionOffline")
      }
      val response = Algorithms.ProcessingAlgorithms(list, radio, al)
      println(response)
      var coords = arrayOfNulls<String>(0)
      if (response != null) {
        coords = response.split(" ".toRegex()).toTypedArray()
      }
      val r = JSONObject()
      try {
        r.put("status", STATUS_OK)
        r.put("lon", coords[0])
        r.put("lat", coords[1])
      } catch (e: JSONException) {
        return JsonHelper.printError(e, "estimatePositionOffline")
      }
      return r.toString()
    }

    val host: String?
      get() = Companion.host
    val cache: String?
      get() = Companion.cache

    fun setHost(host: String) {
      this.host = host
    }

    val path: String?
      get() = Companion.path

    fun setPath(path: String) {
      this.path = path
    }

    var port: String?
      get() = Companion.port
      set(p) {
        Companion.port = p
      }
    val token: String?
      get() = Companion.token

    fun setToken(token: String) {
      this.token = token
    }

    const val STATUS_OK = 0
    const val STATUS_ERR = 1
    private val host: String? = null
    private val path: String? = null
    private var port: String? = null
    private var cache: String? = null
    private val token: String? = null
    private val os: OS? = null
    fun Initialize() {
      setCache(Preferences.getCache())
      setHost(Preferences.getHost())
      setPort(Preferences.getPort())
      setToken(Preferences.getApiKey())
    }

    private fun decodeToImage(imageString: String): BufferedImage? {
      var image: BufferedImage? = null
      val imageByte: ByteArray
      try {
        val decoder = Base64.getDecoder()
        imageByte = decoder.decode(imageString)
        val bis = ByteArrayInputStream(imageByte)
        image = ImageIO.read(bis)
        bis.close()
      } catch (e: Exception) {
        e.printStackTrace()
      }
      return image
    }
  }
}