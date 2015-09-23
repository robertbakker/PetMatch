package com.example.robert.petmatch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.robert.petmatch.DBHelper;
import com.example.robert.petmatch.R;
import com.example.robert.petmatch.models.Pet;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ListFavoritesActivity extends AppCompatActivity {
    private ArrayAdapter adapter;

    private DBHelper dbHelper;

    @Bind(R.id.favoritesList)
    ListView listView;

    private List<Pet> pets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_favorites);

        ButterKnife.bind(this);

        dbHelper = new DBHelper(getApplication());

        pets = dbHelper.getAllPetsByStatus(Pet.STATUS_LIKED);

        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, pets);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Pet clickedItem = (Pet) parent.getItemAtPosition(position);
                Intent intent = new Intent(getApplication(), PetProfileActivity.class);
                intent.putExtra("Pet", clickedItem);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds pets to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_favorites, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_reset) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
