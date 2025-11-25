package com.example.loginapp.data

import com.example.loginapp.datastore.PrefsDataStore
import kotlinx.coroutines.flow.Flow

class UserRepository(
    private val dao: UserDao,
    private val prefsDataStore: PrefsDataStore
) {
    val isFirstRun: Flow<Boolean> = prefsDataStore.isFirstRun

    // Verificar si un usuario existe
    suspend fun userExists(username: String): Boolean {
        val user = dao.getUserByUsername(username)
        return user != null
    }

    // Registrar usuario nuevo
    suspend fun register(username: String, password: String) {
        dao.saveUser(User(username = username, password = password))
        prefsDataStore.setFirstRunFalse()
    }

    // Login
    suspend fun login(username: String, password: String): Boolean {
        val user = dao.getUserByUsername(username)
        return user?.password == password
    }

    // Cambiar contraseña
    suspend fun changePassword(username: String, oldPassword: String, newPassword: String): Boolean {
        val user = dao.getUserByUsername(username) ?: return false
        return if (user.password == oldPassword) {
            dao.saveUser(user.copy(password = newPassword))
            true
        } else {
            false
        }
    }

    // Obtener usuario actual (para la sesión)
    suspend fun getUser(username: String): User? {
        return dao.getUserByUsername(username)
    }
}