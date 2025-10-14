import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mybarber.model_view.SignUpViewModel
import com.mybarber.model_view.SnackbarViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    onBackClick: () -> Unit,
    onContinueClick: (String) -> Unit
) {


    val snackbarViewModel : SnackbarViewModel = viewModel(LocalContext.current as ComponentActivity)
    val coroutineScope = rememberCoroutineScope()

    val signUpViewModel : SignUpViewModel = viewModel()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .systemBarsPadding(),

    ) {
        Row (modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
            // Back Arrow
            IconButton(onClick = { onBackClick() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Signup",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))



        Spacer(modifier = Modifier.height(32.dp))

        // Label
        Text(
            text = "Enter your mobile number",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Mobile Number TextField
        OutlinedTextField(
            value = signUpViewModel.phoneNumber,
            onValueChange = { newVal ->
                if(newVal.length <= signUpViewModel.maxLength){
                    signUpViewModel.phoneNumber = newVal
                }
            },
            label = { Text("Mobile number") },
            singleLine = true,

            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF2F4F7)),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone,)
        )

        Spacer(modifier = Modifier.weight(1f))
        // Continue Button
        Button(
            onClick = {
                 if(signUpViewModel.phoneNumber.isEmpty()){
                     coroutineScope.launch {
                         snackbarViewModel.showSnackbar("Please enter valid phone number")
                     }
                 } else {
                     onContinueClick(signUpViewModel.phoneNumber)
                 }

                      },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007AFF)) // iOS Blue
        ) {
            Text(
                text = "Continue",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Terms & Privacy Text
        Text(
            text = "By continuing, you agree to our Terms of Service and Privacy Policy.",
            fontSize = 12.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
