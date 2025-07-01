package com.swayni.marvelheroescompose.ui.component


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.swayni.marvelheroescompose.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollapsingToolbar(
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
    showTitle: Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp + (scrollBehavior.state.heightOffset * 4).dp)
            .background(colorResource(R.color.gray_custom_1)).testTag("LogoBox"),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.marvel_logo),
            contentDescription = "Marvel Logo",
            contentScale = ContentScale.Inside,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 80.dp)
        )
    }

    LargeTopAppBar(
        title = {
            if (showTitle){
                Text(
                    text = stringResource(R.string.marvel_characters),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.fillMaxWidth().testTag("CollapsedTitle"),
                    textAlign = TextAlign.Center
                )
            }
        },
        scrollBehavior = scrollBehavior,
        colors = TopAppBarColors(
            containerColor = colorResource(id = android.R.color.transparent),
            scrolledContainerColor = colorResource(id = android.R.color.transparent),
            navigationIconContentColor = colorResource(id = R.color.red),
            actionIconContentColor = colorResource(id = R.color.red),
            titleContentColor = colorResource(id = R.color.red)
        ),
        modifier = Modifier.background(colorResource(android.R.color.transparent))
    )
}
