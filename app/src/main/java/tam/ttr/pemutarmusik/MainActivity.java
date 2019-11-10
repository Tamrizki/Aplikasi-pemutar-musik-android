package tam.ttr.pemutarmusik;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

//    DEKLARASI VARIABEL
    private Button btn_play, btn_pause, btn_stop;
    private MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Inisialisasi();

        btn_play.setOnClickListener(this);
        btn_pause.setOnClickListener(this);
        btn_stop.setOnClickListener(this);
        kondisiAwal();
    }

//    METHODE UNTUK INISIALISASI
    private void Inisialisasi() {
        btn_play = findViewById(R.id.play_msc);
        btn_pause = findViewById(R.id.pause_msc);
        btn_stop = findViewById(R.id.stop_msc);
    }

//    ATUR KONDISI AWAL
    private void kondisiAwal(){
        btn_play.setEnabled(true);
        btn_pause.setEnabled(false);
        btn_stop.setEnabled(false);
    }

    //Method untuk memainkan musik
    private void playAudio(){
        //Menentukan resource audio yang akan dijalankan
        mediaPlayer = MediaPlayer.create(this, R.raw.buzzer);

        //Kondisi Button setelah tombol play di klik
        btn_play.setEnabled(false);
        btn_pause.setEnabled(true);
        btn_stop.setEnabled(true);

        //Menjalankan Audio / Musik
        try{
            mediaPlayer.prepare();
        }catch (IllegalStateException ex){
            ex.printStackTrace();
        }catch (IOException ex1){
            ex1.printStackTrace();
        }
        mediaPlayer.start();

        //Setelah audio selesai dimainkan maka kondisi Button akan kembali seperti awal
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                kondisiAwal();
            }
        });
    }

    //Method untuk mengentikan musik
    private void pauseAudio(){

        //Jika audio sedang dimainkan, maka audio dapat di pause
        if(mediaPlayer.isPlaying()){
            if(mediaPlayer != null){
                mediaPlayer.pause();
                btn_pause.setText("Lanjutkan");
            }
        }else {
            //Jika audio sedang di pause, maka audio dapat dilanjutkan kembali
            if(mediaPlayer != null){
                mediaPlayer.start();
                btn_pause.setText("Pause");
            }
        }

    }

    //Method untuk mengakhiri musik
    private void stopAudio(){
        mediaPlayer.stop();
        try {
            //Menyetel audio ke status awal
            mediaPlayer.prepare();
            mediaPlayer.seekTo(0);
        }catch (Throwable t){
            t.printStackTrace();
        }
        kondisiAwal();
    }

    @Override
    public void onClick(View view) {
        if (view == btn_play){
            playAudio();
        }
        else if (view == btn_pause){
            pauseAudio();
        }
        else if (view == btn_stop){
            stopAudio();
        }
    }
}
