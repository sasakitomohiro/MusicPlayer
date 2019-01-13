package musicplayer.t0m0piii.com.musicplayer

import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.loader.content.CursorLoader
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder

class MainActivity : AppCompatActivity() {

    private lateinit var cursor: Cursor
    private val groupAdapter = GroupAdapter<ViewHolder>()
    private val items: ArrayList<MusicItem> = ArrayList()
    private val recyclerView by lazy {
        findViewById<RecyclerView>(R.id.recyclerView)
    }
    private lateinit var cursorLoader: CursorLoader
    private var pathIndex = 0
    private var nameIndex = 0
    private lateinit var musicPlayerFragment: MusicPlayerFragment
    private val fragmentManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        musicPlayerFragment = MusicPlayerFragment.newInstance().apply {
            val transaction = this@MainActivity.fragmentManager.beginTransaction().replace(R.id.player, this)
            transaction.commit()
        }

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = groupAdapter
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

    private fun load() {
        items.clear()
        cursor = cursorLoader.loadInBackground()!!
        pathIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
        nameIndex = cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)
        cursor.moveToFirst()

        while (!cursor.isLast) {
            val name = cursor.getString(nameIndex)
            val path = cursor.getString(pathIndex)
            items.add(MusicItem(cursor.getString(nameIndex), View.OnClickListener {
                musicPlayerFragment.playMusic(path, name)
            }))
            cursor.moveToNext()
        }
        groupAdapter.update(items)
        cursor.close()
    }
}
