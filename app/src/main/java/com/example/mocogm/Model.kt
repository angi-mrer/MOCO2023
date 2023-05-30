package com.example.mocogm

import android.media.Image
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


enum class Type {
    GESUCHT, GEFUNDEN
}

sealed class User(val email: String? = null, val password: String? = null)

class LogSignUser(email: String, password: String): User(email, password) {

    fun getThisEmail() = email
    fun getThisPassword() = password

    // checkt, ob der eingegebene String ein typisches E-Mail-Adressen-Format hat
    fun isEmailValid(): Boolean {
        val email = getThisEmail() ?: return false // return false if no email is entered
        val emailRegex = Regex("^([A-Za-z0-9_.-])+@([A-Za-z0-9_-])+\\.([A-Za-z]{2,})$")
        return emailRegex.matches(email.toString())
    }

    // Passwort soll mindestens 8 Zeichen haben
    fun isPasswordLongerThan7(): Boolean = getThisPassword()!!.length > 7

    // Passwort soll mindestens eine Zahl haben
    fun doesPasswordContainANumber(): Boolean {
        val pw = getThisPassword()
        return (pw!!.toCharArray().any { it in listOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9') }) // is there a better way to do this?
    }

    fun performLogIn(email: String, password: String): Boolean {

        return if (email == this.email && password == this.password) {
            // TODO: implement check to network/db
            true
        } else false
    }

    fun performSignIn(email: String, password: String): Boolean {
        if (isEmailValid() && isPasswordLongerThan7() && doesPasswordContainANumber()) {
            // TODO: Perform login on database/check if acc exists
            return true
        }
        else return false
    }

    private val getCurrentLoggedInUser = this as User

    fun getUserPosts(): List<ItemModel>? {

        val list = mutableListOf<ItemModel>()
        val currentUser = getCurrentLoggedInUser

        // TODO: fill list with content from DB where owner == current user

        return if(!list.isEmpty()) list else null
    }
}
interface Item {
    // erstmal var, weil wir uns eine Bearbeitungsfunktion offen halten
    var type: Type
    var title: String
    var desc: String
    var picture: Image?
    var loc: String
    var user: User
    var itemId: String?
}

class ItemModel(override var type: Type,
                override var title: String,
                override var desc: String,
                override var picture: Image?,
                override var loc: String,
                override var user: User,
                override itemId: String?
                ): Item {}

//Item ID geben und der DB hinzufügen. ID wird returned
fun addItem(item: Item): String {
    val database: FirebaseDatabase = FirebaseDatabase.getInstance() //gibt eine Instanz der Firebase-Datenbank zurück.
    val itemsRef: DatabaseReference = database.getReference("ItemModel") // Oder "item" ???

    val newItemRef: DatabaseReference = itemsRef.push() // Generiert eine eindeutige ID für das neue Item
    newItemRef.setValue(item) // Speichert das Item unter der generierten ID in der Datenbank
    var itemId = newItemRef //ID lokal speichern um Item löschen zu können
    return newItemRef.key ?: "" // Gibt die generierte ID des neuen Items zurück
}

//löscht die Items anhand der in der Funktion addItem erstellten ID/ref
fun deleteItem(itemId: String?) {
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val itemsRef: DatabaseReference = database.getReference("items")
        val itemRef: DatabaseReference = itemsRef.child(itemId)
        itemRef.removeValue()
}


// Testing if everything works as intended :)
fun main()  {

    /*
    val testlogin = LogSignUser("mjungilligens@gmail.com", "asdfghjkl1")
    if (testlogin.isEmailValid()) println("Email is valid") // funktioniert noch nicht
    if (testlogin.isPasswordLongerThan7()) println("pw is longer than 7")
    if (testlogin.doesPasswordContainANumber()) println("pw contains a number")

     */

    val user = LogSignUser("abc@gmail.de", "asdfghj123")
    val banane = ItemModel(Type.GESUCHT, "hdjashdjkashd", "123", null, "GM", user)

    addItem(banane)
    deleteItem(banane.itemId)



 //   println(addItem(item))

}