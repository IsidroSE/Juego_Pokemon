package com.example.isidr.juego_pokemon;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button jugar, creditos;
    private MediaPlayer reproductor;

    @Override
     protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //CON ESTA ORDEN HACES QUE NO APAREZCA LA BARRIT AZUL CON EL NOMBRE DEL PROYECTO
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        jugar = (Button)findViewById(R.id.jugar);
        creditos = (Button)findViewById(R.id.acerca);
        reproductor = MediaPlayer.create(this, R.raw.musicafondo);
        //Con esta orden si termina la música, se volverá a repetir de nuevo infinitas veces
        reproductor.setLooping(true);
        reproductor.start();

        jugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nuevoform = new Intent(MainActivity.this, jugar.class);
                startActivity(nuevoform);
            }
        });

        creditos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nuevoform = new Intent(MainActivity.this, creditos.class);
                startActivity(nuevoform);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(reproductor.isPlaying()) {
            reproductor.stop();
            reproductor.release();
        }
    }

    @Override
    protected void onResume () {
        super.onResume();
        reproductor.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        reproductor.pause();
    }
}
