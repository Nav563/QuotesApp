package com.example.quotesapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.quotesapp.screens.QuoteDetail
import com.example.quotesapp.screens.QuoteListScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CoroutineScope(Dispatchers.IO).launch {
            //delay(10000)
            DataManager.loadAssetsFromFile(applicationContext)
        }
        setContent {

            //QuoteDetail()
            App()
        }
    }
}


@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun App() {
    if (DataManager.isDataLoaded.value) {
        if (DataManager.currentPage.value == Pages.LISTING){
            QuoteListScreen(data = DataManager.data) {
                DataManager.switchPages(it)
            }
        }else{
            DataManager.currentQuote?.let { QuoteDetail(quote = it) }
        }

    } else {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize(1F)
        ) {
            Text(
                text = "Loading....",
                style = MaterialTheme.typography.headlineLarge
            )
        }
    }
}
enum class Pages{
    LISTING,
    DETAIL
}
