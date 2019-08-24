package com.example.retrofitposts.staticValues;

import com.example.retrofitposts.R;

public class StaticValues {
    public static final String BASE_URL = "http://jsonplaceholder.typicode.com";
    public static final String POSTS_URL = "/posts";
    public static final String COMMENTS_URL = "posts/{id}/comments";
    public static final String POST_COMMENTS = "/comments";
    public static final String COMMENT_ID = "commentId";

    public static final String TAG = "TAG";

    public static final String POST_IS_SUCCESSFUL = "Post is successful";
    public static final String POST_IS_NOT_SUCCESSFUL = "Post is not successful";
    public static final String COMMENT_IS_SUCCESSFUL = "Comment is successful";
    public static final String COMMENT_IS_NOT_SUCCESSFUL = "Comment is not successful";

    public static final String ARG_PARAM1 = "param1";

    public static final int ADD_POST_ID = R.id.addPost;
    public static final int GET_ALL_COMMENTS_ID = R.id.getAllComments;
    public static final int GET_POST_BY_USER_ID = R.id.findPostByUser;
    public static final int GET_ALL_POSTS = R.id.allPosts;

    public static final int GET_ALL_COMMENTS_ORDER = -1;
    public static final int GET_USER_ALL_POSTS = -101;
    public static final String USER_ALL_POSTS_KEY = "all posts key";
}
