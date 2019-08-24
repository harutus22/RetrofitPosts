package com.example.retrofitposts.remote;

import com.example.retrofitposts.module.CommentModel;
import com.example.retrofitposts.module.PostModel;
import com.example.retrofitposts.staticValues.StaticValues;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ServiceApi {

    @GET(StaticValues.COMMENTS_URL)
    Call<List<CommentModel>> getAllComments(@Path("id") int id);

    @GET(StaticValues.POST_COMMENTS)
    Call<List<CommentModel>> getPostComments(@Query("postId") int id);

    @GET(StaticValues.POSTS_URL)
    Call<List<PostModel>> getPosts();

    @GET("posts")
    Call<ArrayList<PostModel>> getDataMap(@QueryMap Map<String, String> params);

    @GET(StaticValues.POSTS_URL)
    Call<List<PostModel>> getUserPosts(@Query("userId") int userId);

    @POST("/posts")
    Call<PostModel> sendPost(@Body PostModel postModel);
}
