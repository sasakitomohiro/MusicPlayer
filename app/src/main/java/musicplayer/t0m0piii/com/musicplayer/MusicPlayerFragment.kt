package musicplayer.t0m0piii.com.musicplayer

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
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
        player.addListener(object: Player.EventListener {
            override fun onTracksChanged(trackGroups: TrackGroupArray?, trackSelections: TrackSelectionArray?) {
            }

            override fun onPlayerError(error: ExoPlaybackException?) {
            }

            override fun onLoadingChanged(isLoading: Boolean) {
            }

            override fun onPositionDiscontinuity(reason: Int) {
            }

            override fun onRepeatModeChanged(repeatMode: Int) {
            }

            override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
            }

            override fun onTimelineChanged(timeline: Timeline?, manifest: Any?, reason: Int) {
            }

            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
            }

            override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters?) {
            }

            override fun onSeekProcessed() {
            }

        })

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        player.playWhenReady = false
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