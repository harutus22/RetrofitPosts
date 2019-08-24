package com.example.retrofitposts.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.retrofitposts.R;
import com.example.retrofitposts.module.CommentModel;
import com.example.retrofitposts.recyclerView.RecycleViewAdapter;
import com.example.retrofitposts.remote.ApiManager;
import com.example.retrofitposts.staticValues.StaticValues;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentRVFragment extends Fragment {

    private RecycleViewAdapter recycleViewAdapter;
    private ApiManager apiManager;

    public static CommentRVFragment newInstance(int param1) {
        CommentRVFragment fragment = new CommentRVFragment();
        Bundle args = new Bundle();
        args.putInt(StaticValues.ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public CommentRVFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiManager = ApiManager.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comment_rv, container, false);
        initializeRecycleView(view);
        fillRecycle();
        return view;
    }

    private void initializeRecycleView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.commentRecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recycleViewAdapter = new RecycleViewAdapter();
        recyclerView.setAdapter(recycleViewAdapter);
    }

    private void fillRecycle(){
        int postId = getArguments().getInt(StaticValues.ARG_PARAM1);
        if(postId == StaticValues.GET_ALL_COMMENTS_ORDER){
            getAllComments();
        } else {
            getPostComments(postId);
        }

    }

    private void getPostComments(int postId) {
        Call<List<CommentModel>> call = apiManager.getService().getPostComments(postId);
        call.enqueue(new Callback<List<CommentModel>>() {
            @Override
            public void onResponse(Call<List<CommentModel>> call, Response<List<CommentModel>> response) {
                if(response.isSuccessful()){
                    addToRecycleView(response);
                    Log.d(StaticValues.TAG, StaticValues.COMMENT_IS_SUCCESSFUL);
                } else {
                    Log.d(StaticValues.TAG, StaticValues.COMMENT_IS_NOT_SUCCESSFUL);
                }
            }

            @Override
            public void onFailure(Call<List<CommentModel>> call, Throwable t) {
                Log.d(StaticValues.TAG, t.getMessage());
            }
        });
    }

    private void getAllComments() {
        Call<List<CommentModel>> call = apiManager.getService().getAllComments(1);
        call.enqueue(new Callback<List<CommentModel>>() {
            @Override
            public void onResponse(Call<List<CommentModel>> call, Response<List<CommentModel>> response) {
                if(response.isSuccessful()){
                    addToRecycleView(response);
                    Log.d(StaticValues.TAG, StaticValues.COMMENT_IS_SUCCESSFUL);
                } else {
                    Log.d(StaticValues.TAG, StaticValues.COMMENT_IS_NOT_SUCCESSFUL);
                }
            }

            @Override
            public void onFailure(Call<List<CommentModel>> call, Throwable t) {

            }
        });
    }

    private boolean checkIfRVEmpty(){
        return recycleViewAdapter.getModelType().isEmpty();
    }

    private void addToRecycleView(Response<List<CommentModel>> response){
        if(!checkIfRVEmpty()){
            recycleViewAdapter.getModelType().clear();
        }
        recycleViewAdapter.getModelType().addAll(response.body());
        recycleViewAdapter.notifyDataSetChanged();
    }
}
