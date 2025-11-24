package com.example.loginapp.data

//import com.google.firebase.firestore.auth.User

class UserRepository(private val dao: UserDao) {
    suspend fun register(password: String) {
        dao.saveUser(User(password = password))
    }

    suspend fun login(password: String): Boolean {
        val user = dao.getUser()
        return user?.password == password
    }

    suspend fun changePassword(old: String, new: String): Boolean {
        val user = dao.getUser() ?: return false
        return if (user.password == old) {
            dao.saveUser(User(password = new))
            true
        } else {
            false
        }
    }
}


