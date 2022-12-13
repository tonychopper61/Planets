package com.example.planets;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    View v;
    Connection connection;
    List<Mask> data;
    ListView listView;
    Adapter pAdapter;
    Spinner spinnerFilter;
    String  label="Planet";
    String [] Filter={"Без упорядочивания","Расстояние по возрастанию", "Расстояние по убыванию", "Название по алфавиту"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        v = findViewById(com.google.android.material.R.id.ghost_view);

        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Filter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFilter=findViewById(R.id.filter);
        spinnerFilter.setAdapter(adapter);


        spinnerFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                String Choice = null;
                switch (position)
                {
                    case 0:
                    {
                        Choice = "Select * From Planets";
                        SelectList(Choice);
                        break;
                    }
                    case 1:
                    {
                        Choice = "Select * From Planets ORDER BY Distance ASC";
                        SelectList(Choice);
                        break;
                    }
                    case 2:
                    {
                        Choice = "Select * From Planets ORDER BY Distance DESC";
                        SelectList(Choice);
                        break;
                    }
                    case 3:
                    {
                        Choice = "Select * From Planets ORDER BY Name";
                        SelectList(Choice);
                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        GetTextFromSQL(v);


    }

    public void enterMobile() {
        pAdapter.notifyDataSetInvalidated();
        listView.setAdapter(pAdapter);
    }

    public void GetTextFromSQL(View v) {
        data = new ArrayList<Mask>();
        listView = findViewById(R.id.BD);
        pAdapter = new Adapter(MainActivity.this, data);
        try {
            ConSQL connectionHelper = new ConSQL();
            connection = connectionHelper.conclass();
            if (connection != null) {

                String query = "Select * From Planets";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    Mask tempMask = new Mask
                            (resultSet.getInt("ID_planet"),
                                    resultSet.getString("Name"),
                                    resultSet.getString("Distance"),
                                    resultSet.getString("Image")

                            );
                    data.add(tempMask);
                    pAdapter.notifyDataSetInvalidated();
                }
                connection.close();
            } else {
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        enterMobile();

    }

    public void SelectList(String Choice)
    {
        data = new ArrayList<Mask>();
        listView = findViewById(R.id.BD);
        pAdapter = new Adapter(MainActivity.this, data);
        try {
            ConSQL connectionHelper = new ConSQL();
            connection = connectionHelper.conclass();
            if (connection != null) {

                String query = Choice;
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    Mask tempMask = new Mask
                            (resultSet.getInt("ID_planet"),
                                    resultSet.getString("Name"),
                                    resultSet.getString("Distance"),
                                    resultSet.getString("Image")

                            );
                    data.add(tempMask);
                    pAdapter.notifyDataSetInvalidated();
                }
                connection.close();
            } else {
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        enterMobile();
    }

    public void onClickAdd(View v) {
        switch (v.getId()) {
            case R.id.btnadd:
                Intent intent = new Intent(this, AddActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search,menu);
        MenuItem item=menu.findItem(R.id.search);
        SearchView searchView=(SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                txtSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                txtSearch(newText);

                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id=item.getItemId();

        if(id==R.id.SearchPlanet)
        {
            label="Planet";
        }
        else
        if(id==R.id.SearchDistance)
        {
            label="Distance";
        }
        return super.onOptionsItemSelected(item);
    }

    private  void txtSearch(String str)
    {
        data = new ArrayList<Mask>();
        listView = findViewById(R.id.BD);
        pAdapter = new Adapter(MainActivity.this, data);
        try {
            String query="";
            ConSQL connectionHelper = new ConSQL();
            connection = connectionHelper.conclass();
            if (connection != null) {
                if(label=="Planet")
                {
                    query = "Select * From Planets WHERE Name like'%"+str+"%'";
                }
                else
                if(label=="Distance")
                {
                    query = "Select * From Planets WHERE Distance like'%"+str+"%'";
                }

                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    Mask tempMask = new Mask
                            (resultSet.getInt("ID_planet"),
                                    resultSet.getString("Name"),
                                    resultSet.getString("Distance"),
                                    resultSet.getString("Image")

                            );
                    data.add(tempMask);
                    pAdapter.notifyDataSetInvalidated();
                }
                connection.close();
            } else {
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        enterMobile();
    }


}