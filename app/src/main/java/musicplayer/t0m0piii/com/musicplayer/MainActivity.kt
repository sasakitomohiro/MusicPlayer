package musicplayer.t0m0piii.com.musicplayer

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory

class MainActivity : AppCompatActivity() {

    private lateinit var uri: Uri
    private lateinit var player: SimpleExoPlayer
    private lateinit var mediaSource: MediaSource
    private lateinit var extractorMediaSourceFactory: ExtractorMediaSource.Factory
    private val playerView by lazy {
        findViewById<PlayerView>(R.id.playerView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        uri = Uri.parse("hogehoge")
        player = ExoPlayerFactory.newSimpleInstance(
            DefaultRenderersFactory(applicationContext),
            DefaultTrackSelector(),
            DefaultLoadControl()
        )
        extractorMediaSourceFactory = ExtractorMediaSource.Factory(DefaultDataSourceFactory(applicationContext, "ExoPlayerSample"))
        mediaSource = extractorMediaSourceFactory.createMediaSource(uri)
        player.prepare(mediaSource)
        player.playWhenReady = false
        playerView.player = player
    }
}
