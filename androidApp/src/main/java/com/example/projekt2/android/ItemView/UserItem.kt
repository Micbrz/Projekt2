package com.example.projekt2.android.ItemView

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.projekt2.android.Data.Models.UserModel
import com.example.projekt2.android.navigation.Routes

@Composable
fun UserItem(users: UserModel, navHostController: NavHostController){

        ConstraintLayout(modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, top = 6.dp, bottom = 10.dp)
            .clickable{

                val routes = Routes.OtherUser.routes.replace("{data}",users.uid)
                navHostController.navigate(routes)
            }){
            val(userImage,name,lastname,date,time,title,image) = createRefs()

            Image(painter = rememberAsyncImagePainter(model = users.image),
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
                text = users.lastname,style = TextStyle(
                    fontSize = 20.sp
                ), modifier = Modifier.constrainAs(lastname){
                    top.linkTo(userImage.top)
                    start.linkTo(name.end, margin = 6.dp)
                    bottom.linkTo(userImage.bottom)
                }
            )

            /*Text(
                text = users.name,style = TextStyle(
                    fontSize = 18.sp
                ), modifier = Modifier.constrainAs(title){
                    top.linkTo(name.bottom, margin = 8.dp)
                    start.linkTo(name.start)
                }
            )*/





    }
    Divider(color = Color.LightGray, thickness = 1.dp)
}
