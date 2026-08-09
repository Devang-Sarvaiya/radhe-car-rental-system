// ChatMain.java
package com.example.caronrentrenter.chatbot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.caronrentrenter.R;

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

public class ChatMain extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView welcome;

    EditText edt;
    ImageButton send;

    List<Messege> messegeList;

    MessegeAdapter messegeAdapter;

    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_main);

        messegeList = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView);
        welcome = findViewById(R.id.welcome);
        edt = findViewById(R.id.edt);
        send = findViewById(R.id.send);

        messegeAdapter = new MessegeAdapter(messegeList);
        recyclerView.setAdapter(messegeAdapter);

        Animatoo.INSTANCE.animateShrink(ChatMain.this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        // Set OnClickListener for the send button
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });

        // Set OnEditorActionListener for the EditText
        edt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_SEND ||
                        (keyEvent != null && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN)) {
                    // If "Done" or "Send" button is pressed or "Enter" key is pressed
                    sendMessage();
                    return true;
                }
                return false;
            }
        });

        // Register the RecyclerView for the context menu
        registerForContextMenu(recyclerView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.copy_text) {
            // Get the position of the long-pressed item
            int position = messegeAdapter.getLongPressedItemPosition();
            // Get the message at the position
            String message = messegeList.get(position).getMsg();

            // Copy the message to the clipboard
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Message", message);
            clipboard.setPrimaryClip(clip);

            Toast.makeText(this, "Message copied", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onContextItemSelected(item);
    }

    // Method to send the message
    private void sendMessage() {
        String question = edt.getText().toString().trim();

        if (TextUtils.isEmpty(question)) {
            Toast.makeText(ChatMain.this, "Please Enter Something!", Toast.LENGTH_SHORT).show();
        } else {
            addtoChat(question, Messege.SENT_BY_ME);
            edt.setText("");
            callAPI(question);
            welcome.setVisibility(View.GONE);
        }
    }

    void addtoChat(String message, String sentBy) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                messegeList.add(new Messege(message, sentBy));
                messegeAdapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(messegeAdapter.getItemCount());
            }
        });
    }

    void addResponse(String response) {
        messegeList.remove(messegeList.size() - 1);
        addtoChat(response, Messege.SENT_BY_BOT);
    }

    void callAPI(String question) {
        messegeList.add(new Messege("Typing.. ", Messege.SENT_BY_BOT));

        JSONObject jsonBody = null;
        try {
            jsonBody = new JSONObject();
            jsonBody.put("model", "gpt-3.5-turbo");

            JSONArray jsonArray = new JSONArray();
            JSONObject object = new JSONObject();

            object.put("role", "user");
            object.put("content", question);

            jsonArray.put(object);
            jsonBody.put("messages", jsonArray);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        RequestBody body = RequestBody.create(jsonBody.toString(), JSON);
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .header("Authorization", "Bearer YOUR_OPENAI_API_KEY") // Configure your own OpenAI API key — do not commit a real value
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                addResponse("Failed to get Response due to " + e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        JSONArray jsonArray = jsonObject.getJSONArray("choices");
                        String result = jsonArray.getJSONObject(0).getJSONObject("message").getString("content");
                        addResponse(result.trim());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    addResponse("Failed to get Response due to " + response.body().string());
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.INSTANCE.animateShrink(ChatMain.this);
    }
}
