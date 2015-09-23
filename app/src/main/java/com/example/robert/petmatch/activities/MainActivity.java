package com.example.robert.petmatch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.robert.petmatch.DBHelper;
import com.example.robert.petmatch.R;
import com.example.robert.petmatch.adapters.PetCardStackAdapter;
import com.example.robert.petmatch.models.Pet;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private List<Pet> pets;
    private PetCardStackAdapter petCardStackAdapter;
    private DBHelper dbHelper;

    @Bind(R.id.buttonLike)
    ImageButton buttonLike;

    @Bind(R.id.buttonDislike)
    ImageButton buttonDislike;

    @Bind(R.id.card_container)
    SwipeFlingAdapterView flingContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        dbHelper = new DBHelper(getApplication());
        pets = dbHelper.getAllPetsByStatus(Pet.STATUS_UNDECIDED);

        petCardStackAdapter = new PetCardStackAdapter(getApplication(), R.layout.pet_card, pets);

        //set the listener and the adapter
        flingContainer.setAdapter(petCardStackAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                if (pets.size() > 0) {
                    pets.remove(0);
                    petCardStackAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                Pet pet = (Pet) dataObject;
                pet.dislike();
                dbHelper.updatePet(pet);
                Toast.makeText(getApplication(), getString(R.string.message_disliked), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                Pet pet = (Pet) dataObject;
                pet.like();
                dbHelper.updatePet(pet);
                String message = String.format(getString(R.string.message_liked), pet.getName());
                Toast.makeText(getApplication(), message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdapterAboutToEmpty(int i) {

            }

            @Override
            public void onScroll(float scrollProgressPercent) {
                View view = flingContainer.getSelectedView();
                if (view != null) {
                    view.findViewById(R.id.right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
                    view.findViewById(R.id.left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
                }
            }
        });

        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                Pet pet = (Pet) dataObject;
                Intent intent = new Intent(getApplication(), PetProfileActivity.class);
                intent.putExtra("Pet", pet);
                startActivity(intent);
            }
        });

        buttonLike.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                flingContainer.getTopCardListener().selectRight();
            }
        });

        buttonDislike.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                flingContainer.getTopCardListener().selectLeft();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_favorites) {
            Intent intent = new Intent(this, ListFavoritesActivity.class);
            startActivity(intent);
            return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_reset) {
            List<Pet> pets = dbHelper.getAllPets();
            for (Pet pet : pets) {
                pet.removeStatus();
                dbHelper.updatePet(pet);
            }

            finish();
            startActivity(getIntent());

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
