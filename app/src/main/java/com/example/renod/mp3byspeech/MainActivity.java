package com.example.renod.mp3byspeech;

import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.media.MediaPlayer;
import java.util.Random;

public class MainActivity extends Activity {

    private TextView txtSpeechInput;
    private ImageButton btnSpeak;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private MediaPlayer player = new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtSpeechInput = (TextView) findViewById(R.id.txtSpeechInput);
        btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);

        // hide the action bar
        //getActionBar().hide();

        btnSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });

    }

    /**
     * Showing google speech input dialog
     * */
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    analyzer(result);
                }
                break;
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void analyzer(ArrayList<String> result) {
        String[] sentenceSplitted = result.get(0).split(" ");
        String verb = sentenceSplitted[0];
        String music = "";
        int cpt = 1;
        for(String tmp : sentenceSplitted) {
            if(cpt++ > 1) music += tmp + " ";
        }
        switch(verb) {
            case "jouer":
                txtSpeechInput.setText(launchMusic(music));
                break;
            case "lancer":
                txtSpeechInput.setText(launchMusic(music));
                break;
            case "launch":
                txtSpeechInput.setText(launchMusic(music));
                break;
            case "joue":
                txtSpeechInput.setText(launchMusic(music));
                break;
            case "play":
                txtSpeechInput.setText(launchMusic(music));
                break;
            case "lance":
                txtSpeechInput.setText(launchMusic(music));
                break;
            case "stop":
                txtSpeechInput.setText(sentenceSplitted[0]);
                player.release();
                break;
            case "arrêter":
                txtSpeechInput.setText(sentenceSplitted[0]);
                player.release();
                break;
            case "arrête":
                txtSpeechInput.setText(sentenceSplitted[0]);
                player.release();
                break;
            case "écouter":
                txtSpeechInput.setText(launchMusic(music));
                break;
            case "pause":
                txtSpeechInput.setText(sentenceSplitted[0]);
                player.pause();
                break;
            case "reprendre":
                player.start();
                break;
            case "resume":
                player.start();
                break;
            case "random":
                txtSpeechInput.setText(randomMusic());
                break;
            case "fermer":
                System.exit(0);
                break;
            case "close":
                System.exit(0);
                break;
            default:
                txtSpeechInput.setText("Je n'ai pas compris. Veuillez répéter.");
                break;
        }
    }

    public String launchMusic(String musicName) {
        if (player.isPlaying()){
            player.release();
            player = new MediaPlayer();
        }
        if (musicName.contains("coconut ")) {
            player = MediaPlayer.create(this, R.raw.coconut);
            player.start();
            return "coconut playing";
        }else if(musicName.contains("corobizar ")){
            player = MediaPlayer.create(this, R.raw.corobizar);
            player.start();
            return "Corobizar playing";
        }else if(musicName.contains("hôtel California ")) {
            player = MediaPlayer.create(this, R.raw.hotelcalifornia);
            player.start();
            return "Hotel California playing";
        }else if(musicName.contains("Marvin Gaye ")) {
            player= MediaPlayer.create(this, R.raw.ihearditthroughthegrapevine);
            player.start();
            return "I heard it through the grapevine playing";
        }
        return "Aucune musique de ce nom";
    }

    public String randomMusic() {
        String[] randomSong = {"coconut ", "corobizar ", "hôtelCalifornia ", "Marvin Gaye "};
        Random r = new Random();
        String randomMusic = randomSong[r.nextInt(3)];
        return(launchMusic(randomMusic));

    }

}