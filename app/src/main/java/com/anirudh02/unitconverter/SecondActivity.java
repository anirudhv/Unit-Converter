package com.anirudh02.unitconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayout.Tab;
public class SecondActivity extends AppCompatActivity {

TabLayout tabLayout;
int curr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        setTitle("Unit Converter");
        //Show a toast message that says this is the help page.
        Toast.makeText(this, "Help Page", Toast.LENGTH_SHORT).show();
        //Initialize tabLayout variable to point to the TabLayout that was created in the layout resource file, and then select the Help tab
        tabLayout = (TabLayout)findViewById(R.id.tabs);
        tabLayout.getTabAt(3).select();
        //Create a tab selected listener that calls the MoveToMainActivity method and stores the tab the user selected in an integer variable
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(Tab tab) {
                curr = tab.getPosition();
                MoveToMainActivity();
            }

            @Override
            public void onTabUnselected(Tab tab) {

            }

            @Override
            public void onTabReselected(Tab tab) {

            }
        });
    }

    //This method goes back to the Main Activity (which is where the Length, Weight, and Temperature tabs are implemented)
    // whenever a tab is selected. The flags are used to ensure that the user cannot come back to this page with the back button,
    //but only by pressing the Help tab.
    public void MoveToMainActivity() {
        Intent mIntent = new Intent(this, MainActivity.class);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        mIntent.putExtra("tab", curr);
        startActivity(mIntent);
    }

}