package com.example.robert.petmatch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.robert.petmatch.R;
import com.example.robert.petmatch.models.Pet;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PetProfileActivity extends AppCompatActivity {

    @Bind(R.id.name)
    TextView name;

    @Bind(R.id.image)
    ImageView image;

    @Bind(R.id.aboutText)
    TextView aboutText;

    @Bind(R.id.descriptionText)
    TextView descriptionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_profile);
        ButterKnife.bind(this);

        Intent i = getIntent();
        Pet pet = (Pet) i.getSerializableExtra("Pet");

        String species = getString(getResources().getIdentifier("pet_species_" + pet.getSpecies(), "string", getPackageName())).toLowerCase();

        name.setText(String.format(getString(R.string.pet_title), pet.getName(), species));

        int imageResource = getResources().getIdentifier(pet.getImage(), "drawable", getPackageName());
        image.setImageResource(imageResource);

        aboutText.setText(String.format(getString(R.string.pet_profile_about), pet.getName()));
        descriptionText.setText(pet.getDescription());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pet_profile, menu);
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
