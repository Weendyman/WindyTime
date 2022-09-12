package com.example.windtime3;

import static java.lang.Math.log;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.constraintlayout.widget.Placeholder;
import androidx.fragment.app.FragmentActivity;

import com.example.windtime3.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;


public class
MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    public String baseUrl = "https://api.openweathermap.org/data/2.5/";
    public static String appId = "eeceb4e5d659ceb2fcf468d6db42a87d";
    public static String lat = "18.70685806067791";
    public static String lon = "-68.44111568510208";
    public static String metric ="metric";

    private TextView item1;
    private TextView item2;
    private TextView item3;
    private TextView item4;
    private TextView item5;
    private TextView item6;
    private TextView item7;
    private TextView item8;
    private TextView item9;
    private TextView item10;
    private  TextView item82;
    private TextView item122;
    private TextView item164;
    private ImageView view1;
    private ImageView image1;
    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    public class Place   {
        String name;
        Double lat;
        Double lng;
     public Place(String name,double lat,double lng){
       this.name =name;
       this.lat=lat;
       this.lng=lng;
     }
    @Override
        public String toString(){
        return  name;
    }
    }

    public  void getCurrentData(Place place){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        WeatherApi weatherApi = retrofit.create(WeatherApi.class);


        Call<Root> call = weatherApi.getCurrentWeatherData(place.lat.toString(),place.lng.toString(),appId,metric);

        call.enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                if (response.isSuccessful()) {
                    Log.d("mytag", "response" + response.body());
                    Root root = response.body();
                    item1.setText(""+root.city.name);
                    item2.setText(""+root.list.get(0).dt_txt);
                    item3.setText(""+root.list.get(1).dt_txt);
                    item4.setText(""+root.list.get(2).dt_txt);
                    item5.setText(""+root.list.get(3).dt_txt);
                    item6.setText(""+root.list.get(4).dt_txt);
                    item7.setText(""+root.list.get(5).dt_txt);
                    item8.setText(""+root.list.get(6).dt_txt);
                    item82.setText(""+ root.list.get(0).wind.speed);
                    item122.setText(""+root.list.get(0).wind.gust);
                    item164.setText(""+root.list.get(0).main.temp);


                    view1.setRotation(root.list.get(0).wind.deg);

                } else {

                    Log.d("mytag", "error" + response.code());
                }


            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {

                Log.d("mytag", "error" + t.getLocalizedMessage());

            }
        });





    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        Spinner spinner = findViewById(R.id.spinner);

        item1 = findViewById(R.id.item1);
        item2 =findViewById(R.id.item2);
        item3=findViewById(R.id.item3);
        item4=findViewById(R.id.item4);
        item5=findViewById(R.id.item5);
        item6=findViewById(R.id.item6);
        item7=findViewById(R.id.item7);
        item8=findViewById(R.id.item8);
        item9=findViewById(R.id.item9);
        item10=findViewById(R.id.item10);
        item82=findViewById(R.id.item82);
        item122=findViewById(R.id.item122);
        item164=findViewById(R.id.item164);


        view1=findViewById(R.id.imageView1);
        view1.setImageResource(R.drawable.strelka);




        Place start = new Place(" ",0,0);
        Place bavaro =new Place("Bavaro",18.70685806067791,-68.44111568510208);
        Place borovsk =new Place("Borovsk",55.191885,  36.515605);

        ArrayList<Place> plases =new ArrayList<>();

        plases.add(bavaro);
        plases.add(borovsk);
        plases.add(new Place("Novoseltsvo",55.998922, 37.590274));
        plases.add(new Place("Elgouna",27.318422,  33.712308));
        plases.add(new Place("Pereslavl-Zalessky" , 56.749528,  38.845007));
        plases.add(new Place("Mezhvodnoe" ,45.556208, 32.830862));






        ArrayAdapter<Place> arrayAdapter = new ArrayAdapter<Place>(this, android.R.layout.simple_spinner_item,plases);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        try {
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Place place= (Place) parent.getSelectedItem();
                    setMapPosition(place);
                    getCurrentData(place);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
        catch (Exception ex){

            Log.d("bla","error"+ex.getMessage());
        }









        /*OkHttpClient client = new OkHttpClient();
        String url=" https://api.openweathermap.org/data/2.5/forecast?lat=18.70685806067791&lon=-68.44111568510208&appid=eeceb4e5d659ceb2fcf468d6db42a87d&units=metric";
        Request request=new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    final String myResponse=response.body().string();
                    ObjectMapper om = new ObjectMapper();
                    om.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                    Root root = om.readValue(myResponse, Root.class);


                    MapsActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            item1.setText(""+root.list.get(0).wind.speed);
                        }
                    });

                }
            }
        });

    */



    }


 public  void  setMapPosition(Place place)

 {

     LatLng position =new LatLng(place.lat,place.lng);
     CameraUpdate camPosition = CameraUpdateFactory.newLatLng(position);
     mMap.moveCamera(camPosition);
     mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
     mMap.addMarker(new MarkerOptions().position(position));

 }



























    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        LatLng bavaro = new LatLng(18.70685806067791,-68.44111568510208);
        mMap.addMarker(new MarkerOptions().position(bavaro).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bavaro, 15.0f));
        mMap.getUiSettings().setZoomControlsEnabled(true);

        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
    }
}