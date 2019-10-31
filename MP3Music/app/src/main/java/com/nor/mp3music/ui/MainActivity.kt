package com.nor.mp3music.ui

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
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
import com.nor.mp3music.service.MP3Service
import com.nor.mp3music.ui.fragments.AlbumFragment
import com.nor.mp3music.ui.fragments.ArtistFragment
import com.nor.mp3music.ui.fragments.song.SongFragment

class MainActivity : ActivityBase<ActivityMainBinding>(), BottomNavigationView.OnNavigationItemSelectedListener {

    private val PERMISSIONS = arrayOf<String>(
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    private val fmSong = SongFragment()
    private val fmArtist = ArtistFragment()
    private val fmAlbum = AlbumFragment()

    private val fms = arrayOf(fmAlbum, fmArtist, fmSong)

    var service: MP3Service? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initAct() {
        super.initAct()
        doRequestPermission(PERMISSIONS, {
            initFragment()
            showFragment(fmSong)
            binding.navBottom.setOnNavigationItemSelectedListener(this)
            val intent = Intent(this, MP3Service::class.java)
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
            startService(intent)
        })
    }

    private val connection = object : ServiceConnection {
        override fun onServiceDisconnected(p0: ComponentName?) {

        }

        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            if(p1 is MP3Service.MP3Binder) {
                service = p1.service
                binding.playView.service = service
            }
        }

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

    override fun onDestroy() {
        super.onDestroy()
        unbindService(connection)
    }

}
