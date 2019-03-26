package com.example.wabprojectredo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.util.Log
import android.R
import android.net.Uri
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_music.*


//import com.sun.org.apache.xalan.internal.xsltc.compiler.Constants.REDIRECT_URI


class MusicActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.wabprojectredo.R.layout.activity_music)

        //TODO: make these be dynamic somehow. Maybe pull the urls from the database,
        //if i want to get fancy do a recycler view and have unlimited playlists
        btn_music_spotify1.setOnClickListener {
            val url = "https://open.spotify.com/playlist/75ARDXD31P1pM0jtVmc8eZ"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }

        btn_music_spotify2.setOnClickListener {
            val url = "https://open.spotify.com/playlist/4dJ8Ys2bOgWQIRvs7QSkeE"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }

        btn_music_youtube1.setOnClickListener {
            val url = "https://www.youtube.com/watch?v=Uim4GwSfzxY&list=PLBhIAkl3-Iovud5DyiASzw83S6d0fwOT2"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }

        btn_music_youtube2.setOnClickListener {
            val url = "https://www.youtube.com/watch?v=8rLTERuuxm4&list=PLBhIAkl3-Iovo1Bbw-k_NXGus-RnG2FtN"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }


    }
}

