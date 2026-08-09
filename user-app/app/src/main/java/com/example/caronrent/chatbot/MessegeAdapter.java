// MessegeAdapter.java
package com.example.caronrent.chatbot;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caronrent.R;

import java.util.List;

public class MessegeAdapter extends RecyclerView.Adapter<MessegeAdapter.MyViewHolder>{

    List<Messege> messegeList;
    private int longPressedItemPosition = -1;

    public MessegeAdapter(List<Messege> messegeList) {
        this.messegeList = messegeList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View chatView = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item,null);
        MyViewHolder myViewHolder = new MyViewHolder(chatView);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Messege messege = messegeList.get(position);
        if(messege.getSentby().equalsIgnoreCase(Messege.SENT_BY_ME))
        {
            holder.leftView.setVisibility(View.GONE);
            holder.rightView.setVisibility(View.VISIBLE);
            holder.right.setText(messege.getMsg());
        }
        else
        {
            holder.rightView.setVisibility(View.GONE);
            holder.leftView.setVisibility(View.VISIBLE);
            holder.left.setText(messege.getMsg());
        }
    }

    @Override
    public int getItemCount() {
        return messegeList.size();
    }

    public int getLongPressedItemPosition() {
        return longPressedItemPosition;
    }

    public void setLongPressedItemPosition(int position) {
        longPressedItemPosition = position;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

        LinearLayout leftView,rightView;
        TextView left,right;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            leftView=itemView.findViewById(R.id.leftView);
            rightView=itemView.findViewById(R.id.rightView);
            left=itemView.findViewById(R.id.left);
            right=itemView.findViewById(R.id.right);

            // Set long click listener on the itemView
            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View v) {
            // Get the position of the long-pressed item
            int position = getAdapterPosition();
            // Set the longPressedItemPosition to this position
            setLongPressedItemPosition(position);
            // Return false to allow the event to be propagated to other listeners
            return false;
        }
    }
}
