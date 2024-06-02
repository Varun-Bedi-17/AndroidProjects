package com.example.instagramclone.data

import android.util.Log
import com.example.instagramclone.domain.model.Response
import com.example.instagramclone.domain.model.User
import com.example.instagramclone.domain.repository.AuthenticationRepository
import com.example.instagramclone.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await


class AuthenticationRepoImpl(
    private val auth: FirebaseAuth,
    private val fireStore: FirebaseFirestore,
) : AuthenticationRepository {
    private var operationSuccessful = false
    override fun isUserAuthenticated(): Boolean {
        return auth.currentUser != null
    }

    override fun getFirebaseAuthState(): Flow<Boolean> = callbackFlow {
        val authStateListener = FirebaseAuth.AuthStateListener {
            trySend(auth.currentUser == null)
        }
        auth.addAuthStateListener(authStateListener)
        awaitClose {
            auth.removeAuthStateListener(authStateListener)
        }
    }

    override fun firebaseSignIn(email: String, password: String): Flow<Response<Boolean>> = flow {
        operationSuccessful = false
        try {
            emit(Response.Loading)
            auth.signInWithEmailAndPassword(email, password).await()
            operationSuccessful = true

            emit(Response.Success(operationSuccessful))
        } catch (e: java.lang.Exception) {
            emit(Response.Error(e.localizedMessage ?: "An unexpected error"))
        }
    }

    override fun firebaseSignOut(): Flow<Response<Boolean>> = flow {
        try {
            emit(Response.Loading)
            auth.signOut()
            emit(Response.Success(true))
        } catch (e: java.lang.Exception) {
            emit(Response.Error(e.localizedMessage ?: "An unexpected error"))
        }
    }

    override fun firebaseSignUp(
        email: String,
        password: String,
        username: String,
    ): Flow<Response<Boolean>> = flow {
        operationSuccessful = false
        try {
            emit(Response.Loading)
            auth.createUserWithEmailAndPassword(email, password).await()
            operationSuccessful = true

            if (operationSuccessful) {
                val userId = auth.currentUser?.uid!!
                val obj =
                    User(userName = username, password = password, email = email, userId = userId)

                fireStore.collection(Constants.COLLECTION_NAME_USERS).document(userId).set(obj).await()
                emit(Response.Success(operationSuccessful))
            } else {
                emit(Response.Success(operationSuccessful))
            }
        } catch (e: java.lang.Exception) {
            emit(Response.Error(e.localizedMessage ?: "An unexpected error"))
        }
    }
}