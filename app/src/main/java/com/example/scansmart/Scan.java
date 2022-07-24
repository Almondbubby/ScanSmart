package com.example.scansmart;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Scan extends AppCompatActivity {

    TextView text;
    TextView title;

    int cal;
    String foodName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        title = findViewById(R.id.foodName);
        text = findViewById(R.id.foodText);
        ScanOptions options = new ScanOptions();
        options.setPrompt("Press volume up to turn flash on");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLauncher.launch(options);

        Button backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchActivities();
            }
        });

        Button listButton = (Button) findViewById(R.id.addToList);
        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Globals.calEaten += cal;
                Globals.foodNames.add(foodName);
                Globals.calorieList.add(cal);
                switchActivities();
            }
        });
    }


    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result -> {
        if(result.getContents() != null){
            callAPI(result.getContents());
        }
    });
    public void callAPI(String code){
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url("https://world.openfoodfacts.org/api/v0/product/"+code+".json").build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void run() {
                        text.setText("Failure!");
                    }
                });
            }
            @Override
            public void onResponse(Call call, final Response response) {
                runOnUiThread(new Runnable() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void run() {
                        try {
                            String responseBody = (Objects.requireNonNull(response.body())).string();
                            parse(responseBody);
                        } catch (NullPointerException | IOException ignored) {
                        }
                    }
                });
            }
        });

    }

    public void parse(String full){

        JSONObject ob = null;

        try {
            ob = new JSONObject(full);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String name = null;
        try {
            assert ob != null;
            name = ob.getJSONObject("product").getString("product_name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        foodName = name;
        title.setText(name);


        StringBuilder x = new StringBuilder();


        try {
            String kcal = ob.getJSONObject("product").getJSONObject("nutriments").getString("energy-kcal_serving");
            if(kcal.equals("")) kcal = "No infomation";
            x.append("Calories per serving: " + Integer.parseInt(kcal));
            cal = Integer.parseInt(kcal);
        }
        catch (Exception e) {
            e.printStackTrace();
            try {
                String kcal = ob.getJSONObject("product").getJSONObject("nutriments").getString("energy-kcal");
                if(kcal.equals("")) kcal = "No infomation";
                x.append("Calories: " + kcal);
                cal = Integer.parseInt(kcal);
            }
            catch (JSONException f) {
                f.printStackTrace();

                x.append("\nCalories: No information");

            }
        }

        try {
            String servingSize = ob.getJSONObject("product").getString("serving_size_imported");
            if(servingSize.equals("")) servingSize = "No infomation";
            x.append("\nServing Size: " + servingSize);
        }
        catch (JSONException e) {
            e.printStackTrace();
            String servingSize = null;
            try {
                servingSize = ob.getJSONObject("product").getString("serving_size");
                if(servingSize.equals("")) servingSize = "No infomation";
                x.append("\nServing Size: " + servingSize);
            } catch (JSONException ex) {
                ex.printStackTrace();
                x.append("\nServing Size: No information");
            }
        }


        try {
            String nutriScore = ob.getJSONObject("product").getString("nutriscore_grade");
            if(nutriScore.equals("")) nutriScore = "No infomation";
            x.append("\nNutriScore: " + nutriScore);
        }
        catch (JSONException e) {
            e.printStackTrace();
            x.append("\nNutriScore: No information");
        }

        try {
            String allergens = ob.getJSONObject("product").getString("allergens_imported");
            if(allergens.equals("")) allergens = "No allergens";
            x.append("Allergens: " + allergens);
        }
        catch (JSONException e) {
            String allergens = null;
            try {
                allergens = ob.getJSONObject("product").getString("allergens");
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
            if(allergens.equals("")) allergens = "No allergens";
            x.append("\nAllergens: " + allergens);
            e.printStackTrace();
        }

        try {
            String ecoScore = ob.getJSONObject("product").getString("ecoscore_score");
            if(ecoScore.equals("")) ecoScore = "No information";
            x.append("\nEcoScore: " + ecoScore);
        }
        catch (JSONException e) {
            x.append("\nEcoScore: No information");
            e.printStackTrace();
        }

        try {
            String carbs = ob.getJSONObject("product").getJSONObject("nutriments").getString("carbohydrates");
            Globals.totalCarbs += Double.parseDouble(carbs);
            if(carbs.equals("")) carbs = "No information";
            x.append("\nCarbs : " + carbs + " grams");
        }
        catch (JSONException e) {
            e.printStackTrace();
            x.append("\nCarbs: No information");

        }

        try {
            String fat = ob.getJSONObject("product").getJSONObject("nutriments").getString("fat");
            Globals.totalFat += Double.parseDouble(fat);
            if(fat.equals("")) fat = "No information";
            x.append("\nFat : " + fat + " grams");
        }
        catch (JSONException e) {
            e.printStackTrace();
            x.append("\nFat: No information");

        }

        try {
            String satFat = ob.getJSONObject("product").getJSONObject("nutriments").getString("saturated-fat");
            Globals.totalSatFat += Double.parseDouble(satFat);
            if(satFat.equals("")) satFat = "No information";
            x.append("\nSaturated Fat : " + satFat + " grams");
        }
        catch (JSONException e) {
            e.printStackTrace();
            x.append("\nSaturated Fat: No information");

        }

        try {
            String transFat = ob.getJSONObject("product").getJSONObject("nutriments").getString("trans-fat");
            Globals.totalTransFat += Double.parseDouble(transFat);
            if(transFat.equals("")) transFat = "No information";
            x.append("\nTrans Fat : " + transFat + " grams");
        }
        catch (JSONException e) {
            e.printStackTrace();
            x.append("\nTrans Fat: No information");

        }

        try {
            String iron = ob.getJSONObject("product").getJSONObject("nutriments").getString("iron");
            Globals.totalIron += Double.parseDouble(iron);
            if(iron.equals("")) iron = "No information";
            x.append("\nIron : " + iron + " milligrams");

        }
        catch (JSONException e) {
            e.printStackTrace();
            x.append("\nIron: No information");

        }

        try {
            String proteins = ob.getJSONObject("product").getJSONObject("nutriments").getString("proteins");
            Globals.totalProtein += Double.parseDouble(proteins);
            if(proteins.equals("")) proteins = "No information";
            x.append("\nProteins : " + proteins + " grams");

        }
        catch (JSONException e) {
            e.printStackTrace();
            x.append("\nProteins: No information");

        }

        try {
            String sugars = ob.getJSONObject("product").getJSONObject("nutriments").getString("sugars");
            Globals.totalSugar += Double.parseDouble(sugars);
            if(sugars.equals("")) sugars = "No information";
            x.append("\nSugar : " + sugars + " grams");
        }
        catch (JSONException e) {
            e.printStackTrace();
            x.append("\nSugar: No information");

        }

        try {
            String sodium = ob.getJSONObject("product").getJSONObject("nutriments").getString("sodium");
            Globals.totalSodium += Double.parseDouble(sodium);
            if(sodium.equals("")) sodium = "No information";
            x.append("\nSodium : " + sodium + " milligrams");
        }
        catch (JSONException e) {
            e.printStackTrace();
            x.append("\nSodium: No information");

        }

        text.setText(x);

    }

    private void switchActivities() {
        Intent switchActivityIntent = new Intent(this, Home.class);
        startActivity(switchActivityIntent);
    }

}