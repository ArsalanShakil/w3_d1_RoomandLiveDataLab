package com.example.w3_d1_roomandlivedatalab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.w3_d1_roomandlivedatalab.data.ContactInfo
import com.example.w3_d1_roomandlivedatalab.data.User
import com.example.w3_d1_roomandlivedatalab.data.UserDB
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        //... somewhere, e.g. in onCreate()
        //using Coroutines (but any async will do)
        //Avoid .allowMainThreadQueries()
        val ref = this
        val db by lazy { UserDB.get(this) }

        lifecycleScope.launch(Dispatchers.IO) {
            //if you would like to get the DB here:
            //val db = UserDB.get(applicationContext)
            /*val id = db.userDao().insert(User(0, "Janne", "Mallory"))
            db.contactDao().insert(ContactInfo(id, "twitter", "@janne"))
            db.contactDao().insert(ContactInfo(id, "phone", "98746"))*/
                textView.text = "${db.userDao().getUserWithContacts(2).user?.firstname}"//db.contactDao().getAll(id).value.toString()


        }

    }



}