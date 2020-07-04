package com.example.chatapp.Fragments;

import com.example.chatapp.Notification.MyResponse;
import com.example.chatapp.Notification.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAI2J25fA:APA91bFnM-cVIVNP-s1db1g7qyUSUPHaRvD5TXBuSvKRXTx118HeMlnRJONXHSMfY0sTbmUUIOqVjAYG_ga87SVGU3HhkLN9scMVRXopFNhO7nUlSOEU0_r4pUwbvExHkPuKZ7ZmaMeq"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
