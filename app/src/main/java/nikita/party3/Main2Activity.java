package nikita.party3;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.applovin.sdk.AppLovinSdk;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Main2Activity extends AppCompatActivity implements RewardedVideoAdListener {

    NavigationView navigationView;
    private int coin;
    RewardedVideoAd mRewardedVideoAd;
    private DrawerLayout dl;
    TextView textViewnameuser, textView2;
    ImageView imageView;
    Button buttonstartbanner;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase firebaseDatabase;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://party2-ec0fd.appspot.com");
    DatabaseReference mDatabase;
    AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main2);
        buttonstartbanner = findViewById(R.id.buttonstartbanner);
        dl = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);
        dl.addDrawerListener(toggle);
        navigationView =  findViewById(R.id.navView);
        toggle.syncState();
        textView2 = findViewById(R.id.textView2);
        AppLovinSdk.initializeSdk(this);

        setupDrawerContent(navigationView);
        View headerView = navigationView.getHeaderView(0);
        textViewnameuser = headerView.findViewById(R.id.textViewusername);
        textViewnameuser.setText(user.getDisplayName());
        buttonstartbanner.setEnabled(false);
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        myToolbar.setNavigationIcon(R.drawable.ic_dehaze_black_24dp);

        






/*
        //закгрузка фото
        storageRef.child("images/"+ user.getUid()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                imageView = findViewById(R.id.ava);
                Picasso.get().load(uri).resize(75,75).centerCrop()
                        .into(imageView);

            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Ошибка!", Toast.LENGTH_SHORT);
                toast.show();
            }
        });*/





            MobileAds.initialize(this, new OnInitializationCompleteListener() {
                @Override
                public void onInitializationComplete(InitializationStatus initializationStatus) {
                }
            });
            mAdView = findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);



        MobileAds.initialize(this, "ca-app-pub-6015294152911442~7358842002");

        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);

        mRewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917",
                new AdRequest.Builder().build());
        firebaseDatabase = FirebaseDatabase.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    Integer res = dataSnapshot.child("users").child(user.getUid()).getValue(Integer.class);
                if (res!=null) {
                    textView2.setText(String.valueOf(res));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }




    public void selectItemDrawer(MenuItem menuItem){

        Fragment fragment = null;
        Class fragmentClass;
        switch (menuItem.getItemId()){
            case R.id.facebook:
                fragmentClass = Network.class;
                Intent brawserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://vk.com/public190886758"));
                startActivity(brawserIntent);
                break;
            case R.id.Out:
                fragmentClass = Network.class;
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Main2Activity.this, MainActivity.class));
                break;

            default:
                fragmentClass = Network.class;

        }
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        /*FragmentManager fragmentManager = getFragmentManager();*/
        fragmentManager.beginTransaction().replace(R.id.framelayout,fragment).commit();
       // menuItem.setIcon(R.drawable.fui_ic_facebook_white_22dp);
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        dl.closeDrawers();

    }

    public void setupDrawerContent(NavigationView navigationView){

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                selectItemDrawer(menuItem);

                return true;
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                dl.openDrawer(Gravity.LEFT);
        }
        return (super.onOptionsItemSelected(menuItem));
    }



    @Override
    public void onRewardedVideoAdLoaded() {


        if (mRewardedVideoAd.isLoaded()){
            buttonstartbanner.setEnabled(true);
        } else  buttonstartbanner.setEnabled(false);

        buttonstartbanner.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {

                if (mRewardedVideoAd.isLoaded()) {
                    buttonstartbanner.setEnabled(true);
                    mRewardedVideoAd.show();
                } else {
                    onRewardedVideoAdLoaded();
                }
            }
        });
    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {
        mRewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917",
                new AdRequest.Builder().build());
        onRewardedVideoAdLoaded();

    }

    @Override
    public void onRewarded(RewardItem rewardItem) {





        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Integer res = dataSnapshot.child("users").child(user.getUid()).getValue(Integer.class);
                if (res!=null){
                    mDatabase.child("users").child(user.getUid()).setValue(res+=1);
                    textView2.setText(String.valueOf(res));
                } else {
                    mDatabase.child("users").child(user.getUid()).setValue(1);
                    textView2.setText(String.valueOf(1));
                }




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void onRewardedVideoAdLeftApplication() {
        mRewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917",
                new AdRequest.Builder().build());
    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {
        mRewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917",
                new AdRequest.Builder().build());
    }

    @Override
    public void onRewardedVideoCompleted() {
        mRewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917",
                new AdRequest.Builder().build());
    }


    @Override
    public void onBackPressed() { }


}
