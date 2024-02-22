package com.example.batmobile.services

import android.app.Activity
import android.content.Context
import androidx.core.content.ContextCompat
import com.example.batmobile.R
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.ItemizedIconOverlay
import org.osmdroid.views.overlay.OverlayItem

class Map {
    companion object{
        fun setMap(mapView: MapView, latitude:Double, longitude:Double, activity: Activity, context: Context){
            Configuration.getInstance().userAgentValue = activity.packageName
            // Postavite parametre mape
            mapView.setTileSource(TileSourceFactory.MAPNIK)
            mapView.setBuiltInZoomControls(true)

            val newPoint = GeoPoint(latitude, longitude)
            mapView.controller.setCenter(newPoint)
            mapView.controller.setZoom(13.0)
            // Dodavanje pina na tacnu lokaciju
            val items = ArrayList<OverlayItem>()
            val overlayItem = OverlayItem("Lokacija", "Lokacija domacinstva", newPoint)
            overlayItem.setMarker(ContextCompat.getDrawable(context, R.drawable.location_pin))
            items.add(overlayItem)

            val overlay = ItemizedIconOverlay<OverlayItem>(items, null, context)
            mapView.overlays.clear()
            mapView.overlays.add(overlay)
        }
    }

}