package com.mybarber.View

import RoundedOutlinedTextField
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.em
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.mybarber.model_view.CreateProfileViewModel
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateProfileScreen(
    onContinue: () -> Unit = {}
) {


    val createProfileViewModel : CreateProfileViewModel = viewModel()

    if(createProfileViewModel.showDatePicker.value){
        //val calendar = Calendar.getInstance();
        val datePickerState = rememberDatePickerState(initialDisplayMode = DisplayMode.Input)

        DatePickerDialog(
            onDismissRequest = { createProfileViewModel.showDatePicker.value = false },
            {
                TextButton(onClick = {
                   // onDateSelected(datePickerState.selectedDateMillis)
                    datePickerState.selectedDateMillis?.let {
                        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                        createProfileViewModel.dob.value = formatter.format(Date(it))   // format the selected date
                    }
                    createProfileViewModel.showDatePicker.value = false
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { createProfileViewModel.showDatePicker.value = false }) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        // Title
        Text(
            text = "Create Profile",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        // First Name

        RoundedOutlinedTextField(
            value = createProfileViewModel.firstName.value,
            onValueChange = { newVal->
                createProfileViewModel.firstName.value = newVal
                            },
            hint = "First name",
            modifier = Modifier.fillMaxWidth(),
            keyboardType = KeyboardType.Text,
            keyboardAction = ImeAction.Next
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Last Name
//        OutlinedTextField(
//            value = createProfileViewModel.lastName.value,
//            onValueChange = { createProfileViewModel.lastName.value = it },
//            placeholder = { Text("Last name") },
//            singleLine = true,
//            modifier = Modifier.fillMaxWidth()
//        )

        RoundedOutlinedTextField(
            value = createProfileViewModel.lastName.value,
            onValueChange = { newVal->
                createProfileViewModel.lastName.value = newVal
            },
            hint = "Last name",
            modifier = Modifier.fillMaxWidth(),
            keyboardType = KeyboardType.Text
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Date of Birth
  //      OutlinedTextField(
//            value = createProfileViewModel.dob.value,
//            onValueChange = { createProfileViewModel.dob.value = it },
//            placeholder = { Text("Date of birth") },
//            singleLine = true,
//            enabled = false,
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//            modifier = Modifier.
//            fillMaxWidth().
//            clickable(onClick = {
//                createProfileViewModel.showDatePicker.value = true
//            })
//        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF1F5F9), RoundedCornerShape(8.dp))
                .clickable { createProfileViewModel.showDatePicker.value = true } // ← clickable wrapper
        ){
            RoundedOutlinedTextField(
                value = createProfileViewModel.dob.value,
                isEnable = false,
                onValueChange = { newVal->
                    createProfileViewModel.dob.value = newVal
                },
                hint = "Date of birth",
                modifier = Modifier.fillMaxWidth(),
                keyboardType = KeyboardType.Number,
            )
        }




        Spacer(modifier = Modifier.height(32.dp))

        // Continue Button
        Button(
            onClick = onContinue,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = "Continue", fontSize = 16.sp)
        }

    }
}


@Composable
fun RoundedOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String = "",
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        textStyle = TextStyle(
            fontSize = 17.sp,
            letterSpacing = 0.2.em
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
