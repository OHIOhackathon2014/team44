package com.android.fittree;

import android.app.Activity;
import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.app.Fragment;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class PhoneMainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_main);
        if(savedInstanceState==null){
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }
    //keytool -list -v -alias androiddebugkey -keystore %%Your path%% -storepass android -keypass android
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        MenuInflater blowup = getMenuInflater();
        blowup.inflate(R.menu.phone_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id ==R.id.action_map){
            openMap();
        }else if (id ==R.id.aboutus){
            Intent intent = new Intent("android.intent.action.ABOUTUS");
            startActivity(intent);
        }else if (id==R.id.preferences){
            Intent intent = new Intent("android.intent.action.PREFS");
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }
        private ArrayAdapter<String> mMenuAdapter;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            String[] data = {"Maps","Fitness","Calories", "Shit have yet to happen"};
            List<String> Menu = new ArrayList<String>(Arrays.asList(data));

            mMenuAdapter = new ArrayAdapter<String>(
                    getActivity(),
                    R.layout.list_item_menu,
                    R.id.list_item_menu_textview,
                    Menu
            );

            View rootView = inflater.inflate(R.layout.fragment_phone_main, container, false);
            ListView listView =(ListView)rootView.findViewById(
                    R.id.listview_main);
            listView.setAdapter(mMenuAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    String forecast =mMenuAdapter.getItem(position);
                    if(forecast.equals("Maps")) {
                        Intent intent = new Intent(getActivity(), mapActivity.class);
                        startActivity(intent);
                    }
                }
            });
            return rootView;
        }
    }
    private void openMap(){
        final String Log_tag = PhoneMainActivity.class.getSimpleName();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String location = prefs.getString(getString(R.string.pref_location_key),getString(R.string.pref_location_default));

        Uri geoLocation =Uri.parse("geo:0,0?").buildUpon()
                .appendQueryParameter("q",location)
                .build();
        Log.d("Check this", geoLocation.toString());
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);

        if(intent.resolveActivity(getPackageManager())!=null){
            Log.d(Log_tag, "We are in " + location);
            startActivity(intent);
        }else{
            Log.d(Log_tag,"Couldn't call "+ location + ",no such place");
        }
    }
}