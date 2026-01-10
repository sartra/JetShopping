package hoods.com.jetshopping.ui.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import hoods.com.jetshopping.ui.Category
import hoods.com.jetshopping.ui.Utils

@Composable
fun HomeScreen(
    onNavigate: (Int) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { onNavigate(-1) }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "See Item Details"
                )
            }
        }
    ) { paddingValues ->
        when (val currentState = state.value) {
            is HomeState.Content -> {
                LazyColumn() {
                    item {
                        LazyRow() {
                            items(Utils.category, key = { it.id }) { category ->
                                CategoryItem(
                                    iconRes = category.resId,
                                    title = category.title,
                                    selected = category.id == currentState.category.id,
                                    onClick = { viewModel.onCategoryChange(category) }
                                )
                                Spacer(modifier = Modifier.size(4.dp))
                            }
                        }
                    }
                }
            }
            is HomeState.Loading -> {
                // Show loading indicator
            }
            is HomeState.Error -> {
                // Show error message
            }
        }
    }

}

@Composable
fun CategoryItem(
    @DrawableRes iconRes: Int,
    title: String,
    selected: Boolean,
    onClick: () -> Unit
){
    val interactionSource = remember { MutableInteractionSource() }
    
    Card(
        modifier = Modifier
            .padding(8.dp)
            .selectable(
                selected = selected,
                interactionSource = interactionSource,
                indication = rememberRipple(),
                onClick = { onClick() }
            ),
        border = BorderStroke(
            1.dp, if (selected) MaterialTheme.colors.primary
            else  MaterialTheme.colors.primary.copy(alpha = 0.5f)
        ),
        backgroundColor = if (selected) MaterialTheme.colors.primary
        else  MaterialTheme.colors.primary.copy(alpha = 0.5f),
        contentColor = if (selected) MaterialTheme.colors.onPrimary
        else  MaterialTheme.colors.onSurface
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(8.dp)
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = title,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Medium
            )
        }
    }
}