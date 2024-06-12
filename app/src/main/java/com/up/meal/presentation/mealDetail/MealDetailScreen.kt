package com.up.meal.presentation.mealDetail

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.up.meal.R
import com.up.meal.common.CustomVerticalRowWithColumns
import com.up.meal.common.FileUtil
import com.up.meal.common.NetworkImage
import com.up.meal.common.openIntentChooser
import com.up.meal.common.takeScreenshot
import com.up.meal.domain.model.Ingredient
import com.up.meal.domain.model.IngredientDetail
import com.up.meal.domain.model.MealDetail
import com.up.meal.presentation.ingredient.IngredientBottomSheetViewState
import com.up.meal.presentation.ingredient.IngredientDetailViewModel
import com.up.meal.presentation.ui.theme.FavoriteTint
import com.up.meal.presentation.ui.theme.TopAppBarDarkBackground
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MealDetailScreen(
    viewModel: MealDetailViewModel,
    ingredientViewModel: IngredientDetailViewModel,
    mealId: String,
    onBackPressed: () -> Unit
) {
    LaunchedEffect(key1 = mealId) {
        viewModel.getMealDetailsById(mealId)
        viewModel.isMealCardAddedToFavorites(mealId)
    }
    val viewState = rememberSaveable(inputs = arrayOf(mealId), key = mealId) {
        viewModel.mealDetailViewState
    }

    val isMealAddedAsFavorite = rememberSaveable {
        viewModel.isMealAddedAsFavorite
    }

    val ingredientBottomSheetViewState = remember {
        ingredientViewModel.ingredientBottomSheetViewState
    }

    val context = LocalContext.current
    val view = LocalView.current

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )

    val coroutineScope = rememberCoroutineScope()

    val onShareClicked = {
        view.takeScreenshot(context) {
            val uri = FileUtil.storeScreenShot(
                it,
                context,
                viewState.value.mealDetail?.mealName ?: "Meal"
            )
            context.openIntentChooser(
                "Hey there check this meal. It's mind blowing!",
                contentUri = uri
            )
        }
    }

    val onFavoriteClicked = {
        if (isMealAddedAsFavorite.value) {
            viewModel.removeMealCardFromFavorite(mealId)
        } else {
            viewModel.addMealCardToFavorites()
        }
    }

    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            IngredientDetailBottomSheetContent(ingredientBottomSheetViewState)
        },
        sheetPeekHeight = 0.dp,
        topBar = {
            AppBar(
                isFavorite = isMealAddedAsFavorite.value,
                onBackPressed = onBackPressed,
                onFavoriteClicked = { onFavoriteClicked() },
                onShareClicked = { onShareClicked() }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            if (viewState.value.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            viewState.value.error?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colors.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp)
                        .align(Alignment.Center)
                )
            }
            viewState.value.mealDetail?.let {
                MealDetailScreenContent(
                    mealDetail = it,
                    onIngredientCardClicked = {
                        coroutineScope.launch {
                            if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                                ingredientViewModel.getIngredientDetails(it)
                                bottomSheetScaffoldState.bottomSheetState.expand()
                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun AppBar(
    isFavorite: Boolean = false,
    onBackPressed: () -> Unit,
    onShareClicked: () -> Unit,
    onFavoriteClicked: () -> Unit
) {
    TopAppBar(
        navigationIcon = {
            Icon(
                imageVector = Icons.Rounded.ArrowBack,
                contentDescription = null,
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null,
                        onClick = { onBackPressed() }
                    )
            )
        },
        title = {},
        backgroundColor = if (isSystemInDarkTheme()) TopAppBarDarkBackground else MaterialTheme.colors.background,
        actions = {
            Icon(
                imageVector = Icons.Rounded.Favorite,
                tint = if (isFavorite) FavoriteTint else LocalContentColor.current.copy(alpha = LocalContentAlpha.current),
                contentDescription = null,
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null,
                        onClick = { onFavoriteClicked() }
                    )
            )
            Icon(
                imageVector = Icons.Rounded.Share,
                contentDescription = null,
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null,
                        onClick = { onShareClicked() }
                    )
            )
        }
    )
}

@Composable
fun MealDetailScreenContent(
    mealDetail: MealDetail,
    modifier: Modifier = Modifier,
    onIngredientCardClicked: (ingredientName: String) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
        item {
            NetworkImage(
                url = mealDetail.image,
                modifier = Modifier
                    .padding(16.dp)
                    .aspectRatio(1f)
                    .clip(CircleShape)
                    .shadow(8.dp)
            )
        }
        item {
            Text(
                text = mealDetail.mealName,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .fillMaxWidth(),
                style = MaterialTheme.typography.h6,
                fontWeight = Bold,
            )
        }
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .fillMaxWidth()
            ) {

            }
        }
        item {
            if (mealDetail.instructions.isNotEmpty()) {
                MealIngredientsSection(modifier, mealDetail.ingredients, onIngredientCardClicked)
            } else {
                //Todo: handle else case
            }
        }
        item {
            if (mealDetail.instructions.isNotEmpty()) {
                MealInstructions(modifier = modifier, instructions = mealDetail.instructions)
            } else {
                //Todo: handle else case
            }
        }
    }
}

