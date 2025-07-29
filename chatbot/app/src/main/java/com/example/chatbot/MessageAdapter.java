package com.example.chatbot;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.nio.file.attribute.FileTime;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {


    List<MessageModel> messageModelList;
    public MessageAdapter(List<MessageModel> messageModelList){
        this.messageModelList=messageModelList;
    }

    @NonNull
    @Override
    public MessageAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View chatView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,null);
        MyViewHolder myViewHolder=new MyViewHolder(chatView);
        return myViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.MyViewHolder holder, int position) {
        MessageModel messageModel = messageModelList.get(position);

        // First, hide both views (reset)
        holder.leftChatView.setVisibility(View.GONE);
        holder.rightChatView.setVisibility(View.GONE);

        if (messageModel.getSentBy().equals(MessageModel.sent_by_me)) {
            // Show user's message on the right
            holder.leftChatView.setVisibility(View.VISIBLE);
            holder.leftTextView.setText(messageModel.getMessage());
        } else {
            // Show bot's message on the left
            holder.rightChatView.setVisibility(View.VISIBLE);
            holder.rightTextView.setText(messageModel.getMessage());

        }
    }



    @Override
    public int getItemCount() {
        return messageModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout leftChatView,rightChatView;
        TextView leftTextView,rightTextView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            leftChatView=itemView.findViewById(R.id.left_chat_view);
            rightChatView=itemView.findViewById(R.id.right_chat_view);
            leftTextView=itemView.findViewById(R.id.left_text_view);
            rightTextView=itemView.findViewById(R.id.right_text_view);


        }
    }
}
