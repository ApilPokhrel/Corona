package com.rumsan.corona;

import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;


import com.rumsan.corona.entity.WorldDataModel;
import com.rumsan.corona.fragment.FAQsFragment;
import com.rumsan.corona.fragment.HomeFragment;
import com.rumsan.corona.fragment.HospitalFragment;
import com.rumsan.corona.fragment.MythsFragment;
import com.rumsan.corona.fragment.NewsFragment;

import java.io.Serializable;
import java.util.List;


public class MainActivity extends AppCompatActivity implements HomeFragment.OnFragmentInteractionListener
        ,HospitalFragment.OnFragmentInteractionListener
        ,NewsFragment.OnFragmentInteractionListener
        ,FAQsFragment.OnFragmentInteractionListener
        ,MythsFragment.OnFragmentInteractionListener  {


  private MythsFragment mythsFragment;
  private NewsFragment newsFragment;
  private HomeFragment homeFragment;
  private FAQsFragment faQsFragment;
  private HospitalFragment hospitalFragment;

  boolean isHome = true;
  int backIndex = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getSupportActionBar().hide();
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        if (savedInstanceState == null) {
            mythsFragment = MythsFragment.newInstance("myths");
            homeFragment = HomeFragment.newInstance("home");
            newsFragment = NewsFragment.newInstance("news");
            faQsFragment = FAQsFragment.newInstance("faqs");
            hospitalFragment = HospitalFragment.newInstance("hospital");
        }
          displayFragmentHome();

    }

    protected void displayFragmentMyth() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if(mythsFragment == null) mythsFragment = MythsFragment.newInstance("myths");
        if (mythsFragment.isAdded()) {
            ft.show(mythsFragment);
        } else {
            ft.add(R.id.frame_container, mythsFragment, "myths");
        }
        if (newsFragment.isAdded()) { ft.hide(newsFragment); }
        if (homeFragment.isAdded()) { ft.hide(homeFragment); }
        if (faQsFragment.isAdded()) { ft.hide(faQsFragment); }
        if (hospitalFragment.isAdded()) { ft.hide(hospitalFragment); }

        ft.commit();
    }

    protected void displayFragmentHome() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if(homeFragment == null) homeFragment = HomeFragment.newInstance("home");
        if (homeFragment.isAdded()) {
            ft.show(homeFragment);
        } else {
            ft.add(R.id.frame_container, homeFragment, "home");
        }
        if (newsFragment.isAdded()) { ft.hide(newsFragment); }
        if (mythsFragment.isAdded()) { ft.hide(mythsFragment); }
        if (faQsFragment.isAdded()) { ft.hide(faQsFragment); }
        if (hospitalFragment.isAdded()) { ft.hide(hospitalFragment); }

        ft.commit();
        isHome = true;
    }

    protected void displayFragmentNews() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if(newsFragment == null) newsFragment = NewsFragment.newInstance("news");
        if (newsFragment.isAdded()) {
            ft.show(newsFragment);
        } else {
            ft.add(R.id.frame_container, newsFragment, "news");
        }
        if (mythsFragment.isAdded()) { ft.hide(mythsFragment); }
        if (homeFragment.isAdded()) { ft.hide(homeFragment); }
        if (faQsFragment.isAdded()) { ft.hide(faQsFragment); }
        if (hospitalFragment.isAdded()) { ft.hide(hospitalFragment); }

        ft.commit();
    }

    protected void displayFragmentFaq() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if(faQsFragment == null) FAQsFragment.newInstance("faqs");
        if (faQsFragment.isAdded()) {
            ft.show(faQsFragment);
        } else {
            ft.add(R.id.frame_container, faQsFragment, "faq");
        }
        if (newsFragment.isAdded()) { ft.hide(newsFragment); }
        if (homeFragment.isAdded()) { ft.hide(homeFragment); }
        if (mythsFragment.isAdded()) { ft.hide(mythsFragment); }
        if (hospitalFragment.isAdded()) { ft.hide(hospitalFragment); }

        ft.commit();
    }

    protected void displayFragmentHospital() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if(hospitalFragment == null) hospitalFragment = HospitalFragment.newInstance("hospital");
        if (hospitalFragment.isAdded()) {
            ft.show(hospitalFragment);
        } else {
            ft.add(R.id.frame_container, hospitalFragment, "hospital");
        }
        if (newsFragment.isAdded()) { ft.hide(newsFragment); }
        if (homeFragment.isAdded()) { ft.hide(homeFragment); }
        if (mythsFragment.isAdded()) { ft.hide(mythsFragment); }
        if (faQsFragment.isAdded()) { ft.hide(faQsFragment); }

        ft.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onLoadSymptoms() {
        Intent intent = new Intent(MainActivity.this, SymptomActivity.class);
        startActivity(intent);
    }

    @Override
    public void onLoadHospitals() {
       isHome = false;
       displayFragmentHospital();
    }

    @Override
    public void onLoadLiveData(List<WorldDataModel> datas) {
        if(datas == null){
            Toast.makeText(this, "Oops! something went wrong", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(MainActivity.this, DataActivity.class);
        intent.putExtra("datas", (Serializable) datas);
        startActivity(intent);
    }

    @Override
    public void onLoadNews() {
        isHome = false;
        displayFragmentNews();
    }

    @Override
    public void onLoadMyths() {
        isHome = false;
        displayFragmentMyth();

    }

    @Override
    public void onLoadFaq() {
        isHome = false;
        displayFragmentFaq();

    }

    @Override
    public void onLoadExplain() {
        Intent intent = new Intent(MainActivity.this, BrowserActivity.class);
        intent.putExtra("url", "https://nepalcorona.info/explainers");
        startActivity(intent);
    }

    @Override
    public void onLoadPodcast() {
        Intent intent = new Intent(MainActivity.this, PodcastActivity.class);
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        if(!isHome){
            displayFragmentHome();
        } else {
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            backIndex = 0;
                        }
                    },
                    850);
            backIndex++;
            if(backIndex > 1){
                finishAffinity();
                super.onBackPressed();
                return;
            }
            Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressHospital() {
        onBackPressed();
    }

    @Override
    public void onBackPressNews() {
        onBackPressed();
    }

    @Override
    public void onBackPressFaq() {
        onBackPressed();
    }

    @Override
    public void onBackPressMyths() {
        onBackPressed();
    }
}
