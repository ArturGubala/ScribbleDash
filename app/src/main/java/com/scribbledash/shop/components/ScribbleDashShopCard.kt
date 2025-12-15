package com.scribbledash.shop.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.scribbledash.R
import com.scribbledash.core.presentation.utils.ShopItemTier
import com.scribbledash.core.presentation.utils.ShopItemType
import com.scribbledash.domain.model.ShopItem
import com.scribbledash.ui.theme.ScribbleDashTheme

@Composable
fun ScribbleDashShopCard(
    item: ShopItem,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
) {
    Box(
        modifier = modifier.clickable { onItemClick() }
    ) {
        if (isSelected) {
            Image(
                painter = painterResource(R.drawable.ic_check),
                contentDescription = "Selected",
                modifier = Modifier
                    .size(32.dp)
                    .align(alignment = Alignment.TopEnd)
                    .offset(x = 10.dp, y = -10.dp)
                    .zIndex(1f)
            )
        }

        Box(
            modifier = Modifier
                .size(width = 110.dp, height = 152.dp)
                .background(
                    color = when (item.category) {
                        ShopItemTier.BASIC -> Color.White
                        ShopItemTier.PREMIUM -> Color(0xFF9747FF) // Purple
                        ShopItemTier.LEGENDARY -> Color(0xFFFF9747) // Orange
                    },
                    shape = RoundedCornerShape(16.dp)
                )
                .then(
                    if (isSelected) {
                        Modifier.border(
                            width = 2.dp,
                            color = Color(0xFF0DD280),
                            shape = RoundedCornerShape(16.dp)
                        )
                    } else {
                        Modifier
                    }
                )
                .padding(12.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Category label
                Text(
                    text = item.category.name,
                    style = MaterialTheme.typography.labelSmall,
                    color = when (item.category) {
                        ShopItemTier.BASIC -> Color(0xFF9E9E9E)
                        ShopItemTier.PREMIUM, ShopItemTier.LEGENDARY -> Color.White
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Preview window
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .clip(RoundedCornerShape(14.dp))
                        .then(
                            // Apply background color only if no image
                            if (item.previewBackgroundImage == null) {
                                Modifier.background(
                                    color = item.previewBackgroundColor ?: Color.White
                                )
                            } else {
                                Modifier
                            }
                        )
                        .then(
                            if (item.category == ShopItemTier.BASIC &&
                                        item.type == ShopItemType.PEN_COLOR) {
                                Modifier.border(
                                    width = 2.dp,
                                    color = Color(0xFFF6F1EC),
                                    shape = RoundedCornerShape(14.dp)
                                )
                            } else if (item.category == ShopItemTier.BASIC &&
                                item.type == ShopItemType.CANVAS_BACKGROUND) {
                                Modifier.border(
                                    width = 2.dp,
                                    color = Color(0xFFF6F1EC),
                                    shape = RoundedCornerShape(14.dp)
                                )
                            } else if (item.category != ShopItemTier.BASIC &&
                                item.type == ShopItemType.CANVAS_BACKGROUND) {
                                Modifier.border(
                                    width = 2.dp,
                                    color = Color.White,
                                    shape = RoundedCornerShape(14.dp)
                                )
                            } else {
                                Modifier
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    item.previewBackgroundImage?.let { imageRes ->
                        Image(
                            painter = painterResource(id = imageRes),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                        )
                    }

//                    item.previewColor?.let { color ->
//                        ScribbleDashPreviewPath(
//                            color = color,
//                            modifier = Modifier
//                                .fillMaxWidth(0.85f)
//                                .aspectRatio(94f / 70f)
//                        )
//                    }

                    if (item.isGradient && item.gradientColors != null) {
                        ScribbleDashPreviewPath(
                            color = null,
                            gradientColors = item.gradientColors,
                            modifier = Modifier
                                .fillMaxWidth(0.85f)
                                .aspectRatio(94f / 70f)
                            )
                    } else {
                        item.previewColor?.let { color ->
                            ScribbleDashPreviewPath(
                                color = color,
                                gradientColors = null,
                                modifier = Modifier
                                    .fillMaxWidth(0.85f)
                                    .aspectRatio(94f / 70f)
                            )
                        }
                    }

                    if (!item.isPurchased) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_lock),
                                contentDescription = "Locked",
                                tint = Color(0xFF514437),
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (item.isPurchased) {
                        Icon(
                            painter = painterResource(R.drawable.ic_cart),
                            contentDescription = "Purchased",
                            tint = when (item.category) {
                                ShopItemTier.BASIC -> Color(0xFF9E9E9E)
                                ShopItemTier.PREMIUM, ShopItemTier.LEGENDARY -> Color.White
                            },
                            modifier = Modifier.size(24.dp)
                        )
                    } else {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_coin),
                                contentDescription = null,
                                tint = Color(0xFFFFC107), // Gold color
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = item.cost.toString(),
                                style = MaterialTheme.typography.headlineSmall,
                                color = when (item.category) {
                                    ShopItemTier.BASIC -> Color(0xFF514437)
                                    ShopItemTier.PREMIUM, ShopItemTier.LEGENDARY -> Color.White
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShopCardCanvasPreview() {
    ScribbleDashTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ScribbleDashShopCard(
                    item = ShopItem(
                        id = "white",
                        category = ShopItemTier.BASIC,
                        type = ShopItemType.PEN_COLOR,
                        previewColor = Color.Black,
                        previewBackgroundColor = Color.White,
                        isPurchased = true
                    ),
                    isSelected = true,
                    onItemClick = {}
                )

                ScribbleDashShopCard(
                    item = ShopItem(
                        id = "beige",
                        category = ShopItemTier.BASIC,
                        type = ShopItemType.PEN_COLOR,
                        previewColor = Color.Black,
                        previewBackgroundColor = Color.White,
                        isPurchased = false,
                        cost = 80
                    ),
                    isSelected = false,
                    onItemClick = {}
                )

                ScribbleDashShopCard(
                    item = ShopItem(
                        id = "light_blue",
                        category = ShopItemTier.BASIC,
                        type = ShopItemType.PEN_COLOR,
                        previewColor = Color.Black,
                        previewBackgroundColor = Color.White,
                        isPurchased = true
                    ),
                    isSelected = false,
                    onItemClick = {}
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ScribbleDashShopCard(
                    item = ShopItem(
                        id = "wood_texture",
                        category = ShopItemTier.LEGENDARY,
                        type = ShopItemType.PEN_COLOR,
                        previewColor = Color(0xFF008C92),
                        previewBackgroundColor = Color.White,
                        isPurchased = false,
                        cost = 250
                    ),
                    isSelected = false,
                    onItemClick = {}
                )

                ScribbleDashShopCard(
                    item = ShopItem(
                        id = "paper_texture",
                        category = ShopItemTier.PREMIUM,
                        type = ShopItemType.CANVAS_BACKGROUND,
                        previewColor = Color.Black,
                        previewBackgroundImage = R.drawable.canvas_paper_texture, // Add your texture
                        isPurchased = true
                    ),
                    isSelected = false,
                    onItemClick = {}
                )

                ScribbleDashShopCard(
                    item = ShopItem(
                        id = "wood_texture",
                        category = ShopItemTier.LEGENDARY,
                        type = ShopItemType.CANVAS_BACKGROUND,
                        previewColor = Color.Black,
                        previewBackgroundImage = R.drawable.canvas_wood_texture, // Add your texture
                        isPurchased = false,
                        cost = 250
                    ),
                    isSelected = false,
                    onItemClick = {}
                )
            }
        }
    }
}
