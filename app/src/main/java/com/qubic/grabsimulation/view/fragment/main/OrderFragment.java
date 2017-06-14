package com.qubic.grabsimulation.view.fragment.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;
import com.google.maps.android.PolyUtil;
import com.google.maps.android.SphericalUtil;
import com.qubic.grabsimulation.R;
import com.qubic.grabsimulation.api.model.entity.Track;
import com.qubic.grabsimulation.view.activity.LocationActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dennyho on 3/18/17.
 */

public class OrderFragment extends Fragment
        implements
            OnMapReadyCallback,
            GoogleMap.OnPolylineClickListener {


    MapView mapView;

    //polyline styling
    private static final PatternItem DOT = new Dot();
    private static final PatternItem GAP = new Gap(20);
    private static final List<PatternItem> PATTERN_POLYLINE_DOTTED = Arrays.asList(GAP, DOT);

    // For Locations Activity
    static private final int PICK_UP_REQUEST = 1;
    static private final int DROP_OFF_REQUEST = 2;
    static private final String LOCATION_TYPE = "LOCATION_TYPE";
    TextView pickUpText;
    TextView dropOffText;
    public OrderFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        ButterKnife.bind(this, view);

        FrameLayout pickUp = (FrameLayout) view.findViewById(R.id.pick_up);
        FrameLayout dropOff = (FrameLayout) view.findViewById(R.id.drop_off);
        CardView orderCard = (CardView) view.findViewById(R.id.order_card);
        pickUpText = (TextView) view.findViewById(R.id.pick_up_field);
        dropOffText = (TextView) view.findViewById(R.id.drop_off_field);
        orderCard.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));

        pickUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LocationActivity.class);
                startActivityForResult(intent, PICK_UP_REQUEST);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        dropOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LocationActivity.class);
                intent.putExtra(LOCATION_TYPE, "LOCATION_DROP_OFF");
                startActivityForResult(intent, DROP_OFF_REQUEST);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        mapView = (MapView) view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);

        mapView.onResume(); // display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mapView.getMapAsync(this);

        return view;
    }

    /**
     * Showing location picker activity for pick up
     * TODO: change toast code to show location picker activity
     */
    @OnClick(R.id.pick_up)
    public void showPickUpLocationPicker() {
        Toast.makeText(getActivity(), "Pick Up Clicked !", Toast.LENGTH_SHORT).show();
    }

    /**
     * Showing location picker activity for drop off
     * TODO: change toast code to show location picker activity
     */
    @OnClick(R.id.drop_off)
    public void showDropOffLocationPicker() {
        Toast.makeText(getActivity(), "Drop Off Clicked !", Toast.LENGTH_SHORT).show();
    }

    /**
     * Get result from locationActivity after selected a location
     * @param requestCode - Code that passed when called startActivityForResult()
     * @param resultCode - Result code to know whether it's success or not or another
     * @param data - Data that has been sent from locationActivity
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            String s = data.getStringExtra("LOCATION_DATA");
            switch (requestCode) {
                case PICK_UP_REQUEST: {
                    pickUpText.setText(s);
                    break;
                }
                case DROP_OFF_REQUEST: {
                    dropOffText.setText(s);
                    break;
                }
                default:
                    super.onActivityResult(requestCode, resultCode, data);
                    break;
            }
        }
    }

    /**
     * Listens for when the map is ready
     * @param googleMap the map that is used in layout
     */
    public void onMapReady(final GoogleMap googleMap) {
        Track mikroskilToCpTrack = Track.getTrackList().get(0);

        // adding the point from Mikroskil
        LatLng start = new LatLng (mikroskilToCpTrack.getOrigin().getLat(),
                mikroskilToCpTrack.getOrigin().getLng());

        final List<LatLng> points = new ArrayList<LatLng>();
        points.add(start);
        for (String polyline : mikroskilToCpTrack.getPolylines()) {
            points.addAll(PolyUtil.decode(polyline));
        }

        // adding polyline and marker
        MarkerOptions markerOption = new MarkerOptions().position(start)
                .anchor((float) 0.5, (float) 0.5)
                .flat(true)
                .rotation((float) -20)
                .title("Car marker");

        final Marker marker = googleMap.addMarker(markerOption);
        marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.car));

        Polyline mikroskilToCpPolyline = googleMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .geodesic(true));

        mikroskilToCpPolyline.setPoints(points);
        mikroskilToCpPolyline.setTag("STMIK Mikroskil to Mall Center Point.");
        mikroskilToCpPolyline.setStartCap(new RoundCap());
        mikroskilToCpPolyline.setEndCap(new RoundCap());
        mikroskilToCpPolyline.setWidth(12);
        mikroskilToCpPolyline.setColor(R.color.black);
        mikroskilToCpPolyline.setJointType(JointType.ROUND);

        //simulating the car
        final Handler handler = new Handler();

        final Runnable runnable = new Runnable() {
            int index = 0;
            public void run() {
                if (index > 0) {
                    marker.setRotation((float) SphericalUtil.computeHeading(
                            points.get(index),
                            points.get(index-1)
                    ));
                }
                marker.setPosition(points.get(index));
                index++;
                if (index < points.size()) {
                    handler.postDelayed(this, 2000);
                } else {
                    handler.removeCallbacks(this);
                }
            }
        };

        handler.post(runnable);

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(3.5890519, 98.6850316), 15));

        googleMap.setOnPolylineClickListener(this);
    }

    /**
     * Listens for clicks on a polyline.
     * @param polyline The polyline object that the user has clicked.
     */
    @Override
    public void onPolylineClick(Polyline polyline) {
        // Flip from solid stroke to dotted stroke pattern.
        if ((polyline.getPattern() == null) || (!polyline.getPattern().contains(DOT))) {
            polyline.setPattern(PATTERN_POLYLINE_DOTTED);
        } else {
            // The default pattern is a solid stroke.
            polyline.setPattern(null);
        }

        Toast.makeText(getActivity(), "Route type " + polyline.getTag().toString(),
                Toast.LENGTH_SHORT).show();
    }

}
