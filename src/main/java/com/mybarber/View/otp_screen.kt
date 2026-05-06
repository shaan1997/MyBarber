import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mybarber.R
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
    val snackbarViewModel : SnackbarViewModel = viewModel(LocalContext.current as ComponentActivity)
    val coroutineScope = rememberCoroutineScope()
    val otpViewModel : OtpViewModel = viewModel()

    LaunchedEffect(Unit) { otpViewModel.focusRequesters[0].requestFocus() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp)
            .systemBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
         Column {
             Row (modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
                 IconButton(onClick = { onBack() }) {
                     Icon(
                         imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                         contentDescription = stringResource(R.string.back),
                         tint = MaterialTheme.colorScheme.onBackground
                     )
                 }
                 Spacer(modifier = Modifier.weight(1f))
                 Box(
                     modifier = Modifier.fillMaxWidth(),
                     contentAlignment = Alignment.Center
                 ) {
                     Text(
                         text = stringResource(R.string.otp_verification_title),
                         fontWeight = FontWeight.Bold,
                         fontSize = 20.sp,
                         color = MaterialTheme.colorScheme.onBackground
                     )
                 }
             }

             Spacer(modifier = Modifier.height(32.dp))

             Text(
                 text = stringResource(R.string.otp_sent_to, mobileNumber),
                 fontSize = 18.sp,
                 fontWeight = FontWeight.Bold,
                 color = MaterialTheme.colorScheme.onBackground
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
                                 value.isEmpty() -> {
                                     state.value = ""
                                     if (index > 0) {
                                         otpViewModel.focusRequesters[index - 1].requestFocus()
                                     }
                                 }
                                 value.length == 1 && value[0].isDigit() -> {
                                     state.value = value
                                     if (index < otpViewModel.otpLength - 1) {
                                         otpViewModel.focusRequesters[index + 1].requestFocus()
                                     } else {
                                         focusManager.clearFocus()
                                     }
                                 }
                                 value.length > 1 -> {
                                     val digits = value.filter { it.isDigit() }
                                     digits.forEachIndexed { i, ch ->
                                         val pos = index + i
                                         if (pos < otpViewModel.otpLength) otpViewModel.otpValues[pos].value = ch.toString()
                                     }
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
                                 if (keyEvent.type == KeyEventType.KeyDown && keyEvent.key == Key.Backspace) {
                                     if (state.value.isEmpty() && index > 0) {
                                         otpViewModel.otpValues[index - 1].value = ""
                                         otpViewModel.focusRequesters[index - 1].requestFocus()
                                         true
                                     } else false
                                 } else false
                             },
                         keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                         textStyle = LocalTextStyle.current.copy(
                             textAlign = TextAlign.Center,
                             color = MaterialTheme.colorScheme.onSurface
                         ),
                         colors = OutlinedTextFieldDefaults.colors(
                             focusedBorderColor = MaterialTheme.colorScheme.primary,
                             unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                             focusedContainerColor = MaterialTheme.colorScheme.surface,
                             unfocusedContainerColor = MaterialTheme.colorScheme.surface
                         )
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
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text(
                text = stringResource(R.string.continue_btn),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}
