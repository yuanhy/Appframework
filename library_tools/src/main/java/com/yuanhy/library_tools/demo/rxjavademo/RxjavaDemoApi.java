package com.yuanhy.library_tools.demo.rxjavademo;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface RxjavaDemoApi {

    @GET("/banner/json")
    Observable<PrBean> banner();

    @GET("/project/list/{index}/json")
    Observable<HttpRequest<ListItemEnty>> listItem(@Path( "index")int index,
    @Query("cid") int cid);
    @POST("/lg/collect/1165/json")
    Observable<PrBean> collect();
//    https://www.wanandroid.com/user/login
    @POST("/user/login")
Observable<String> user(@Query("username")String username,@Query("password")String password);
    @GET("/zksc/front/attence/syncStudentInfo.htm")
    Observable<SyncStudentInfoEnty2> syncStudentInfo(@QueryMap Map<String, String> map);
}
