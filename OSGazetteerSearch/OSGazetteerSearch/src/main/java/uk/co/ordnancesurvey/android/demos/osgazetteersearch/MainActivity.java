package uk.co.ordnancesurvey.android.demos.osgazetteersearch;

import android.app.Activity;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import uk.co.ordnancesurvey.android.maps.FailedToLoadException;
import uk.co.ordnancesurvey.android.maps.Geocoder;
import uk.co.ordnancesurvey.android.maps.GridPoint;
import uk.co.ordnancesurvey.android.maps.OSMap;
import uk.co.ordnancesurvey.android.maps.OSTileSource;
import uk.co.ordnancesurvey.android.maps.MapFragment;
import uk.co.ordnancesurvey.android.maps.MarkerOptions;
import uk.co.ordnancesurvey.android.maps.BitmapDescriptorFactory;
import uk.co.ordnancesurvey.android.maps.BitmapDescriptor;
import uk.co.ordnancesurvey.android.maps.Placemark;


public class MainActivity extends Activity implements OnQueryTextListener {

    /**
     * This API Key is registered for this application.
     *
     * Define your own OS Openspace API KEY details below
     * @see http://www.ordnancesurvey.co.uk/oswebsite/web-services/os-openspace/index.html
     */
    private final static String OS_API_KEY = "E93E8C82D5BC1A4DE0430B6CA40AAB92";

    private final static boolean OS_IS_PRO = false;

    private final static String TAG = MainActivity.class.getSimpleName();


    private static final EnumSet<Geocoder.GeocodeType> POSTCODE
            = EnumSet.of(Geocoder.GeocodeType.OnlinePostcode);

    private static final EnumSet<Geocoder.GeocodeType> PLACES
            = EnumSet.of(Geocoder.GeocodeType.OnlineGazetteer);

    private Geocoder mGeocoder;
    private OSMap mMap;

    private TextView  mStatusView;
    private SearchView mSearchView;

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
        mStatusView = new TextView(this);

        Log.v(TAG, "onCreate complete.");

}


    @Override
    protected void onDestroy() {

        super.onDestroy();

        if (mGeocoder != null) {
            mGeocoder.close();
            mGeocoder = null;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actions, menu);
        mSearchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        mSearchView.setOnQueryTextListener(this);
        return true;
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean    onOptionsItemSelected (MenuItem item) {
        Toast.makeText(this, "Selected Item: " + item.getTitle(), Toast.LENGTH_SHORT).show();
        return true;
    }

    // The following callbacks are called for the SearchView.OnQueryChangeListener
    public boolean onQueryTextChange(String newText) {
        newText = newText.isEmpty() ? "" : "Query so far: " + newText;
     //   setContentView(mSearchText);
        return true;
    }

    public boolean onQueryTextSubmit(String query) {
        //Toast.makeText(this, "Searching for: " + query + "...", Toast.LENGTH_SHORT).show();
        if (query == null || query.trim().length() == 0) {
            return true;
        }
      //  performQuery(query);
         mSearchView.clearFocus();
        return true;
    }

    public void performQuery(String query) {

        new PlaceQueryTask().execute(query);
    }

    class PlaceQueryTask extends AsyncTask<String, Void, Geocoder.Result> {

        @Override
        protected Geocoder.Result doInBackground(String... queries) {

            if (queries.length == 0) {
                return null;
            }

            String query = queries[0];

            if (query == null) {
                return null;
            }

            query = query.trim();

            if (query.length() == 0) {
                return null;
            }

            return mGeocoder.geocodeString(query, PLACES, null, 0, 15);
        }


        @Override
        protected void onPostExecute(Geocoder.Result results) {

            if (results == null) {
                return;
            }

      //      mAdapter.addAll(results.getPlacemarks());

       //     mAdapter.notifyDataSetChanged();
        }
    }


}
