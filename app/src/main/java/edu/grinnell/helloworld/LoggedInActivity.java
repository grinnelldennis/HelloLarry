package edu.grinnell.helloworld;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.ArrayList;

public class LoggedInActivity extends Activity {

    private String TAG = LoggedInActivity.class.getSimpleName();
    private ListView someListView;

    ArrayList<HashMap<String, String>> memberList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);

        memberList = new ArrayList<>();
        someListView = (ListView) findViewById(R.id.someListView);

        new GetMembers().execute();
        /*
        String[] members = {"Larry", "Also Larry", "Larray Wut", "Still Larry", "Neverending Larry"};
        ListAdapter someAdapter = new CustomAdapter(this, members);
        ListView someListView = (ListView) findViewById(R.id.someListView);
        someListView.setAdapter(someAdapter);*/
    }

    private class GetMembers extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(LoggedInActivity.this, "JSON Data is downloading", Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            //request
            String url = "http://www.cs.grinnell.edu/~pradhanp/android.json";
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    //get json array node
                    JSONArray members = jsonObj.getJSONArray("members");

                    for (int i = 0; i < members.length(); i++) {
                        JSONObject m = members.getJSONObject(i);

                        HashMap<String, String> member = new HashMap<>();
                        member.put("name", m.getString("name"));
                        member.put("year", m.getString("year"));
                        member.put("cellphone", m.getString("cellphone"));
                        member.put("email", m.getString("email"));
                        member.put("image", m.getString("image"));

                        memberList.add(member);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            ListAdapter someAdapter = new SimpleAdapter(LoggedInActivity.this, memberList,
                    R.layout.list_item, new String[]{ "email","mobile"},
                    new int[]{R.id.email, R.id.mobile});
            someListView.setAdapter(someAdapter);
        }
    }


}
