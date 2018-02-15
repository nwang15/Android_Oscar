package ca.nait.nwang15.oscarproject;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;

public class oscar_list extends ListActivity {

    ArrayList<HashMap<String,String>> oscar = new ArrayList<HashMap<String, String>>();
    final String REVIEWER = "reviewer";
    final String TIME = "time";
    final String CATEGORY = "category";
    final String NOMINEE = "nominee";
    final String REVIEW = "review";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oscar_list);

        displayOscar();
    }

    private void displayOscar()
    {
        String[] keys = new String[]{TIME,REVIEWER, CATEGORY, NOMINEE,REVIEW};
        int[] ids = new int[]{R.id.custom_row_date,R.id.custom_row_reviewer,
                R.id.custom_row_category,R.id.custom_row_nominee,R.id.custom_row_review };

        SimpleAdapter adapter = new SimpleAdapter(this, oscar, R.layout.oscar_list_row, keys, ids);
        populateList();

        setListAdapter(adapter);
    }

    private void populateList()
    {
        BufferedReader in = null;
        try
        {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI("http://www.youcode.ca/Lab01Servlet"));
            HttpResponse response = client.execute(request);
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            String line = "";

            while((line = in.readLine()) != null)
            {
                HashMap<String,String> temp = new HashMap<String,String>();
                temp.put(TIME, line);

                line = in.readLine();
                temp.put(REVIEWER, line);

                line = in.readLine();
                temp.put(CATEGORY, line);

                line = in.readLine();
                temp.put(NOMINEE, line);

                line = in.readLine();
                temp.put(REVIEW, line);

                oscar.add(temp);

            }

            in.close();
        }
        catch (Exception e)
        {
            Toast.makeText(this, "Error: " + e, Toast.LENGTH_LONG).show();
        }
    }
}
