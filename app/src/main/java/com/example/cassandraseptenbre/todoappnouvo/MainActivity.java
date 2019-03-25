package com.example.cassandraseptenbre.todoappnouvo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.PrivateKey;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final Object =;
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        readItems();
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        lvItem = (ListView) findViewById(R.id.lvItems);
        lvItem.setAdapter(itemsAdapter);

        //mock data
        //items.add("Firts item");
        //items.add("second item");

        setupListViewListener();
    }

    public void onAddItem(View v) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        etNewItem.setText("");
        writeItem();
        Toast.makeText(getApplicationContext(), "Item added to list", Toast.LENGTH_SHORT).show();
    }

    private void setupListViewListener() {
        Log.i("MainActivity","setting up listener on list view");
        lvItem.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("MainActivity", "Item remove from list"+ position);
                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                writeItem();
                return true;
            }
        });
    }
    private File getDataFile(){
        return new File(getFilesDir(),"todo.text");
    }
    private void readItems(){
        try{
            items = new ArrayList<>(FileUtils.readlines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e){
             Log.e ("MainActivity","Error reading file", e);
             items = new ArrayList<>();
        }
    }

    private void writeItem() {
        try {
            FileUtils.writeLines(getDataFile(),items);
        }catch (IOException e) {
            Log.e("MainActivity","Error writing file", e);
        }
    }
}

