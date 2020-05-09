package com.insta.parseinstagram.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.insta.parseinstagram.Post;
import com.insta.parseinstagram.PostAdapter;
import com.insta.parseinstagram.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostFragment extends Fragment {


    public static final String TAG = "PostsFragment";
    private RecyclerView rvPosts;
    protected PostAdapter adapter;
    protected List<Post> allPosts;
    SwipeRefreshLayout swipeContainer;

    public PostFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvPosts = view.findViewById(R.id.rvPosts);
        allPosts = new ArrayList<>();
        swipeContainer = view.findViewById(R.id.swipeContainer);

        // Configure refreshing colors
        swipeContainer.setColorSchemeColors(getResources().getColor(android.R.color.darker_gray),
                getResources().getColor(android.R.color.holo_purple),
                getResources().getColor(android.R.color.holo_orange_light),
                getResources().getColor(android.R.color.holo_red_dark));

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {

                Log.i(TAG, "fetching new data!");
                queryPosts();
            }
        });

        // Create  the adapter
        adapter = new PostAdapter(getContext(), allPosts);

        // Create the  data source
        // Set the adapter on the recycler view
        rvPosts.setAdapter(adapter);

        // Set the layout manager on the recycler view
        rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));
        queryPosts();
    }

    protected void queryPosts() {
        ParseQuery<Post> postQuery = ParseQuery.getQuery(Post.class);
        postQuery.include(Post.KEY_USER);
        postQuery.setLimit(20);
        postQuery.addDescendingOrder(Post.KEY_CREATED_KEY);

        postQuery.findInBackground(new FindCallback<Post>() {

            @Override
            public void done(List<Post> posts, ParseException e) {

                if(e != null) {
                    Log.e(TAG, "Error with query");
                    e.printStackTrace();
                    return;
                }
                for(int i = 0; i < posts.size(); i++) {
                    Post post = posts.get(i);
                    Log.d(TAG, "Post " + post.getDescription() + " Username: " + post.getUser().getUsername());
                }
                allPosts.clear();
                allPosts.addAll(posts);

                // Signal refresh has finished
                swipeContainer.setRefreshing(false);

                adapter.notifyDataSetChanged();
            }
        });
    }
}
