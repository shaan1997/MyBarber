import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.ImeOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
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
       /* OutlinedTextField(
            value = signUpViewModel.phoneNumber,
            onValueChange = { newVal ->
                if(newVal.length <= signUpViewModel.maxLength){
                    signUpViewModel.phoneNumber = newVal
                }
            },
            placeholder = { Text("Mobile number") },
            singleLine = true,
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFE9E8E8))
                .clip(RoundedCornerShape(20.dp)),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,  // 👈 removes grey background
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                errorContainerColor = Color.Transparent,

                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone,)
        )*/

        RoundedOutlinedTextField(
            value = signUpViewModel.phoneNumber,
            onValueChange = { newVal->
                if(newVal.length <= signUpViewModel.maxLength){
                signUpViewModel.phoneNumber = newVal
            } },
            hint = "Mobile number",
            modifier = Modifier.fillMaxWidth(),
            keyboardType = KeyboardType.Phone
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

@Composable
fun RoundedOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String = "",
    modifier: Modifier = Modifier,
    isEnable: Boolean = true,
    keyboardAction: ImeAction = ImeAction.Done,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        enabled = isEnable,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = keyboardAction),
        textStyle = TextStyle(
            fontSize = 17.sp,
            letterSpacing = 0.05.em
        ),
        decorationBox = { innerTextField ->
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .background(
                        color = Color(0xFFF0F2F5), // custom background
                        shape = RoundedCornerShape(12.dp) // fully rounded corners
                    )
                    .padding(horizontal = 16.dp, vertical = 18.dp)
            ) {
                // Show hint if text is empty
                if (value.isEmpty()) {
                    Text(
                        text = hint,
                        color = Color.Gray
                    )
                }
                innerTextField()
            }
        }
    )
}

