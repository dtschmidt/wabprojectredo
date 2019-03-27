package com.example.wabprojectredo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
//import com.example.wabproject.ChatRoom1Activity
import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        btn_chat_chatroom1.setOnClickListener {
            val intent = Intent(this, ChatRoom1Activity::class.java)
            startActivity(intent)
        }

        btn_chat_chatroom2.setOnClickListener {
            val intent = Intent(this, ChatRoom2Activity::class.java)
            startActivity(intent)
        }
    }


}
