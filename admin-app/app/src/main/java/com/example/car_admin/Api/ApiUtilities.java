//package com.example.car_admin.Api;
//
//import static com.example.car_admin.Constants.BASE_URL;
//
//import retrofit2.Retrofit;
//
//public class ApiUtilities {
//
//    private static Retrofit retrofit = null;
//    public static ApiInterface getClient(){
//        if (retrofit==null)
//        {
//            retrofit=new Retrofit.Builder()
//                    .baseUrl(BASE_URL)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build();
//        }
//        return retrofit.create(ApiInterface.class);
//    }
//
//}
