package com.example.mocogm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

///////////////////////////////////////////////////////////////////////////////////////
//In Build Gradle hinzufügen:
//implementation 'com.google.firebase:firebase-firestore-ktx:23.0.3'
//implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.0'
///////////////////////////////////////////////////////////////////////////////////////



///////////////////////////////////////////////////////////////////////////////////////
//ViewModel-Klasse, um den Zugriff auf die Chatnachrichten zu verwalten
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ChatViewModel : ViewModel() {
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val collectionRef = firestore.collection("chat")

    suspend fun sendMessage(sender: String, message: String) {
        val chatMessage = ChatMessage(sender, message, System.currentTimeMillis())
        collectionRef.add(chatMessage).await()
    }

    fun getChatMessages() = collectionRef.orderBy("timestamp").get()
}
///////////////////////////////////////////////////////////////////////////////////////



//Data Class für Nachrichten
data class ChatMessage(
    val sender: String = "",
    val message: String = "",
    val timestamp: Long = 0
)

class ChatActivity : AppCompatActivity() {
    private lateinit var viewModel: ChatViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        viewModel = ViewModelProvider(this).get(ChatViewModel::class.java)

        btnSend.setOnClickListener {
            val sender = "John" // Der Name des Absenders (hier statisch festgelegt)
            val message = etMessage.text.toString().trim()
            sendMessage(sender, message)
            etMessage.text.clear()
        }
        observeChatMessages()
    }

    private fun sendMessage(sender: String, message: String) {
        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                viewModel.sendMessage(sender, message)
            }
        }
    }

    private fun observeChatMessages() {
        viewModel.getChatMessages().addOnSuccessListener { querySnapshot ->
            val chatMessages = querySnapshot.toObjects(ChatMessage::class.java)
            // Hier kannst du die empfangenen Chatnachrichten verarbeiten und anzeigen
        }
    }
}
