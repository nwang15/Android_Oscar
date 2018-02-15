package ca.nait.nwang15.oscarproject;


import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceActivity;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class oscar_preference extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener{

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.addPreferencesFromResource(R.xml.preferences);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.registerOnSharedPreferenceChangeListener(this);

//        mainView = findViewById(R.id.layout_preferences);
        String bgColor = preferences.getString("main_bg_color","#cccccc");
        this.getListView().setBackgroundColor(Color.parseColor(bgColor));



        //setContentView(R.layout.activity_oscar_preference);
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s)
    {
        String bgColor = preferences.getString("main_bg_color","#cccccc");
        this.getListView().setBackgroundColor(Color.parseColor(bgColor));
        this.finish();
    }
}
