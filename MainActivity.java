package naxa.location;

//import android.app.Activity;
//import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends FragmentActivity implements LocationListener  {
	
	GoogleMap googleMap;
	LatLng latLong;
	double latitude, longitude;
	long back_pressed;
	static int mapType;
	//static final String PREF = "PREF";
	//SharedPreferences shPref;
	//int mapType;
		
	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_main );
		setUpMapIfNeeded();
	}
	
	private void setUpMapIfNeeded() {
		if ( null == googleMap ) {
			googleMap = ( ( SupportMapFragment ) getSupportFragmentManager().findFragmentById( R.id.map ) ).getMap();
			if  ( null != googleMap ) {
				setUpMap();
			}
		}
	}

	private void setUpMap() {
		googleMap.setMyLocationEnabled( true );
		LocationManager lm = ( LocationManager ) getSystemService( LOCATION_SERVICE );
		Criteria criteria = new Criteria();
		String provider = lm.getBestProvider( criteria, true );
		Location myLocation = lm.getLastKnownLocation( provider );
		
		if( null != myLocation ) {
			onLocationChanged( myLocation );
		} 
		
		lm.requestLocationUpdates( provider, 20000, 0, this );
		
		setMapType();
	}
	
	public void onLocationChanged( Location location ) {
		latitude = location.getLatitude();
		longitude = location.getLongitude();
		latLong = new LatLng( latitude, longitude );
		googleMap.addMarker( new MarkerOptions().position( latLong ).title( "Me" ) );
	}

	private void setMapType() { 
		//shPref = getSharedPreferences( PREF, Activity.MODE_PRIVATE );
		//mapType = shPref.getInt( "mapType", 1 );
		if (  0 != mapType  ) {
			googleMap.setMapType( mapType );
		} else {
			googleMap.setMapType( GoogleMap.MAP_TYPE_HYBRID );
		}
	}

	@Override
	public boolean onCreateOptionsMenu( Menu menu ) {
		getMenuInflater().inflate( R.menu.main, menu );
		return true;
	}

	@Override
	public boolean onOptionsItemSelected( MenuItem item ) {
		//shPref = getSharedPreferences( PREF, Activity.MODE_PRIVATE );
		//SharedPreferences.Editor edit = shPref.edit();
		switch ( item.getItemId() ) {
			case R.id.action_item1:
				mapType = GoogleMap.MAP_TYPE_NORMAL;
				googleMap.setMapType( mapType );
				//edit.putInt( "mapType", mapType );
				//edit.putInt( "mapType", GoogleMap.MAP_TYPE_NORMAL );
				//edit.commit();
				return true;
			case R.id.action_item2:
				mapType = GoogleMap.MAP_TYPE_SATELLITE;
				googleMap.setMapType( mapType );
				//edit.putInt( "mapType", mapType );
				//edit.putInt( "mapType", GoogleMap.MAP_TYPE_SATELLITE );
				//edit.commit();
				return true;
			case R.id.action_item3:
				mapType = GoogleMap.MAP_TYPE_TERRAIN;
				googleMap.setMapType( mapType );
				//edit.putInt( "mapType", mapType );
				//edit.putInt( "mapType", GoogleMap.MAP_TYPE_TERRAIN );
				//edit.commit();
				return true;
			default:
				return true;
		}
	}
	
	@Override
	public void onBackPressed() {
		if ( back_pressed + 2000 > System.currentTimeMillis() ) {
			finish();
		} else {
			Toast.makeText( getBaseContext(), "Press again to exit!", Toast.LENGTH_SHORT ).show();
			back_pressed = System.currentTimeMillis();
		}
	}

	@Override
	public void onProviderDisabled( String provider ) {}

	@Override
	public void onProviderEnabled( String provider ) {}

	@Override
	public void onStatusChanged( String provider, int status, Bundle extras ) {}
}
