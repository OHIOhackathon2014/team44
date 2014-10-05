package com.android.fittree;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import static com.google.android.gms.common.GooglePlayServicesUtil.isGooglePlayServicesAvailable;


public class mapActivity extends Activity {
    static final LatLng TutorialsPoint = new LatLng(0 , 0);
    private GoogleMap googleMap;
    LatLng startLatLng = new LatLng(82.88, 40.00);
    /* private PolylineOptions rectOpt = new PolylineOptions()
             .add(new LatLng(37.35, -122.0))
             .add(new LatLng(37.35, -122.0))
             .add(new LatLng(37.35, -122.0))
             .add(new LatLng(37.35, -122.0))
             .add(new LatLng(37.35, -122.0));*/
    private List<LatLng> list = new ArrayList<LatLng>();
    private Marker TP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        int check = isGooglePlayServicesAvailable(this);
        /*try {
            if (googleMap == null) {*/
        googleMap = ((MapFragment) getFragmentManager().
                findFragmentById(R.id.map)).getMap();
        //}
        googleMap.setMyLocationEnabled(true);
        //Location myLocation = googleMap.getMyLocation();
        //googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(myLocation.getLatitude(),myLocation.getLongitude())));
        //googleMap.animateCamera(CameraUpdateFactory.zoomTo(12));


        googleMap.setOnMapLongClickListener(myOnMapLongClickListener);
        Polygon polygon = googleMap.addPolygon(new PolygonOptions()
                .add(new LatLng(37.35, -122.0),
                        new LatLng(37.35, -122.0),
                        new LatLng(37.35, -122.0),
                        new LatLng(37.35, -122.0),
                        new LatLng(37.35, -122.0))
                .strokeWidth(5)
                .strokeColor(Color.BLUE));

        // }
        /*googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            Polyline polyline =googleMap.addPolyline(rectOpt);
            polyline.setPoints(list);
            TP.setPosition(new LatLng(39.57,82.59));
            googleMap.addPolyline(rectOpt);
            for(LatLng item : list){
                TP = googleMap.addMarker(new MarkerOptions().position(item));
            }
        } catch(Exception e){
            e.printStackTrace();
            Log.d("Map", "Shit Happen");*/
    }
    GoogleMap.OnMapLongClickListener myOnMapLongClickListener= new GoogleMap.OnMapLongClickListener() {
        @Override
        public void onMapLongClick(LatLng point) {
            googleMap.addMarker(new MarkerOptions()
                    .position(point)
                    .title(point.toString()));

            Location myLocation = googleMap.getMyLocation();
            if(myLocation==null){
                Toast.makeText(getApplicationContext(), "My location not available", Toast.LENGTH_LONG).show();

            }else{
                PolylineOptions polylineoptions = new PolylineOptions();
                polylineoptions.add(point);
                polylineoptions.add(new LatLng(myLocation.getLatitude(), myLocation.getLongitude()));
                googleMap.addPolyline(polylineoptions);
            }
        }
    };



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_map, container, false);
            return rootView;
        }
    }
}
