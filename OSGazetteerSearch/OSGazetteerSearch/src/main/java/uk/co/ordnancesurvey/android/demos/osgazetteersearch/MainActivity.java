package uk.co.ordnancesurvey.android.demos.osgazetteersearch;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import uk.co.ordnancesurvey.android.maps.GridPoint;
import uk.co.ordnancesurvey.android.maps.OSMap;
import uk.co.ordnancesurvey.android.maps.OSTileSource;
import uk.co.ordnancesurvey.android.maps.MapFragment;
import uk.co.ordnancesurvey.android.maps.MarkerOptions;
import uk.co.ordnancesurvey.android.maps.BitmapDescriptorFactory;
import uk.co.ordnancesurvey.android.maps.BitmapDescriptor;


public class MainActivity extends Activity implements OSMap.OnMapClickListener{

    /**
     * This API Key is registered for this application.
     *
     * Define your own OS Openspace API KEY details below
     * @see http://www.ordnancesurvey.co.uk/oswebsite/web-services/os-openspace/index.html
     */
    private final static String OS_API_KEY = "E93E8C82D5B81A4DE0430B6CA40AAB92";

    private final static boolean OS_IS_PRO = true;


    private final static String TAG = MainActivity.class.getSimpleName();

    private OSMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MapFragment mf = ((MapFragment) getFragmentManager().findFragmentById(R.id.map_fragment));

        mMap = mf.getMap();

        //create list of tileSources
        ArrayList<OSTileSource> sources = new ArrayList<OSTileSource>();

          //create web tile source with API details
        sources.add(mMap.webTileSource(OS_API_KEY, OS_IS_PRO, null));

        mMap.setTileSources(sources);

        // register as OnMapClickListener
        mMap.setOnMapClickListener(this);

        Log.v(TAG, "onCreate complete.");

    }


    @Override
    protected void onDestroy() {

        super.onDestroy();

    }


    @Override
    public boolean onMapClick(GridPoint gp)
    {

        final String locationMessage = String.format("Map tapped at OS GridPoint\n{%.0f, %.0f}", gp.x, gp.y);

        Log.v(TAG, locationMessage);

        BitmapDescriptor icon = BitmapDescriptorFactory.defaultMarker();

        mMap.addMarker(new MarkerOptions()
                .gridPoint(gp)
                .title("Map clicked here!")
                .snippet(locationMessage)
                .icon(icon));

        return true;

    }



}