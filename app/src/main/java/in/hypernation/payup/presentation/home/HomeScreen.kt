package `in`.hypernation.payup.presentation.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import `in`.hypernation.payup.R
import `in`.hypernation.payup.ui.theme.Black70
import `in`.hypernation.payup.ui.theme.GhostBlack
import `in`.hypernation.payup.ui.theme.GhostBlack60
import `in`.hypernation.payup.ui.theme.Green
import `in`.hypernation.payup.ui.theme.Peach
import `in`.hypernation.payup.ui.theme.notoSansFamily
import `in`.hypernation.payup.utils.RUPEE_SYMBOL
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber


@Composable
fun HomeView(
    viewModel : HomeViewModel = koinViewModel()
){
    val linkState = viewModel.linkState.value
    Scaffold (
        contentColor = MaterialTheme.colorScheme.surface,
        floatingActionButton = {
            ScanFAB (onClick = {
                viewModel.handleEvent(HomeEvent.OnPayWithQR)
            })
        },
        floatingActionButtonPosition = FabPosition.Center
    ){
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column (
                modifier = Modifier.fillMaxSize()
            ) {
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(align = Alignment.Top)
                        .padding(end = 30.dp, top = 10.dp, bottom = 10.dp)

                ) {
                    AvatarCard()
                    Column (
                        modifier = Modifier
                            .padding(top = 28.dp)
                            .wrapContentHeight(align = Alignment.CenterVertically)
                    ) {
                        Text(
                            text = "hi,",
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 16.sp,
                            fontStyle = FontStyle.Normal
                        )
                        Text(
                            text = "what a beautiful day!",
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 16.sp,
                            fontStyle = FontStyle.Normal
                        )

                    }
                    Row(
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp)
                    ) {
                        Image( // Settings Button
                            painter = painterResource(id = R.drawable.settings),
                            contentDescription = "setting",
                            modifier = Modifier
                                .size(24.dp)
                        )
                    }


                } // Top bar
                Row (
                    modifier = Modifier
                        .padding(35.dp)
                        .height(200.dp)
                        .fillMaxWidth()
                ){
                    Column (
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .weight(1f)
                            .padding(top = 12.dp)
                    ) {
                        if(linkState.account.isLinked){
                            Text(
                                text = "you’re linked to",
                                color = MaterialTheme.colorScheme.onSurface,
                                fontSize = 18.sp,
                                modifier = Modifier.fillMaxWidth()
                            )

                            if(linkState.account.bankName !=null){
                                Text(
                                    text = linkState.account.bankName,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Normal,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                )
                            }
                            Column (
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Bottom
                            ) {
                                BalanceCard(linkState){
                                    viewModel.handleEvent(HomeEvent.OnCheckBalance)
                                }
                            }

                        } else {
                            Text(
                                text = "your UPI is not linked yet.",
                                color = MaterialTheme.colorScheme.onSurface,
                                fontSize = 18.sp,
                                modifier = Modifier.fillMaxWidth()
                            )

                            Text(
                                text = "Link Now",
                                color = Peach,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 8.dp)
                                    .clickable { viewModel.handleEvent(HomeEvent.Linked(0)) }
                            )

                        }

                    }
                    Image(painter = painterResource(
                        id = R.drawable.pana_scan_pay),
                        contentDescription = "scan vector",
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .height(200.dp)
                    )

                }

                RoundButtonCard(icon = R.drawable.scan, text = "Scan any QR Code"){
                    viewModel.handleEvent(HomeEvent.OnPayWithQR)
                }
                RoundButtonCard(icon = R.drawable.at_the_rate, text = "Pay with UPI ID", dp = 1.dp){
                    viewModel.handleEvent(HomeEvent.OnPayWithUPI)
                }
            }

        }
    }

}


@Composable
fun BalanceCard(linkState: LinkState, onCheckBalance : () -> Unit){
    ElevatedCard (
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = Green
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 10.dp
        ),
        modifier = Modifier
            .fillMaxWidth()

    ) {
        Card (
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.elevatedCardColors(
                containerColor = MaterialTheme.colorScheme.background
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp)
        ) {
            Column (
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Last Balance",
                    color = GhostBlack60,
                    fontSize = 10.sp,
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = linkState.account?.bankBalance ?: "$RUPEE_SYMBOL ---" ,
                    color = GhostBlack,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = "Update Balance",
                    color = Peach,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 20.dp)
                        .clickable {
                            onCheckBalance()
                        }
                )
            }

        }
    }

}

@Composable
fun RoundButtonCard(icon : Int, text : String, dp : Dp = 0.dp, onClick: () -> Unit){
    ElevatedCard (
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.secondary
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 8.dp
        ),
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier
            .padding(top = 10.dp, bottom = 10.dp, start = 30.dp, end = 30.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable {
                onClick()
            }
    ) {
        Card (
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background
            ),
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier
                .padding(start = 8.dp)
                .fillMaxWidth()
        ) {
            Row (
                modifier = Modifier.padding(start = 30.dp, end = 30.dp, top = 20.dp, bottom = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = icon),
                    contentDescription = text,
                    colorFilter = ColorFilter.tint(Color.Black),
                    modifier = Modifier
                        .size(24.dp)
                        .padding(dp)

                )
                Text(
                    text = text,
                    fontSize = 14.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(start = 15.dp)
                )
            }
        }
    }
}

@Composable
fun ScanFAB(onClick: () -> Unit) {
    ExtendedFloatingActionButton(
        onClick = { onClick() },
        icon = { Column (
            modifier = Modifier.padding(start = 24.dp, top = 12.dp, bottom = 12.dp)
        ){
            Image(
                painter = painterResource(id = R.drawable.scan),
                contentDescription = "scan btn",
                modifier = Modifier.size(24.dp)
            )
        } },
        text = { Text(text = "Scan & Pay", fontSize = 16.sp, fontWeight = FontWeight.Normal, modifier = Modifier.padding(end = 24.dp)) },
        containerColor = MaterialTheme.colorScheme.primary,
        shape = RoundedCornerShape(100.dp),
        modifier = Modifier.padding(12.dp)
    )
}

@Composable
fun AvatarCard(){
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier.size(100.dp)
    ) {
        ElevatedCard (
            shape = CircleShape,
            colors = CardDefaults.elevatedCardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp
            ),
            modifier = Modifier
                .size(60.dp)
                .align(Alignment.BottomCenter)
        ) {

        }
        Image(
            painter = painterResource(id = R.drawable.male_avatar),
            contentDescription = "bg",
            contentScale = ContentScale.Inside,
            alignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 10.dp, end = 4.dp)
                .align(Alignment.Center)
        )


    }
}