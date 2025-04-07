package uz.abdurakhmonov.stopwatch.utils

import android.Manifest
import android.R.attr.fontWeight
import android.content.pm.PackageManager
import android.graphics.drawable.Icon
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.abdurakhmonov.stopwatch.R
import uz.abdurakhmonov.stopwatch.ui.theme.bgDrawerColor
import uz.abdurakhmonov.stopwatch.ui.theme.btnColor
import uz.abdurakhmonov.stopwatch.ui.theme.btnContentColor
import uz.abdurakhmonov.stopwatch.ui.theme.fontFamily
import uz.abdurakhmonov.stopwatch.ui.theme.primary
import uz.abdurakhmonov.stopwatch.ui.theme.primary1
import uz.abdurakhmonov.stopwatch.ui.theme.secondary


@Composable
fun ItemFlags(
    flags: List<String> = listOf(),
    count: Int = 1,
    timeOld: String = "00:00:00",
    timeNew: String = "00:00:00"
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Text(
            text = "$count",
            color = primary1,
            fontSize = 18.sp,
            fontFamily = fontFamily,
        )
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = "+ $timeOld",
            color = primary1,
            fontSize = 18.sp,
            fontFamily = fontFamily,
        )
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = timeNew,
            color = primary,
            fontSize = 20.sp,
            fontFamily = fontFamily,
            fontWeight = FontWeight.Medium
        )
    }
    Text(
        text = flags.joinToString(", "),
        color = primary,
        fontSize = 16.sp,
        fontFamily = fontFamily,
    )
}

@Composable
fun ItemStatus() {

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemAppbar(
    title: String,
    startIcon: Painter? = null,
    actionIcon: Icon? = null,
    onClickNavigation: () -> Unit
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = secondary,
        ),
        navigationIcon = {
            startIcon?.let { icon ->
                IconButton(
                    onClick = onClickNavigation
                ) {
                    Icon(painter = icon, contentDescription = "icons", tint = primary)
                }
            }
        },

        title = {
            Text(
                text = title,
                fontFamily = fontFamily,
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                color = primary
            )
        }
    )
}

@Composable
fun MyNavigationDrawer(
    state: DrawerState,
    click: (String) -> Unit,
    content: @Composable () -> Unit
) {
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .fillMaxWidth(0.7f),
                drawerContainerColor = bgDrawerColor
            ) {
                Text(
                    text = "Menu",
                    modifier = Modifier.padding(16.dp),
                    fontSize = 20.sp,
                    color = primary
                )
                Divider()
                NavigationDrawerItem(
                    label = {
                        Text("StopWatch",color = primary)
                    },
                    selected = false,
                    onClick = {
                        click.invoke("StopWatch")
                        scope.launch {
                            state.close()
                        }
                    }
                )
                NavigationDrawerItem(
                    label = {
                        Text("WeatherMap", color = primary)
                    },
                    selected = false,
                    onClick = {
                        click.invoke("WeatherMap")
                        scope.launch {
                            state.close()
                        }
                    }
                )
            }
        },
        drawerState = state
    ) {
        content()
    }
}


@Composable
fun AnimatedButton(
    modifier: Modifier = Modifier,
    shape: Shape = ButtonDefaults.shape,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    icon: Painter,
    onClick: () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.88f else 1f,
        animationSpec = tween(durationMillis = 150), label = ""
    )
    val coroutine = rememberCoroutineScope()

    Button(
        contentPadding = contentPadding,
        onClick = {
            onClick.invoke()
            isPressed = !isPressed
            coroutine.launch(Dispatchers.Unconfined) {
                delay(150)
                isPressed = !isPressed
                cancel()
            }
        },
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            containerColor = btnColor,
            contentColor = btnContentColor
        ),
        modifier = modifier
            .graphicsLayer(scaleX = scale, scaleY = scale)
    ) {
        Icon(painter = icon, contentDescription = "icons")
    }
}

@Composable
fun ItemContent(
    text: String,
    image: Painter,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = image,
            contentDescription = "icon",
            tint = primary
        )
        Text(
            text = text,
            color = primary,
            fontSize = 16.sp,
            fontFamily = fontFamily,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}


@Composable
fun ItemDay(list: List<String>){

}

@Composable
fun RequestLocationPermission(
    onPermissionResult: (Boolean) -> Unit
) {
    val context = LocalContext.current
    val permissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            onPermissionResult(isGranted)
        }

    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION) else onPermissionResult(true)
    }
}
