package musicplayer.t0m0piii.com.musicplayer

import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.loader.content.CursorLoader
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

class MainActivity : AppCompatActivity() {

    private lateinit var uri: Uri
    private lateinit var player: SimpleExoPlayer
    private lateinit var mediaSource: MediaSource
    private lateinit var extractorMediaSourceFactory: ExtractorMediaSource.Factory
    private lateinit var cursor: Cursor
    private val playerView by lazy {
        findViewById<PlayerView>(R.id.playerView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val selection = MediaStore.Files.FileColumns.MEDIA_TYPE + "=" + MediaStore.Files.FileColumns.MEDIA_TYPE_AUDIO
        val  projection: Array<String> = arrayOf(MediaStore.Images.Media.DATA, MediaStore.Images.Media.DISPLAY_NAME)
        val queryUri = MediaStore.Files.getContentUri("external")
        val cursorLoader = CursorLoader(
            applicationContext,
            queryUri,
            projection,
            selection,
            null,
            MediaStore.Files.FileColumns.DATE_ADDED + " DESC")

        cursor = cursorLoader.loadInBackground()!!
        val pathIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()

        uri = Uri.fromFile(File(cursor.getString(pathIndex)))
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
