package com.qubic.grabsimulation.view.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.qubic.grabsimulation.R;
import com.qubic.grabsimulation.adapter.LocationAdapter;
import com.qubic.grabsimulation.api.model.entity.Locations;
import com.qubic.grabsimulation.listener.LocationRVTouchListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity for Selecting Location.
 */

public class LocationActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    static private final String LOCATION_TYPE = "LOCATION_TYPE";

    private boolean showMapMenu;
    private EditText locationEditText;
    private Button clearBtn;

    private RecyclerView locationRecyclerView;
    private LocationAdapter locationAdapter;
    private RecyclerView.LayoutManager locationLayoutManager;

    List<Locations> locationsList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

//        locationEditText = (EditText) findViewById(R.id.search_location_input);
//        clearBtn = (Button) findViewById(R.id.clear_btn);
        Toolbar locationToolbar = (Toolbar) findViewById(R.id.select_location_toolbar);

        setSupportActionBar(locationToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            locationToolbar.setTitle("");
        }

//        clearBtn.setVisibility(RelativeLayout.INVISIBLE);
        setEvent();
        showMapMenu = false;

        locationsList = Locations.createListLocations();
        for (Locations list : locationsList) {
            System.out.println("name: " + list.getName());
            System.out.println("address: " + list.getAddress());
        }
        locationAdapter = new LocationAdapter(this, locationsList);

        locationRecyclerView = (RecyclerView) findViewById(R.id.location_recycler_view);
        locationRecyclerView.setHasFixedSize(true);
        locationLayoutManager = new LinearLayoutManager(getApplicationContext());
        locationRecyclerView.setLayoutManager(locationLayoutManager);

        locationRecyclerView.setItemAnimator(new DefaultItemAnimator());
        locationRecyclerView.setAdapter(locationAdapter);

        locationRecyclerView.addOnItemTouchListener(new LocationRVTouchListener(this,
                locationRecyclerView, new LocationRVTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Locations loc = locationsList.get(position);
                Intent resultIntent = new Intent(getBaseContext(), MainActivity.class);
                resultIntent.putExtra("LOCATION_DATA", loc.getName());
                setResult(RESULT_OK, resultIntent);
                finish();
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    // Create menu options on the right toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.select_location_menu, menu);
        MenuItem mapMenu = menu.findItem(R.id.select_map_button);
        MenuItem searchItem = menu.findItem(R.id.location_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        // To manipulate text in searchItem, ex like hint
        SearchView.SearchAutoComplete searchText = (SearchView.SearchAutoComplete) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        // Set Layout for search
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT);
        layoutParams.gravity = Gravity.TOP;
        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconified(false);
        searchView.setBackgroundResource(R.drawable.border_drop_off_left);
            searchView.setLayoutParams(layoutParams);
        searchView.setOnQueryTextListener(this);
        mapMenu.setVisible(showMapMenu);

        // GET TYPE OF LOCATION
        final String locationType = getIntent().getStringExtra(LOCATION_TYPE);
        if (locationType != null && locationType.equals("LOCATION_DROP_OFF")) {
            searchText.setHint(R.string.drop_off);
        } else {
            searchText.setHint(R.string.pick_up);
        }

        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                locationAdapter.setFilter(locationsList);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                return false;
            }
        });
        MenuItemCompat.expandActionView(searchItem);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextChange(String query) {
        final List<Locations> filteredLocations = filter(locationsList, query);
        locationAdapter.setFilter(filteredLocations);
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private List<Locations> filter(List<Locations> lists, String query) {
        query = query.toLowerCase();
        final List<Locations> filteredList = new ArrayList<>();
        for (Locations list : lists) {
            final String text = list.getName().toLowerCase();
            if (text.contains(query)) {
                filteredList.add(list);
            }
        }
        return filteredList;
    }

    public void toggleMenus(MenuItem menuItem) {
        showMapMenu = !showMapMenu;
        invalidateOptionsMenu();
    }

    private void setEvent() {
//        clearBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                locationEditText.setText("");
//            }
//        });
//        locationEditText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (s.length() > 0) {
//                    clearBtn.setVisibility(View.VISIBLE);
//                } else {
//                    clearBtn.setVisibility(View.GONE);
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//
    }

}
