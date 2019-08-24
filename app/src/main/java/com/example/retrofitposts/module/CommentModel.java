package com.example.retrofitposts.module;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.retrofitposts.ModelType;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommentModel implements Parcelable, ModelType {

    @SerializedName("postId")
    @Expose
    private String postId;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("body")
    @Expose
    private String body;

    public CommentModel(){}

    protected CommentModel(Parcel in) {
        postId = in.readString();
        id = in.readString();
        name = in.readString();
        email = in.readString();
        body = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(postId);
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(body);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CommentModel> CREATOR = new Creator<CommentModel>() {
        @Override
        public CommentModel createFromParcel(Parcel in) {
            return new CommentModel(in);
        }

        @Override
        public CommentModel[] newArray(int size) {
            return new CommentModel[size];
        }
    };

    public String getPostId() {
        return postId;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getBody() {
        return body;
    }


    @Override
    public int getType() {
        return ModelType.TYPE_COMMENT;
    }
}
