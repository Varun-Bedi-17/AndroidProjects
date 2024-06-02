package com.example.retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.retrofit.modals.TokenModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val quotesAPI = RetrofitHelper.getInstance().create(QuotesAPI::class.java)
        var r:Response<AccessTokenResponse>
        GlobalScope.launch {
            r = quotesAPI.refreshAccessToken(refreshToken = "AQClhj6uXpp7OhP-sePrlZMjPAchACs46SgmhCCmJRTOod2WzQezD_zLC19cDao_cbWb5clpoCcIMH-nasBB_58ZcrOPUaOVXCewfbQO_2fH_977GEjFJVfIS0o_hATPsSWXPlwXf0kx4KOd1vwYE4dttijHYcbgYXF_njPa5lHxuAK7TceCucbIY5x0NjbtfY0hOc3Mr9I3lANcB2H3eNjZlhZs7cX9zGEmqZxpviw_oWUgvDfkPs3nR7ii3xIUuraTaj-hhG_brsr3agcB45gaoNcScOuvAYKZ8RReqealC3i8maz6rzmNtSvIXoUoAX5FfoI9Lu7iG7yfuXOQz_uEHxZbffEHmcfdmEiBqj40Bgoy7Ux2Zfz8VR2qcDJvTmKmkdhj68o6t4GfRvbt3TPcfzG3tyeZUxdMxHJEy1JnuldZBUThVSAg6dzki-jys1OEisip7-vKGZC_7utqEx3O0aXSq1QpniTLO0GQbI0hGA1mxGn2IctaMsJA85X4DiOF53iYOiO1ic-OFjqnM10pwnoZV2BWu9KA3PEaId2Y4ITQ2cflK8Ijm8VjVLsIwjDikrn6nYVQn6UuPCo_XMNNqiaGB0tASx2ZUQ9qy8PyY_L57W_TPWr8Pg8wWmCT0aVioQP3C7CpOmBD90wg-NNQV0_4TTGAdoLMoapX1FNrTmP2ZHJVlUrC5_B68hQydP1hfV_VoP1Nsf_TMmkWuMiI7boK2TiI_xTo2nFl1-Y")
            Log.d("fcbih", r.code().toString())
        }



//        r.enqueue(object : Callback<AccessTokenResponse> {
//            override fun onResponse(
//                call: Call<AccessTokenResponse>,
//                response: Response<AccessTokenResponse>
//            ) {
//                Log.d("fcd",response.code().toString())
//                if (response.isSuccessful) {
//                    val accessTokenResponse = response.body()
//                    val accessToken = accessTokenResponse?.accessToken
//                    Log.d("Quotes Fetch", accessToken.toString())
//
//                    // Use the new access token to make requests to the Spotify API
//                } else {
//                    throw RuntimeException("Failed to refresh access token")
//                }
//            }
//
//            override fun onFailure(call: Call<AccessTokenResponse>, t: Throwable) {
//                throw RuntimeException("Failed to refresh access token")            }
//
//        })

//        GlobalScope.launch {
//            Log.d("ahshijs","sduhcuhsd")
//
//            val result = quotesAPI.getAccessToken("client_credentials")
////            if (result.code() == 404){
////                Log.d("ahshijs","hellosrun")
////            }
////            Log.d("Quotes Fetch" , result.body().toString())
//            result.enqueue(object:Callback<TokenModel>{
//                override fun onResponse(call: Call<TokenModel>, response: Response<TokenModel>) {
//                    if (response.isSuccessful) {
//                        val accessTokenResponse = response.body()
//                        val newAccessToken = accessTokenResponse?.accessToken
//                        Log.d("ash", newAccessToken.toString())
//                        // Save the new access token for future use
//                    } else {
//                        throw RuntimeException("Failed to refresh access token")
//                    }
//                }
//
//                override fun onFailure(call: Call<TokenModel>, t: Throwable) {
//                    throw RuntimeException("Failed to refresh access token")
//                }
//
//            })
//        }



//
//        val spotifyApiService = Help.retrofit.create(QuotesAPI::class.java)

//        val call = spotifyApiService.refreshAccessToken(refreshToken = "<your_refresh_token>")
//
//        call.enqueue(object : Callback<AccessTokenResponse> {
//            override fun onResponse(
//                call: Call<AccessTokenResponse>,
//                response: Response<AccessTokenResponse>
//            ) {
//                if (response.isSuccessful) {
//                    val accessTokenResponse = response.body()
//                    val accessToken = accessTokenResponse?.accessToken
//                    Log.d("Quotes Fetch", accessToken.toString())
//
//                    // Use the new access token to make requests to the Spotify API
//                } else {
//                    throw RuntimeException("Failed to refresh access token")
//                }
//            }
//
//            override fun onFailure(call: Call<AccessTokenResponse>, t: Throwable) {
//                // Handle failure
//            }
//
//        })
    }
}