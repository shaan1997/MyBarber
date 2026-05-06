import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mybarber.R
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
    val invalidPhoneError = stringResource(R.string.error_invalid_phone)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .systemBarsPadding(),
    ) {
        Row (modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
            IconButton(onClick = { onBackClick() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = stringResource(R.string.signup_title),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = stringResource(R.string.enter_mobile_number),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(12.dp))

        RoundedOutlinedTextField(
            value = signUpViewModel.phoneNumber,
            onValueChange = { newVal->
                if(newVal.length <= signUpViewModel.maxLength){
                    signUpViewModel.phoneNumber = newVal
                }
            },
            hint = stringResource(R.string.mobile_number_hint),
            modifier = Modifier.fillMaxWidth(),
            keyboardType = KeyboardType.Phone
        )

        Spacer(modifier = Modifier.weight(1f))
        
        Button(
            onClick = {
                 if(signUpViewModel.phoneNumber.isEmpty()){
                     coroutineScope.launch {
                         snackbarViewModel.showSnackbar(invalidPhoneError)
                     }
                 } else {
                     onContinueClick(signUpViewModel.phoneNumber)
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

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(R.string.terms_and_privacy),
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
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
        textStyle = LocalTextStyle.current.copy(
            fontSize  = 17.sp,
            letterSpacing = 0.05.em,
            color = MaterialTheme.colorScheme.onSurface,
        ),
        decorationBox = { innerTextField ->
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 18.dp)
            ) {
                if (value.isEmpty()) {
                    Text(
                        text = hint,
                        style = LocalTextStyle.current.copy(
                            fontSize  = 17.sp,
                            letterSpacing = 0.05.em,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
                        ),
                    )
                }
                innerTextField()
            }
        }
    )
}
