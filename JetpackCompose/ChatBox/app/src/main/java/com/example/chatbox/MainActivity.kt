package com.example.chatbox

import android.content.pm.ModuleInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.primarySurface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chatbox.ui.theme.ChatBoxTheme

val conversationMessages = listOf(
    Message("Varun", "Hyy"),
    Message("Varun", "Hello"),
    Message("Varun",
        "In this code snippet, you can see that LazyColumn has an items child. It takes a List as a parameter and its lambda receives a parameter we’ve named message (we could have named it whatever we want) which is an instance of Message. In short, this lambda is called for each item of the provided List. Copy the sample dataset into your project to help bootstrap the conversation quickly."),
    Message("Varun", "Okk"),
    Message("Varun",
        "The conversation is getting more interesting. It's time to play with animations! You will add the ability to expand a message to show a longer one, animating both the content size and the background color. To store this local UI state, you need to keep track of whether a message has been expanded or not. To keep track of this state change, you have to use the functions remember and mutableStateOf."),
    Message("Varun", "Composable functions can store local state in memory by using remember"),
    Message("Varun", "Hyy"),
    Message("Varun", "Hyy"),
    Message("Varun", "Hyy"),
    Message("Varun", "Hyy"),
    Message("Varun", "Hyy")
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatBoxTheme {
                // A surface container using the 'background' color from the theme
                Conversation(messages = conversationMessages)
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun Greeting() {
    Conversation(messages = conversationMessages)
}


@Composable
fun MessageCard(message: Message) {

    var isMessageExpanded by remember {
        mutableStateOf(false)
    }
    val surfaceColor by animateColorAsState(
        if (isMessageExpanded) MaterialTheme.colors.primary else MaterialTheme.colors.surface
    )
    Row(modifier = Modifier.fillMaxWidth()) {
        Image(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = null,
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .border(1.dp, Color.Black, CircleShape)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .clickable {
                    isMessageExpanded = !isMessageExpanded
                },
            verticalArrangement = Arrangement.Center,
        ) {
            Text(text = message.authorName,
                style = MaterialTheme.typography.subtitle2)
            Surface(
                elevation = 2.dp,
                shape = RoundedCornerShape(20.dp),
                color = surfaceColor,
                modifier = Modifier.animateContentSize()
            ) {
                Text(text = message.message,
                    modifier = Modifier.padding(5.dp),
                    maxLines = if (isMessageExpanded) Int.MAX_VALUE else 1,
                    overflow = TextOverflow.Ellipsis)
            }
        }
    }
}

@Composable
fun Conversation(messages: List<Message>) {
    LazyColumn(modifier = Modifier
        .padding(5.dp)) {
        items(messages) { message ->
            MessageCard(message = message)
        }
    }
}