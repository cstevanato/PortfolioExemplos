package com.example.portfolio.exemplos.features.authentication.data


import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    val currentUser: FirebaseUser?

    suspend fun signUp(email: String, password: String): AuthResult<FirebaseUser>

    suspend fun signIn(email: String, password: String): AuthResult<FirebaseUser>

    fun signOut()
    fun isUserLoggedIn(): Boolean
}