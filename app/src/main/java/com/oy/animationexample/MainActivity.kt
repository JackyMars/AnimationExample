package com.oy.animationexample





import android.graphics.pdf.PdfDocument.Page
import androidx.compose.ui.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Animatable
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.google.accompanist.pager.*
import com.oy.animationexample.domain.model.BannerImage
import com.oy.animationexample.util.DEFAULT_IMAGE
import com.oy.animationexample.util.TAG
import com.oy.animationexample.util.loadPicture
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.measureTimedValue
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import kotlin.math.absoluteValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val images = mutableListOf(
                BannerImage("https://images.unsplash.com/photo-1665354248121-69d2fdb8864f?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzNjcyOTd8MHwxfGFsbHwyfHx8fHx8Mnx8MTY2NTM4OTY2NA&ixlib=rb-1.2.1&q=80&w=400"),
                BannerImage("https://images.unsplash.com/photo-1665384469210-fe98839c174e?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzNjcyOTd8MHwxfGFsbHw4fHx8fHx8Mnx8MTY2NTM4OTY2NA&ixlib=rb-1.2.1&q=80&w=400"),
                BannerImage("https://images.unsplash.com/photo-1665325306815-fb9cde3d4b92?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzNjcyOTd8MHwxfGFsbHwyfHx8fHx8Mnx8MTY2NTQwNzQzNw&ixlib=rb-1.2.1&q=80&w=400"),
                BannerImage("https://images.unsplash.com/photo-1665326523639-1b770f5ddae9?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzNjcyOTd8MHwxfGFsbHw1fHx8fHx8Mnx8MTY2NTQwNzQzNw&ixlib=rb-1.2.1&q=80&w=400"),
                BannerImage("https://images.unsplash.com/photo-1664575601711-67110e027b9b?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzNjcyOTd8MXwxfGFsbHw2fHx8fHx8Mnx8MTY2NTQwNzQzNw&ixlib=rb-1.2.1&q=80&w=400"),
                BannerImage("https://images.unsplash.com/photo-1665341930997-9b78a214a252?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzNjcyOTd8MHwxfGFsbHw3fHx8fHx8Mnx8MTY2NTQwNzQzNw&ixlib=rb-1.2.1&q=80&w=400"),
                BannerImage("https://images.unsplash.com/photo-1665142462602-bdf5cc5fd228?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzNjcyOTd8MHwxfGFsbHw4fHx8fHx8Mnx8MTY2NTQwNzQzNw&ixlib=rb-1.2.1&q=80&w=400"),
                BannerImage("https://images.unsplash.com/photo-1665324031594-382930e876df?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzNjcyOTd8MHwxfGFsbHw5fHx8fHx8Mnx8MTY2NTQwNzQzNw&ixlib=rb-1.2.1&q=80&w=400"),
                BannerImage("https://images.unsplash.com/photo-1665325306815-fb9cde3d4b92?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzNjcyOTd8MHwxfGFsbHwyfHx8fHx8Mnx8MTY2NTQwNzQzNw&ixlib=rb-1.2.1&q=80&w=400")
            )
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)

            ) {
                Banner(
                    images = images,
                    selectImage =  BannerImage("https://images.unsplash.com/photo-1665354248121-69d2fdb8864f?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzNjcyOTd8MHwxfGFsbHwyfHx8fHx8Mnx8MTY2NTM4OTY2NA&ixlib=rb-1.2.1&q=80&w=400"),
                    indicatorPadding = 35.dp,
                    align = Alignment.BottomCenter
                )
            }

        }
    }
}
@OptIn(ExperimentalPagerApi::class)
@Composable
fun Banner(
    images:List<BannerImage>,
    selectImage:BannerImage,
    indicatorPadding:Dp,
    align:Alignment
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

    Box{
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
    image:BannerImage,
    imageHeight:Dp,
    page:Int,
    pageState:PagerState
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
            .aspectRatio(1f)
//            .clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .height(imageHeight)
        ){

            val image = loadPicture(url = image.url).value
            image?.let { img ->
                Log.d(TAG, "BannerItem: ${img.asImageBitmap().height}")
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
private fun onClick(){

}