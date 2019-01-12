package musicplayer.t0m0piii.com.musicplayer

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import java.io.File

class MusicPlayerActivity : AppCompatActivity() {
    private lateinit var uri: Uri
    private lateinit var player: SimpleExoPlayer
    private lateinit var mediaSource: MediaSource
    private lateinit var extractorMediaSourceFactory: ExtractorMediaSource.Factory
    private val playerView by lazy {
        findViewById<PlayerView>(R.id.playerView)
    }
    private val title by lazy {
        findViewById<TextView>(R.id.title)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_player)

        val intent = getIntent()
        Log.d("uri", intent.getStringExtra("uri"))
        uri = Uri.fromFile(File(intent.getStringExtra("uri")))
        title.text = intent.getStringExtra("title")
        player = ExoPlayerFactory.newSimpleInstance(
            DefaultRenderersFactory(applicationContext),
            DefaultTrackSelector(),
            DefaultLoadControl()
        )
        extractorMediaSourceFactory = ExtractorMediaSource.Factory(DefaultDataSourceFactory(applicationContext, "MusicPlayer"))
        mediaSource = extractorMediaSourceFactory.createMediaSource(uri)
        player.prepare(mediaSource)
        player.playWhenReady = false
        playerView.player = player
    }

    override fun onBackPressed() {
        super.onBackPressed()
        player.playWhenReady = false
        finish()
    }
}