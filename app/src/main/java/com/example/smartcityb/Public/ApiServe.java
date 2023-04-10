package com.example.smartcityb.Public;

import com.example.smartcityb.PublicBean.NewSortListBean;
import com.example.smartcityb.busBean.BusLineBean;
import com.example.smartcityb.cityBean.AllMetroLine;
import com.example.smartcityb.cityBean.CityLineBean;
import com.example.smartcityb.cityBean.MetroLineBean;
import com.example.smartcityb.cityBean.MetroLinePic;
import com.example.smartcityb.livePaymentBean.AccountListBean;
import com.example.smartcityb.livePaymentBean.LivingBillBean;
import com.example.smartcityb.livePaymentBean.LivingCategoryBean;
import com.example.smartcityb.livePaymentBean.UserInfoBean;
import com.example.smartcityb.parkBean.ParkBean;
import com.example.smartcityb.parkBean.ParkHistoryBean;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiServe {

    //1.1.1.用户登录
    @POST("prod-api/api/login")
    Call<ResponseBody> reqLogin(@Body RequestBody body);

    //1.5.1.获取新闻分类
    @GET("prod-api/press/category/list")
    Call<ResponseBody> getCategoryList();

    //1.6.1.查询引导页及主页轮播
    @GET("prod-api/api/rotation/list")
    Call<ResponseBody> getRotationList(@Query("type") String type);

    //1.9.1.获取系统全部服务
    @GET("prod-api/api/service/list")
    Call<ResponseBody> getServiceList();

    //1.9.1.获取系统全部服务
    @GET("prod-api/api/service/list")
    Call<ResponseBody> getServiceList(@Query("serviceType") String serviceType);

    //1.5.5.获取新闻列表
    @GET("prod-api/press/press/list")
    Call<ResponseBody> getPressList(@Query("type") String type);

    //1.5.5.获取新闻列表
    @GET("prod-api/press/press/list")
    Call<NewSortListBean> getPressListQuery(@Query("title") String title);

    //5.5.1查询职位列表
    @GET("prod-api/api/job/profession/list")
    Call<ResponseBody> getProfessionList(@Header("Authorization") String token);

    //5.2.2查询投递历史列表
    @GET("prod-api/api/job/deliver/list")
    Call<ResponseBody> getDeliverList(@Header("Authorization") String token);

    //9.5.1查询缴费分类
    @GET("prod-api/api/living/category/list")
    Call<LivingCategoryBean> getLivingCategory();


    //1.2.1.查询个人基本信息
    @GET("prod-api/api/common/user/getInfo")
    Call<UserInfoBean> getUserInfo(@Header("Authorization") String Token);


    //9.8.1根据身份证号查询缴费编号
    @GET("prod-api/api/living/account/list")
    Call<AccountListBean> getAccountList(@Header("Authorization") String Token ,@Query("categoryId") String id,@Query("idCard") String idCard);

    //9.7.1根据缴费编号查询缴费账单
    @GET("prod-api/api/living/bill")
    Call<LivingBillBean> getLivingBill(@Header("Authorization") String Token, @Query("paymentNo") String o1,@Query("categoryId") String o2);

    //2.2.3.查询停车记录列表
    @GET("prod-api/api/park/lot/list")
    Call<ParkBean> getParkList(@Query("pageNum") Integer pageNum, @Query("pageSize") Integer pageSize);


    //2.2.3.查询停车记录列表
    @GET("prod-api/api/park/lot/list")
    Call<ParkBean> getParkDetail(@Query("parkName") String parkName);

    //2.2.3.查询停车记录列表
    @GET("prod-api/api/park/lot/record/list")
    Call<ParkHistoryBean> getParkRecord(@Query("pageNum") Integer pageNum, @Query("pageSize") Integer pageSize);

    //12.2.1.查询路线列表
    @GET("prod-api/api/bus/line/list")
    Call<BusLineBean> getBusLine();

    //3.7.4.首页地铁站点查询
    @GET("prod-api/api/metro/list")
    Call<CityLineBean> getMetroList(@Query("currentName") String currentName);

    //3.7.6.查询地铁站详情
    @GET("prod-api/api/metro/line/{id}")
    Call<MetroLineBean> getMetroLine(@Path("id") Integer id);


    //3.7.3.查询城市所有线路
    @GET("prod-api/api/metro/line/list")
    Call<AllMetroLine> getMetroLineList();

    //3.7.1.获取城市铁总览图
    @GET("prod-api/api/metro/city")
    Call<MetroLinePic> getMetroCity();
}
