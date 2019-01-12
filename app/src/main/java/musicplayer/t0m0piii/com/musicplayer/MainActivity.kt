package musicplayer.t0m0piii.com.musicplayer

import android.content.Intent
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.loader.content.CursorLoader
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import java.io.File

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

    override fun onStop() {
        super.onStop()
        cursor.close()
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
                val intent = Intent(this, MusicPlayerActivity::class.java)
                intent.putExtra("uri", path)
                intent.putExtra("title", name)
                startActivity(intent)
            }))
            cursor.moveToNext()
        }
        groupAdapter.update(items)
    }
}
