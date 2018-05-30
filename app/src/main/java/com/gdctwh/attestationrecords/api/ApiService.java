package com.gdctwh.attestationrecords.api;

import com.gdctwh.attestationrecords.bean.CommentAndtCollenctBean;
import com.gdctwh.attestationrecords.bean.NewsBean;
import com.gdctwh.attestationrecords.bean.NewsDetailsBean;
import com.gdctwh.attestationrecords.bean.HotNewsBean;
import com.gdctwh.attestationrecords.bean.HotNewsDetailsBean;
import com.gdctwh.attestationrecords.bean.LogOutBean;
import com.gdctwh.attestationrecords.bean.LoginBean;
import com.gdctwh.attestationrecords.bean.UserInfoBean;


import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2018/3/30.
 */

public interface ApiService {
    /**
     * 用户登录
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("api/member.php")
    Observable<LoginBean> login(@FieldMap Map<String,String> params);

    /**
     * 获取用户信息
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("api/member.php")
    Observable<UserInfoBean> getUerInfo(@FieldMap Map<String,String> params);

    /**
     * 退出登录
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("api/member.php")
    Observable<LogOutBean> logout(@FieldMap Map<String,String> params);


    /**
     * 热点新闻列表
     * @param page
     * @return
     */
    @GET("{page}")
    Observable<HotNewsBean> hotNews(@Path("page") String page);

    /**
     * 热点新闻详情
     * @param
     * @return
     */
    @GET("content/{yearAndMonth}/{day}/{page}")
    Observable<HotNewsDetailsBean> hotNewsDetails(@Path("yearAndMonth") String yearAndMoth
            ,@Path("day") String day,@Path("page")String page);


    /**
     * 视频
     */
    @GET("list9_1.json")
    Observable<String> video();

    /**
     * 文章评论和收藏
     * @param act 动作  get_pl:获取评论列表    get_pl_info :评论数量   get_sc:收藏列表  get_sc_info:收藏数量
     * @param id
     * @return
     */
    @GET("api/article.php")
    Observable<CommentAndtCollenctBean> commentAndCollect(@Query("act")String act, @Query("art_id") String id);


    /**
     * 资讯接口
     * @param category_id 1:滚动新闻  2：创图资讯  3：文化动态   4：艺术品鉴证  5：收藏拍卖
     * @return
     */
    @GET("index.php?route=api/newsblog")
    Observable<NewsBean> news(@Query("category_id") int category_id );

    /**
     * 资讯详情
     * @param article_id
     * @return
     */
    @GET("index.php?route=api/newsblog/article")
    Observable<NewsDetailsBean> cultureDetails(@Query("article_id") String article_id);

}
