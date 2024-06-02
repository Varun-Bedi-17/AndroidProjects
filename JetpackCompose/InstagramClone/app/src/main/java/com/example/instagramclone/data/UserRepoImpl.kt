package com.example.instagramclone.data

import com.example.instagramclone.domain.model.Response
import com.example.instagramclone.domain.model.User
import com.example.instagramclone.domain.repository.UserRepository
import com.example.instagramclone.utils.Constants
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class UserRepoImpl(private val fireStore: FirebaseFirestore) : UserRepository {

    override fun getUserDataFromFirebase(userId: String): Flow<Response<User>> = callbackFlow {
        Response.Loading
        val snapShotListener =
            fireStore.collection(Constants.COLLECTION_NAME_USERS).document(userId)
                .addSnapshotListener { snapShot, error ->
                    val response = if (snapShot != null) {
                        val userInfo = snapShot.toObject(User::class.java)
                        Response.Success(userInfo!!)
                    } else {
                        Response.Error(error?.message ?: error.toString())
                    }
                    trySend(response).isSuccess
                }
        awaitClose {
            snapShotListener.remove()
        }
    }

    override fun setUserDetails(user: User) = flow {
        var operationSuccessfull = false
        val userObj = mutableMapOf<String, String>()
        userObj["name"] = user.name
        userObj["userName"] = user.userName
        userObj["bio"] = user.bio
        try {
            emit(Response.Loading)
            fireStore.collection(Constants.COLLECTION_NAME_USERS).document(user.userId)
                .update(userObj as MutableMap<String, Any>)
                .addOnSuccessListener { }.await()
            if(operationSuccessfull){
                Response.Success(operationSuccessfull)
            }
            else{
                Response.Error("Edit does not succeed")
            }
        } catch (e: Exception) {
            Response.Error(e.localizedMessage ?: "An Unexpected Error")
        }
    }

}