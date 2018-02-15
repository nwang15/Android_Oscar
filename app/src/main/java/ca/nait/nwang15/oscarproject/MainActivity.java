package ca.nait.nwang15.oscarproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
                                     SharedPreferences.OnSharedPreferenceChangeListener{

    SharedPreferences settings;
    View mainView;

//    RadioGroup radioGroup;
//    RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        settings = PreferenceManager.getDefaultSharedPreferences(this);
        settings.registerOnSharedPreferenceChangeListener(this);

        mainView = findViewById(R.id.send_activity_linear_layout);
        String bgColor = settings.getString("main_bg_color_list","#660000");
        mainView.setBackgroundColor(Color.parseColor(bgColor));


        //radioGroup = (RadioGroup) findViewById(R.id.radio_group_choice);

        if(android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Button sendButton = (Button)findViewById(R.id.send_oscar_button);
        sendButton.setOnClickListener(this);

//        Button viewButton = (Button)findViewById(R.id.send_oscar_button);
//        viewButton.setOnClickListener(this);

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s)
    {
        String bgColor = settings.getString("main_bg_color_list","#660000");
        mainView.setBackgroundColor(Color.parseColor(bgColor));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

//    public void onRadioButtonClicked(View view) {
//        int radioButtonID = radioGroup.getCheckedRadioButtonId();
//        radioButton = (RadioButton) findViewById(radioButtonID);
//    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {

            case R.id.menu_item_view:
            {
                Intent intent = new Intent(this, oscar_receive.class);
                this.startActivity(intent);
                break;
            }
            case R.id.menu_item_preference:
            {
                Intent intent = new Intent(this, oscar_preference.class);
                this.startActivity(intent);
                break;
            }
        }
        return true;
    }
    @Override
    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.send_oscar_button:
            {

                RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio_group_choice);

                int clickID = radioGroup.getCheckedRadioButtonId();
                View radioButton = radioGroup.findViewById(clickID);
                int radioChoice = radioGroup.indexOfChild(radioButton);
                String[] categoryList = new String[]{"film", "actor", "actress", "editing", "effects"};
                String Category =  categoryList[radioChoice];

                EditText nomiee = (EditText) findViewById(R.id.send_nominee_edit_text);
                EditText review = (EditText) findViewById(R.id.send_oscar_comment_text);

//                String Category = radioButton.getText().toString();
                String Review = review.getText().toString();
                String Nominee = nomiee.getText().toString();

                postToOscar(Review,Nominee,Category);
                review.setText("");
                nomiee.setText("");
                break;
            }
        }
        Intent intent = new Intent(this, oscar_receive.class);
        this.startActivity(intent);
    }

    private void postToOscar(String Review,String Nominee,String Category)
    {


        String userName = settings.getString("user_name", "unknown");
        String password = settings.getString("Password","111111");
        String ServerUrl = settings.getString("server_url", "http://www.youcode.ca/Lab01Servlet");
        try
        {
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost("http://www.youcode.ca/Lab01Servlet");



            List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
            postParameters.add(new BasicNameValuePair("REVIEW", Review));
            postParameters.add(new BasicNameValuePair("REVIEWER", userName));
            postParameters.add(new BasicNameValuePair("NOMINEE", Nominee));
            postParameters.add(new BasicNameValuePair("CATEGORY", Category));
            postParameters.add(new BasicNameValuePair("PASSWORD", password));
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(postParameters);
            post.setEntity(formEntity);
            client.execute(post);
        }
        catch(Exception e)
        {
            Toast.makeText(this, "Error: " + e, Toast.LENGTH_LONG).show();
        }

    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu)
//    {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.main_menu, menu);
//
//        return true;
//    }

}
