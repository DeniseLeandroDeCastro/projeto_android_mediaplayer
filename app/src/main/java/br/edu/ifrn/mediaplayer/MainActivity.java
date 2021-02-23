package br.edu.ifrn.mediaplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private SeekBar seekVolume;
    private AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.teste);
        inicializarSeekBar();
    }
    private void inicializarSeekBar() {
        seekVolume = findViewById(R.id.seekVolume);

        //Configura o audio manager
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        //Recupera os valores de volume máximo e o volume atual
        int volumeMaximo = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int volumeAtual = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        //Configura os valores máximos para o Seekbar
        seekVolume.setMax(volumeMaximo);

        //Configura o progresso atual do Seekbar
        seekVolume.setProgress(volumeAtual);

        //Método que permite o controle do volume pelo usuário quando arrastar o seekbar
        seekVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    //Método para executar o som
    public void executarSom(View view) {
        if(mediaPlayer != null) {
            mediaPlayer.start();
        }
    }
    //Método para pausar a música
    public void pausarMusica(View view) {
        if(mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }
    //Método para parar a música
    public void pararMusica(View view) {
        if(mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.teste);
        }
    }

    //Método para parar a música, quando o usuário sair da tela em que a música está sendo executada
    //Assim economiza recursos do usuário
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mediaPlayer !=null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release(); //libera a música da memória (economia de recursos)
            mediaPlayer = null;

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }
}