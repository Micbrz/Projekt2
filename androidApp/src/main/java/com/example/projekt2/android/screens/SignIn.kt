package com.example.projekt2.android.screens

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.projekt2.android.Components.DividerTextComponent
import com.example.projekt2.android.Components.EmailText
import com.example.projekt2.android.Components.NormalTextComponent
import com.example.projekt2.android.Components.PasswordText
import com.example.projekt2.android.Components.RegistrationButton
import com.example.projekt2.android.Components.SignInScreenn
import com.example.projekt2.android.Components.forgot
import com.example.projekt2.android.Data.Login.LoginResult
import com.example.projekt2.android.Data.Login.LoginUIEvent
import com.example.projekt2.android.Data.Login.LoginViewModel
import com.example.projekt2.android.R
import com.example.projekt2.android.navigation.Routes
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


@Composable
fun SignIn(navHostController: NavHostController) {
    val loginViewModel: LoginViewModel = viewModel()

    val context = LocalContext.current

    val token = stringResource(R.string.default_web_client_id)
    val launcherNav = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {
        navHostController.navigate(Routes.HomeScreen.routes)
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
    ) {
        val task =
            try {
                val account = GoogleSignIn.getSignedInAccountFromIntent(it.data)
                    .getResult(ApiException::class.java)
                val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)
                FirebaseAuth.getInstance().signInWithCredential(credential)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            navHostController.navigate(Routes.HomeScreen.routes)
                        }
                    }
            }
            catch (e: ApiException) {
                Log.w("TAG", "GoogleSign in Failed", e)
            }
    }

    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {
        Surface(

            modifier = Modifier.fillMaxSize()
                .padding(28.dp)
                .background(Color.White)

        )
        {
            Column(modifier = Modifier.fillMaxSize()) {
                Spacer(modifier = Modifier.height(40.dp))
                NormalTextComponent(loginText = stringResource(id = R.string.logIn))
                EmailText(
                    labelValue = stringResource(id = R.string.email),
                    painterResource = painterResource(id = R.drawable.message),
                    onTextSelected = {
                        loginViewModel.onEvent(LoginUIEvent.EmailChanged(it))
                    })
                Spacer(modifier = Modifier.height(10.dp))
                PasswordText(
                    labelValue = stringResource(id = R.string.password),
                    painterResource = painterResource(id = R.drawable.ic_lock),
                    onTextSelected = {
                        loginViewModel.onEvent(LoginUIEvent.PasswordChanged(it))
                    }
                )
                Spacer(modifier = Modifier.height(20.dp))
                forgot(onClick={navHostController.navigate(Routes.ForgotPassword.routes)})
                Spacer(modifier = Modifier.height(280.dp))
                RegistrationButton(
                    value = stringResource(id = R.string.Login),
                    onButtonClicked = {
                        loginViewModel.onEvent(LoginUIEvent.LoginButtonClicked)

                    },
                    isEnabled = loginViewModel.allValidationsPassed.value
                )
                Spacer(modifier = Modifier.height(15.dp))

                DividerTextComponent()
                Spacer(modifier = Modifier.height(15.dp))
                Button(onClick = {
                    val gso = GoogleSignInOptions
                        .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(token)
                        .requestEmail()
                        .build()
                    val googleSignInClient = GoogleSignIn.getClient(context, gso)
                    launcher.launch(googleSignInClient.signInIntent)
                },  modifier = Modifier.fillMaxWidth()
                    .heightIn(45.dp),
                    contentPadding = PaddingValues(),
                    colors = ButtonDefaults.buttonColors(Color.Black)
                ) {
                    Text(text = "Zaloguj się z google")
                }

                loginViewModel.loginResult.observeAsState().value?.let { loginResult ->
                    if (loginResult == LoginResult.SUCCESS) {
                        Toast.makeText(context, "Logowanie udane!", Toast.LENGTH_SHORT).show()
                        navHostController.navigate(Routes.HomeScreen.routes)
                    }
                else{
                    Toast.makeText(context,"logowanie nie udane :(",Toast.LENGTH_SHORT).show()
                }}
                Spacer(modifier = Modifier.height(20.dp))
                SignInScreenn(onClick = { navHostController.navigate(Routes.SignUp.routes) })


            }
        }

    }
}

