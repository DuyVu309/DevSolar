package com.nor.mp3music.ui

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nor.filternews.base.ActivityBase
import com.nor.filternews.base.FragmentBase
import com.nor.mp3music.R
import com.nor.mp3music.dao.SystemDao
import com.nor.mp3music.databinding.ActivityMainBinding
import com.nor.mp3music.models.Album
import com.nor.mp3music.models.Artist
import com.nor.mp3music.models.Song
import com.nor.mp3music.ui.fragments.AlbumFragment
import com.nor.mp3music.ui.fragments.ArtistFragment
import com.nor.mp3music.ui.fragments.SongFragment

class MainActivity : ActivityBase<ActivityMainBinding>(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var dao: SystemDao

    private val PERMISSIONS = arrayOf<String>(
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    private val fmSong = SongFragment()
    private val fmArtist = ArtistFragment()
    private val fmAlbum = AlbumFragment()

    private val fms = arrayOf(fmAlbum, fmArtist, fmSong)

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initAct() {
        super.initAct()
        initFragment()
        showFragment(fmSong)
        dao = SystemDao(this)
        doRequestPermission(PERMISSIONS, {
            val songs = dao.getMedia(Song::class.java)
            val artist = dao.getMedia(Artist::class.java)
            val album = dao.getMedia(Album::class.java)
            val a = 3
        })

        binding.navBottom.setOnNavigationItemSelectedListener(this)
    }

    private fun initFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        fms.forEach {
            transaction.add(R.id.panel, it)
        }
        transaction.commitAllowingStateLoss()
    }

    fun showFragment(fm: FragmentBase<*>) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        fms.forEach {
            transaction.hide(it)
        }
        transaction.show(fm)
        transaction.commitAllowingStateLoss()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_song -> showFragment(fmSong)
            R.id.nav_album -> showFragment(fmAlbum)
            R.id.nav_artist -> showFragment(fmArtist)
        }
        return true
    }
}
