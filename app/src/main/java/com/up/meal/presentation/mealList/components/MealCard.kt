package com.up.meal.presentation.mealList.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import com.up.meal.common.CustomVerticalRowWithColumns
import com.up.meal.common.NetworkImage
import com.up.meal.domain.model.MealCard
import com.up.meal.presentation.ui.theme.MealAppTheme

@Composable
fun MealCard(
    mealCard: MealCard,
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
            text = mealCard.mealName,
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
    mealsCards: List<MealCard>,
    modifier: Modifier = Modifier,
    onClick: (id: String) -> Unit
) {
    CustomVerticalRowWithColumns(
        modifier = modifier,
        items = mealsCards,
        numOfColumns = 2,
        horizontalArrangement = Arrangement.SpaceAround,
        resetArrangementIfOneItemInRow = false
    ) { default_modifier, idx ->
        MealCard(
            modifier = default_modifier,
            mealCard = mealsCards[idx],
            onClick = onClick
        )
    }
}

@Preview(name = "Meal Card Light Theme")
@Composable
fun MealCardPreviewLightTheme() {
    MealAppTheme {
        MealCard(
            MealCard.mock()
        )
    }
}

@Preview(name = "Meal Card Dark Theme")
@Composable
fun MealCardsPreviewDarkTheme() {
    MealAppTheme(darkTheme = true) {
        MealCard(
            MealCard.mock()
        )
    }
}