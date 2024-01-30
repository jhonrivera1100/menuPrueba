package com.innovation.tramo.models.googleMaps


data class DirectionsResponse(
    val routes: List<Route>
)

data class Route(
    val legs: List<Leg>,
    val overview_polyline: Polyline
)

data class Leg(
    val distance: Distance,
    val duration: Duration,
    val end_address: String,
    val start_address: String,
    val steps: List<Step>
)

data class Distance(
    val text: String,
    val value: Int
)

data class Duration(
    val text: String,
    val value: Int
)

data class Step(
    val distance: Distance,
    val duration: Duration,
    val start_location: Location,
    val end_location: Location,
    val polyline: Polyline
)

data class Location(
    val lat: Double,
    val lng: Double
)

data class Polyline(
    val points: String
)

