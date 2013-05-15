package com.antonfagerberg.instagram

import net.liftweb.json.DefaultFormats
import com.antonfagerberg.instagram.responses._

class Instagram(accessTokenOrClientId: Either[String, String], timeOut: Int = 10000) {
  implicit val formats = DefaultFormats

  val authentication = accessTokenOrClientId match {
    case Left(accessToken) => s"access_token=$accessToken"
    case Right(clientId) => s"client_id=$clientId"
  }

  /** Get basic information about a name.
    *
    * @param userId Id-number of the name to get information about.
    * @return       Response.
    */
  def userInfo(userId: String): Response[Profile] = {
    Request.getJson(s"https://api.instagram.com/v1/users/$userId/?$authentication", _.extract[Profile], timeOut)
  }

  /** Search for a name by name.
    *
    * @param name   Name of user.
    * @param count  Max number of results to return.
    * @param minId  Return media later than this.
    * @param maxId  Return media earlier than this.
    * @return       Response.
    */
  def userSearch(name: String, count: Option[Int] = None, minId: Option[String] = None, maxId:  Option[String] = None): Response[List[UserSearch]] = {
    Request.getJson(s"https://api.instagram.com/v1/users/search?$authentication&q=$name&count=${count.mkString}&min_id=${minId.mkString}&max_id=${maxId.mkString}", _.extract[List[UserSearch]], timeOut)
  }

  /** Get the list of users this user follows.
    *
    * @param userId Id-number of user.
    * @return       Response.
    */
  def follows(userId: String): Response[List[User]] = {
    Request.getJson(s"https://api.instagram.com/v1/users/$userId/follows?$authentication", _.extract[List[User]], timeOut)
  }

  /** Get the list of users this user is followed by.
    *
    * @param userId Id-number of user.
    * @return       Response.
    */
  def followedBy(userId: String): Response[List[User]] = {
    Request.getJson(s"https://api.instagram.com/v1/users/$userId/followed-by?$authentication", _.extract[List[User]], timeOut)
  }

  /** See the authenticated user's feed.
    *
    * @param count  Max number of results to return.
    * @param minId  Return media later than this.
    * @param maxId  Return media earlier than this.
    * @return       Response.
    */
  def feed(count: Option[Int] = None, minId: Option[String] = None, maxId:  Option[String] = None): Response[List[Media]] = {
    Request.getJson(s"https://api.instagram.com/v1/users/self/feed?$authentication&count=${count.mkString}&min_id=${minId.mkString}&max_id=${maxId.mkString}", _.extract[List[Media]], timeOut)
  }

  /** Get the most recent media published by a user.
    *
    * @param count        Max number of results to return.
    * @param minTimestamp Return media after this UNIX timestamp.
    * @param maxTimestamp Return media before this UNIX timestamp.
    * @param minId        Return media later than this.
    * @param maxId        Return media earlier than this.
    * @return             Response.
    */
  def mediaRecent(userId: String, count: Option[Int] = None, minTimestamp: Option[String] = None, maxTimestamp: Option[String] = None, minId: Option[String] = None, maxId: Option[String] = None): Response[List[Media]] = {
    Request.getJson(s"https://api.instagram.com/v1/users/$userId/media/recent/?$authentication&max_timestamp=${maxTimestamp.mkString}&min_timestamp=${minTimestamp.mkString}&min_id=${minId.mkString}&max_id=${maxId.mkString}", _.extract[List[Media]], timeOut)
  }

  /** See the authenticated user's list of liked media.
    *
    * @param count      Max number of results to return.
    * @param maxLikeId  Return media liked before this id.
    * @return           Response.
    */
  def liked(count: Option[Int] = None, maxLikeId: Option[String] = None): Response[List[Media]] = {
    Request.getJson(s"https://api.instagram.com/v1/users/self/media/liked?$authentication&count=${count.mkString}&max_like_id=${maxLikeId.mkString}", _.extract[List[Media]], timeOut)
  }

  /** Get information about a media object.
    *
    * @param mediaId  Id-number of media object.
    * @return         Response.
    */
  def media(mediaId: String): Response[Media] = {
    Request.getJson(s"https://api.instagram.com/v1/media/$mediaId?$authentication", _.extract[Media], timeOut)
  }

