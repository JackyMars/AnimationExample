package com.oy.animationexample.presentation.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.google.accompanist.pager.*
import com.oy.animationexample.domain.model.BannerImage
import com.oy.animationexample.util.loadPicture
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Banner(
    images:List<BannerImage>,
    selectImage: BannerImage,
    indicatorPadding: Dp,
    align: Alignment
){
    var currentIndex = 0

    images.forEachIndexed { index, image ->
        if(image.url == selectImage.url){
            currentIndex = index
            return@forEachIndexed
        }
    }

    //頁面狀態改變
    val pagerState = rememberPagerState(initialPage = currentIndex)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
    ){
        HorizontalPager(
            count = images.size,
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 36.dp),
            modifier = Modifier.fillMaxSize()
        ) { page ->
            //put banner Item
//            Log.d(TAG, "Banner: HorizontalPager:${page}")
            BannerItem(images[page],225.dp,page,pagerState)
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            activeColor = Color.Red,
            inactiveColor = Color.LightGray.copy(alpha = 0.9f),
            modifier = Modifier
                .align(align)
                .padding(indicatorPadding)
        )

        LaunchedEffect(key1 = pagerState.currentPage) {
            launch {
                delay(2000)
                with(pagerState) {
                    val target = if (currentPage < pageCount - 1) currentPage + 1 else 0

                    animateScrollToPage(
                        page = target,
                        animationSpec = tween(
                            durationMillis = 500,
                            easing = FastOutSlowInEasing
                        )
                    )
                }
            }
        }


    }

}
@OptIn(ExperimentalPagerApi::class)
@Composable
fun BannerItem(
    image: BannerImage,
    imageHeight: Dp,
    page:Int,
    pageState: PagerState
//    onClick: () -> Unit
){
    Card(
        Modifier
            .graphicsLayer {
                // Calculate the absolute offset for the current page from the
                // scroll position. We use the absolute value which allows us to mirror
                // any effects for both directions

                //calculateCurrentOffsetForPage
                val pageOffset =
                    ((pageState.currentPage + pageState.currentPageOffset) - page).absoluteValue


                // We animate the scaleX + scaleY, between 85% and 100%
                lerp(
                    start = 0.85f,
                    stop = 1f,
                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                ).also { scale ->
                    scaleX = scale
                    scaleY = scale
                }

                // We animate the alpha, between 50% and 100%
                alpha = lerp(
                    start = 0.5f,
                    stop = 1f,
                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                )
            }
            .fillMaxWidth()
            .aspectRatio(1f),
        elevation = 0.dp

//            .clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .height(imageHeight)
        ){

            val image = loadPicture(url = image.url).value
            image?.let { img ->
//                Log.d(TAG, "BannerItem: ${img.asImageBitmap().height}")
                Image(
                    bitmap = img.asImageBitmap(),
                    contentDescription = "banner image",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.matchParentSize()
                )

            }

        }
    }
}