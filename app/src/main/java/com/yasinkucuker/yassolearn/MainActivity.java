package com.yasinkucuker.yassolearn;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.yasinkucuker.yassolearn.Adapters.CategoryAdapter;
import com.yasinkucuker.yassolearn.Models.Category;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private CategoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        listView = findViewById(R.id.listView);
        List<Category> categories = new ArrayList<>();

        // Kategorileri oluştur
        categories.add(new Category("Grammar", "Dil bilgisi kuralları ve yapıları", R.drawable.ic_launcher_foreground));
        categories.add(new Category("Reading", "Okuma ve anlama becerileri", R.drawable.ic_launcher_foreground));
        categories.add(new Category("Speaking", "Konuşma pratiği ve telaffuz", R.drawable.ic_launcher_foreground));
        categories.add(new Category("Listening", "Dinleme ve anlama çalışmaları", R.drawable.ic_launcher_foreground));
        categories.add(new Category("Writing", "Yazma ve kompozisyon teknikleri", R.drawable.ic_launcher_foreground));
        categories.add(new Category("Vocabulary", "Kelime hazinesi geliştirme", R.drawable.ic_launcher_foreground));
        categories.add(new Category("Idioms", "Deyimler ve kalıp ifadeler", R.drawable.ic_launcher_foreground));
        categories.add(new Category("Translate", "Çeviri araçları", R.drawable.ic_launcher_foreground));

        adapter = new CategoryAdapter(this, categories);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Category category = categories.get(position);
            Toast.makeText(MainActivity.this,
                    category.getTitle(),
                    Toast.LENGTH_SHORT).show();

            if(position == 0){
                Intent intent = new Intent(MainActivity.this, Grammar.class);
                startActivity(intent);
            }

            if(position == 1){
                Intent intent = new Intent(MainActivity.this, Reading.class);
                startActivity(intent);
            }

            if(position == 2){
                Intent intent = new Intent(MainActivity.this, Speaking.class);
                startActivity(intent);
            }

            if(position == 3){
                Intent intent = new Intent(MainActivity.this, Listening.class);
                startActivity(intent);
            }

            if(position == 4){
                Intent intent = new Intent(MainActivity.this, Writing.class);
                startActivity(intent);
            }

            if(position == 5){
                Intent intent = new Intent(MainActivity.this, Vocabulary.class);
                startActivity(intent);
            }

            if(position == 6){
                Intent intent = new Intent(MainActivity.this, Idioms.class);
                startActivity(intent);
            }

            if(position == 7){
                Intent intent = new Intent(MainActivity.this, Translate.class);
                startActivity(intent);
            }
        });

    }
}