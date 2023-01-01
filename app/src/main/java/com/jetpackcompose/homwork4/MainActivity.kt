package com.jetpackcompose.homwork4

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.annotation.ExperimentalCoilApi
import com.jetpackcompose.homwork4.model.ProfileDetails
import com.jetpackcompose.homwork4.ui.theme.Homwork4Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Homwork4Theme {
                // A surface container using the 'background' color from the theme
                Surface(
//                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ProfileScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun ProfileScreen() {
    val profileDetails= ProfileDetails()
    val notification= rememberSaveable{mutableStateOf("")}

    if (notification.value.isNotEmpty()){
        Toast.makeText(LocalContext.current,notification.value,Toast.LENGTH_LONG).show()
        notification.value=""
    }

    val list_of_fields= arrayListOf(stringResource(id = R.string.first_name),
        stringResource(id = R.string.last_name),
        stringResource(id = R.string.telephone_number),
        stringResource(id = R.string.email),
        stringResource(id = R.string.gender),
        stringResource(id = R.string.customer_number),
        stringResource(id = R.string.admin)
    )
    val listofuserfields= arrayListOf(
        profileDetails.firstName,
        profileDetails.lastName,
        profileDetails.telephone,
        profileDetails.email,
        profileDetails.gender,
        profileDetails.customerNo,
        profileDetails.isAdmin.toString()
    )
    Column(modifier = Modifier
        .fillMaxWidth()
        .verticalScroll(rememberScrollState())
    ){
        Row(
            modifier = Modifier
                .background(color = MaterialTheme.colors.onSecondary)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Icon(painter = rememberImagePainter(R.drawable.ic_back ), contentDescription = null,
                modifier = Modifier.padding(12.dp),
                tint=MaterialTheme.colors.onSurface)
        }
        ProfileImage(profileDetails)
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp, top = 14.dp)
            .background(MaterialTheme.colors.background)) {
            repeat(7){
                Text(text = list_of_fields[it],
                    color= MaterialTheme.colors.onBackground,
                    fontSize=15.sp,
                    fontWeight = FontWeight.Bold, modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp))
                Text(
                    text=listofuserfields[it],
                    modifier= Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, bottom = 10.dp),
                    color = MaterialTheme.colors.primaryVariant,
                    fontSize=15.sp,
                    fontWeight = FontWeight.Bold
                )
                Divider(color = MaterialTheme.colors.onPrimary, thickness = 2.dp)
            }
        }


    }
}


@OptIn(ExperimentalCoilApi::class)
@Composable
fun ProfileImage(profileDetails: ProfileDetails) {


    val imageUri= rememberSaveable{ mutableStateOf("")}
    val painter= rememberImagePainter(
        if (imageUri.value.isEmpty())
            profileDetails.avatarUrl
        else
            imageUri.value


    )
    val launcher= rememberLauncherForActivityResult(
        contract =ActivityResultContracts.GetContent()){ uri: Uri? ->
        uri?.let {
            imageUri.value=it.toString()
        }

    }
    Column (modifier = Modifier
        .fillMaxWidth()
        .height(300.dp)
        .background(color = MaterialTheme.colors.onSecondary),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){


        Card(modifier = Modifier
            .padding(8.dp)
            .padding(top = 50.dp)
            .size(150.dp),
            shape = CircleShape,

            ) {

            Image(painter = painter,
                contentDescription =null,
                modifier = Modifier
                    .wrapContentSize(),
                contentScale =ContentScale.Crop)
        }
        Card(modifier = Modifier
            .padding(8.dp)
            .size(50.dp)
            .zIndex(2f)
            .graphicsLayer {
                translationX = 140f
                translationY = -140f
            },
            shape = CircleShape) {
            Image(painter = rememberImagePainter(R.drawable.ic_camera), contentDescription =null,
                modifier = Modifier
                    .background(Color.Red)
                    .padding(11.dp)
                    .clickable { launcher.launch("image/*") },
                contentScale =ContentScale.Inside

            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Homwork4Theme {
        ProfileScreen()
    }
}