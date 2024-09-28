package com.example.mypharmacy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.mypharmacy.databinding.ActivityMainBinding;
import com.example.mypharmacy.R;
import com.example.mypharmacy.user.HomeFragmentUser;
import com.example.mypharmacy.user.LibraryFragmentUser;
import com.example.mypharmacy.user.ShortsFragmentUser;
import com.example.mypharmacy.user.SubscriptionFragmentUser;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        replaceFragment(new HomeFragmentUser());
        binding.bottomNavigationView.setBackground(null);

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.home) {
                replaceFragment(new HomeFragmentUser());
                return true;
            } else if (itemId == R.id.shorts ) {
                replaceFragment(new ShortsFragmentUser());
                return true;
            }
            else if (itemId == R.id.subscriptions) {
                replaceFragment(new SubscriptionFragmentUser());
                return true;
            }
            else if (itemId == R.id.library) {
                replaceFragment(new LibraryFragmentUser());
                return true;
            }

            return false; // Cas par défaut où l'ID de l'élément n'est pas géré
        });


    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}