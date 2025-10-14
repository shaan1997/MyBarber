// imports you may need
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mybarber.model_view.OtpViewModel
import com.mybarber.model_view.SnackbarViewModel
import kotlinx.coroutines.launch
import kotlin.math.min

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtpScreen(
    mobileNumber: String,
    onBack: () -> Unit,
    onContinue: () -> Unit
) {

    val focusManager = LocalFocusManager.current

    // join to get full OTP
   // val otp = otpValues.joinToString("") { it.value }

    val snackbarViewModel : SnackbarViewModel = viewModel(LocalContext.current as ComponentActivity)
    val coroutineScope = rememberCoroutineScope()

    val otpViewModel : OtpViewModel = viewModel()

    // autofocus first box when screen appears
    LaunchedEffect(Unit) { otpViewModel.focusRequesters[0].requestFocus() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .systemBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
         Column {
             Row (modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
                 // Back Arrow
                 IconButton(onClick = { onBack() }) {
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
                         text = "OTP Verification",
                         fontWeight = FontWeight.Bold,
                         fontSize = 20.sp
                     )
                 }
             }

             Spacer(modifier = Modifier.height(16.dp))



             Spacer(modifier = Modifier.height(32.dp))

             // Label
             Text(
                 text = "We have sent OTP to $mobileNumber",
                 fontSize = 18.sp,
                 fontWeight = FontWeight.Bold
             )

             Spacer(modifier = Modifier.height(24.dp))

             Row(
                 horizontalArrangement = Arrangement.spacedBy(8.dp),
                 verticalAlignment = Alignment.CenterVertically
             ) {
                 otpViewModel.otpValues.forEachIndexed { index, state ->
                     OutlinedTextField(
                         value = state.value,
                         onValueChange = { value ->
                             when {
                                 // user cleared this field (delete)
                                 value.isEmpty() -> {
                                     state.value = ""
                                     if (index > 0) {
                                         // move focus to previous box
                                         otpViewModel.focusRequesters[index - 1].requestFocus()
                                     }
                                 }

                                 // user typed a single digit
                                 value.length == 1 && value[0].isDigit() -> {
                                     state.value = value
                                     if (index < otpViewModel.otpLength - 1) {
                                         otpViewModel.focusRequesters[index + 1].requestFocus()
                                     } else {
                                         // last digit entered -> hide keyboard / clear focus
                                         focusManager.clearFocus()
                                     }
                                 }

                                 // user pasted multiple characters into one box -> distribute them
                                 value.length > 1 -> {
                                     val digits = value.filter { it.isDigit() }
                                     digits.forEachIndexed { i, ch ->
                                         val pos = index + i
                                         if (pos < otpViewModel.otpLength) otpViewModel.otpValues[pos].value = ch.toString()
                                     }
                                     // move focus to the box after the last filled (or clear)
                                     val next = min(index + digits.length, otpViewModel.otpLength - 1)
                                     if (next < otpViewModel.otpLength - 1) otpViewModel.focusRequesters[next + 1].requestFocus()
                                     else focusManager.clearFocus()
                                 }
                             }
                         },
                         singleLine = true,
                         modifier = Modifier
                             .width(52.dp)
                             .focusRequester(otpViewModel.focusRequesters[index])
                             .onKeyEvent { keyEvent ->
                                 // catch Backspace when this field is already empty
                                 if (keyEvent.type == KeyEventType.KeyDown && keyEvent.key == Key.Backspace) {
                                     if (state.value.isEmpty() && index > 0) {
                                         // clear previous and move focus back
                                         otpViewModel.otpValues[index - 1].value = ""
                                         otpViewModel.focusRequesters[index - 1].requestFocus()
                                         true
                                     } else false
                                 } else false
                             },
                         keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                         textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center)
                     )
                 }
             }
         }
        Spacer(modifier = Modifier.weight(weight = 1f))
        Button(
            onClick = {
                if(otpViewModel.otpValues[0].value.isNotEmpty()){
                    onContinue()
                } else{
                    coroutineScope.launch {
                        snackbarViewModel.showSnackbar(message = "Please enter valid otp")
                    }

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
    }
}
