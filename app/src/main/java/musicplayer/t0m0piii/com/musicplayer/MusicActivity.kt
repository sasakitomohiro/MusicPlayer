package musicplayer.t0m0piii.com.musicplayer

import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import androidx.loader.content.CursorLoader
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import musicplayer.t0m0piii.com.musicplayer.databinding.ActivityMusicBinding

class MusicActivity : AppCompatActivity() {
    private val binding: ActivityMusicBinding by lazy {
      DataBindingUtil.setContentView<ActivityMusicBinding>(this, R.layout.activity_music)
    }
    private lateinit var cursor: Cursor
    private lateinit var cursorLoader: CursorLoader
    private lateinit var musicPlayerFragment: MusicPlayerFragment
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<FrameLayout>
    private val fragmentManager = supportFragmentManager
    private val homeIntent: Intent = Intent(Intent.ACTION_MAIN)
    private val groupAdapter = GroupAdapter<ViewHolder>()
    private val items: ArrayList<MusicItem> = ArrayList()
    private var pathIndex = 0
    private var nameIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        musicPlayerFragment = MusicPlayerFragment.newInstance().apply {
            val transaction = this@MusicActivity.fragmentManager.beginTransaction().replace(R.id.player, this)
            transaction.commit()
        }
        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheetBehavior)

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = groupAdapter
        val selection = MediaStore.Files.FileColumns.MEDIA_TYPE + "=" + MediaStore.Files.FileColumns.MEDIA_TYPE_AUDIO
        val  projection: Array<String> = arrayOf(MediaStore.Images.Media.DATA, MediaStore.Images.Media.DISPLAY_NAME)
        val queryUri = MediaStore.Files.getContentUri("external")
        cursorLoader = CursorLoader(
            applicationContext,
            queryUri,
            projection,
            selection,
            null,
            MediaStore.Files.FileColumns.DATE_ADDED + " DESC")
    }

    override fun onResume() {
        super.onResume()
        load()
    }

    override fun onBackPressed() {
        if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        } else {
            homeIntent.addCategory(Intent.CATEGORY_HOME)
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(homeIntent)
        }
    }

    private fun load() {
        items.clear()
        cursor = cursorLoader.loadInBackground()!!
        pathIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
        nameIndex = cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)
        cursor.moveToFirst()

        if (cursor.count == 0) {
            items.add(MusicItem("データがありません", null))
        } else {
            while (!cursor.isLast) {
                val name = cursor.getString(nameIndex)
                val path = cursor.getString(pathIndex)
                items.add(MusicItem(cursor.getString(nameIndex), View.OnClickListener {
                    musicPlayerFragment.playMusic(path, name)
                }))
                cursor.moveToNext()
            }
        }
        groupAdapter.update(items)
        cursor.close()
    }
}
