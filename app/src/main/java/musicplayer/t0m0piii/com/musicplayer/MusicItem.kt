package musicplayer.t0m0piii.com.musicplayer

import android.view.View
import com.xwray.groupie.databinding.BindableItem
import musicplayer.t0m0piii.com.musicplayer.databinding.ItemMusicBinding

class MusicItem(val name: String, val onClickListener: View.OnClickListener?) : BindableItem<ItemMusicBinding>() {
    override fun getLayout(): Int = R.layout.item_music

    override fun bind(viewBinding: ItemMusicBinding, position: Int) {
        viewBinding.name.text = name
        viewBinding.root.setOnClickListener(onClickListener)
    }

    override fun getSpanSize(spanCount: Int, position: Int): Int {
        return 1
    }
}