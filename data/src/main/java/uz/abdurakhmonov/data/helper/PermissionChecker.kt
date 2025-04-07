package uz.abdurakhmonov.data.helper

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PermissionChecker @Inject constructor(
    @ApplicationContext val context: Context
) {
    fun hasPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
    }
}
