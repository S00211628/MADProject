package com.example.madproject1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    TextInputLayout textInputLayout;
    AutoCompleteTextView autoCompleteTextView;

    TextView tvColourOneTitle;
    TextView tvColourTwoTitle;
    TextView tvMultiplierTitle;
    TextView tvToleranceTitle;

    TextView tvWhatColour;

    TextView tvYourResult;
    TextView tvResultContainer;

    View BandOne;
    View BandTwo;
    View BandThree;
    View BandFour;

    Button btnconfm;
    Boolean somethingChoosen = false;

   List<Integer> Numbers;

    int stage = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textInputLayout = findViewById(R.id.textInputLayout);
        autoCompleteTextView = findViewById(R.id.autoCompleteTV);

        tvWhatColour = findViewById(R.id.tvWhatColourIs);

        tvColourOneTitle = findViewById(R.id.tvColourOneTitle);
        tvColourTwoTitle = findViewById(R.id.tvColourTwoTitle);
        tvMultiplierTitle = findViewById(R.id.tvMultiplierTitle);
        tvToleranceTitle = (TextView) findViewById(R.id.tvToleranceTitle);


        BandOne = findViewById(R.id.BandOne);
        BandTwo = findViewById(R.id.BandTwo);
        BandThree = findViewById(R.id.BandThree);
        BandFour = findViewById(R.id.BandFour);

        btnconfm = findViewById(R.id.btnConfim);

        tvYourResult = findViewById(R.id.tvYourResult);
        tvResultContainer = findViewById(R.id.tvResultContainer);



        tvWhatColour.setText("What Colour is Band " + (stage + 1) + "?");





    }
    @Override
    public void onResume(){
        super.onResume();
        ChooseColour();

    }


    public void ChooseColour(){


        TextView[] Titles = {tvColourOneTitle, tvColourTwoTitle, tvMultiplierTitle, tvToleranceTitle};
        View[] Bands = {BandOne, BandTwo, BandThree, BandFour};

        String[] Band_Hex_Codes = getResources().getStringArray(R.array.Band_Hex_Codes);
        String[] Band_Colours = getResources().getStringArray(R.array.Band_Colours);
        String[] Band_Values = getResources().getStringArray(R.array.Band_Values);

        String[] Multiplier_Hex_Codes = getResources().getStringArray(R.array.Multiplier_Hex_Codes);
        String[] Multiplier_Colours = getResources().getStringArray(R.array.Multiplier_Colours);
        String[] Multiplier_Values = getResources().getStringArray(R.array.Multiplier_Values);

        String[] Tolerance_Hex_Codes = getResources().getStringArray(R.array.Tolerance_Hex_Codes);
        String[] Tolerance_Colours = getResources().getStringArray(R.array.Tolerance_Colours);
        String[] Tolerance_Values = getResources().getStringArray(R.array.Tolerance_Values);

        ArrayAdapter<String> itemAdapter = new ArrayAdapter<>(MainActivity.this, R.layout.drop_down_item, Band_Colours);
        autoCompleteTextView.setAdapter(itemAdapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Titles[stage].setText((String) Band_Colours[position]);
                Bands[stage].setBackgroundColor(Color.parseColor(Band_Hex_Codes[position]));
                somethingChoosen = true;
                Log.e("", Integer.toString(stage));
            }
        });

        if (stage >= 1) {

            ArrayAdapter<String> itemAdapter1 = new ArrayAdapter<>(MainActivity.this, R.layout.drop_down_item, Multiplier_Colours);
            autoCompleteTextView.setAdapter(itemAdapter1);
            autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    Titles[stage].setText((String) Multiplier_Colours[position]);
                    Bands[stage].setBackgroundColor(Color.parseColor(Multiplier_Hex_Codes[position]));
                    somethingChoosen = true;
                    Log.e("","here");
                }
            });
        }
        if (stage > 2) {
            ArrayAdapter<String> itemAdapter3 = new ArrayAdapter<>(MainActivity.this, R.layout.drop_down_item, Tolerance_Colours);
            autoCompleteTextView.setAdapter(itemAdapter3);
            autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    Titles[stage].setText((String) Tolerance_Colours[position]);
                    Bands[stage].setBackgroundColor(Color.parseColor(Tolerance_Hex_Codes[position]));
                    somethingChoosen = true;
                }
            });
        }
    }

    public void GoToNextStage(View view) {

        if (somethingChoosen){
            stage++;
            somethingChoosen = false;
        }
        else{
            Toast.makeText(MainActivity.this, "Please Choose a Colour", Toast.LENGTH_SHORT).show();
        }

        if (stage == 4 ){
            DisplayResults();
        }
    }




   public void DisplayResults(){

//        calcNumber();
       tvResultContainer.setText("52m Ohms at 5%");

        btnconfm.setVisibility(View.GONE);
        textInputLayout.setVisibility(View.GONE);
        autoCompleteTextView.setVisibility(View.GONE);
        tvWhatColour.setVisibility(View.GONE);

        tvResultContainer.setVisibility(View.VISIBLE);
        tvYourResult.setVisibility(View.VISIBLE);
   }


    public void TryAgain(View view) {
        stage = 0;

        tvColourOneTitle.setText("");
        tvColourTwoTitle.setText("");
        tvMultiplierTitle.setText("");
        tvToleranceTitle.setText("");
        BandOne.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.emptyrectagle));
        BandTwo.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.emptyrectagle));
        BandThree.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.emptyrectagle));
        BandFour.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.emptyrectagle));

            btnconfm.setVisibility(View.VISIBLE);
            textInputLayout.setVisibility(View.VISIBLE);
            autoCompleteTextView.setVisibility  (View.VISIBLE);
            tvWhatColour.setVisibility(View.VISIBLE);

            tvResultContainer.setVisibility(View.GONE);
            tvYourResult.setVisibility(View.GONE);
    }





    public void calcNumber(){
        tvResultContainer.setText(Numbers.get(0));
    }
}