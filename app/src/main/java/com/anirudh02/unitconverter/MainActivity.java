package com.anirudh02.unitconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import static android.text.TextUtils.isEmpty;

public class MainActivity extends AppCompatActivity {

    Spinner spinner;
    String[]  lengths = {"mm", "cm", "m", "km", "inch", "ft", "yd", "mile"};
    String[] weights = {"mg", "g", "kg", "ton", "oz", "lb"};
    String[] temperatures = {"C", "F", "K"};
    int current;
    TabLayout tabLayout;
    TextView textView;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Unit Converter");
        //Initialize the tabLayout variable so that it is pointing to/referencing the TabLayout that was created in the layout resource files
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        //The setup method, which is called immediately when the app is started, will populate the table
        //and update the dropdown menu with values for the selected tab.
        //When the app is first launched, the Length tab is selected by default
        setUp();
        //This tab selected listener will check to see which tab the user selected. If the length, weight, or temperature tab was selected,
        //the setup method will be called to populate the table and update the dropdown menu values for that selection
        //If the help tab was selected, the goToAbout method will be called to load the Help page.
        //The selected tab number will be stored in an integer variable called "current"
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                current = tab.getPosition();
                if(current != 3) {
                    setUp();
                }
                else {
                    gotoAbout();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //If the user selected a tab from the help page, which is in another activity, the number of the tab that they selected
        //will be sent over to this activity via an Intent.
        //The code below will access this intent (should it exist), extract the tab number, and then go to the tab that the user selected.
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            tabLayout.getTabAt(extras.getInt("tab")).select();
        }
        //Initialize the editText variable to point to/reference the Text box where the user can type numerical values
        editText = (EditText)findViewById(R.id.inputNumber);
        //Whenever the user updates the text in the text box, the updateText() method will be called to perform conversions from the unit
        //that the user selected to all other units available in the selected category/tab, and then update the table accordingly as long as the text input is not empty.
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!isEmpty(charSequence.toString()))
                updateText();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    //This method simply opens up the Help page since it is in a different activity from the other three tabs.
    //This method is called in the tab selected listener implemented above whenever the "Help" tab is selected.
    //The flags are added to ensure that if the user presses the "Back" button from the "Help" Page, the app is closed rather than them
    //being brought back to the current activity which is focused on the Length, Weight, and Temperature tabs.
    //Likewise, if the user was previously in the activity with the help page, but is now in this activity, the app will still close rather than
    //redirecting them back to the Help page. I have chosen to close the app on all Back button presses to ensure consistency. Switching from the
    //Length tab to the Weight tab and then pressing the Back button would go to the Home page by default since both tabs are in the same activity.
    //I didn't want the result of a Back button press to be different for the Help page just because it is in another activity.
    public void gotoAbout() {
        Intent mIntent = new Intent(this, SecondActivity.class);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(mIntent);
    }
    //This method will be called if the user has selected the Length, Weight, or Temperature tab.
    // It will update the table and dropdown menu with the units for the selected category, clear the text box text,
    // and reset the numeric values to 0 (or what 0 degrees in one unit is equivalent in the others in the Temperature tab)
    //The method will also implement a listener for the dropdown menu and call the updateText method if a new unit is selected.
    //So if the user had first entered "10" and selected "m" in the dropdown menu, but they later changed the "m" to "mm",
    //the table would have to be updated since conversions would now be done for "10 m" and not "10 mm".
    public void setUp() {
        editText = (EditText) findViewById(R.id.inputNumber);
        editText.setText("");

        if(current == 0) {
            Toast.makeText(this, "Length Converter", Toast.LENGTH_SHORT).show();
            spinner = (Spinner) findViewById(R.id.spinner);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lengths);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            textView = (TextView)findViewById(R.id.one);
            textView.setText(R.string.mm);
            textView.setBackground(getResources().getDrawable(R.color.white));
            textView = (TextView)findViewById(R.id.valOne);
            textView.setText(R.string.zero);
            textView.setBackground(getResources().getDrawable(R.color.white));
            textView = (TextView) findViewById(R.id.two);
            textView.setText(R.string.cm);
            textView.setBackground(getResources().getDrawable(R.color.white));
            textView = (TextView)findViewById(R.id.valTwo);
            textView.setText(R.string.zero);
            textView.setBackground(getResources().getDrawable(R.color.white));
            textView = (TextView) findViewById(R.id.three);
            textView.setText(R.string.m);
            textView.setBackground(getResources().getDrawable(R.color.white));
            textView = (TextView)findViewById(R.id.valThree);
            textView.setText(R.string.zero);
            textView.setBackground(getResources().getDrawable(R.color.white));
            textView = (TextView) findViewById(R.id.four);
            textView.setText(R.string.km);
            textView.setBackground(getResources().getDrawable(R.color.white));
            textView = (TextView)findViewById(R.id.valFour);
            textView.setText(R.string.zero);
            textView.setBackground(getResources().getDrawable(R.color.white));
            textView = (TextView) findViewById(R.id.five);
            textView.setText(R.string.in);
            textView.setBackground(getResources().getDrawable(R.color.white));
            textView = (TextView)findViewById(R.id.valFive);
            textView.setText(R.string.zero);
            textView.setBackground(getResources().getDrawable(R.color.white));
            textView = (TextView) findViewById(R.id.six);
            textView.setText(R.string.ft);
            textView.setBackground(getResources().getDrawable(R.color.white));
            textView = (TextView)findViewById(R.id.valSix);
            textView.setText(R.string.zero);
            textView.setBackground(getResources().getDrawable(R.color.white));
            textView = (TextView) findViewById(R.id.seven);
            textView.setText(R.string.yd);
            textView.setBackground(getResources().getDrawable(R.color.white));
            textView = (TextView)findViewById(R.id.valSeven);
            textView.setText(R.string.zero);
            textView.setBackground(getResources().getDrawable(R.color.white));
            textView = (TextView) findViewById(R.id.eight);
            textView.setText(R.string.mi);
            textView.setBackground(getResources().getDrawable(R.color.white));
            textView = (TextView)findViewById(R.id.valEight);
            textView.setText(R.string.zero);
            textView.setBackground(getResources().getDrawable(R.color.white));
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    updateText();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }

        if(current == 1) {
            Toast.makeText(this, "Weight Converter", Toast.LENGTH_SHORT).show();
            spinner = (Spinner) findViewById(R.id.spinner);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, weights);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            textView = (TextView)findViewById(R.id.one);
            textView.setText(R.string.mg);
            textView.setBackground(getResources().getDrawable(R.color.white));
            textView = (TextView)findViewById(R.id.valOne);
            textView.setText(R.string.zero);
            textView.setBackground(getResources().getDrawable(R.color.white));
            textView = (TextView) findViewById(R.id.two);
            textView.setText(R.string.g);
            textView.setBackground(getResources().getDrawable(R.color.white));
            textView = (TextView)findViewById(R.id.valTwo);
            textView.setText(R.string.zero);
            textView.setBackground(getResources().getDrawable(R.color.white));
            textView = (TextView) findViewById(R.id.three);
            textView.setText(R.string.kg);
            textView.setBackground(getResources().getDrawable(R.color.white));
            textView = (TextView)findViewById(R.id.valThree);
            textView.setText(R.string.zero);
            textView.setBackground(getResources().getDrawable(R.color.white));
            textView = (TextView) findViewById(R.id.four);
            textView.setText(R.string.ton);
            textView.setBackground(getResources().getDrawable(R.color.white));
            textView = (TextView)findViewById(R.id.valFour);
            textView.setText(R.string.zero);
            textView.setBackground(getResources().getDrawable(R.color.white));
            textView = (TextView) findViewById(R.id.five);
            textView.setText(R.string.oz);
            textView.setBackground(getResources().getDrawable(R.color.white));
            textView = (TextView)findViewById(R.id.valFive);
            textView.setText(R.string.zero);
            textView.setBackground(getResources().getDrawable(R.color.white));
            textView = (TextView) findViewById(R.id.six);
            textView.setText(R.string.lb);
            textView.setBackground(getResources().getDrawable(R.color.white));
            textView = (TextView)findViewById(R.id.valSix);
            textView.setText(R.string.zero);
            textView.setBackground(getResources().getDrawable(R.color.white));
            textView = (TextView) findViewById(R.id.seven);
            textView.setText("");
            textView.setBackground(getResources().getDrawable(R.color.lightblue));
            textView = (TextView)findViewById(R.id.valSeven);
            textView.setText("");
            textView.setBackground(getResources().getDrawable(R.color.lightblue));
            textView = (TextView) findViewById(R.id.eight);
            textView.setText("");
            textView.setBackground(getResources().getDrawable(R.color.lightblue));
            textView = (TextView)findViewById(R.id.valEight);
            textView.setText("");
            textView.setBackground(getResources().getDrawable(R.color.lightblue));
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    updateText();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }

        if(current == 2) {
            Toast.makeText(this, "Temperature Converter", Toast.LENGTH_SHORT).show();
            spinner = (Spinner) findViewById(R.id.spinner);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, temperatures);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            textView = (TextView)findViewById(R.id.one);
            textView.setText(R.string.c);
            textView.setBackground(getResources().getDrawable(R.color.white));
            textView = (TextView)findViewById(R.id.valOne);
            textView.setText(R.string.zero);
            textView.setBackground(getResources().getDrawable(R.color.white));
            textView = (TextView) findViewById(R.id.two);
            textView.setText(R.string.f);
            textView.setBackground(getResources().getDrawable(R.color.white));
            textView = (TextView)findViewById(R.id.valTwo);
            textView.setText(R.string.zero);
            textView.setBackground(getResources().getDrawable(R.color.white));
            textView = (TextView) findViewById(R.id.three);
            textView.setText(R.string.k);
            textView.setBackground(getResources().getDrawable(R.color.white));
            textView = (TextView)findViewById(R.id.valThree);
            textView.setText(R.string.zero);
            textView.setBackground(getResources().getDrawable(R.color.white));
            textView = (TextView) findViewById(R.id.four);
            textView.setText("");
            textView.setBackground(getResources().getDrawable(R.color.lightblue));
            textView = (TextView)findViewById(R.id.valFour);
            textView.setText("");
            textView.setBackground(getResources().getDrawable(R.color.lightblue));
            textView = (TextView) findViewById(R.id.five);
            textView.setText("");
            textView.setBackground(getResources().getDrawable(R.color.lightblue));
            textView = (TextView)findViewById(R.id.valFive);
            textView.setText("");
            textView.setBackground(getResources().getDrawable(R.color.lightblue));
            textView = (TextView) findViewById(R.id.six);
            textView.setText("");
            textView.setBackground(getResources().getDrawable(R.color.lightblue));
            textView = (TextView)findViewById(R.id.valSix);
            textView.setText("");
            textView.setBackground(getResources().getDrawable(R.color.lightblue));
            textView = (TextView) findViewById(R.id.seven);
            textView.setText("");
            textView.setBackground(getResources().getDrawable(R.color.lightblue));
            textView = (TextView)findViewById(R.id.valSeven);
            textView.setText("");
            textView.setBackground(getResources().getDrawable(R.color.lightblue));
            textView = (TextView) findViewById(R.id.eight);
            textView.setText("");
            textView.setBackground(getResources().getDrawable(R.color.lightblue));
            textView = (TextView)findViewById(R.id.valEight);
            textView.setText("");
            textView.setBackground(getResources().getDrawable(R.color.lightblue));
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    updateText();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
    }

    //This method is called whenever the user updates the numeric value in the text box or the unit in the dropdown menu is changed
    //to perform conversions and update the table
    public void updateText() {
        editText = (EditText)findViewById(R.id.inputNumber);
        double currnum = 0;
        if(!editText.getText().toString().equals(""))
            currnum = Double.parseDouble(editText.getText().toString());
        spinner = (Spinner) findViewById(R.id.spinner);
        String measure = spinner.getSelectedItem().toString();

        if(current == 0) {
            double mm = 0;
            double cm = 0;
            double m = 0;
            double km = 0;
            double inch = 0;
            double ft = 0;
            double yd = 0;
            double mi = 0;
            if(measure.equals("mm")) {
                mm = currnum;
                cm = currnum/10;
                m = currnum/1000;
                km = currnum/1e+6;
                inch = currnum/25.4;
                ft = currnum/305;
                yd = currnum/914;
                mi = currnum/1.609e+6;
            }
            if(measure.equals("cm")) {
                cm = currnum;
                mm = currnum * 10;
                m = currnum/100;
                km = currnum/100000;
                inch = currnum/2.54;
                ft = currnum/30.48;
                yd = currnum/91.44;
                mi = currnum/160934;
            }
            if(measure.equals("m")) {
                m = currnum;
                mm = currnum * 1000;
                cm = currnum * 100;
                km = currnum/1000;
                inch = m * 39.37;
                ft = currnum * 3.281;
                yd = currnum * 1.094;
                mi = currnum/1609;
            }
            if(measure.equals("km")) {
                km = currnum;
                mm = currnum * 1e+6;
                cm = currnum * 100000;
                m = currnum * 1000;
                inch = currnum * 39370;
                ft = currnum * 3281;
                yd = currnum * 1094;
                mi = currnum/1.609;
            }
            if(measure.equals("inch")) {
                inch = currnum;
                mm = currnum * 25.4;
                cm = currnum * 2.54;
                m = currnum/39.37;
                km = currnum/39370;
                ft = currnum/12;
                yd = currnum/36;
                mi = currnum/63360;

            }
            if(measure.equals("ft")) {
                ft = currnum;
                mm = currnum * 304.8;
                cm = currnum * 30.48;
                m = currnum/3.281;
                km = currnum/3281;
                inch = currnum * 12;
                yd = currnum/3;
                mi = currnum/5280;
            }
            if(measure.equals("yd")) {
                yd = currnum;
                mm = currnum * 914.4;
                cm = currnum * 91.44;
                m = currnum/1.094;
                km = currnum/1094;
                inch = currnum * 36;
                ft = currnum * 3;
                mi = currnum/1760;
            }
            if(measure.equals("mile")) {
                mi = currnum;
                mm = currnum * 1.609e+6;
                cm = currnum * 160934;
                m = currnum * 1609.34;
                km = currnum * 1.60934;
                inch = currnum * 63360;
                ft = currnum * 5280;
                yd = currnum * 1760;
            }
            textView = (TextView)findViewById(R.id.valOne);
            textView.setText(Double.toString(mm));
            textView = (TextView)findViewById(R.id.valTwo);
            textView.setText(Double.toString(cm));
            textView = (TextView)findViewById(R.id.valThree);
            textView.setText(Double.toString(m));
            textView = (TextView)findViewById(R.id.valFour);
           textView.setText(Double.toString(km));
            textView = (TextView)findViewById(R.id.valFive);
            textView.setText(Double.toString(inch));
            textView = (TextView)findViewById(R.id.valSix);
           textView.setText(Double.toString(ft));
            textView = (TextView)findViewById(R.id.valSeven);
            textView.setText(Double.toString(yd));
            textView = (TextView)findViewById(R.id.valEight);
            textView.setText(Double.toString(mi));
        }

        if(current == 1) {
            double mg = 0;
            double g = 0;
            double kg = 0;
            double ton = 0;
            double oz  = 0;
            double lb = 0;
            if(measure.equals("mg")) {
                mg = currnum;
                g = currnum/1000;
                kg = currnum/1e+6;
                ton = currnum/1e-9;
                oz = currnum/28350;
                lb = currnum/453592;
            }
            if(measure.equals("g")) {
                g = currnum;
                mg = currnum * 1000;
                kg= currnum/1000;
                ton = currnum/1e+6;
                oz = currnum/28.35;
                lb = currnum/454;
            }
            if(measure.equals("kg")) {
                kg = currnum;
                mg  = currnum * 1e+6;
                g = currnum * 1000;
                ton = currnum/1000;
                oz = currnum * 35.274;
                lb = currnum * 2.205;
            }
            if(measure.equals("ton")) {
                ton = currnum;
                mg = currnum * 1e+9;
                g = currnum * 1e+6;
                kg = currnum * 1000;
                oz = currnum * 35274;
                lb = currnum * 2205;
            }
            if(measure.equals("oz")) {
                oz = currnum;
                mg = currnum * 28350;
                g = currnum * 28.35;
                kg = currnum/35.274;
                ton = currnum/35274;
                lb = currnum/16;
            }
            if(measure.equals("lb")) {
                lb = currnum;
                mg = currnum * 453592;
                g = currnum * 454;
                kg = currnum/2.205;
                ton = currnum/2205;
                oz = lb * 16;
            }
            textView = (TextView) findViewById(R.id.valOne);
            textView.setText(Double.toString(mg));
            textView = (TextView) findViewById(R.id.valTwo);
            textView.setText(Double.toString(g));
            textView = (TextView) findViewById(R.id.valThree);
            textView.setText(Double.toString(kg));
            textView = (TextView) findViewById(R.id.valFour);
            textView.setText(Double.toString(ton));
            textView = (TextView) findViewById(R.id.valFive);
            textView.setText(Double.toString(oz));
            textView = (TextView) findViewById(R.id.valSix);
            textView.setText(Double.toString(lb));
        }

        if(current == 2) {
            double c  = 0;
            double f = 0;
            double k = 0;
            if(measure.equals("C")) {
                c = currnum;
                f = (currnum * 9/5) + 32;
                k = currnum + 273.15;
            }
            if(measure.equals("F")) {
                f = currnum;
                c = (currnum - 32) * 5/9;
                k = c + 273.15;
            }
            if(measure.equals("K")) {
                k = currnum;
                f = (currnum - 273.15) * 9/5 + 32;
                c = currnum - 273.15;
            }
            textView = (TextView) findViewById(R.id.valOne);
            textView.setText(Double.toString(c));
            textView = (TextView) findViewById(R.id.valTwo);
            textView.setText(Double.toString(f));
            textView = (TextView) findViewById(R.id.valThree);
            textView.setText(Double.toString(k));
        }

    }
}