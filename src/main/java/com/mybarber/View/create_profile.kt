package com.mybarber.View

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
    val context = LocalContext.current

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
        OutlinedTextField(
            value = createProfileViewModel.firstName.value,
            onValueChange = { createProfileViewModel.firstName.value = it },
            placeholder = { Text("First name") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Last Name
        OutlinedTextField(
            value = createProfileViewModel.lastName.value,
            onValueChange = { createProfileViewModel.lastName.value = it },
            placeholder = { Text("Last name") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Date of Birth
        OutlinedTextField(
            value = createProfileViewModel.dob.value,
            onValueChange = { createProfileViewModel.dob.value = it },
            placeholder = { Text("Date of birth") },
            singleLine = true,
            enabled = false,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.
            fillMaxWidth().
            clickable(onClick = {
                createProfileViewModel.showDatePicker.value = true
            })
        )

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

fun onDateSelected(selectedDateMillis: Long?) {

}
