package com.example.whatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

public class WhatsAppUsers extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ListView listView;
    private ArrayList<String> wUsers;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whats_app_users);


        listView = findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
        wUsers = new ArrayList<>();
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, wUsers);
        final SwipeRefreshLayout mySwipeRefreshLayout = findViewById(R.id.swipeContainer);


        try {
            ParseQuery<ParseUser> query = ParseUser.getQuery();
            query.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
            query.findInBackground(new FindCallback<ParseUser>() {
                @Override
                public void done(List<ParseUser> objects, ParseException e) {
                    if (objects.size() > 0 && e == null) {
                        for (ParseUser whatsappUser: objects) {
                            wUsers.add(whatsappUser.getUsername());

                        }listView.setAdapter(adapter);

                    }

                }
            });

        }catch (Exception e){
            e.printStackTrace();

    }

    mySwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            try {
                ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();
                parseQuery.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
                parseQuery.whereNotContainedIn("username",wUsers);
                parseQuery.findInBackground(new FindCallback<ParseUser>() {
                    @Override
                    public void done(List<ParseUser> objects, ParseException e) {
                        if (objects.size() > 0 ) {
                            if ( e == null) {
                                for (ParseUser user : objects){
                                    wUsers.add(user.getUsername());
                                }
                            }adapter.notifyDataSetChanged();
                            if (mySwipeRefreshLayout.isRefreshing()){
                                mySwipeRefreshLayout.setRefreshing(false);
                            }
                        }else {
                            if (mySwipeRefreshLayout.isRefreshing()) {
                                mySwipeRefreshLayout.setRefreshing(false);
                    }
                        }
                    }
                });

            }catch (Exception e ) {
                e.printStackTrace();
            }
        }
    });}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.logoutUserItem:
                FancyToast.makeText(WhatsAppUsers.this,ParseUser.getCurrentUser() + " is loged out", Toast.LENGTH_SHORT,FancyToast.WARNING,true).show();
                ParseUser.getCurrentUser().logOutInBackground(new LogOutCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                        Intent intent = new Intent(WhatsAppUsers.this, LogIn.class);
                        startActivity(intent);
                        finish();}
                    }
                });

        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(WhatsAppUsers.this,WhatsAppChat.class);
        intent.putExtra("selectedUser",wUsers.get(position));
        startActivity(intent);
        }
    }
