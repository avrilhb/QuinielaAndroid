package com.avrilhb.quinielaandroid.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.avrilhb.quinielaandroid.QuinielaFragment;
import com.avrilhb.quinielaandroid.R;
import com.avrilhb.quinielaandroid.login.view.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.crash.FirebaseCrash;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by avrilhb on 30/04/18.
 */

public class ContainerActivity extends AppCompatActivity {

    private static final String TAG = "ContainerActivity";
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    QuinielaFragment quinielaFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        FirebaseCrash.log("Iniciando " + TAG);

        setContentView(R.layout.activity_container);

        firebaseInitialize();

        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottombar);
        bottomBar.setDefaultTab(R.id.quiniela);

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId){
                    case R.id.quiniela:
                        QuinielaFragment homeFragment = new QuinielaFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .addToBackStack(null).commit();

                        break;
                }
            }
        });
    }

    private void firebaseInitialize(){
        firebaseAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null){
                    FirebaseCrash.logcat(Log.WARN, TAG, "usuario logueado " + firebaseUser.getEmail());
                }else {
                    FirebaseCrash.logcat(Log.WARN, TAG, "usuario  no logueado " );
                }
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){

            case R.id.mSignOut:
                //Cerrar sesión de Firebase
                firebaseAuth.signOut();
                FirebaseCrash.logcat(Log.WARN, TAG, "Se cerró la sesión" );

                //Redirigir a Login
                Intent intent = new Intent(ContainerActivity.this, LoginActivity.class);
                startActivity(intent);

                break;
            case R.id.mAbout:
                Toast.makeText(this, "Quiniela by Avril", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
