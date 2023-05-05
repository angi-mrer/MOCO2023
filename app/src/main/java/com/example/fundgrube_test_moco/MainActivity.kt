package com.example.fundgrube_test_moco

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
           /* Column(
                modifier = Modifier
                    .fillMaxSize(1f)
                    .background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround
            )
             {
                Greeting("Seb")
                Greeting("Ich bin ein Satz")
                 //mapsProperties()
            }*/

            val painter = painterResource(id = R.drawable.google_maps_logo)
            val description = "Google Maps - API"
            val title = "Google Maps - API"
            MapImage(painter = painter, contentDescription = description, title = title)
            //ItemGefunden()
            ItemGesucht()
        }
        /*
        setContent{
            Row (
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .height(80.dp)
                    .background(Color.Green),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ){
                Greeting("Oberer Tab")
                Greeting("UntererTab")
            }
        }*/
        }
    }

@Composable
fun createBox(){
    class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
        private lateinit var mMap: GoogleMap

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_maps)
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
            mapFragment.getMapAsync(this)
        }

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera. In this case,
         * we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to install
         * it inside the SupportMapFragment. This method will only be triggered once the user has
         * installed Google Play services and returned to the app.
         */
        override fun onMapReady(googleMap: GoogleMap) {
            mMap = googleMap

            // Add a marker in Sydney and move the camera
            val sydney = LatLng(-34.0, 151.0)
            mMap.addMarker(MarkerOptions()
                .position(sydney)
                .title("Marker in Sydney"))
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        }
    }
}


@Composable
fun MapImage(
    painter: Painter,
    contentDescription: String,
    title: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        elevation = 5.dp
    ){
        Box(modifier = Modifier.size(200.dp)){
            Image(painter = painter,
                contentDescription = contentDescription,
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    //.fillMaxSize()
                    .size(25.dp)
                    .padding(25.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                Text(title, style = TextStyle(color = Color.White, fontSize = 16.sp))

            }
        }
    }
}

@Composable
fun mapsBox(){
    val singapore = LatLng(1.35, 103.87)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(singapore, 10f)
    }
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        Marker(
            state = MarkerState(position = singapore),
            title = "Singapore",
            snippet = "Marker in Singapore"
        )
    }
}

@Composable
fun mapsProperties(){
    var uiSettings by remember { mutableStateOf(MapUiSettings()) }
    var properties by remember {
        mutableStateOf(MapProperties(mapType = MapType.SATELLITE))
    }

    Box(Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.matchParentSize(),
            properties = properties,
            uiSettings = uiSettings
        )
        Switch(
            checked = uiSettings.zoomControlsEnabled,
            onCheckedChange = {
                uiSettings = uiSettings.copy(zoomControlsEnabled = it)
            }
        )
    }
}

/*
@Composable
fun Gesucht(){
Column(
        modifier = Modifier
){
        Box(                    // Gesucht-Box mit Lupe
            modifier = Modifier
                .fillMaxWidth()
                .height(144.dp)
                .background(color = Color(0xff00a3fe))
        ){
            Row (
                    modifier = Modifier
                        .padding(30.dp)
                    ){
                Image(
                    painter = painterResource(id = R.drawable.lupe),
                    contentDescription = "lupe 1",
                    modifier = Modifier
                        .size(size = 63.dp)
                )
                Text(
                    text = "Gesucht ",
                    color = Color.White,
                    style = TextStyle(
                        fontSize = 45.sp
                    ),
                    //horizontalAlignment = Alignment.CenterHorizontally,
                    //verticalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier
                        .width(width = 265.dp)
                        .height(200.dp)
                        .padding(start = 25.dp)
                )
            }
        }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            //.size()
            .background(color = Color.White)
            .padding(start = 20.dp, top = 28.dp, end = 20.dp)
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                ,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(                        //Graue Box mit "Add Photo"
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .width(width = 172.dp)
                    .height(height = 172.dp)
                    .background(color = Color(0xffD9D9D9))

            ) {
                Text(
                    text = "add photo",
                    color = Color.Black,
                    style = TextStyle(
                        fontSize = 30.sp
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                    // .width(width = 148.dp)
                    // .align(alignment = Alignment.Center)
                )
            }

            Box( // "Ich suche... - Box"
                modifier = Modifier
                    .border(1.dp, Color.Black)
                    .padding(start = 5.dp)
            ) {
                Text(
                    text = "Ich suche....",
                    color = Color(0xff3a3a3a),
                    style = TextStyle(
                        fontSize = 30.sp
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
            Box(
                modifier = Modifier
                    .border(1.dp, Color.Black)
                    .padding(5.dp)
            ) {
                Text(
                    text = "Jorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc vulputate libero et velit interdum, ac aliquet odio mattis. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Curabitur tempus urna at turpis condimentum lobortis. Ut commodo efficitur neque.",
                    color = Color(0xff424242),
                    style = TextStyle(
                        fontSize = 15.sp
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .background(color = Color(0xffd9d9d9)),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.spacedBy(32.dp),
                    verticalAlignment = Alignment.CenterVertically,

                    ) {
                    Image(
                        painter = painterResource(id = R.drawable.placeholder),
                        contentDescription = "placeholder 1",
                        modifier = Modifier
                            .size(size = 72.dp)
                    )
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .background(color = Color(0xff00a3fe))
                            .padding(start = 32.dp, top = 10.dp, end = 32.dp, bottom = 10.dp)
                    ) {
                        Text(
                            text = "Standort auswählen",
                            color = Color.White,
                            style = TextStyle(
                                fontSize = 15.sp
                            ),
                            modifier = Modifier
                            // .width(width = 142.dp)
                        )
                    }

                }
            }
            Box(
                modifier = Modifier
                    .border(1.dp, Color.Black)
                    .padding(start = 5.dp, top = 16.dp)
            ) {
                Text(
                    text = "Schreibe hier, wo du deinen Gegenstand verloren hast...",
                    color = Color(0xff424242),
                    style = TextStyle(
                        fontSize = 15.sp
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(256.dp)
                )
            }
            Box(
                modifier = Modifier
                    .background(color = Color.Black)
                    .fillMaxWidth()
                    .height(120.dp)
            ) {
                Box(
                    modifier = Modifier
                        .background(color = Color(0xff00a3fe))
                        .width(width = 260.dp)
                        .height(height = 74.dp)
                ) {

                }
            }
        }
    }
}
}
*/

