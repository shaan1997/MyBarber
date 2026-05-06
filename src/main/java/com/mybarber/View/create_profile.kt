package com.mybarber.View

import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.em
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mybarber.R
import com.mybarber.model_view.CreateProfileViewModel
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateProfileScreen(
    onContinue: () -> Unit = {}
) {
    val createProfileViewModel : CreateProfileViewModel = viewModel()



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = stringResource(R.string.create_profile_title),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        RoundedOutlinedTextField(
            value = createProfileViewModel.firstName.value,
            onValueChange = { newVal->
                createProfileViewModel.firstName.value = newVal
            },
            hint = stringResource(R.string.first_name_hint),
            modifier = Modifier.fillMaxWidth(),
            keyboardType = KeyboardType.Text,
            keyboardAction = ImeAction.Next
        )

        Spacer(modifier = Modifier.height(16.dp))

        RoundedOutlinedTextField(
            value = createProfileViewModel.lastName.value,
            onValueChange = { newVal->
                createProfileViewModel.lastName.value = newVal
            },
            hint = stringResource(R.string.last_name_hint),
            modifier = Modifier.fillMaxWidth(),
            keyboardType = KeyboardType.Text,
            keyboardAction = ImeAction.Done
        )

        Spacer(modifier = Modifier.height(16.dp))

        RoundedOutlinedTextField(
            value = createProfileViewModel.email.value,
            onValueChange = { newVal->
                createProfileViewModel.email.value = newVal
            },
            hint = stringResource(R.string.email_hint),
            modifier = Modifier.fillMaxWidth(),
            keyboardType = KeyboardType.Email,
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onContinue,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text(text = stringResource(R.string.continue_btn), fontSize = 16.sp)
        }
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
            letterSpacing = 0.05.em,
            color = MaterialTheme.colorScheme.onSurface
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
                        style = TextStyle(
                            fontSize = 17.sp,
                            letterSpacing = 0.05.em,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                        )
                    )
                }
                innerTextField()
            }
        }
    )
}
