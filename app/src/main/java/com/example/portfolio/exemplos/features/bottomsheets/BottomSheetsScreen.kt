package com.example.portfolio.exemplos.features.bottomsheets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.portfolio.exemplos.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetsScreen(modifier: Modifier = Modifier) {

    Scaffold(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        val sheetState = rememberModalBottomSheetState()
        var isSheetOpen by rememberSaveable { mutableStateOf(false) }


        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Button(onClick = {
                    isSheetOpen = true
                }) {
                    Text(text = "Open Sheet")
                }
            }

        }
        if (isSheetOpen) {
            ModalBottomSheet(
                sheetState = sheetState,
                onDismissRequest = { isSheetOpen = false },
            ) {
                Image(
                    painter = painterResource(R.drawable.pic2_compress),
                    contentDescription = null
                )
            }
        }

//        val scaffoldState = rememberBottomSheetScaffoldState()
//        val scope = rememberCoroutineScope()
//        BottomSheetScaffold(
//            scaffoldState = scaffoldState,
//            sheetContent = {
//                Image(
//                    painter = painterResource(R.drawable.pic2_compress),
//                    contentDescription = null
//                )
//            },
//            sheetPeekHeight = 0.dp,
////            sheetSwipeEnabled = false
//        ) {
//            Box(
//                modifier = modifier
//                    .fillMaxSize()
//                    .padding(paddingValues),
//                contentAlignment = Alignment.Center
//            ) {
//                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//                    Button(onClick = {
//                        scope.launch {
//                            scaffoldState.bottomSheetState.expand()
//                        }
//                    }) {
//                        Text(text = "Open Sheet")
//                    }
//                }
//            }
//        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetScaffoldScreen(modifier: Modifier = Modifier) {

    val scaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 30.dp,
        sheetContent = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentAlignment = Alignment.Center
            ) {
                Button(onClick = {
                    scope.launch {
                        scaffoldState.bottomSheetState.partialExpand()
                    }
                }) {
                    Text("Close")
                }
            }
        }
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Button(onClick = {
                scope.launch {
                    scaffoldState.bottomSheetState.expand()
                }
            }) {
                Text("Open")
            }
        }
    }
}