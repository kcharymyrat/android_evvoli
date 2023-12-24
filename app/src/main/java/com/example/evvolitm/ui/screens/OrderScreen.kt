package com.example.evvolitm.ui.screens


import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.evvolitm.R
import com.example.evvolitm.presentation.CartScreenState
import com.example.evvolitm.ui.theme.Shapes
import java.util.Calendar
import java.util.Date
import java.util.Locale


@Composable
fun OrderForm(
    navController: NavHostController,
    cartScreenState: CartScreenState,
    onCreateNewCardScreenState: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val paymentOptions = listOf("Cash", "Card Terminal")
    val countryCode = "+993" // example country code


    if (cartScreenState.id == null) {
        onCreateNewCardScreenState()
    }

    val cartQty = cartScreenState.cartQty
    val cartPrice = cartScreenState.cartTotalPrice
    val cartItems = cartScreenState.cartItems

    val focusManager = LocalFocusManager.current

    var customerName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var selectedPaymentOption by remember { mutableStateOf(paymentOptions[0]) }
    var isFocused by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf<Date?>(null) }

    var isValid by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize() // Fill the maximum available space
            .padding(dimensionResource(id = R.dimen.padding_medium))
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f) // Take all available space minus the space needed for the button
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = modifier.padding(horizontal = 16.dp)
                    ) {
                        Text(
                            text = "Payment option",
                            style = MaterialTheme.typography.titleSmall,
                        )
                        paymentOptions.forEach { option ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                RadioButton(
                                    selected = (option == selectedPaymentOption),
                                    onClick = { selectedPaymentOption = option }
                                )
                                Text(
                                    text = option,
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                Card {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = modifier.padding(horizontal = 16.dp)
                    ) {
                        OutlinedTextField(
                            value = customerName,
                            onValueChange = { customerName = it },
                            label = { Text("Name") },
                            isError = !isValid && customerName.isEmpty(),
                            colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = Color.White, // Set background color to white
                                focusedContainerColor = Color.White,
                                disabledContainerColor = Color.White,
                                errorContainerColor = Color.White,
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                        if (!isValid && customerName.isEmpty()) {
                            Text("Name is required", color = MaterialTheme.colorScheme.error)
                        }

                        OutlinedTextField(
                            value = phone,
                            onValueChange = { phone = it },
                            label = { Text("Phone") },
                            isError = !isValid && !isValidPhone(phone),
                            leadingIcon = if (isFocused) {
                                { Text(countryCode, modifier = Modifier.padding(end = 4.dp)) }
                            } else null, // Use leadingIcon for non-editable prefix
                            colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = Color.White, // Set background color to white
                                focusedContainerColor = Color.White,
                                disabledContainerColor = Color.White,
                                errorContainerColor = Color.White,
                            ),
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                            modifier = Modifier
                                .fillMaxWidth()
                                .onFocusChanged { focusState ->
                                    isFocused = focusState.isFocused
                                }
                        )
                        if (!isValid && !isValidPhone(phone)) {
                            Text("Invalid phone number", color = MaterialTheme.colorScheme.error)
                        }

                        OutlinedTextField(
                            value = address,
                            onValueChange = { address = it },
                            label = { Text("Address") },
                            isError =!isValid && address.isEmpty(),
                            colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = Color.White, // Set background color to white
                                focusedContainerColor = Color.White,
                                disabledContainerColor = Color.White,
                                errorContainerColor = Color.White,
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                        if (!isValid && address.isEmpty()) {
                            Text("Address is required", color = MaterialTheme.colorScheme.error)
                        }

                        println("selectedDate = $selectedDate")
                        println("isValid = $isValid")
                        OutlinedTextField(
                            value = selectedDate?.toFormattedString() ?: "",
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Select Date and Time") },
                            isError = !isValid && (selectedDate == null),
                            trailingIcon = {
                                IconButton(onClick = { showDateTimePicker(context) { date ->
                                    selectedDate = date
                                }}) {
                                    Icon(Icons.Default.CalendarToday, contentDescription = "Select Date")
                                }
                            },
                            colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = Color.White, // Set background color to white
                                focusedContainerColor = Color.White,
                                disabledContainerColor = Color.White,
                                errorContainerColor = Color.White,
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                        if (!isValid && (selectedDate == null)) {
                            Text("Delivery date is required", color = MaterialTheme.colorScheme.error)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.padding_small))
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                ) {
                    Text(
                        text = "TOTAL",
                        style = MaterialTheme.typography.titleLarge,
                    )
                    Text(
                        text = "%.2f".format(cartPrice) + " m.",
                        style = MaterialTheme.typography.titleLarge,
                    )
                }
                Button(
                    onClick = {
                        isValid = validateForm(customerName, phone, address, selectedDate)
                        if (isValid) handleSubmit(customerName, phone, address, selectedDate)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Submit Order")
                }
            }
        }
    }
}

@Composable
fun DateTimeField(isValid: Boolean) {
    var selectedDate by remember { mutableStateOf<Date?>(null) }
    val context = LocalContext.current

    OutlinedTextField(
        value = selectedDate?.toFormattedString() ?: "",
        onValueChange = {},
        readOnly = true,
        label = { Text("Select Date and Time") },
        isError = !isValid && selectedDate != null,
        trailingIcon = {
            IconButton(onClick = { showDateTimePicker(context) { date ->
                selectedDate = date
            }}) {
                Icon(Icons.Default.CalendarToday, contentDescription = "Select Date")
            }
        },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.White, // Set background color to white
            focusedContainerColor = Color.White,
            disabledContainerColor = Color.White,
            errorContainerColor = Color.White,
        ),
        modifier = Modifier.fillMaxWidth()
    )
    if (!isValid && selectedDate != null) {
        Text("Delivery date is required", color = MaterialTheme.colorScheme.error)
    }
}



fun isValidPhone(phone: String): Boolean {
    // Adjust the condition based on your phone number format requirements
    return phone.length == 8
}

fun validateForm(
    name: String,
    phone: String,
    address: String,
    selectedDate: Date?
): Boolean {
    return name.isNotEmpty() && isValidPhone(phone) && address.isNotEmpty() && (selectedDate == null)
}

fun handleSubmit(
    customerName: String,
    phoneNumber: String,
    address: String,
    selectedDate: Date?
) {
    // Here, you will send data to your server
    // Example:
    // viewModel.submitOrder(customerName, phoneNumber, address)
}

fun showDateTimePicker(context: Context, onDateTimeSelected: (Date) -> Unit) {
    val calendar = Calendar.getInstance()
    DatePickerDialog(context, { _, year, month, dayOfMonth ->
        TimePickerDialog(context, { _, hourOfDay, minute ->
            calendar.set(year, month, dayOfMonth, hourOfDay, minute)
            onDateTimeSelected(calendar.time)
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show()
    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
}

fun Date.toFormattedString(): String {
    val format = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    return format.format(this)
}

fun serializeDateTime(date: Date?): String {
    return date?.toFormattedString() ?: ""
}
