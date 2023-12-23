package com.example.evvolitm.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue


import androidx.compose.ui.platform.LocalContext
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.evvolitm.domain.model.CartItemProduct
import com.example.evvolitm.presentation.CartScreenState
import java.util.*



@Composable
fun OrderForm(
    navController: NavHostController,
    cartScreenState: CartScreenState,
    onUpdateCartAndItsState: (CartItemProduct, Boolean) -> Unit,
    onCreateNewCardScreenState: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (cartScreenState.id == null) {
        onCreateNewCardScreenState()
    }

    val cartQty = cartScreenState.cartQty
    val cartPrice = cartScreenState.cartTotalPrice
    val cartItems = cartScreenState.cartItems

    var customerName by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    // ... other fields ...

    Column(
        modifier = modifier
    ) {
        TextField(
            value = customerName,
            onValueChange = { customerName = it },
            label = { Text("Customer Name") }
        )
        TextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("Phone Number") }
        )
        TextField(
            value = address,
            onValueChange = { address = it },
            label = { Text("Address") }
        )

        DateTimeField()

        Button(onClick = { handleSubmit(customerName, phoneNumber, address) }) {
            Text("Submit Order")
        }
    }
}

fun handleSubmit(customerName: String, phoneNumber: String, address: String) {
    // Here, you will send data to your server
    // Example:
    // viewModel.submitOrder(customerName, phoneNumber, address)
}


@Composable
fun DateTimeField() {
    var selectedDate by remember { mutableStateOf<Date?>(null) }
    val context = LocalContext.current

    TextField(
        value = selectedDate?.toFormattedString() ?: "",
        onValueChange = {},
        readOnly = true,
        label = { Text("Select Date and Time") },
        trailingIcon = {
            IconButton(onClick = { showDateTimePicker(context) { date ->
                selectedDate = date
            }}) {
                Icon(Icons.Default.CalendarToday, contentDescription = "Select Date")
            }
        }
    )
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
