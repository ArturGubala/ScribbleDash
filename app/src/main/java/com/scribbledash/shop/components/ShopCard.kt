package com.scribbledash.shop.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.scribbledash.R
import com.scribbledash.core.presentation.utils.ShopItemTier
import com.scribbledash.domain.model.ShopItem
import com.scribbledash.ui.theme.ScribbleDashTheme

@Composable
fun ShopCard(
    item: ShopItem,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    isDisabled: Boolean = false
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
                    .offset(x = 10.dp, y = (-10).dp)
                    .zIndex(1f)
            )
        }

        Box(
            modifier = Modifier
                .size(width = 110.dp, height = 152.dp)
                .background(
                    color = when (item.category) {
                        ShopItemTier.BASIC -> Color.White
                        ShopItemTier.PREMIUM -> Color(0xFFAB5CFA)
                        ShopItemTier.LEGENDARY -> Color(0xFFFA852C)
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
                Text(
                    text = item.category.name,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = when (item.category) {
                        ShopItemTier.BASIC -> Color(0xFF9E9E9E)
                        ShopItemTier.PREMIUM, ShopItemTier.LEGENDARY -> Color.White
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            color = item.previewBackground ?: Color.White
                        ).then(
                            if (item.category == ShopItemTier.BASIC) {
                                Modifier.border(
                                    width = 2.dp,
                                    color = Color(0xFFF6F1EC),
                                    shape = RoundedCornerShape(14.dp)
                                )
                            } else {
                                Modifier
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (!item.isPurchased) {
                        Icon(
                            painter = painterResource(R.drawable.ic_lock),
                            contentDescription = "Locked",
                            tint = Color(0xFFBDBDBD),
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    item.previewColor?.let { color ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.7f)
                                .height(3.dp)
                                .background(color)
                        )
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
                            Image(
                                painter = painterResource(R.drawable.ic_coin),
                                contentDescription = null,
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
fun ShopCardPreview() {
    ScribbleDashTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ShopCard(
                item = ShopItem(
                    id = "1",
                    category = ShopItemTier.BASIC,
                    previewColor = Color.Black,
                    isPurchased = true
                ),
                isSelected = true,
                onItemClick = {}
            )

            ShopCard(
                item = ShopItem(
                    id = "2",
                    category = ShopItemTier.PREMIUM,
                    previewColor = Color.Cyan,
                    isPurchased = false,
                    cost = 120
                ),
                isSelected = false,
                onItemClick = {}
            )

            ShopCard(
                item = ShopItem(
                    id = "3",
                    category = ShopItemTier.LEGENDARY,
                    previewBackground = Color(0xFFD4A574),
                    isPurchased = false,
                    cost = 999
                ),
                isSelected = false,
                onItemClick = {}
            )
        }
    }
}
