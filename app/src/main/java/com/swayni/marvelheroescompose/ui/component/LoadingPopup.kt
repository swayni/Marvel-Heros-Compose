package com.swayni.marvelheroescompose.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.swayni.marvelheroescompose.R

@Composable
fun LoadingPopup(){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier.width(250.dp).wrapContentHeight(),
            shape = RoundedCornerShape(20.dp),
            colors = CardColors(
                containerColor = colorResource(R.color.red),
                contentColor = colorResource(R.color.white),
                disabledContentColor = colorResource(R.color.red),
                disabledContainerColor = colorResource(R.color.red)
            ),
            border = BorderStroke(width = 1.dp, color = colorResource(R.color.gray_custom_1))
        ){
            Column (modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(top = 20.dp, bottom = 30.dp), horizontalAlignment = Alignment.CenterHorizontally){
                Row (modifier = Modifier.padding(bottom = 10.dp)) {
                    Text(text = stringResource(R.string.loading), fontSize = 18.sp, fontStyle = FontStyle.Italic, fontWeight = FontWeight.SemiBold, color = colorResource(R.color.gray_custom_1))
                }
                Row (){
                    CircularProgressIndicator(
                        modifier = Modifier.wrapContentSize(),
                        color = colorResource(R.color.gray_custom_1),
                        strokeWidth = 5.dp,
                        trackColor = colorResource(R.color.gray_custom_2),
                        strokeCap = StrokeCap.Round
                    )
                }
            }
        }
    }
}