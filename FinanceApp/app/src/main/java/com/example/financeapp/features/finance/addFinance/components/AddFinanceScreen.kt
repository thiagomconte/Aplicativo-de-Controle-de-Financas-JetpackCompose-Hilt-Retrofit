package com.example.financeapp.features.finance.addFinance.components

import android.app.DatePickerDialog
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.financeapp.R
import com.example.financeapp.features.finance.addFinance.AddFinanceEvent
import com.example.financeapp.features.finance.addFinance.AddFinanceViewModel
import com.example.financeapp.models.Finance
import com.example.financeapp.ui.theme.DebtRed
import com.example.financeapp.ui.theme.ProfitGreen
import com.example.financeapp.utils.Constants
import com.example.financeapp.utils.Maps
import com.example.financeapp.utils.ResponseState
import com.example.financeapp.utils.UiEvent
import com.example.financeapp.utils.components.LoadingComponent
import com.example.financeapp.utils.components.TopAppBarComponent
import kotlinx.coroutines.flow.collect
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun AddFinanceScreen(
    onPopBackStack: () -> Unit,
    viewModel: AddFinanceViewModel = hiltViewModel()
) {

    val descriptionError = remember { mutableStateOf(false) }
    val valueError = remember { mutableStateOf(false) }
    val dateError = remember { mutableStateOf(false) }
    val selectedTypeError = remember { mutableStateOf(false) }

    val description = remember { mutableStateOf(TextFieldValue("")) }
    val date = remember { mutableStateOf("") }
    val value = remember { mutableStateOf(TextFieldValue("")) }
    val selectedType = remember { mutableStateOf("") }
    val mYear: Int
    val mMonth: Int
    val mDay: Int
    val now = Calendar.getInstance()
    mYear = now.get(Calendar.YEAR)
    mMonth = now.get(Calendar.MONTH)
    mDay = now.get(Calendar.DAY_OF_MONTH)
    Locale.setDefault(Locale.getDefault())
    val datePickerDialog = DatePickerDialog(
        LocalContext.current,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            val cal = Calendar.getInstance()
            cal.set(year, month, dayOfMonth)
            date.value = longToDate(cal.timeInMillis)
        }, mYear, mMonth, mDay
    )

    val state = viewModel.financeState.collectAsState(ResponseState.New).value
    LaunchedEffect(Unit) {
        viewModel.channel.collect { event ->
            if (event is UiEvent.PopBackStack) {
                onPopBackStack()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBarComponent()
        },
    ) {
        when (state) {
            is ResponseState.Loading -> LoadingComponent()
            else -> {
                if (state is ResponseState.Error) {
                    Toast.makeText(LocalContext.current, state.msg, Toast.LENGTH_LONG).show()
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp)
                        .verticalScroll(rememberScrollState()),
                ) {
                    Text(
                        stringResource(R.string.new_resume),
                        style = MaterialTheme.typography.h3,
                        fontFamily = FontFamily.Serif,
                        fontStyle = FontStyle.Italic,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.padding(vertical = 12.dp))
                    InputText(
                        onTextChange = {
                            description.value = it
                        },
                        field = description,
                        text = stringResource(R.string.description),
                        KeyboardType.Text,
                        descriptionError
                    )
                    Spacer(modifier = Modifier.padding(vertical = 12.dp))
                    InputText(
                        onTextChange = {
                            value.value = it
                        },
                        field = value,
                        text = stringResource(R.string.value),
                        keyBoardType = KeyboardType.Number,
                        error = valueError
                    )
                    Spacer(modifier = Modifier.padding(vertical = 12.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier
                            .clickable {
                                datePickerDialog.show()
                            }
                            .padding(horizontal = 4.dp, vertical = 8.dp),
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_calendar_today_black_24dp),
                            contentDescription = "calendar",

                        )
                        Text(
                            text = if (date.value.isBlank()) stringResource(R.string.select_a_date) else date.value,
                            color = Color.Gray
                        )
                    }
                    if (dateError.value) {
                        Text(
                            stringResource(R.string.select_a_date_error),
                            style = MaterialTheme.typography.subtitle2,
                            modifier = Modifier.padding(horizontal = 8.dp),
                            color = Color.Red,

                        )
                    }
                    Spacer(modifier = Modifier.padding(vertical = 12.dp))
                    Row {
                        RadioButton(
                            selected = selectedType.value == Constants.PROFIT_TEXT,
                            onClick = {
                                selectedType.value = Constants.PROFIT_TEXT
                            },
                            modifier = Modifier.align(CenterVertically),
                            colors = RadioButtonDefaults.colors(selectedColor = ProfitGreen)
                        )
                        Text(Constants.PROFIT_TEXT, modifier = Modifier.align(CenterVertically))
                        Spacer(modifier = Modifier.size(16.dp))
                        RadioButton(
                            selected = selectedType.value == Constants.DEBT_TEXT,
                            onClick = {
                                selectedType.value = Constants.DEBT_TEXT
                            },
                            modifier = Modifier.align(CenterVertically),
                            colors = RadioButtonDefaults.colors(selectedColor = DebtRed)
                        )
                        Text(Constants.DEBT_TEXT, modifier = Modifier.align(CenterVertically))
                    }
                    if (selectedTypeError.value) {
                        Text(
                            stringResource(R.string.select_a_type_error),
                            style = MaterialTheme.typography.subtitle2,
                            modifier = Modifier.padding(horizontal = 8.dp),
                            color = Color.Red,

                        )
                    }
                    Spacer(modifier = Modifier.padding(vertical = 12.dp))
                    Button(
                        onClick = {
                            validate(
                                description.value.text,
                                value.value.text,
                                date.value,
                                selectedType.value,
                                onDescriptionError = { descriptionError.value = it },
                                onValueError = { valueError.value = it },
                                onDateError = { dateError.value = it },
                                onSelectedTypeError = { selectedTypeError.value = it },
                                onValidate = {
                                    viewModel.onEvent(
                                        AddFinanceEvent.AddFinance(
                                            Finance(
                                                "",
                                                description.value.text,
                                                value.value.text.toFloat(),
                                                formatDate(date.value),
                                                Maps.types[selectedType.value] ?: Constants.PROFIT
                                            )
                                        )
                                    )
                                }
                            )
                        },
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.White,
                            backgroundColor = Color.Black
                        ),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            stringResource(R.string.confirm),
                            style = MaterialTheme.typography.h6,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                    Spacer(modifier = Modifier.padding(vertical = 12.dp))
                    OutlinedButton(
                        onClick = { onPopBackStack() },
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.Black,
                            backgroundColor = Color.White,
                        ),
                        border = BorderStroke(2.dp, Color.Black),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            stringResource(R.string.back),
                            style = MaterialTheme.typography.h6,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

fun formatDate(value: String): String {
    val fields = value.split("/")
    return "${fields[2]}/${fields[1]}/${fields[0]}"
}

@Composable
fun InputText(
    onTextChange: (TextFieldValue) -> Unit,
    field: MutableState<TextFieldValue>,
    text: String,
    keyBoardType: KeyboardType,
    error: MutableState<Boolean>,
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        value = field.value,
        onValueChange = {
            onTextChange(it)
        },
        label = {
            Text(text, color = Color.Black)
        },
        keyboardOptions = KeyboardOptions(keyboardType = keyBoardType),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            unfocusedLabelColor = Color.Black,
            focusedLabelColor = Color.Black,
            cursorColor = Color.Black,
            focusedIndicatorColor = if (error.value) Color.Red else Color.Black,
            unfocusedIndicatorColor = if (error.value) Color.Red else Color.Black,
            textColor = Color.Black,
        )
    )
}

fun validate(
    description: String,
    value: String,
    date: String,
    selectedType: String,
    onDescriptionError: (Boolean) -> Unit,
    onValueError: (Boolean) -> Unit,
    onDateError: (Boolean) -> Unit,
    onSelectedTypeError: (Boolean) -> Unit,
    onValidate: () -> Unit
) {
    onDescriptionError(description.isBlank())
    onValueError(value.isBlank())
    onDateError(date.isBlank())
    onSelectedTypeError(selectedType.isBlank())
    if (description.isNotBlank() && value.isNotBlank() && date.isNotBlank() && selectedType.isNotBlank()) {
        onValidate()
    }
}

fun longToDate(milliseconds: Long): String {
    milliseconds.let {
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.US)
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = it
        return formatter.format(calendar.time)
    }
}