@Composable
fun MealInfoCard(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    info: String
) {
    Card(
        modifier = modifier
            .padding(horizontal = 8.dp, vertical = 4.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = info,
                modifier = modifier.size(24.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface)
            )
            Text(
                text = info,
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier
                    .padding(start = 4.dp)
                    .align(Alignment.CenterVertically)
            )
        }
    }
}

@Composable
fun MealIngredientsSection(
    modifier: Modifier = Modifier,
    ingredients: List<Ingredient>,
    onIngredientCardClicked: (ingredientName: String) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "Ingredients",
            style = MaterialTheme.typography.subtitle1,
            modifier = modifier
                .padding(top = 12.dp, bottom = 4.dp)
                .fillMaxWidth()
        )

        CustomVerticalRowWithColumns(
            items = ingredients,
            numOfColumns = 2
        ) { default_modifier, idx ->
            IngredientCard(
                default_modifier
                    .fillMaxWidth()
                    .weight(1f),
                ingredientNumber = idx + 1,
                ingredient = ingredients[idx],
                onIngredientCardClicked = onIngredientCardClicked
            )
        }
    }
}

@Composable
fun IngredientCard(
    modifier: Modifier = Modifier,
    ingredientNumber: Int,
    ingredient: Ingredient,
    onIngredientCardClicked: (ingredientName: String) -> Unit
) {
    Row(
        modifier = modifier
            .padding(vertical = 8.dp, horizontal = 4.dp)
            .clickable { onIngredientCardClicked.invoke(ingredient.name) }
    ) {
        Card(
            elevation = 4.dp,
            shape = CircleShape
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
            ) {
                Text(
                    text = "$ingredientNumber",
                    style = MaterialTheme.typography.overline,
                    color = MaterialTheme.colors.onSurface,
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
        }
        Spacer(modifier = Modifier.padding(4.dp))
        Column {
            Text(
                text = ingredient.name,
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.onSurface
            )
            Text(
                text = ingredient.amount,
                style = MaterialTheme.typography.overline,
                color = MaterialTheme.colors.onSurface.copy(alpha = .5f)
            )
        }
    }
}

@Composable
fun MealInstructions(
    modifier: Modifier = Modifier,
    instructions: String
) {
    Card(
        elevation = 1.dp,
        shape = MaterialTheme.shapes.medium,
        modifier = modifier.padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = "Instructions - $instructions",
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.9f),
            modifier = modifier
                .padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Composable
fun IngredientDetailBottomSheetContent(
    viewState: State<IngredientBottomSheetViewState>
) {
    val configuration = LocalConfiguration.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(
                (configuration.screenHeightDp * 0.25).dp
            )
            .padding(16.dp, 16.dp, 16.dp, 32.dp)
    ) {
        if (viewState.value.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
        viewState.value.error?.let {
            Text(
                text = it,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp)
            )
        }
        viewState.value.ingredientDetail?.let {
            IngredientDetailContent(it)
        }
    }
}

@Composable
fun IngredientDetailContent(
    ingredientDetail: IngredientDetail
) {
    LazyColumn {
        item {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                NetworkImage(
                    url = ingredientDetail.getIngredientImage(IngredientDetail.Dimension.LARGE),
                    modifier = Modifier
                        .padding(16.dp)
                        .size(200.dp)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .align(Alignment.Center),
                    failureFallback = {
                        Image(
                            painter = painterResource(id = R.drawable.ingredient_placeholder),
                            contentDescription = null,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                )
            }
        }
        item {
            Text(
                text = ingredientDetail.name,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .fillMaxWidth(),
                style = MaterialTheme.typography.caption,
                fontWeight = Bold,
            )
        }
        item {
            if (ingredientDetail.description.isNotEmpty()) {
                Card(
                    elevation = 1.dp,
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = ingredientDetail.description,
                        style = MaterialTheme.typography.subtitle2,
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}