package com.example.madproject1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.accessibilityservice.FingerprintGestureController;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    // Assigning all the Elements on the Screen to Variables.
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

    Boolean somethingChosen = false;

    ArrayList Resistor_Numbers = new ArrayList();
    // ============================================================

    int stage = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Linking Variables to their elements on the Main Activity.
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


        tvYourResult = findViewById(R.id.tvYourResult);
        tvResultContainer = findViewById(R.id.tvResultContainer);
        // ============================================================
    }

    // Call the ChooseColour Function so that the autocorrect
    // doesn't break if you leave and come back to the app.
    @Override
    public void onResume(){
        super.onResume();
        if (stage == 0)
        {
            ChooseColour();
            tvWhatColour.setText("What Colour is Band " + (stage + 1) + "?");
        }
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

        Object[] list = {Band_Colours, Multiplier_Colours, Tolerance_Colours};




        if (stage<1){
            ArrayAdapter<String> itemAdapter1 = new ArrayAdapter<>(MainActivity.this, R.layout.drop_down_item, Band_Colours);
            autoCompleteTextView.setAdapter(null);
            autoCompleteTextView.setAdapter(itemAdapter1);
            autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                        Titles[stage].setText((String) Band_Colours[position]);
                        Bands[stage].setBackgroundColor(Color.parseColor(Band_Hex_Codes[position]));
                        Resistor_Numbers.add(Integer.parseInt(Band_Values[position]));
                        somethingChosen = true;
                    GoToNextStage(autoCompleteTextView);
                }
            });

        } else if (stage == 2){
            ArrayAdapter<String> itemAdapter2 = new ArrayAdapter<>(MainActivity.this, R.layout.drop_down_item, Multiplier_Colours);
            autoCompleteTextView.setAdapter(null);
            autoCompleteTextView.setAdapter(itemAdapter2);
            autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    Titles[stage].setText((String) Multiplier_Colours[position]);
                    Bands[stage].setBackgroundColor(Color.parseColor(Multiplier_Hex_Codes[position]));
                    Resistor_Numbers.add(Multiplier_Values[position]);
                    somethingChosen = true;
                    GoToNextStage(autoCompleteTextView);
                }
            });
        } else if(stage > 2){
            ArrayAdapter<String> itemAdapter3 = new ArrayAdapter<>(MainActivity.this, R.layout.drop_down_item, Tolerance_Colours);
            autoCompleteTextView.setAdapter(null);
            autoCompleteTextView.setAdapter(itemAdapter3);
            autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                        Titles[stage].setText((String) Tolerance_Colours[position]);
                        Bands[stage].setBackgroundColor(Color.parseColor(Tolerance_Hex_Codes[position]));
                        Resistor_Numbers.add(Double.parseDouble(Tolerance_Values[position]));
                        somethingChosen = true;
                        GoToNextStage(autoCompleteTextView);
                }
            });
        }
    }


    public void GoToNextStage(View view) {
        if (somethingChosen && stage <4){
            stage++;
            somethingChosen = false;
            ChooseColour();
            tvWhatColour.setText("What Colour is Band " + (stage + 1) + "?");
        }
        else{
            Toast.makeText(MainActivity.this, "Please Choose a Colour", Toast.LENGTH_SHORT).show();
        }

        if (stage == 4 ){
            DisplayResults();
            Resistor_Numbers.clear();

        }

    }

   public void DisplayResults(){
        String ToleranceAns = Resistor_Numbers.get(3).toString();
        String Multiplier = Resistor_Numbers.get(2).toString();
        Double Band_Number_Concat;
        Double DeciamalMultipliers;
        Double temp;
        String First_Second_Bands_Concat = "";
        Band_Number_Concat = concatenateDigits((Integer) Resistor_Numbers.get(0),(Integer) Resistor_Numbers.get(1));

        if (Pattern.compile("^\\d+$").matcher(Multiplier).matches()){
            if (Integer.parseInt(Multiplier) == 1){
                Multiplier = "";
            }else if(Integer.parseInt(Multiplier) == 9){
                DeciamalMultipliers = Band_Number_Concat / 10;
                First_Second_Bands_Concat = DeciamalMultipliers.toString();
                Multiplier = "";
            }else if(Integer.parseInt(Multiplier) == 99) {
                DeciamalMultipliers = Band_Number_Concat / 100;
                First_Second_Bands_Concat = DeciamalMultipliers.toString();
                Multiplier = "";
            }
        }
            First_Second_Bands_Concat = String.format("%.0f", Band_Number_Concat);



        Log.e("","-------------------------");
        Log.e("",First_Second_Bands_Concat);
        Log.e("",Multiplier);
        Log.e("",ToleranceAns);
        Log.e("","-------------------------");

       tvResultContainer.setText(First_Second_Bands_Concat + Multiplier+ " Ω at ± "+ ToleranceAns + "%");

        textInputLayout.setVisibility(View.GONE);
        autoCompleteTextView.setVisibility(View.GONE);
        tvWhatColour.setVisibility(View.GONE);
        tvResultContainer.setVisibility(View.VISIBLE);
        tvYourResult.setVisibility(View.VISIBLE);
   }


    public void TryAgain(View view) {
        stage = 0;
        tvWhatColour.setText("What Colour is Band " + (stage + 1) + "?");
        Resistor_Numbers = new ArrayList();

        tvColourOneTitle.setText("");
        tvColourTwoTitle.setText("");
        tvMultiplierTitle.setText("");
        tvToleranceTitle.setText("");
        BandOne.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.emptyrectagle));
        BandTwo.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.emptyrectagle));
        BandThree.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.emptyrectagle));
        BandFour.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.emptyrectagle));

        textInputLayout.setVisibility(View.VISIBLE);
        autoCompleteTextView.setVisibility  (View.VISIBLE);
        tvWhatColour.setVisibility(View.VISIBLE);

        tvResultContainer.setVisibility(View.GONE);
        tvYourResult.setVisibility(View.GONE);
        ChooseColour();
    }



    public static Double concatenateDigits(int... digits) {
        StringBuilder sb = new StringBuilder(digits.length);
        for (int digit : digits) {
            sb.append(digit);
        }
        return Double.parseDouble(sb.toString());
    }

}