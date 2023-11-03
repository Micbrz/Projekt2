package com.example.projekt2.android.screens


import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.projekt2.android.R
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.ui.layout.ContentScale

@Composable
fun Profile(){
    val notification = rememberSaveable{mutableStateOf("")}
    if(notification.value.isNotEmpty()){
        Toast.makeText(LocalContext.current, notification.value, Toast.LENGTH_LONG).show()
        notification.value=""
    }
    Column(modifier = Modifier.verticalScroll(rememberScrollState())
        .padding(5.dp)
    ){
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween){
            Text(text = "Usun",
                modifier = Modifier.clickable{ notification.value = "Usuniety"})
            Text(text = "Zapisz",
                modifier = Modifier.clickable{notification.value = "Zapisane"})
        }
    }
}

@Composable
fun ProfileImage(){
    val imageUrl = rememberSaveable{mutableStateOf("")}
    val painter = rememberImagePainter(
        if(imageUrl.value.isEmpty()){
            R.drawable.ic_user
        }
        else{
            imageUrl.value
        }
    )
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ){ uri: Uri? ->
        uri?.let {imageUrl.value = it.toString()}
    }
    Column(
        modifier=Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally)
        {
            Card(shape = CircleShape,
                modifier = Modifier.padding(8.dp)
                .size(100.dp)
            )
            {
                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier
                        .wrapContentSize()
                        .clickable{ launcher.launch("image/*")},
                    contentScale = ContentScale.Crop
                )
            }
            Text(text = "Zmień zdjęcie profilowe")
        }

}
@Preview(showBackground = true)
@Composable
fun prev(){
    Profile()
    ProfileImage()
}