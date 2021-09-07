package com.example.w3_d1_roomandlivedatalab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.w3_d1_roomandlivedatalab.data.ContactInfo
import com.example.w3_d1_roomandlivedatalab.data.User
import com.example.w3_d1_roomandlivedatalab.data.UserDB
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val db by lazy { UserDB.get(this) }
        //... somewhere, e.g. in onCreate()
        //using Coroutines (but any async will do)
        //Avoid .allowMainThreadQueries()
        GlobalScope.launch {
            //if you would like to get the DB here:
            //val db = UserDB.get(applicationContext)
            val id = db.userDao().insert(User(0, "Jane", "Mallory"))
            db.contactDao().insert(ContactInfo(id, "twitter", "@jane"))
            db.contactDao().insert(ContactInfo(id, "phone", "9876"))
            withContext(Main) {
                textView.text = db.contactDao().getAll(id).toString()
            }

        }

    }



}