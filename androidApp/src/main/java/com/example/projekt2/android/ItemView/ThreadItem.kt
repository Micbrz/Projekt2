package com.example.projekt2.android.ItemView

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberAsyncImagePainter
import com.example.projekt2.android.Data.ThreadModel
import com.example.projekt2.android.Data.UserModel

@Composable
fun ThreadItem(thread: ThreadModel, users: UserModel, userId:String){
    //val context = LocalContext.current

        ConstraintLayout(modifier = Modifier.fillMaxWidth().padding(16.dp)){
            val(userImage,name,date,time,title,image) = createRefs()

            Image(painter = rememberAsyncImagePainter(model = users.toString),
                contentDescription = "close",
                modifier = Modifier.constrainAs(userImage){
                    top.linkTo(parent.top,)
                    start.linkTo(parent.start)
                }
                    .size(36.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Text(
                text = users.name,style = TextStyle(
                    fontSize = 20.sp
                ), modifier = Modifier.constrainAs(name){
                    top.linkTo(userImage.top)
                    start.linkTo(userImage.end, margin = 12.dp)
                    bottom.linkTo(userImage.bottom)
                }
            )

            Text(
                text = thread.thread,style = TextStyle(
                    fontSize = 18.sp
                ), modifier = Modifier.constrainAs(title){
                    top.linkTo(name.bottom, margin = 8.dp)
                    start.linkTo(name.start)
                }
            )
            if (thread.image != "") {
                Card(modifier = Modifier
                    .constrainAs(image) {
                        top.linkTo(title.bottom, margin = 8.dp)
                        start.linkTo(title.start)
                        end.linkTo(parent.end)
                    }) {
                    Image(
                        painter = rememberAsyncImagePainter(model = thread.image),
                        contentDescription = "close",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentScale = ContentScale.Crop
                    )
                }
            }


        Divider(color = Color.LightGray, thickness = 1.dp)
    }

}