package com.example.nutritionscan;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    Button btn_scan;
    TextView text;
    TextView title;
    ImageView foodImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_scan = findViewById(R.id.btn_scan);
        title = findViewById(R.id.title);
        text = findViewById(R.id.text);
        btn_scan.setOnClickListener(v->{
            scanCode();
        });
    }
    private void scanCode(){
        ScanOptions options = new ScanOptions();
        options.setPrompt("Press volume up to turn flash on");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLauncher.launch(options);
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
        title.setText(name);


        StringBuilder x = new StringBuilder();



        try {
            String allergens = ob.getJSONObject("product").getString("allergens_imported");
            if(allergens.equals("")) allergens = "No allergens";
            x.append("Allergens: " + allergens);
        } catch (JSONException e) {
            String allergens = null;
            try {
                allergens = ob.getJSONObject("product").getString("allergens");
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
            if(allergens.equals("")) allergens = "No allergens";
            x.append("Allergens: " + allergens);
            e.printStackTrace();
        }

        try {
            String ecoScore = ob.getJSONObject("product").getString("ecoscore_score");
            if(ecoScore.equals("")) ecoScore = "No information";
            x.append("\nEcoScore: " + ecoScore);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            String servingSize = ob.getJSONObject("product").getString("serving_size_imported");
            x.append("\nServing Size: " + servingSize);
        } catch (JSONException e) {
            e.printStackTrace();
        }



        text.setText(x);

    }


}