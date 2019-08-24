package com.example.retrofitposts.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.retrofitposts.ModelType;
import com.example.retrofitposts.R;
import com.example.retrofitposts.module.PostModel;
import com.example.retrofitposts.recyclerView.RecycleViewAdapter;
import com.example.retrofitposts.remote.ApiManager;
import com.example.retrofitposts.staticValues.StaticValues;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostRVFragment extends Fragment {

    private RecycleViewAdapter recycleViewAdapter;
    private ApiManager apiManager;
    private OnItemClick onItemClick;
    private List<ModelType> model= new ArrayList<>();

    private RecycleViewAdapter.OnPostClicked onPostClicked = new RecycleViewAdapter.OnPostClicked() {
        @Override
        public void onPostClicked(int postId) {
            if(onItemClick != null){
                onItemClick.onItemClick(postId);
            }
        }
    };

    public static PostRVFragment newInstance(int param1) {
        PostRVFragment fragment = new PostRVFragment();
        Bundle args = new Bundle();
        args.putInt(StaticValues.USER_ALL_POSTS_KEY, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public PostRVFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_rv, container, false);
        initializeRecycleView(view);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiManager = ApiManager.getInstance();
        int id = getArguments().getInt(StaticValues.USER_ALL_POSTS_KEY);
        if(id == StaticValues.GET_USER_ALL_POSTS){
            fillRecycle(id);
        } else {
            fillRecycle(-1);
        }
    }

    private void fillRecycle(int number) {
        Call<List<PostModel>> listCall;
        if(number >= 0 && number <= 10){
            listCall = apiManager.getService().getUserPosts(number);
        } else {
            listCall = apiManager.getService().getPosts();
        }
        listCall.enqueue(new Callback<List<PostModel>>() {
            @Override
            public void onResponse(Call<List<PostModel>> call, Response<List<PostModel>> response) {
                if(response.isSuccessful()){
                    addItems(response);
                } else {
                    Log.d(StaticValues.TAG, StaticValues.POST_IS_NOT_SUCCESSFUL);
                }
            }

            @Override
            public void onFailure(Call<List<PostModel>> call, Throwable t) {
                Log.d(StaticValues.TAG, t.getMessage());
            }
        });
    }

    private void addItems(Response<List<PostModel>> response){
        if(!isRecycleEmpty()){
            recycleViewAdapter.getModelType().clear();
        }
        if(model.isEmpty()){
            model.addAll(response.body());
        }
        recycleViewAdapter.getModelType().addAll(response.body());
        recycleViewAdapter.notifyDataSetChanged();
        Log.d(StaticValues.TAG, StaticValues.POST_IS_SUCCESSFUL);
    }

    private void initializeRecycleView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.postRecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recycleViewAdapter = new RecycleViewAdapter();
        recycleViewAdapter.setOnPostClicked(onPostClicked);
        recyclerView.setAdapter(recycleViewAdapter);
    }

    public interface OnItemClick{
        void onItemClick(int position);
    }

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(isRecycleEmpty()) {
            recycleViewAdapter.getModelType().addAll(model);
        }
    }

    private boolean isRecycleEmpty(){
        return recycleViewAdapter.getModelType().isEmpty();
    }

    public void openUserPosts(int userId){
        fillRecycle(userId);
    }
}
