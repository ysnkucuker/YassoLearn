package com.yasinkucuker.yassolearn;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Speaking extends AppCompatActivity {

    private EditText editTextSpeech;
    private Button buttonRecord;
    private Button buttonPlay;

    private TextView textView;

    private SpeechRecognizer speechRecognizer;
    private Intent speechRecognizerIntent;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_speaking);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        textView = findViewById(R.id.textView);
        editTextSpeech = findViewById(R.id.editTextSpeech);
        buttonRecord = findViewById(R.id.buttonRecord);
        buttonPlay = findViewById(R.id.buttonPlay);

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");

        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {
                editTextSpeech.setText("");
                editTextSpeech.setHint("Konuşmayı başlatın...");
            }

            @Override
            public void onBeginningOfSpeech() {
                editTextSpeech.setHint("Konuşma algılanıyor...");
            }

            @Override
            public void onRmsChanged(float rmsdB) {}

            @Override
            public void onBufferReceived(byte[] buffer) {}

            @Override
            public void onEndOfSpeech() {
                editTextSpeech.setHint("Konuşma sonlandı. Sonuçlar alınıyor...");
            }

            @Override
            public void onError(int error) {
                editTextSpeech.setHint("Konuşma algılanamadı. Tekrar deneyin.");
            }

            @Override
            public void onResults(Bundle results) {
                ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (matches != null && !matches.isEmpty()) {
                    editTextSpeech.setText(matches.get(0));
                }
            }

            @Override
            public void onPartialResults(Bundle partialResults) {}

            @Override
            public void onEvent(int eventType, Bundle params) {}
        });

        buttonRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    speechRecognizer.startListening(speechRecognizerIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(Speaking.this, "Cihazınız ses tanıma özelliğini desteklemiyor.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GenerativeModel gm =
                        new GenerativeModel(
                                /* modelName */ "gemini-1.5-flash",

                                /* apiKey */ "AIzaSyCRnlHvyDipTw8HxfW6SdUIiVoHN2ugK_I");
                GenerativeModelFutures model = GenerativeModelFutures.from(gm);

                Content content =
                        new Content.Builder().addText("Kullanıcıdan gelen cevaba göre tek cümle ingilizce dialog devam ettir." + editTextSpeech.getText().toString()).build();


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
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (speechRecognizer != null) {
            speechRecognizer.destroy();
        }
    }

    public void buttonCallGeminiAPI(View view){

    }


}