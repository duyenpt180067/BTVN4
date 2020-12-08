package com.example.btvn4;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;


import com.example.btvn4.adapter.EmailAdapter;
import com.example.btvn4.model.Email;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String [] listName = {"Edurila.com","Chris Abad", "Tuto.com", "suport", "Matt from lonic", "Google", "Shopee","Adobe", "Dell", "Adobe Creative Cloud"};
    String [] listTime = {"12:34 PM","11:22 AM", "11:04 AM", "10:26 AM", "02:10 PM","03:26 PM", "01:11 AM", "05:27 PM", "08:36 AM","04:26 PM"};
    String [] listContent = {"$19 Only(First 10 spots)-Best selling...","Help make Campaign Monitor better...","8h de formation gratuite et les nouvea...",
            "Societe Ovh: suivi de vos services - hp...","The New lonic Creator Is Here!","$19 Only(First 10 spots)-Best selling...","Help make Campaign Monitor better",
            "8h de formation gratuite et les nouvea...","Societe Ovh: suivi de vos services - hp...","The New lonic Creator Is Here!"};
    String [] listTitleContent = {"Are you looking to Learn Web Designin...","Let us know your thoughts! No Images...","Photoshop, SEO,, Blender, CSS, WordPre...",
            "SAS OVH - http://www.ovh.com 2 rue K...","Announcing the all-new Creator, build ...","Are you looking to Learn Web Designin...","Let us know your thoughts! No Images...",
            "Photoshop, SEO,, Blender, CSS, WordPre...", "SAS OVH - http://www.ovh.com 2 rue K...","Announcing the all-new Creator, build ..."};
    List<Email> list;
    ActionBar actionBar;
    SearchView txtSearchView;
    Email email;
    ListView listView;
    ListView listView2;
    String querySearch;
    SearchView searchView;
    EmailAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        list = new ArrayList<>();
        for (int i = 0; i<10; i++){
            list.add(new Email(listName[i].substring(0,1),listName[i],listTime[i],listContent[i], listTitleContent[i]));
        }


        EmailAdapter adapter = new EmailAdapter(this, list);
        ListView listView = findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);
        listView.setLongClickable(true);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        getMenuInflater().inflate(R.menu.main_menu,menu);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.v("TAG", "Search with keyword: " + query);
                querySearch = query;
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.v("TAG", "Keyword: " + newText);
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_favorite){
            List<Email> items2 = new ArrayList<>();
//            if(listView.getVisibility() == View.GONE){
            for (int i = 0; i < list.size(); i++){
                if(list.get(i).isFavorite()) {
                    Log.v("favorite", "item" + i);
                    items2.add(list.get(i));
                    Log.v("add2favorite", "item" + items2.size());
                }
            }
            listView.setVisibility(View.GONE);
            EmailAdapter adapter1 = new EmailAdapter(this, items2);
            listView2 = findViewById(R.id.list_favorite);
            listView2.setAdapter(adapter1);
//            }
//            else{
//                listView.setVisibility(View.VISIBLE);
//                listView2.setVisibility(View.GONE);
//            }

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu,v,menuInfo);
        menu.setHeaderTitle("Select action");
        menu.add(0,101, 0,"Details");
        menu.add(0,102,0,"Delete");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        if(item.getItemId() == 101){
            go2Details(list.get(info.position));
        }
        return super.onContextItemSelected(item);
    }
    private void go2Details(Email item){
        Intent details = new Intent(MainActivity.this,Details.class);
        //create Bundle
        Bundle myBunder = new Bundle();
        //add <key,value>
        myBunder.putString("name",item.getNameEmail());
        myBunder.putString("title",item.getTitleContent());
        myBunder.putString("time",item.getTime());
        myBunder.putString("content",item.getContent());
        myBunder.putBoolean("favorite",item.isFavorite());
        //attatch the container to the intent
        details.putExtras(myBunder);

        startActivityForResult(details,10);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try{
            if(requestCode == 10 && resultCode == Activity.RESULT_OK){
                Log.v("TAG", "sucess");
            }

        }catch (Exception e){
            Log.v("TAG", "KHONG MO DC INTENT");
        }
    }
}