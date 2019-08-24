package com.example.retrofitposts.recyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.retrofitposts.ModelType;
import com.example.retrofitposts.R;
import com.example.retrofitposts.module.PostModel;

public class RecyclePostViewHolder extends RecyclerView.ViewHolder {

    private TextView title;
    private TextView body;
    private OnClicked onClicked;

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(onClicked != null){
                onClicked.onPostClicked(getAdapterPosition());
            }
        }
    };

    public RecyclePostViewHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.postTitle);
        body = itemView.findViewById(R.id.postBody);
        itemView.setOnClickListener(onClickListener);
    }

    public void bind(ModelType post){
        title.setText(((PostModel)post).getTitle());
        body.setText(((PostModel)post).getBody());
    }

    interface OnClicked{
        void onPostClicked(int position);
    }

    public void setOnClicked(OnClicked onClicked) {
        this.onClicked = onClicked;
    }
}
