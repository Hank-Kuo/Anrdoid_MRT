package com.example.guozhenyuan.myapplication;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter  extends BaseAdapter{

    private Context context;
    private ArrayList<Chatmessage> chatlist;

    public ChatAdapter(Context context, ArrayList<Chatmessage> chatlist) {
        this.context = context;
        this.chatlist = chatlist;
    }


    @Override
    public int getCount() {
        return chatlist.size();
    }

    @Override
    public Object getItem(int position) {
        return chatlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=View.inflate(context,R.layout.list_item,null);
        TextView name=(TextView)convertView.findViewById(R.id.message_user);
        TextView message=(TextView)convertView.findViewById(R.id.message_text);
        TextView time=(TextView)convertView.findViewById(R.id.message_time);
        name.setText(chatlist.get(position).getMessageUser());
        message.setText(chatlist.get(position).getMessageText());
        time.setText((chatlist.get(position).getMessageTime()));
        return convertView;
    }
}