  /** Get a list of currently popular media.
    *
    * @return Response.
    */
  def popular: Response[List[Media]] = {
    Request.getJson(s"https://api.instagram.com/v1/media/popular?$authentication", _.extract[List[Media]], timeOut)
  }

  /** Get a full list of comments on a media.
    *
    * @param mediaId  Id-number of media object.
    * @return         Response.
    */
  def comments(mediaId: String): Response[List[Comment]] = {
    Request.getJson(s"https://api.instagram.com/v1/media/$mediaId/comments?$authentication", _.extract[List[Comment]], timeOut)
  }

  /** Get a list of users who have liked this media.
    *
    * @param mediaId  Id-number of media object.
    * @return         Response.
    */
  def likes(mediaId: String): Response[List[User]] = {
    Request.getJson(s"https://api.instagram.com/v1/media/$mediaId/likes?$authentication", _.extract[List[User]], timeOut)
  }

  /** Get information about a tag object.
    *
    * @param tag  Name of tag to search for.
    * @return     Response.
    */
  def tagInformation(tag: String): Response[Tag] = {
    Request.getJson(s"https://api.instagram.com/v1/tags/$tag?$authentication", _.extract[Tag], timeOut)
  }

  /** Get information about a tag object.
    *
    * @param tag  Name of tag to search for.
    * @return     Response.
    */
  def tagSearch(tag: String): Response[List[Tag]] = {
    Request.getJson(s"https://api.instagram.com/v1/tags/search?q=$tag&$authentication", _.extract[List[Tag]], timeOut)
  }

  /** Get a list of recently tagged media.
    *
    * @param tag      Name of tag to search for.
    * @param minTagId Return media later than this.
    * @param maxTagId Return media earlier than this.
    * @return         Response.
    */
  def tagRecent(tag: String, minTagId: Option[String] = None, maxTagId: Option[String] = None): Response[List[Media]] = {
    Request.getJson(s"https://api.instagram.com/v1/tags/$tag/media/recent?$authentication&min_tag_id=${minTagId.mkString}&max_tag_id=${maxTagId.mkString}", _.extract[List[Media]], timeOut)
  }

  /** Get information about a location.
    *
    * @param locationId Id-number of location.
    * @return           Response.
    */
  def location(locationId: String): Response[Location] = {
    Request.getJson(s"https://api.instagram.com/v1/locations/$locationId?$authentication", _.extract[Location], timeOut)
  }

  /** Get a list of media objects from a given location.
    *
    * @param locationId   Id-number of location.
    * @param minTimestamp Return media after this UNIX timestamp.
    * @param maxTimestamp Return media before this UNIX timestamp.
    * @param minId        Return media later than this.
    * @param maxId        Return media earlier than this.
    * @return             Response.
    */
  def locationMedia(locationId: String, minTimestamp: Option[String] = None, maxTimestamp: Option[String] = None, minId: Option[String] = None, maxId: Option[String] = None): Response[List[Media]] = {
    Request.getJson(s"https://api.instagram.com/v1/locations/$locationId/media/recent?$authentication&min_timestamp=${minTimestamp.mkString}&max_timestamp=${maxTimestamp.mkString}&min_id=${minId.mkString}&max_id=${maxId.mkString}", _.extract[List[Media]], timeOut)
  }

  /** Search for a location by geographic coordinate.
    *
    * @param coordinates    Latitude & Longitude coordinates.
    * @param distance       Default is 1000m (distance=1000), max distance is 5000.
    * @param foursquareV2Id Returns a location mapped off of a foursquare v2 api location id. If used, you are not required to use lat and lng.
    * @return
    */
  def locationSearch(coordinates: Option[(String, String)], distance: Option[Int] = None, foursquareV2Id: Option[String] = None): Response[List[Location]] = {
    val latitudeLongitude =
      if (coordinates.isDefined) s"&lat=${coordinates.get._1}&lng=${coordinates.get._2}"
      else ""

    Request.getJson(s"https://api.instagram.com/v1/locations/search?$authentication&foursquare_v2_id=${foursquareV2Id.mkString}$latitudeLongitude", _.extract[List[Location]], timeOut)
  }

}