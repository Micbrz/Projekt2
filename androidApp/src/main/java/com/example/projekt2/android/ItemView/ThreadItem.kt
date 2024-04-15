package com.example.projekt2.android.ItemView

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.projekt2.android.Data.Models.CommentModel
import com.example.projekt2.android.Data.Models.ThreadModel
import com.example.projekt2.android.Data.Models.UserModel
import com.example.projekt2.android.Data.ViewModel.CommentViewModel
import java.text.SimpleDateFormat
import java.util.*


@SuppressLint("RememberReturnType")
@Composable
fun ThreadItem(thread: ThreadModel, users: UserModel, userId:String){
    val threadId = thread.thread
    val commentViewModel = CommentViewModel()
    var comments by remember { mutableStateOf<List<CommentModel>>(emptyList()) }

    LaunchedEffect(thread.thread) {

        commentViewModel.getCommentsForThread(thread.thread) { itcomments ->
            comments = itcomments
        }
    }
    val context = LocalContext.current

    val onCommentPosted: () -> Unit = {
        // Wyświetlenie komunikatu
        println("Komentarz został dodany!")
    }
        var commentText by remember {mutableStateOf("")}
        var errorMessage by remember {mutableStateOf("")}

        ConstraintLayout(modifier = Modifier.fillMaxWidth().padding(top=16.dp,end = 16.dp,start = 16.dp)){
            val(userImage,name,date,time,title,image,commentField,sendButton,lastname) = createRefs()

            val formattedDate = remember {
                val timestamp = thread.timeStam.toLongOrNull() ?: 0L
                val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
                val netDate = Date(timestamp)
                sdf.format(netDate)
            }
            val formattedTime = remember {
                val timestamp = thread.timeStam.toLongOrNull() ?: 0L
                val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
                val netDate = Date(timestamp)
                sdf.format(netDate)
            }
            Text(
                text = formattedDate,
                style = TextStyle(fontSize = 15.sp),
                modifier = Modifier
                    .constrainAs(date) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    }
                    .padding(4.dp)
            )
            Text(
                text = formattedTime,
                style = TextStyle(fontSize = 15.sp),
                modifier = Modifier
                    .constrainAs(time) {
                        top.linkTo(date.bottom)
                        start.linkTo(date.start)
                    }
                    .padding(4.dp)
            )

            Image(painter = rememberAsyncImagePainter(model = users.image),
                contentDescription = "close",
                modifier = Modifier.constrainAs(userImage){
                    top.linkTo(parent.top)
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
                    start.linkTo(name.end, margin = 4.dp)
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
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        Log.d(TAG,"Image= $thread")
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

        OutlinedTextField(
            value = commentText,
            onValueChange = { commentText = it},
            label = {Text("Dodaj komentarz")},
            modifier = Modifier.constrainAs(commentField){
                top.linkTo(image.bottom)
                start.linkTo(image.start)
                end.linkTo(sendButton.start,margin = 6.dp)
                bottom.linkTo(parent.bottom)

            }
                .fillMaxWidth().padding(start= 75.dp).height(60.dp)
        )
            Button(
                onClick = {if (commentText.isNotBlank()){
                    // tutaj logika wysylania i zapisywania w bazie danych
                    commentViewModel.addComment(threadId = threadId, comment = commentText, userId = userId)
                    commentText = ""
                    onCommentPosted()
                }else{
                    errorMessage = "Komentarz nie może być pusty"
                }
                },
                modifier = Modifier.constrainAs(sendButton){
                    top.linkTo(image.bottom,margin = 8.dp)
                    start.linkTo(commentField.end)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }.padding(end=65.dp)
            ){
                Text("Wyślij")
            }
            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    modifier = Modifier.constrainAs(date) {
                        top.linkTo(sendButton.bottom, margin = 4.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                )
            }
            val commentsColumn = createRef()
            Column(
                modifier = Modifier
                    .constrainAs(commentsColumn) {
                        top.linkTo(image.bottom, margin = 8.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(commentField.top, margin = 8.dp)
                    }
                    .padding(horizontal = 8.dp)
            ) {

                // Wyświetlanie komentarzy
                comments.forEach { comment ->
                    var author by remember(comment) { mutableStateOf<UserModel?>(null) }
                    var authorPhotoUrl by remember(comment) { mutableStateOf<String?>(null) }
                    LaunchedEffect(comment) {
                        commentViewModel.getUserByComment(comment) { fetchedAuthor ->
                            author = fetchedAuthor
                            // Pobranie URL zdjęcia autora
                            fetchedAuthor?.let {
                                authorPhotoUrl = it.image // Tutaj użyj właściwości toString jako URL zdjęcia
                            }
                        }
                    }

                    author?.let { CommentItem(comment, it) }
                }
            }
        Divider(color = Color.LightGray, thickness = 1.dp)
    }

}

@SuppressLint("RememberReturnType")
@Composable
fun CommentItem(comment: CommentModel, author: UserModel?) {
    /*
    val formattedTime = remember {
        val timestamp = thread.timeStam.toLongOrNull() ?: 0L
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        val netDate = Date(timestamp)
        sdf.format(netDate)
    }*/

    Column {
        author?.let { user ->
            val formattedDate = remember {
                val timestamp = comment.timeStamp.toLongOrNull() ?: 0L
                val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
                val netDate = Date(timestamp)
                sdf.format(netDate)
            }
            Text(
                text = "${comment.comment}",
                style = TextStyle(fontSize = 16.sp),
                modifier = Modifier.padding(8.dp)
            )
            Row(verticalAlignment = Alignment.CenterVertically){
                Image(
                    painter = rememberImagePainter(user.image),
                    contentDescription = "Author Photo",
                    modifier = Modifier.size(30.dp).clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = "${user.name} ${user.lastname}",
                    style = TextStyle(fontSize = 14.sp),
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = formattedDate,
                    style = TextStyle(fontSize = 14.sp),
                    modifier = Modifier.padding(start = 8.dp)
                )

            }
            Divider()

        }
    }
}
