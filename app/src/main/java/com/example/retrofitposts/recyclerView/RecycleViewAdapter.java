package com.example.retrofitposts.recyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.retrofitposts.ModelType;
import com.example.retrofitposts.R;
import com.example.retrofitposts.module.PostModel;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter {

    private List<ModelType> modelType = new ArrayList<>();
    private OnPostClicked onPostClicked;

    private RecyclePostViewHolder.OnClicked onClicked = new RecyclePostViewHolder.OnClicked() {
        @Override
        public void onPostClicked(int position) {
            if(onPostClicked != null){
                PostModel postModel = (PostModel) modelType.get(position);
                onPostClicked.onPostClicked(Integer.valueOf(postModel.getId()));
            }
        }
    };

    public RecycleViewAdapter(){
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        switch(i){
            case ModelType.TYPE_POST:
                view = LayoutInflater.from(viewGroup.getContext()).
                        inflate(R.layout.recycler_view_posts, viewGroup, false);
                RecyclePostViewHolder recyclePostViewHolder = new RecyclePostViewHolder(view);
                return recyclePostViewHolder;

            case ModelType.TYPE_COMMENT:
                view = LayoutInflater.from(viewGroup.getContext()).
                        inflate(R.layout.recycler_view_comments, viewGroup, false);
                RecycleCommentViewHolder recycleCommentViewHolder = new RecycleCommentViewHolder(view);
                return recycleCommentViewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        switch (getItemViewType(i)){
            case ModelType.TYPE_POST:
                ((RecyclePostViewHolder)viewHolder).bind(modelType.get(i));
                ((RecyclePostViewHolder) viewHolder).setOnClicked(onClicked);
                break;

            case ModelType.TYPE_COMMENT:
                ((RecycleCommentViewHolder)viewHolder).bind(modelType.get(i));
                break;
        }

    }

    @Override
    public int getItemCount() {
        if(modelType == null){
            return 0;
        } else {
            return modelType.size();
        }
    }

    public List<ModelType> getModelType() {
        return modelType;
    }

    @Override
    public int getItemViewType(int position) {
        return modelType.get(position).getType();
    }

    public interface OnPostClicked{
        void onPostClicked(int postId);
    }

    public void setOnPostClicked(OnPostClicked onPostClicked) {
        this.onPostClicked = onPostClicked;
    }
}
