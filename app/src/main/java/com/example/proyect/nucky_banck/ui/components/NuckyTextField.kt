package com.example.proyect.nucky_banck.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyect.nucky_banck.ui.theme.NuckyColors

@Composable
fun NuckyTextField(

    value: String,

    onValueChange: (String) -> Unit,

    label: String,

    leadingIcon: @Composable (() -> Unit)? = null,

    errorMessage: String? = null,

    isPassword: Boolean = false,

    keyboardType: KeyboardType = KeyboardType.Text
) {

    var passwordVisible by remember {
        mutableStateOf(false)
    }

    OutlinedTextField(

        value = value,

        onValueChange = onValueChange,

        label = {
            Text(text = label)
        },

        leadingIcon = leadingIcon,

        trailingIcon = {

            if (isPassword) {

                IconButton(
                    onClick = {
                        passwordVisible = !passwordVisible
                    }
                ) {

                    Icon(
                        imageVector =
                            if (passwordVisible)
                                Icons.Default.Visibility
                            else
                                Icons.Default.VisibilityOff,

                        contentDescription = null
                    )
                }
            }
        },

        visualTransformation =

            if (isPassword && !passwordVisible)
                PasswordVisualTransformation()
            else
                VisualTransformation.None,

        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        ),

        singleLine = true,

        isError = errorMessage != null,

        supportingText = {

            if (errorMessage != null) {

                Text(
                    text = errorMessage,
                    color = NuckyColors.ErrorRed,
                    fontSize = 12.sp
                )
            }
        },

        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(12.dp),

        colors = OutlinedTextFieldDefaults.colors(

            focusedBorderColor = NuckyColors.NavyBlue,

            unfocusedBorderColor = NuckyColors.BorderGray,

            focusedLabelColor = NuckyColors.NavyBlue,

            unfocusedLabelColor = NuckyColors.TextGray,

            cursorColor = NuckyColors.NavyBlue,

            focusedTextColor = NuckyColors.TextDark,

            unfocusedTextColor = NuckyColors.TextDark,

            focusedLeadingIconColor = NuckyColors.NavyBlue,

            unfocusedLeadingIconColor = NuckyColors.NavyBlue,

            focusedTrailingIconColor = NuckyColors.NavyBlue,

            unfocusedTrailingIconColor = NuckyColors.NavyBlue,

            errorBorderColor = NuckyColors.ErrorRed
        )
    )
}