@Composable
fun ItemGesucht(){
    Column(
        modifier = Modifier
    ){
        Box(                    // Gesucht-Box mit Lupe
            modifier = Modifier
                .fillMaxWidth()
                .height(144.dp)
                .background(color = Color(0xff00a3fe)),
            contentAlignment = Alignment.Center
        ){
            Row (
                modifier = Modifier
                    .padding(30.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ){
                Image(
                    painter = painterResource(id = R.drawable.lupe),
                    contentDescription = "lupe 1",
                    modifier = Modifier
                        .size(size = 63.dp)
                )
                Text(
                    text = "Gesucht ",
                    color = Color.White,
                    style = TextStyle(
                        fontSize = 45.sp
                    ),
                    //textAlign = TextAlign.Center,
                    //horizontalAlignment = Alignment.CenterHorizontally,
                    //verticalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier
                        .width(width = 265.dp)
                        .height(200.dp)
                        .padding(start = 25.dp)
                        .wrapContentHeight()

                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                //.size()
                .background(color = Color.White)
                .padding(start = 20.dp, end = 20.dp)            // Padding am "Add photo"
        ) {
            LazyColumn(
                modifier = Modifier,
                    //.padding(top = 15.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp)

            ) {

                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp)
                            .background(color = Color.White)
                    ){}
                    Box(                        //Graue Box mit "Add Photo"
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .width(width = 172.dp)
                            .height(height = 172.dp)
                            .background(color = Color(0xffD9D9D9))

                    ) {
                        Text(
                            text = "add photo",
                            color = Color.Black,
                            style = TextStyle(
                                fontSize = 30.sp
                            ),
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                            // .width(width = 148.dp)
                            // .align(alignment = Alignment.Center)
                        )
                    }
                }
                item {
                    Box( // "Ich suche... - Box"
                        modifier = Modifier
                            .border(1.dp, Color.Black)
                            .padding(start = 5.dp)
                    ) {
                        Text(
                            text = "Ich suche....",
                            color = Color(0xff3a3a3a),
                            style = TextStyle(
                                fontSize = 30.sp
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }
                }
                item {
                    Box(
                        modifier = Modifier
                            .border(1.dp, Color.Black)
                            .padding(5.dp)
                    ) {
                        Text(
                            text = "Jorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc vulputate libero et velit interdum, ac aliquet odio mattis. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Curabitur tempus urna at turpis condimentum lobortis. Ut commodo efficitur neque.",
                            color = Color(0xff424242),
                            style = TextStyle(
                                fontSize = 15.sp
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }
                }
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(140.dp)
                            .background(color = Color(0xffd9d9d9)),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            modifier = Modifier,
                            horizontalArrangement = Arrangement.spacedBy(32.dp),
                            verticalAlignment = Alignment.CenterVertically,

                            ) {
                            Image(
                                painter = painterResource(id = R.drawable.placeholder),
                                contentDescription = "placeholder 1",
                                modifier = Modifier
                                    .size(size = 72.dp)
                            )
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .background(color = Color(0xff00a3fe))
                                    .padding(
                                        start = 32.dp,
                                        top = 10.dp,
                                        end = 32.dp,
                                        bottom = 10.dp
                                    )
                            ) {
                                Text(
                                    text = "Standort auswählen",
                                    color = Color.White,
                                    style = TextStyle(
                                        fontSize = 15.sp
                                    ),
                                    modifier = Modifier
                                    // .width(width = 142.dp)
                                )
                            }
                        }
                    }
                }
                item {
                    Box(
                        modifier = Modifier
                            .border(1.dp, Color.Black)
                            .padding(start = 5.dp, top = 16.dp)
                    ) {
                        Text(
                            text = "Schreibe hier, wo du deinen Gegenstand verloren hast...",
                            color = Color(0xff424242),
                            style = TextStyle(
                                fontSize = 15.sp
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(256.dp)
                        )
                    }
                }
                item {
                    Box(
                        modifier = Modifier
                            .background(color = Color.White)
                            .fillMaxWidth()
                            .height(120.dp)
                            .padding(10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .background(color = Color(0xff00a3fe))
                                .width(width = 260.dp)
                                .height(height = 74.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "Suche beginnen",
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    color = Color.White
                                ),

                            )
                        }
                    }
                }
            }
        }
    }
}




