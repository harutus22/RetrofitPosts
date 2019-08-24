package com.example.retrofitposts;

import android.content.DialogInterface;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.retrofitposts.fragment.CommentRVFragment;
import com.example.retrofitposts.fragment.PostRVFragment;
import com.example.retrofitposts.module.PostModel;
import com.example.retrofitposts.remote.ApiManager;
import com.example.retrofitposts.staticValues.StaticValues;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private FragmentTransaction fragmentTransaction;
    private ApiManager apiManager;
    private EditText userId;
    private PostRVFragment postRVFragment;

    private PostRVFragment.OnItemClick onItemClick = new PostRVFragment.OnItemClick() {
        @Override
        public void onItemClick(int position) {
            getCommentFragment(position);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiManager = ApiManager.getInstance();

        openPostFragment(-1);
    }

    private void openPostFragment(int number) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        postRVFragment = PostRVFragment.newInstance(number);
        postRVFragment.setOnItemClick(onItemClick);
        fragmentTransaction.replace(R.id.container, postRVFragment);
        fragmentTransaction.commit();
    }

    private void makePost() {
        Call<PostModel> call = apiManager.getService().sendPost(new PostModel("1", "101", "Whered you go?",
                "I miss you so, it seems to be forever when you been gone"));
        call.enqueue(new Callback<PostModel>() {
            @Override
            public void onResponse(Call<PostModel> call, Response<PostModel> response) {
                if (response.isSuccessful()) {
                    Log.d(StaticValues.TAG, "Post is successful");
                } else {
                    Log.d(StaticValues.TAG, "Post is not successful");
                }
            }

            @Override
            public void onFailure(Call<PostModel> call, Throwable t) {
                Log.d(StaticValues.TAG, "Post is failed");
            }
        });
    }

    private void getCommentFragment(int position) {
        CommentRVFragment commentRVFragment = CommentRVFragment.newInstance(position);
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, commentRVFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case StaticValues.ADD_POST_ID:
                makePost();
                break;

            case StaticValues.GET_ALL_COMMENTS_ID:
                getCommentFragment(StaticValues.GET_ALL_COMMENTS_ORDER);
                break;

            case StaticValues.GET_POST_BY_USER_ID:
                openDialog();
                break;

            case StaticValues.GET_ALL_POSTS:
                openPostFragment(-1);
        }
        return super.onOptionsItemSelected(item);
    }

        private void openDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View view = getLayoutInflater().inflate(R.layout.dialog_user_posts_choose, null);
            userId = view.findViewById(R.id.inputedUserId);
            builder.setView(view).
                    setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int id = Integer.valueOf(userId.getText().toString());
                            postRVFragment.openUserPosts(id);
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.create();
            builder.show();
        }
}
