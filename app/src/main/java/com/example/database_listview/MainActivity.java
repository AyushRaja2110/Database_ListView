package com.example.database_listview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;

import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import javax.sql.StatementEvent;

public class MainActivity extends AppCompatActivity {

    DBHelper db;
    Button add_data;
    EditText add_name;

    ListView userlist;

    ArrayList<String> listItem;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DBHelper(this);
        add_data = findViewById(R.id.add_data);
        add_name = findViewById(R.id.add_name);
        userlist = findViewById(R.id.user_list);

        listItem = new ArrayList<>();

        viewData();

        userlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String text = userlist.getItemAtPosition(i).toString();
                Toast.makeText(MainActivity.this, ""+text, Toast.LENGTH_SHORT).show();
            }
        });


        add_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = add_name.getText().toString();
                if (!name.equals("") && db.insertdata(name))
                {
                    Toast.makeText(MainActivity.this, "Data Added successfully", Toast.LENGTH_SHORT).show();
                    add_name.setText("");
                    listItem.clear();
                    viewData();
                }
                else {
                    Toast.makeText(MainActivity.this, "Data Not Added..Please try Again!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void viewData() {

        Cursor cursor = db.viewdata();

        if (cursor.getCount() == 0)
        {
            Toast.makeText(this, "No Data..", Toast.LENGTH_SHORT).show();
        }
        else
        {
            while (cursor.moveToNext())
            {
                listItem.add(cursor.getString(1));
            }
            adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,listItem);
            userlist.setAdapter(adapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu , menu);

        MenuItem searchitem = menu.findItem(R.id.item_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchitem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                ArrayList<String> userslist = new ArrayList();

                for (String user : listItem)
                {
                    if (user.toLowerCase().contains(newText.toLowerCase(Locale.ROOT)))
                    {
                        userslist.add(user);
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1,userslist);
                userlist.setAdapter(adapter);

                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}