package com.example.isidr.juego_pokemon;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Isidro on 12/10/2015.
 */
public class jugar extends AppCompatActivity {

    private String [] nombre_pokemon = {"arbok","beedrill","blastoise","bulbasaur","butterfree","caterpie","charizard","charmander",
    "charmeleon","cloyster","ekans","fearow","gloom","golem","ivysaur","kakuna","krabby","magmar","marowak","metapod","nidoqueen",
    "nidoran","nidorina","pidgeot","pidgeotto","pidgey","pikachu","raichu","raticate","rattata","sandshrew","sandslash","snorlax",
    "spearow","squirtle","starmie","venusaur","vulpix","wartortle","weedle",};

    private String [] sombra_pokemon = {"s_arbok","s_beedrill","s_blastoise","s_bulbasaur","s_butterfree","s_caterpie","s_charizard",
    "s_charmander","s_charmeleon","s_cloyster","s_ekans","s_fearow","s_gloom","s_golem","s_ivysaur","s_kakuna","s_krabby","s_magmar",
    "s_marowak","s_metapod","s_nidoqueen","s_nidoran","s_nidorina","s_pidgeot","s_pidgeotto","s_pidgey","s_pikachu","s_raichu",
    "s_raticate","s_rattata","s_sandshrew","s_sandslash","s_snorlax","s_spearow","s_squirtle","s_starmie","s_venusaur","s_vulpix",
    "s_wartortle","s_weedle",};

    private int intentos = 3;
    private Button aceptar;
    private TextView mostrar_intentos, mensaje_cuenta;
    private EditText usuario_pokemon;
    private int numGenerado;
    private ImageView imagen;
    private MediaPlayer reproductor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //CON ESTA ORDEN HACES QUE NO APAREZCA LA BARRIT AZUL CON EL NOMBRE DEL PROYECTO
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_jugar);

        aceptar = (Button)findViewById(R.id.jugar_aceptar);
        mostrar_intentos = (TextView)findViewById(R.id.tvintentos);
        mensaje_cuenta = (TextView)findViewById(R.id.jugar_cuenta);
        usuario_pokemon = (EditText)findViewById(R.id.etpokemon);
        imagen = (ImageView)findViewById(R.id.jugar_imagen);
        reproductor = MediaPlayer.create(this, R.raw.atrapalosya);
        reproductor.setLooping(true);
        reproductor.start();
        numGenerado = generarAleatorio();
        establecer_sombra(numGenerado);
        mostrar_intentos.setText("Tienes " + intentos + " intentos");

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = usuario_pokemon.getText().toString().toLowerCase();
                if (nombre.equals(nombre_pokemon[numGenerado])) {
                    establecer_pokemon(numGenerado);
                    esperar();
                }
                else {
                    displayToast("Has fallado!");
                    intentos = intentos-1;
                    mostrar_intentos.setText("Te quedan " + intentos + " intentos");
                }

                if (intentos == 0) {
                    finish();
                }
            }
        });
    }

    private void esperar () {
        //Esperaremos 5 segundos, el contador bajara de 1 en 1
        new CountDownTimer(10000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                mensaje_cuenta.setText("Generando nuevo Pokemon en: " + (millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {
                numGenerado = generarAleatorio();
                establecer_sombra(numGenerado);
                mensaje_cuenta.setText("");
                usuario_pokemon.setText("");
            }
        }.start();
    }

    private void establecer_pokemon (int num) {
        int resId = getResources().getIdentifier(nombre_pokemon[num], "drawable", getPackageName());
        imagen.setImageResource(resId);
    }

    private void establecer_sombra (int num) {
        int resId = getResources().getIdentifier(sombra_pokemon[num], "drawable", getPackageName());
        imagen.setImageResource(resId);
    }

    private int generarAleatorio() {
        return (int)(Math.random()*nombre_pokemon.length);
    }

    public void displayToast (String text) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
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
