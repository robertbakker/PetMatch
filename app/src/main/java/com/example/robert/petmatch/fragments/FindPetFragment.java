package com.example.robert.petmatch.fragments;

/**
 * Created by Robert on 14-09-15.
 */

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.robert.petmatch.MainActivity;
import com.example.robert.petmatch.R;
import com.example.robert.petmatch.adapters.PetCardStackAdapter;
import com.example.robert.petmatch.models.Pet;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class FindPetFragment extends Fragment {

    private List<Pet> pets;
    private PetCardStackAdapter petCardStackAdapter;
    private OnPetClickedListener mPetClickedListener;

    @Bind(R.id.buttonLike)
    ImageButton buttonLike;

    @Bind(R.id.buttonDislike)
    ImageButton buttonDislike;

    @Bind(R.id.card_container)
    SwipeFlingAdapterView flingContainer;

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static FindPetFragment newInstance(int sectionNumber) {
        FindPetFragment fragment = new FindPetFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public FindPetFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_find_pet, container, false);
        ButterKnife.bind(this, rootView);

        pets = new ArrayList<>();
        pets.add(new Pet("Johan", "giraffe", "Lief", "image1"));
        pets.add(new Pet("Kiki", "frog", "Lief", "image2"));
        pets.add(new Pet("Betty", "baboon", "Lief", "image3"));
        pets.add(new Pet("Johan", "giraffe", "Lief", "image1"));
        pets.add(new Pet("Kiki", "frog", "Lief", "image2"));
        pets.add(new Pet("Betty", "baboon", "Lief", "image3"));

        petCardStackAdapter = new PetCardStackAdapter(getActivity(), R.layout.pet_card, pets);

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

            }

            @Override
            public void onRightCardExit(Object dataObject) {
                Pet pet = (Pet) dataObject;

            }

            @Override
            public void onAdapterAboutToEmpty(int i) {

            }

            @Override
            public void onScroll(float scrollProgressPercent) {
                View view = flingContainer.getSelectedView();
                if(view != null) {
                    view.findViewById(R.id.right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
                    view.findViewById(R.id.left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
                }
            }
        });

        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                Pet pet = (Pet) dataObject;
                onCardClicked(pet);
            }
        });



        buttonLike.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLike();
            }
        });

        buttonDislike.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                doDislike();
            }
        });

        return rootView;
    }

    public void doLike() {
        flingContainer.getTopCardListener().selectRight();
    }

    public void doDislike() {
        flingContainer.getTopCardListener().selectLeft();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mPetClickedListener = null;
    }



    public void onCardClicked(Pet pet) {
        if (mPetClickedListener != null) {
            mPetClickedListener.onPetClicked(pet);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mPetClickedListener = (OnPetClickedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnPetClickedListener");
        }

        ((MainActivity) getActivity()).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public interface OnPetClickedListener {
        public void onPetClicked(Pet pet);
    }
}
