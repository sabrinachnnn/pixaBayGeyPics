package com.example.pixabaygeypics;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class postAdapter extends RecyclerView.Adapter<postAdapter.PostHolder> {

    Context context;
    List<Item> postList;

    public postAdapter(Context context, List<Item> postList){
        this.context = context;
        this.postList=postList;
    }
    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(context).inflate(R.layout.showimages, parent, false);
        return new PostHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
        Item item = postList.get(position);
        holder.setImageView(item.getImageURL());
        holder.setmLikes(item.getLikes());

    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public class PostHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView mLikes;
        View view;
        public PostHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
        }

        public void setImageView(String url){
            imageView = view.findViewById(R.id.imageview);
            Glide.with(context).load(url).into(imageView);
        }
        public void setmLikes(int likes){
            mLikes = view.findViewById(R.id.likes);
            mLikes.setText(likes+"Likes");
        }
    }
}
