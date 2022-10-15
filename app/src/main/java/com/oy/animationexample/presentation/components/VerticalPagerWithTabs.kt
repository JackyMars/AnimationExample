package com.oy.animationexample.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.isUnspecified
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.VerticalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

const val CONTEXT_TYPE_ICON_TEXT = "ICON_TEXT"
const val CONTEXT_TYPE_TEXT = "TEXT"

@OptIn(ExperimentalPagerApi::class)
@Composable
fun VerticalPagerWithTabs(
    isSelected:Boolean,
    tabs: List<Pair<String, ImageVector>>,
    onSelected:(Boolean) -> Unit
) {
    Row{

        val coroutineScope = rememberCoroutineScope()
        val pagerState = rememberPagerState()

        Tabs(
            tabs = tabs,
            onSelected = {onSelected(isSelected)},
            scope = coroutineScope,
            pagerState = pagerState,
        )

        VerticalPager(
            count = tabs.size,
            state = pagerState,
            // Add 32.dp vertical padding to 'center' the pages
            contentPadding = PaddingValues(vertical = 32.dp),
            // Add some vertical spacing between items
            itemSpacing = 8.dp,
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(color =
                    Color.LightGray
                ),

            ) { page ->
            //to do
            when(page){
                0->PageItem(tabs[page].first)
                1->PageItem(tabs[page].first)
                2->PageItem(tabs[page].first)
            }
        }

    }
}
@OptIn(ExperimentalPagerApi::class)
@Composable
fun Tabs(
    tabs:List<Pair<String, ImageVector>>,
    onSelected:(Boolean) -> Unit,
    scope: CoroutineScope,
    pagerState: PagerState,
    contextType:String = "ICON_TEXT",

    ){
    val startIndex = 0
    fun pageMapper(index: Int): Int {
        return  floorMod((index - startIndex),tabs.count())

    }
    val currentIndex =  remember {
        derivedStateOf { pageMapper(pagerState.currentPage) }
    }

    LazyColumn(
        modifier = Modifier.fillMaxWidth(0.25f)

//                .navigationBarsPadding()
    ){



        itemsIndexed(tabs){ index, tab ->

            Tab(
                count = tabs.count(),
                index = index,
                text = tab.first,
                scope = scope,
                pagerState = pagerState,
                iconImage = tab.second,
                onSelected = {onSelected(false)},
                isSelected = currentIndex.value == index,
                contextType = contextType
            )



        }

    }
}
@OptIn(ExperimentalPagerApi::class)
@Composable
fun Tab(
    count:Int,
    index:Int,
    text:String,
    scope: CoroutineScope,
    pagerState: PagerState,
    iconImage: ImageVector,
    onSelected:(Boolean) -> Unit,
    isSelected:Boolean = false,
    contextType:String = "ICON_TEXT",

    ){
    val startIndex = 0
    fun pageMapper(index: Int): Int {
        return  floorMod((index - startIndex),count)

    }
    val currentIndex =  remember {
        derivedStateOf { pageMapper(pagerState.currentPage) }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .toggleable(
                value = isSelected,
                onValueChange = {

                    onSelected(isSelected)

                    scope.launch {
                        when{
                            currentIndex.value > index -> {
                                pagerState.animateScrollToPage(
                                    page = pagerState.currentPage - (currentIndex.value - index)
                                )
                            }

                            currentIndex.value < index -> {
                                pagerState.animateScrollToPage(
                                    page = pagerState.currentPage + (index - currentIndex.value)
                                )
                            }
                        }
                    }
                }
            ),
//        border = BorderStroke(1.dp, Color.Black),
        elevation = 4.dp,
        backgroundColor = if(isSelected)
                            Color.DarkGray.copy(alpha = 0.2f)
                          else Color.LightGray

    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ){

            when(contextType){
                CONTEXT_TYPE_ICON_TEXT ->  {
                    Icon(
                        modifier = Modifier.padding(start = 5.dp),
                        imageVector = iconImage,
                        contentDescription = text,
                    )
                    Text(
                        modifier = Modifier.padding(start = 5.dp),
                        color = Color.White,
                        text = text,
                        textAlign = TextAlign.Center
                    )
                }
                CONTEXT_TYPE_TEXT -> Text(
                    modifier = Modifier.padding(start = 20.dp),
                    color = Color.White,
                    text = text,
                    textAlign = TextAlign.Center
                )
            }

        }

    }
}

@Composable
fun PageItem(
//    page:Int,
    data:String
){
    Text(text = data)
}
private fun floorMod(x:Int,y:Int):Int{
    return x - Math.floorDiv(x, y) * y
}