package com.appybuilder.alioffical.myolxtypeapp.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appybuilder.alioffical.myolxtypeapp.Adapters.ChatAdapter;
import com.appybuilder.alioffical.myolxtypeapp.Models.ChatModel;
import com.appybuilder.alioffical.myolxtypeapp.Models.ConstantModel;
import com.appybuilder.alioffical.myolxtypeapp.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SellFragment extends Fragment {
    RecyclerView recyclerViewChat;
    View view;
    List<ChatModel> modelList = new ArrayList<ChatModel>();
    ChatAdapter chatAdapter;
    ChatModel chatModel;
    LinearLayoutManager layoutManager;
    private static final String SHARED_PREFS_FILE = "MyChatPreference";
    SharedPreferences sharedPreferences;
    List<ChatModel> chatString = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sell, container, false);
        init_views();
        return view;
    }

    public void init_views() {

        recyclerViewChat = view.findViewById(R.id.chat_recler);

        sharedPreferences = getContext().getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE);
        ConstantModel.strKEY=sharedPreferences.getString("KEY","");
        loadData();
    }

    private void loadData() {
        Gson gson = new Gson();
        String json = sharedPreferences.getString(ConstantModel.strKEY, "");
        if (json.isEmpty()) {
            chatString = new ArrayList<ChatModel>();
        } else {
            Type type = new TypeToken<List<ChatModel>>() {
            }.getType();
            chatString = gson.fromJson(json, type);
        }
        chatAdapter = new ChatAdapter(chatString, getContext());
        recyclerViewChat.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewChat.setAdapter(chatAdapter);
        chatAdapter.notifyDataSetChanged();
    }
}