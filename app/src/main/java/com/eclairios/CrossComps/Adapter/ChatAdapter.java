package com.eclairios.CrossComps.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eclairios.CrossComps.Model.Chat_Model;
import com.eclairios.CrossComps.R;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;
    String msg,pdf;
    public LayoutInflater inflater;
    public List<Chat_Model> message;
    public Context context;
    public ChatAdapter( Context ctx, List<Chat_Model> message) {
        this.inflater = LayoutInflater.from(ctx);
        this.message = message;
        this.context = ctx;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == MSG_TYPE_RIGHT){
            View view = inflater.inflate(R.layout.chat_item_right,parent,false);
            return new ViewHolder(view);
        }
        else{
            View view = inflater.inflate(R.layout.chat_item_left,parent,false);
            return new ViewHolder(view);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       holder.senderText.setText(message.get(position).getMessage());
    }
    @Override
    public int getItemCount() {
        return message.size();
    }
    public  class ViewHolder extends  RecyclerView.ViewHolder{
        TextView senderText, receiverText;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            senderText= itemView.findViewById(R.id.show_sender_msg);

        }
    }
    @Override
    public int getItemViewType(int position) {
        //  return super.getItemViewType(position);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String senderID = preferences.getString("CurrentUserId", "");
        if(message.get(position).getSenderID()!=null) {
            if (message.get(position).getSenderID().equals(senderID)){
                return MSG_TYPE_RIGHT;
            }else{
                return MSG_TYPE_LEFT;
            }
        }else{
            return MSG_TYPE_RIGHT;
        }
    }
}