@Composable
fun ItemGefunden(){
    Column(
        modifier = Modifier
    ){
        Box(                    // Gesucht-Box mit Lupe
            modifier = Modifier
                .fillMaxWidth()
                .height(144.dp)
                .background(color = Color(0xff00bb00)),
            contentAlignment = Alignment.Center
        ){
            Row (
                modifier = Modifier
                    .padding(30.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ){
                Image(
                    painter = painterResource(id = R.drawable.box),
                    contentDescription = "lupe 1",
                    modifier = Modifier
                        .size(size = 63.dp)
                )
                Text(
                    text = "Gefunden ",
                    color = Color.White,
                    style = TextStyle(
                        fontSize = 45.sp
                    ),
                    //textAlign = TextAlign.Center,
                    //horizontalAlignment = Alignment.CenterHorizontally,
                    //verticalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier
                        .width(width = 265.dp)
                        .height(200.dp)
                        .padding(start = 25.dp)
                        .wrapContentHeight()

                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                //.size()
                .background(color = Color.White)
                .padding(start = 20.dp, end = 20.dp)            // Padding am "Add photo"
        ) {
            LazyColumn(
                modifier = Modifier,
                //.padding(top = 15.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp)

            ) {

                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp)
                            .background(color = Color.White)
                    ){}
                    Box(                        //Graue Box mit "Add Photo"
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .width(width = 172.dp)
                            .height(height = 172.dp)
                            .background(color = Color(0xffD9D9D9))

                    ) {
                        Text(
                            text = "add photo",
                            color = Color.Black,
                            style = TextStyle(
                                fontSize = 30.sp
                            ),
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                            // .width(width = 148.dp)
                            // .align(alignment = Alignment.Center)
                        )
                    }
                }
                item {
                    Box( // "Ich suche... - Box"
                        modifier = Modifier
                            .border(1.dp, Color.Black)
                            .padding(start = 5.dp)
                    ) {
                        Text(
                            text = "Ich habe gefunden",
                            color = Color(0xff3a3a3a),
                            style = TextStyle(
                                fontSize = 30.sp
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }
                }
                item {
                    Box(
                        modifier = Modifier
                            .border(1.dp, Color.Black)
                            .padding(5.dp)
                    ) {
                        Text(
                            text = "Jorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc vulputate libero et velit interdum, ac aliquet odio mattis. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Curabitur tempus urna at turpis condimentum lobortis. Ut commodo efficitur neque.",
                            color = Color(0xff424242),
                            style = TextStyle(
                                fontSize = 15.sp
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }
                }
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(140.dp)
                            .background(color = Color(0xffd9d9d9)),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            modifier = Modifier,
                            horizontalArrangement = Arrangement.spacedBy(32.dp),
                            verticalAlignment = Alignment.CenterVertically,

                            ) {
                            Image(
                                painter = painterResource(id = R.drawable.placeholder),
                                contentDescription = "placeholder 1",
                                modifier = Modifier
                                    .size(size = 72.dp)
                            )
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .background(color = Color(0xff00bb00))
                                    .padding(
                                        start = 32.dp,
                                        top = 10.dp,
                                        end = 32.dp,
                                        bottom = 10.dp
                                    )
                            ) {
                                Text(
                                    text = "Standort auswählen",
                                    color = Color.White,
                                    style = TextStyle(
                                        fontSize = 15.sp
                                    ),
                                    modifier = Modifier
                                    // .width(width = 142.dp)
                                )
                            }
                        }
                    }
                }
                item {
                    Box(
                        modifier = Modifier
                            .border(1.dp, Color.Black)
                            .padding(start = 5.dp, top = 16.dp)
                    ) {
                        Text(
                            text = "Schreibe hier, wo du den Gegenstand gefunden hast...",
                            color = Color(0xff424242),
                            style = TextStyle(
                                fontSize = 15.sp
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(256.dp)
                        )
                    }
                }
                item {
                    Box(
                        modifier = Modifier
                            .background(color = Color.White)
                            .fillMaxWidth()
                            .height(120.dp)
                            .padding(10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .background(color = Color(0xff00bb00))
                                .width(width = 260.dp)
                                .height(height = 74.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "Fundstück melden",
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    color = Color.White
                                ),
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
fun DefaultPreview() {
    ItemGefunden()
    //ItemGesucht()
}



