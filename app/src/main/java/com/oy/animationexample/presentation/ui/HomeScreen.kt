package com.oy.animationexample.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.oy.animationexample.domain.model.BannerImage
import com.oy.animationexample.presentation.components.Banner
import com.oy.animationexample.presentation.components.VerticalPagerWithTabs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeScreen: Fragment() {

    private val viewModel:HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {

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

                val tabs:List<Pair<String, ImageVector>> = listOf(
                    "Home" to Icons.Default.Home,
                    "Shopping" to Icons.Default.ShoppingCart,
                    "Settings" to  Icons.Default.Settings
                )

                val isSelected = viewModel.isSelected.value

                Scaffold(
                    topBar = {
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(260.dp)

                        ) {
                            Banner(
                                images = images,
                                selectImage =  BannerImage("https://images.unsplash.com/photo-1665354248121-69d2fdb8864f?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzNjcyOTd8MHwxfGFsbHwyfHx8fHx8Mnx8MTY2NTM4OTY2NA&ixlib=rb-1.2.1&q=80&w=400"),
                                indicatorPadding = 30.dp,
                                align = Alignment.BottomCenter
                            )


                        }
                    }
                ){
                    VerticalPagerWithTabs(
                        isSelected = isSelected,
                        tabs = tabs,
                        onSelected = {viewModel.onSelected(isSelected)}
                    )
                }

            }
        }
    }

}