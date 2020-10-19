package com.example.autocompletetxtview;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    AutoCompleteTextView selectTextView;
    List<ModelClass> mList, tempItems, suggestions;;
    Adapter adapter;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mList = new ArrayList<ModelClass>();
        mList.add(new ModelClass("1","The Avengers"));
        mList.add(new ModelClass("2","Thor"));
        mList.add(new ModelClass("3","Iron Man"));
        mList.add(new ModelClass("4","Doctor Strange"));
        selectTextView = (AutoCompleteTextView) findViewById(R.id.selectTextView);
        selectTextView.setThreshold(1);
        //selectTextView.setInputType(0);
        adapter = new Adapter(this, R.layout.activity_main, R.id.item_text, mList);
        selectTextView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        selectTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
//                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                selectTextView.showDropDown();
                return false;
            }
        });

        selectTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this,
                        "Id: "+mList.get(i).getId()+" Value: "+mList.get(i).getName(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}