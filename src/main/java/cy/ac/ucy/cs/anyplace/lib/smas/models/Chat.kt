package cy.ac.ucy.cs.anyplace.lib.smas.models

import com.google.gson.annotations.SerializedName
import cy.ac.ucy.cs.anyplace.lib.anyplace.models.UserCoordinates
import cy.ac.ucy.cs.anyplace.lib.smas.models.CONSTchatMsg.TP_GET_ALL

/** A Single [ChatMsg] */
data class ChatMsg(
        @SerializedName("mid")
        val mid: String,

        /** From [SmasUser] */
        @SerializedName("uid")
        val uid: String,

        /** Delivery outreach:
         * - 1: delivered to all
         * - 2: on same deck
         * - 3: KNN
         * - 4: bounding box on location
         */
        @SerializedName("mdelivery")
        val mdelivery: Int,

        @SerializedName("mtype")
        val mtype: Int,
        @SerializedName("msg")
        val msg: String?,

        /** Extension in case of image types */
        @SerializedName("mexten")
        val mexten: String,

        /** Server timestamp */
        @SerializedName("time")
        val time: Long,
        /** Server pretty time */
        @SerializedName("timestr")
        val timestr: String,

        // Location
        /** Latitude */
        @SerializedName("x")
        val x: Double,
        /** Longitude */
        @SerializedName("y")
        val y: Double,
        //// Space
        @SerializedName("buid")
        val buid: String,
        @SerializedName("deck")
        val deck: Int,
)

/**
 * [ChatMsg] Response
 */
data class ChatMsgsResp(
        @SerializedName("status")
        val status: String?,
        @SerializedName("descr")
        val descr: String?,
        @SerializedName("uid")
        val uid: String?,

        /** List of messages received*/
        @SerializedName("rows")
        val msgs: List<ChatMsg>,
)

data class MsgGetReq(
        val uid: String,
        val sessionkey: String,
        val mgettype: Int = TP_GET_ALL,
        val from: String? ="") {
  constructor(user: SmasUser, mgettype: Int = TP_GET_ALL, from: String?=null)
          : this(user.uid, user.sessionkey, mgettype, from)
}

/**
 * Message Send Request
 */
data class MsgSendReq(
        // [ChatUser]
        @SerializedName("uid")
        val uid: String,
        @SerializedName("sessionkey")
        val sessionkey: String,

        @SerializedName("buid")
        val buid: String,
        @SerializedName("deck")
        val deck: Int,
        @SerializedName("mdelivery")
        val mdelivery: String,
        @SerializedName("msg")
        val msg: String?,
        @SerializedName("mtype")
        val mtype: Int,
        @SerializedName("mexten")
        val mexten: String?,
        @SerializedName("time")
        val time: String,

        @SerializedName("x")
        val x: Double,
        @SerializedName("y")
        val y: Double
){
  constructor(user: SmasUser, userCoords: UserCoordinates, mdelivery: String, msg: String?, mtype: Int, mexten: String?, time: String) :
          this(user.uid, user.sessionkey, userCoords.buid, userCoords.level, mdelivery, msg, mtype, mexten, time, userCoords.lat, userCoords.lon)
}

/**
 * Response for [MsgSendReq]
 */
data class MsgSendResp(
        @SerializedName("status")
        val status: String,
        @SerializedName("descr")
        val descr: String?,
        @SerializedName("uid")
        val uid: String,
        /** How many users it has reached */
        @SerializedName("rows")
        val deliveredTo: Int?,
        /** The level (deck or floor) this was delivered to */
        @SerializedName("deck")
        val level: Int?
)

/**
 * Used for ReplyTo's (which are not fully implemented)
 */
data class ReplyToMessage(
        val sender : String,
        val message : String?,
        val attachment : String?
)


object CONSTchatMsg {
  /** Get all messages */
  const val TP_GET_ALL=0
  /** Get messages from a particular timestamp onwards */
  const val TP_GET_FROM=3

  const val TP_SEND_TXT = 1
  const val TP_SEND_IMG= 2
  const val TP_SEND_LOCATION= 3
  const val TP_SEND_4= 4 // TODO:DZ was alert. now unused.

  const val STP_TXT = "txt"
  const val STP_IMG= "img"
  const val STP_LOCATION= "loc" // TODO Alert is better as a flag.
  const val STP_TP4= "tp4"
}
