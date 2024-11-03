package com.yasinkucuker.yassolearn;

import android.content.Intent;
import android.os.Bundle;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Listening extends AppCompatActivity {

    EditText editText;
    TextView textView;

    private TextToSpeech textToSpeech;
    private Button buttonPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_listening);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        textView = findViewById(R.id.textView);
        textView.setMovementMethod(new ScrollingMovementMethod());
        editText = findViewById(R.id.promptEdit);

        textToSpeech = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                textToSpeech.setLanguage(Locale.US);
            } else {
                Toast.makeText(Listening.this, "Text to speech initialization failed", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void buttonCallGeminiAPI(View view){

        GenerativeModel gm =
                new GenerativeModel(
                        /* modelName */ "gemini-1.5-flash",
                        /* apiKey */ "AIzaSyCRnlHvyDipTw8HxfW6SdUIiVoHN2ugK_I");
        GenerativeModelFutures model = GenerativeModelFutures.from(gm);

        Content content =
                new Content.Builder().addText("Kullanıcının girdiği sözcük veya cümleye göre ingilizce hikaye anlat " +
                        "Kullanıcının girdiği kelime : " + editText.getText().toString()).build();


        Executor executor = Executors.newSingleThreadExecutor();

        ListenableFuture<GenerateContentResponse> response = model.generateContent(content);
        Futures.addCallback(
                response,
                new FutureCallback<GenerateContentResponse>() {
                    @Override
                    public void onSuccess(GenerateContentResponse result) {
                        String resultText = result.getText();
                        textView.setText(resultText);
                        System.out.println(resultText);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        textView.setText(t.toString());
                        t.printStackTrace();
                    }
                },
                executor);
    }

    public void buttonCallAudition(View view){
        textToSpeech.speak(textView.getText().toString(), TextToSpeech.QUEUE_FLUSH, null, null);
    }

    protected void onDestroy() {
        super.onDestroy();
        textToSpeech.stop();
        textToSpeech.shutdown();
    }


}