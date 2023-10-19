package com.ademcinarci.videoplayer;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ui.StyledPlayerView;

import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ExoPlayer player;
    //Buraya video url'si girilecek.
    String videoUrl = "video-url";
    //Buraya canlı tv url'si gelecek.
    String TVUrl = "livetv-url";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Video URL'sini kontrol et
        if (videoUrl == null || videoUrl.isEmpty()) {
            Toast.makeText(this, "Video URL'si eksik veya hatalı.", Toast.LENGTH_SHORT).show();
            return;
        } else {
            Toast.makeText(this, "Video URL'sinde sorun yok.", Toast.LENGTH_SHORT).show();
        }

        StyledPlayerView playerView = findViewById(R.id.playerView);
        player = new ExoPlayer.Builder(MainActivity.this).build();
        playerView.setPlayer(player);
        MediaItem mediaItem = MediaItem.fromUri(videoUrl);
        player.setMediaItem(mediaItem);
        player.prepare();
        player.setPlayWhenReady(true);

        player.addListener(new Player.Listener() {

            public void onPlayerError(ExoPlaybackException error) {
                if (error.type == ExoPlaybackException.TYPE_SOURCE) {
                    // Bağlantı hatası oluştu
                    Toast.makeText(MainActivity.this, "Bağlantı hatası oluştu. Video oynatılamadı.", Toast.LENGTH_SHORT).show();
                } else if (error.type == ExoPlaybackException.TYPE_RENDERER) {
                    // Video render hatası oluştu
                    Toast.makeText(MainActivity.this, "Video render hatası oluştu.", Toast.LENGTH_SHORT).show();
                } else if (error.type == ExoPlaybackException.TYPE_UNEXPECTED) {
                    // Beklenmeyen bir hata oluştu
                    Toast.makeText(MainActivity.this, "Beklenmeyen bir hata oluştu.", Toast.LENGTH_SHORT).show();
                }

                // Hatanın ayrıntılarını Logcat üzerinden incelemek için:
                Log.e("ExoPlayerError", "Hata türü: " + error.type);
                error.printStackTrace();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (player != null) {
            // Oynatıcı başlatılıyor
            Toast.makeText(this, "Oynatıcı başlatılıyor...", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (player != null) {
            // Oynatıcı durduruluyor
            Toast.makeText(this, "Oynatıcı durduruluyor...", Toast.LENGTH_SHORT).show();
            player.setPlayWhenReady(false);
            player.release();
            player = null;
        }
    }
}
