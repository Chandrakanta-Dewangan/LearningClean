package com.learning.data.core.network.api

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Headers

interface ClassifiedListingApi {
    @GET("waracle_cake-android-client")
    @Headers("Content-type: application/json")
    suspend fun getAllCakes(): Set<Result>
}

data class Result(
    @SerializedName("title") val title: String,
    @SerializedName("desc") val desc: String,
    @SerializedName("image") val image: String
)
/*
[
   {
      "title":"Lemon cheesecake",
      "desc":"A cheesecake made of lemon",
      "image":"https://s3-eu-west-1.amazonaws.com/s3.mediafileserver.co.uk/carnation/WebFiles/RecipeImages/lemoncheesecake_lg.jpg"
   },
   {
      "title":"victoria sponge",
      "desc":"sponge with jam",
      "image":"https://upload.wikimedia.org/wikipedia/commons/0/05/111rfyh.jpg"
   },
   {
      "title":"Carrot cake",
      "desc":"Bugs bunnys favourite",
      "image":"https://hips.hearstapps.com/del.h-cdn.co/assets/18/08/1519321610-carrot-cake-vertical.jpg"
   },
 */

