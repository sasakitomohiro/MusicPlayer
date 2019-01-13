package musicplayer.t0m0piii.com.musicplayer

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import musicplayer.t0m0piii.com.musicplayer.databinding.FragmentMusicPlayerBinding

class MusicPlayerFragment : Fragment() {
    private lateinit var binding: FragmentMusicPlayerBinding
    private lateinit var uri: Uri
    private lateinit var player: SimpleExoPlayer
    private lateinit var mediaSource: MediaSource
    private lateinit var extractorMediaSourceFactory: ExtractorMediaSource.Factory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMusicPlayerBinding.inflate(inflater, container, false)

        uri = Uri.parse("")
        player = ExoPlayerFactory.newSimpleInstance(
            DefaultRenderersFactory(context),
            DefaultTrackSelector(),
            DefaultLoadControl()
        )
        extractorMediaSourceFactory = ExtractorMediaSource.Factory(DefaultDataSourceFactory(context, "MusicPlayer"))
        mediaSource = extractorMediaSourceFactory.createMediaSource(uri)
        player.prepare(mediaSource)
        player.playWhenReady = false
        binding.playerView.player = player

        return binding.root
    }

    fun playMusic(path: String, name: String) {
        player.playWhenReady = false
        binding.name.text= name
        uri = Uri.parse(path)
        mediaSource = extractorMediaSourceFactory.createMediaSource(uri)
        player.prepare(mediaSource)
        player.playWhenReady = true
    }

    companion object {
        fun newInstance() = MusicPlayerFragment()
    }
}