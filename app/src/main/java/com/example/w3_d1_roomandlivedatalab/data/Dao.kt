package com.example.w3_d1_roomandlivedatalab.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {
@Query("SELECT * FROM user")
fun getAll(): LiveData<List<User>>

@Query("SELECT * FROM user WHERE user.uid = :userid")
// the @Relation do the INNER JOIN for you ;)
fun getUserWithContacts(userid: Long): UserContact

 @Insert(onConflict = OnConflictStrategy.REPLACE)
 fun insert(user: User): Long

 @Update
 fun update(user: User)

 @Delete
 fun delete(user: User)
 }

 @Dao
 interface ContactInfoDao { /* ... */ }