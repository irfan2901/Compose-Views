package com.example.composeviews

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composeviews.ui.theme.ComposeViewsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeViewsTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(navController, startDestination = "main") {
                        composable("main") {
                            AllViews(
                                modifier = Modifier.padding(innerPadding),
                                onNavigateNext = {
                                    navController.navigate("next")
                                }
                            )
                        }
                        composable("next") {
                            NextScreen(
                                onNavigateBack = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AllViews(modifier: Modifier = Modifier, onNavigateNext: () -> Unit) {
    var textState by remember { mutableStateOf("") }
    val context = LocalContext.current

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Blue)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "Welcome",
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Red)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Yellow,
                    contentColor = Color.Cyan
                ),
                onClick = {
                    Toast.makeText(context, "Button clicked...", Toast.LENGTH_SHORT).show()
                }
            ) { Text("Click here") }
            Spacer(modifier = Modifier.height(10.dp))
            TextField(
                value = textState,
                onValueChange = { newText -> textState = newText },
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.Blue,
                    unfocusedIndicatorColor = Color.Gray
                ),
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Enter here...") }
            )
            Spacer(modifier = Modifier.height(10.dp))
            Image(
                painter = painterResource(R.drawable.ic_launcher_background),
                contentDescription = "image"
            )
            Spacer(modifier = Modifier.height(10.dp))
            Icon(imageVector = Icons.Default.Add, contentDescription = "icon")
            Spacer(modifier = Modifier.height(10.dp))
            TextButton(onClick = {
                Toast.makeText(context, "Text clicked...", Toast.LENGTH_SHORT).show()
            }) {
                Text("Click here", style = TextStyle(fontWeight = FontWeight.Bold))
            }
            Spacer(modifier = Modifier.height(10.dp))
            CircularProgressIndicator(color = Color.Green, trackColor = Color.Blue)
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = {
                    onNavigateNext()
                }, colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Green
                )
            ) {
                Text(
                    "Next",
                    style = TextStyle(fontWeight = FontWeight.Bold, color = Color.Magenta)
                )
            }
        }
        FloatingActionButton(
            containerColor = Color.Cyan,
            onClick = {
                Toast.makeText(context, "FAB clicked...", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "icon")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun NextScreen(onNavigateBack: () -> Unit) {
    var selectedOption by remember { mutableStateOf("") }
    var isChecked by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    val dropdownItems =
        listOf("Select an option", "Option A", "Option B", "Option C")
    var selectedItem by remember { mutableStateOf(dropdownItems[0]) }
    var showDialog by remember { mutableStateOf(false) }
    var showMenu by remember { mutableStateOf(false) }
    var showOptionsMenu by remember { mutableStateOf(false) }
    Scaffold(topBar = {
        TopAppBar(
            navigationIcon = {
                IconButton(onClick = { onNavigateBack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = ""
                    )
                }
            },
            title = { Text("Second Screen") },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Blue),
            actions = {
                IconButton(onClick = { showOptionsMenu = true }) {
                    Icon(Icons.Filled.MoreVert, contentDescription = "")
                }
                DropdownMenu(expanded = showOptionsMenu, onDismissRequest = { showOptionsMenu = false }) {
                    DropdownMenuItem(text = { Text("Option 1") }, onClick = {
                        showOptionsMenu = false
                        Toast.makeText(context, "Option 1 clicked...", Toast.LENGTH_SHORT).show()
                    })
                    DropdownMenuItem(text = { Text("Option 2") }, onClick = {
                        showOptionsMenu = false
                        Toast.makeText(context, "Option 2 clicked...", Toast.LENGTH_SHORT).show()
                    })
                    DropdownMenuItem(text = { Text("Option 3") }, onClick = {
                        showOptionsMenu = false
                        Toast.makeText(context, "Option 3 clicked...", Toast.LENGTH_SHORT).show()
                    })
                }
            }
        )
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "This is the Next Screen",
                style = TextStyle(fontSize = 24.sp, color = Color.Black)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    colors = RadioButtonColors(
                        selectedColor = Color.Green,
                        unselectedColor = Color.Gray,
                        disabledSelectedColor = Color.Cyan,
                        disabledUnselectedColor = Color.Cyan
                    ),
                    selected = selectedOption == "Option 1",
                    onClick = {
                        selectedOption = "Option 1"
                        Toast.makeText(context, selectedOption, Toast.LENGTH_SHORT).show()
                    }
                )
                Text(
                    "Option 1",
                    style = TextStyle(fontWeight = FontWeight.Bold, color = Color.Black)
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    colors = RadioButtonColors(
                        selectedColor = Color.Green,
                        unselectedColor = Color.Gray,
                        disabledSelectedColor = Color.Cyan,
                        disabledUnselectedColor = Color.Cyan
                    ),
                    selected = selectedOption == "Option 2",
                    onClick = {
                        selectedOption = "Option 2"
                        Toast.makeText(context, selectedOption, Toast.LENGTH_SHORT).show()
                    }
                )
                Text(
                    "Option 2",
                    style = TextStyle(fontWeight = FontWeight.Bold, color = Color.Black)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = {
                        isChecked = it
                        Toast.makeText(
                            context,
                            "Checkbox is ${if (it) "checked" else "unchecked"}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                )
                Text(
                    "Check me",
                    style = TextStyle(fontWeight = FontWeight.Bold, color = Color.Black)
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    TextField(
                        modifier = Modifier.menuAnchor(),
                        value = selectedItem,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded =
                                expanded
                            )
                        })
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }) {
                        dropdownItems.forEachIndexed { index, text ->
                            DropdownMenuItem(
                                text = { Text(text) },
                                onClick = {
                                    selectedItem = dropdownItems[index]
                                    expanded = false
                                },
                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                            )
                        }
                    }
                }
            }
            Text(selectedItem)
            Spacer(modifier = Modifier.height(10.dp))
            Button(onClick = { showDialog = true }) { Text("Show Dialog") }

            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text("Alert") },
                    text = { Text("This is a basic alert dialog.") },
                    confirmButton = {
                        Button(onClick = { showDialog = false }) {
                            Text("OK")
                        }
                    },
                    dismissButton = {
                        Button(onClick = { showDialog = false }) {
                            Text("Cancel")
                        }
                    }
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .background(color = Color.Yellow)
                    .combinedClickable(onClick = {}, onLongClick = {
                        showMenu = true
                    })
            ) {
                Text("Long click", style = TextStyle(fontWeight = FontWeight.Bold, color = Color.Black))

                if (showMenu) {
                    Popup(alignment = Alignment.Center) {
                        ContextMenuOptions(onDismiss = { showMenu = false }, onOptionSelected = { option ->
                            showMenu = false
                            Toast.makeText(context, option, Toast.LENGTH_SHORT).show()
                        })
                    }
                }
            }
            Button(onClick = { onNavigateBack() }) {
                Text("Go Back")
            }
        }
    }
}

@Composable
fun ContextMenuOptions(onDismiss: () -> Unit, onOptionSelected: (String) -> Unit) {
    Box(
        modifier = Modifier
            .background(Color.Magenta)
            .padding(8.dp)
    ) {
        Column {
            Text(
                "Option 1",
                modifier = Modifier
                    .padding(16.dp)
                    .clickable { onOptionSelected("Option 1") }
            )
            Text(
                "Option 2",
                modifier = Modifier
                    .padding(16.dp)
                    .clickable { onOptionSelected("Option 2") }
            )
            Text(
                "Option 3",
                modifier = Modifier
                    .padding(16.dp)
                    .clickable { onOptionSelected("Option 3") }
            )
            Text(
                "Cancel",
                modifier = Modifier
                    .padding(16.dp)
                    .clickable { onDismiss() }
            )
        }
    }
}
