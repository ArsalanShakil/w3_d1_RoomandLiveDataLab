package com.example.w3_d1_roomandlivedatalab.data

import androidx.room.*
import androidx.room.ForeignKey.CASCADE

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    val uid: Long,
    val firstname: String,
    val lastname: String
) {
    //constructor, getter and setter are implicit :)
    override fun toString() = "($uid) $firstname$lastname"
}

@Entity(
    foreignKeys = [ForeignKey(
        entity = User::class,
        onDelete = CASCADE,
        parentColumns = ["uid"],
        childColumns = ["user"]
    )]
)
data class ContactInfo(
    val user: Long,
    val type: String, //e.g. phone, email, fb, twitter,...
    @PrimaryKey
    val value: String
)

class UserContact {
    @Embedded
    var user: User? = null

    @Relation(parentColumn = "uid", entityColumn = "user")
    var contacts: List<ContactInfo>? = null
}