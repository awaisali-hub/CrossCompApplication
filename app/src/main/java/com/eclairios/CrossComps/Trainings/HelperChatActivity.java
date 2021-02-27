package com.eclairios.CrossComps.Trainings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.eclairios.CrossComps.Adapter.ChatAdapter;
import com.eclairios.CrossComps.BackgroundTask;
import com.eclairios.CrossComps.EventDetailActivity;
import com.eclairios.CrossComps.Model.Chat_Model;
import com.eclairios.CrossComps.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class HelperChatActivity extends AppCompatActivity {

    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;

    RecyclerView recyclerView;
    List<Chat_Model> chat_model;
    ChatAdapter chatadapter;

    EditText input_message;
    String currentUserID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helper_chat);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(HelperChatActivity.this);
        currentUserID = preferences.getString("CurrentUserId", "");


        input_message = findViewById(R.id.input_message);
        recyclerView = findViewById(R.id.show_message_recyclerview);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        chat_model = new ArrayList<>();
        chatadapter = new ChatAdapter(this,chat_model);
        recyclerView.setAdapter(chatadapter);

        new BackgroundTaskShowData().execute();

    }

    public void sendMessage(View view) {

        Chat_Model chats = new Chat_Model();
        chats.setMessage(input_message.getText().toString());
        chats.setSenderID(currentUserID);
        chat_model.add(chats);
        chatadapter.notifyDataSetChanged();


        String method = "SendUserMessage";
        String message = input_message.getText().toString();

        BackgroundTask backgroundTask = new BackgroundTask(HelperChatActivity.this);
        backgroundTask.execute(method,currentUserID,message);
        input_message.setText("");
    }

    public void MoveToMyTrainingProgram5(View view) {
        startActivity(new Intent(HelperChatActivity.this,AfterFitnessConsultationActivity.class));
    }


    class BackgroundTaskShowData extends AsyncTask<String, Void, String>
    {
        String json_url;
        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));


                String data = URLEncoder.encode("senderID","UTF-8") + "=" + URLEncoder.encode(currentUserID,"UTF-8")+ "&"+
                        URLEncoder.encode("receiverID","UTF-8") + "=" + URLEncoder.encode("1","UTF-8") ;
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String response = "";
                String line = "";
                while( (line = bufferedReader.readLine()) != null)
                {
                    response += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return response;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPreExecute() {
            json_url = "http://edevz.com/cross_comp/get_chat_message.php";
        }
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
        @Override
        protected void onPostExecute(String result) {

            json_string = result;
            Log.e("bcjknjkksdjc ", "onCreate: "+json_string );
            try {
                jsonObject = new JSONObject(json_string);
                jsonArray = jsonObject.getJSONArray("server_response");
                int count = 0;
                String message,sender_ID,time,message_format;
                while(count < jsonArray.length())
                {
                    JSONObject JO = jsonArray.getJSONObject(count);

                    sender_ID = JO.getString("Sender_ID");
                    message = JO.getString("Message");


                    Chat_Model chats = new Chat_Model();
                    chats.setSenderID(sender_ID);
                    chats.setMessage(message);

                    chat_model.add(chats);
                    count++;
                }
                chatadapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}