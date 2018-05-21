package com.avrilhb.quinielaandroid;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.avrilhb.quinielaandroid.model.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import butterknife.BindView;
import butterknife.ButterKnife;

public class QuinielaFragment extends Fragment {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.txtTitleTool) TextView txtTitleTool;
    @BindView(R.id.txtUserName) TextView txtUserName;
    @BindView(R.id.txtTeam) TextView txtTeam;
    @BindView(R.id.txtPoints) TextView txtPoints;

    private FirebaseDatabase mFirebaseDataBase;
    private DatabaseReference mUsersReference;
    private ChildEventListener mchildEventListener;
    private String email;

    public QuinielaFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quiniela, container, false);

        ButterKnife.bind(this, view);

        txtTitleTool.setVisibility(View.GONE);

        showToolbar(false, view);

        //Retrieve user email from shared preferences
        SharedPreferences preferences =
                getActivity().getSharedPreferences("USER", Context.MODE_PRIVATE);

        if (preferences.contains("email")) {
            email = preferences.getString("email", "");
        }

        //Retrieve data from firebase database
        mFirebaseDataBase = FirebaseDatabase.getInstance();
        mUsersReference = mFirebaseDataBase.getReference().child("users");

        mchildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                User user = dataSnapshot.getValue(User.class);
                setUserData(user.getUserName(), user.getTeam(), user.getPoints());

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        mUsersReference.addChildEventListener(mchildEventListener);

        return view;
    }

    public void showToolbar(boolean upButton, View view){
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.tab_quiniela));
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }

    public void setUserData(String userName, String team, int points){
        txtUserName.setText(userName);
        txtTeam.setText(team);
        txtPoints.setText(getContext().getResources().getString(R.string.points) +  " " + String.valueOf(points));
    }

}
