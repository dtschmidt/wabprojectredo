package com.example.wabprojectredo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.wabprojectredo.classes.ChatFromItem
import com.example.wabprojectredo.classes.ChatMessage
import com.example.wabprojectredo.classes.ChatToItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_chat_room1.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

//hi
class ChatRoom2Activity : AppCompatActivity() {

    companion object {
        //tag used in logs for the activity
        val TAG = "ChatRoom2Log"

        val roomName = "Chat Room 2"
    }

    //hello
    val adapter = GroupAdapter<ViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_room1)


        recyclerview_messages.adapter = adapter
        //setupDummyData()
        listenForMessages()

        btn_cr1_send.setOnClickListener {
            Log.d(TAG, "Attempt to send message..")
            performSendMessage()
        }
    }

    private fun listenForMessages(){
        //orderbychild orders the posts by their timestamps. limittolast gets the most recent 100 posts based on that order
        val ref = FirebaseDatabase.getInstance().getReference("/messages/ChatRoom2").orderByChild("longtimestamp").limitToLast(100)

        ref.addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val chatMessage = p0.getValue(ChatMessage::class.java)

                if(chatMessage != null) {
                    Log.d(TAG, chatMessage?.text)

                    //if the sender of the message is the logged in user, display it as such
                    if (chatMessage.fromId == FirebaseAuth.getInstance().uid)
                        adapter.add(ChatToItem(chatMessage?.text, chatMessage?.fromUsername, chatMessage.timestamp))
                    //else display it as a message from another person
                    else
                        adapter.add(ChatFromItem(chatMessage?.text, chatMessage?.fromUsername, chatMessage.timestamp))
                }

                //sets screen to view recent messages first
                recyclerview_messages.scrollToPosition(adapter.itemCount - 1)
            }


            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildRemoved(p0: DataSnapshot) {

            }
        })
    }

    private fun performSendMessage(){
        val text = edittxt_cr1_messageentry.text.toString()

        //get id and display name from current user
        val instance = FirebaseAuth.getInstance()
        val fromId = instance.currentUser?.uid
        val fromUsername = instance.currentUser?.displayName

        //get current time
        val currentTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        val currentTimeFormatted = currentTime.format(formatter).toString()


        if (fromId == null) return

        val reference = FirebaseDatabase.getInstance().getReference("/messages/ChatRoom2").push()

        val chatMessage = ChatMessage(reference.key!!, text, fromId, fromUsername, roomName, currentTimeFormatted, System.currentTimeMillis())
        reference.setValue(chatMessage)
            .addOnSuccessListener {
                Log.d(TAG, "Chat message saved in database: ${reference.key}")
            }
    }

}

//chat from and to items are declared in chatroom1 activity