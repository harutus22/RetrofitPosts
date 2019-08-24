package com.example.retrofitposts.recyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.retrofitposts.ModelType;
import com.example.retrofitposts.R;
import com.example.retrofitposts.module.CommentModel;

public class RecycleCommentViewHolder extends RecyclerView.ViewHolder {

    private TextView name;
    private TextView email;
    private TextView body;


    public RecycleCommentViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.commentName);
        email = itemView.findViewById(R.id.commentUserEmail);
        body = itemView.findViewById(R.id.commentBody);
    }

    public void bind(ModelType post){
        name.setText(((CommentModel)post).getName());
        email.setText(((CommentModel)post).getEmail());
        body.setText(((CommentModel)post).getBody());
    }
}
