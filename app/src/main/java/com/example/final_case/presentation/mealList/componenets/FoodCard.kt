package com.example.final_case.presentation.mealList.componenets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.final_case.common.CustomVertical2DRow
import com.example.final_case.common.CustomVerticalRowWithColumns
import com.example.final_case.common.NetworkImage
import com.example.final_case.domain.model.FoodCard
import com.example.final_case.presentation.ui.theme.Final_CaseTheme

@Composable
fun MealCard(
    mealCard: FoodCard,
    modifier: Modifier = Modifier,
    onClick: (id: String) -> Unit = {}
) {
    Column(
        modifier = modifier
            .padding(4.dp)
            .width(100.dp)
            .requiredHeight(140.dp)
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null,
                onClick = { onClick(mealCard.id) }
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NetworkImage(
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .shadow(8.dp),
            url = mealCard.thumbnail
        )
        Text(
            modifier = Modifier
                .paddingFromBaseline(top = 24.dp, bottom = 8.dp),
            text = mealCard.foodName,
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.onSurface.copy(alpha = .6f),
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun MealCardsGrid(
    mealCards: List<FoodCard>,
    modifier: Modifier = Modifier,
    onClick: (id: String) -> Unit
) {
    CustomVerticalRowWithColumns(
        modifier = modifier,
        items = mealCards,
        numOfColumns = 2,
        horizontalArrangement = Arrangement.SpaceAround,
        resetArrangementIfOneItemInRow = false
    ) { default_modifier, idx ->
        MealCard(
            modifier = default_modifier,
            mealCard = mealCards[idx],
            onClick = onClick
        )
    }
}

@Preview(name = "Meal Card Light Theme")
@Composable
fun MealCardPreviewLightTheme() {
    Final_CaseTheme {
        FoodCard(
            FoodCard.mock()
        )
    }
}

@Preview(name = "Drink Card Dark Theme")
@Composable
fun DrinkCardsPreviewDarkTheme() {
    Final_CaseTheme(darkTheme = true) {
        FoodCard(
            FoodCard.mock()
        )
    }
}