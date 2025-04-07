package uz.abdurakhmonov.stopwatch.screen.weather_screen

import android.R.attr.fontWeight
import android.R.attr.text
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.abdurakhmonov.stopwatch.R
import uz.abdurakhmonov.stopwatch.ui.theme.fontFamily
import uz.abdurakhmonov.stopwatch.ui.theme.primary
import uz.abdurakhmonov.stopwatch.ui.theme.secondary
import uz.abdurakhmonov.stopwatch.utils.ItemContent
import uz.abdurakhmonov.stopwatch.utils.RequestLocationPermission
import uz.abdurakhmonov.stopwatch.utils.state

@Composable
fun WeatherMapScreen(
    state: WeatherMapScreenContract.UIState,
    intent: (WeatherMapScreenContract.Intent) -> Unit
) {
    RequestLocationPermission {
    }

    intent.invoke(WeatherMapScreenContract.Intent.LoadWeather)
    val brush = Brush.verticalGradient(
        colors = listOf(
            secondary,
            Color(0xFF141428),
            Color(0xFF171D23)
        ), startY = 100f, endY = Float.POSITIVE_INFINITY
    )

    when(state.isLoading){
        true -> {
            Loading(brush)
        }
        false -> {
            Success(brush,state)
        }
    }

}

@Composable
fun Success(brush: Brush,state: WeatherMapScreenContract.UIState){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = brush),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_284),
            contentDescription = "img",
            modifier = Modifier.size(160.dp),
            contentScale = ContentScale.Crop
        )

        Text(
            text = state.temperature,
            fontSize = 64.sp,
            color = primary,
            letterSpacing = TextUnit(1f, TextUnitType.Sp),
            fontFamily = fontFamily,
            fontWeight = FontWeight.SemiBold,
        )
        Text(
            text = "Precipitations",
            fontSize = 18.sp,
            color = primary,
            fontWeight = FontWeight.Normal,
            fontFamily = fontFamily,
        )
        Text(
            text = "Min: ${state.minTemp}  Max: ${state.maxTemp}",
            fontSize = 18.sp,
            color = primary,
            fontWeight = FontWeight.Normal,
            fontFamily = fontFamily,
            modifier = Modifier.padding(top = 4.dp)
        )
        Row(
            modifier = Modifier
                .padding(top = 32.dp)
                .padding(horizontal = 32.dp)
                .clip(RoundedCornerShape(32.dp))
                .fillMaxWidth()
                .background(color = Color(0xFF191933))
                .padding(vertical = 16.dp, horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ItemContent("${34} %", image = painterResource(R.drawable.ic_noun))
            ItemContent("${state.humidity} %", image = painterResource(R.drawable.ic_gradus))
            ItemContent("${state.windSpeed} km/h", image = painterResource(R.drawable.ic_noun_wind))
        }

//        Row(
//            modifier = Modifier
//                .padding(top = 32.dp)
//                .padding(horizontal = 32.dp)
//                .clip(RoundedCornerShape(32.dp))
//                .fillMaxWidth()
//                .background(color = Color(0xFF191933))
//                .padding(vertical = 16.dp, horizontal = 20.dp),
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            ItemContent("8 %", image = painterResource(R.drawable.ic_noun))
//            ItemContent("80 %", image = painterResource(R.drawable.ic_gradus))
//            ItemContent("20 km/h", image = painterResource(R.drawable.ic_noun_wind))
//        }


    }
}

@Composable
fun Loading(brush: Brush){
    Box(modifier = Modifier.fillMaxSize().background(brush = brush), contentAlignment = Alignment.Center){
        CircularProgressIndicator(
            modifier = Modifier
                .size(40.dp)
            ,
            color = primary,
            strokeWidth = 5.dp,
            trackColor = Color.DarkGray
        )
    }
}


@Preview(showBackground = true)
@Composable
fun Preview() {
    //WeatherMapScreen()
}