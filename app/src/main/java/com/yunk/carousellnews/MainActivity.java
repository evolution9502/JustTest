package com.yunk.carousellnews;

import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.yunk.carousellnews.DataUtilities.JsonArraySorter;
import com.yunk.carousellnews.DataUtilities.JsonDataParser;
import com.yunk.carousellnews.ViewUtilities.Article;
import com.yunk.carousellnews.ViewUtilities.ArticleViewAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.yunk.carousellnews.DataUtilities.ArticleConstant.RANK;
import static com.yunk.carousellnews.DataUtilities.ArticleConstant.TIME;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private JsonDataParser jsonDataParser;
    private RecyclerView mainRecyclerView;
    private ArticleViewAdapter myAdapter;
    private JsonArraySorter sorter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //customize the action bar title text, the background color is set on style.xml
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_title_layout);

        mainRecyclerView = findViewById(R.id.rv_article_view);

        List<Article> ArticleDataset = new ArrayList<>();
        myAdapter = new ArticleViewAdapter(MainActivity.this, ArticleDataset);
        jsonDataParser = new JsonDataParser(getString(R.string.DATA_SOURCE), this, myAdapter, ArticleDataset); //Most data processing is done here

        //setting recycle view
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mainRecyclerView.setLayoutManager(layoutManager);
        mainRecyclerView.setAdapter(myAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //menu selected event
        int id = item.getItemId();
        if (id == R.id.action_recent) {
            sorter = new JsonArraySorter(jsonDataParser.getRawJsonArray(), TIME); //sort json array by time created
            jsonDataParser.articleDataArrange(sorter.getSortedJsonArray());
            return true;
        } else if (id == R.id.action_popular) {
            sorter = new JsonArraySorter(jsonDataParser.getRawJsonArray(), RANK); //sort json array by rank
            jsonDataParser.articleDataArrange(sorter.getSortedJsonArray());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
