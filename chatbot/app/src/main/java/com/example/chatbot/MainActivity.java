package com.example.chatbot;
import android.content.Intent;
import android.util.Log;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    EditText messageBox;
    ImageView sendBtn,cross;
    RecyclerView recyclerView;
    List<MessageModel> messageModelList;
    MessageAdapter messageAdapter;


    String key="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";

    public static final MediaType JSON = MediaType.get("application/json");
    OkHttpClient client = new OkHttpClient();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        messageBox=findViewById(R.id.editTextText);
        recyclerView=findViewById(R.id.recyclerView);
        sendBtn=findViewById(R.id.imageButton);
        cross=findViewById(R.id.imageView4);

        SessionManager sessionManager = new SessionManager(MainActivity.this);
        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionManager.logoutUser();
                startActivity(new Intent(MainActivity.this, Sign_in.class));
                finish();
            }
        });

        messageModelList=new ArrayList<>();
        messageAdapter=new MessageAdapter(messageModelList);
        recyclerView.setAdapter(messageAdapter);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);


        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String question=messageBox.getText().toString().trim();

                addToChat(question,MessageModel.sent_by_me);
                messageBox.setText("");
                callAPI(question);
            }
        });
    }
//
private void addToChat(String message, String sentBy){
    runOnUiThread(() -> {
        messageModelList.add(new MessageModel(message, sentBy));
        messageAdapter.notifyItemInserted(messageModelList.size() - 1);

        recyclerView.post(() -> {
            recyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);
        });
    });
}


    private  void response(String response){
        addToChat(response,MessageModel.sent_by_bot);
    }

    // treatment using chatgpt code
    private void callAPI(String question) {
        JSONObject jsonObject = new JSONObject();

        try {

            jsonObject.put("model", "openai/gpt-4o");
            jsonObject.put("max_tokens", 500);

            JSONArray messages = new JSONArray();
            JSONObject userMessage = new JSONObject();
            userMessage.put("role", "user");
            userMessage.put("content", question);
            messages.put(userMessage);

            jsonObject.put("messages", messages);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        RequestBody body = RequestBody.create(jsonObject.toString(), JSON);
        Request request = new Request.Builder()
                .url("https://openrouter.ai/api/v1/chat/completions")
                .header("Authorization", "Bearer " + key)
                .header("Content-Type", "application/json")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(() -> response("Failed: " + e.getMessage()));
            }


            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String responseBody = response.body().string();

                Log.d("API_RESPONSE", responseBody);  // Add this line

                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject1 = new JSONObject(responseBody);
                        JSONArray choices = jsonObject1.getJSONArray("choices");

                        // Try both options based on actual structure
                        String content;
                        if (choices.getJSONObject(0).has("message")) {
                            content = choices.getJSONObject(0).getJSONObject("message").getString("content");
                        } else {
                            content = choices.getJSONObject(0).getString("content");
                        }

                        runOnUiThread(() -> response(content.trim()));
                    } catch (JSONException e) {
                        runOnUiThread(() -> response("Error parsing response:\n" + e.getMessage()));
                    }
                } else {
                    runOnUiThread(() -> response("API Error: " + response.code() + "\n" + responseBody));
                }
            }


        });
    }


}