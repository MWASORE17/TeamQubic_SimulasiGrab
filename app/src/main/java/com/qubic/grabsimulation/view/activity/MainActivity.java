package com.qubic.grabsimulation.view.activity;

import android.animation.ValueAnimator;
import android.icu.lang.UProperty;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.qubic.grabsimulation.R;
import com.qubic.grabsimulation.api.model.entity.User;

import com.qubic.grabsimulation.api.model.session.SessionManager;
import com.qubic.grabsimulation.view.fragment.main.GrabPayFragment;
import com.qubic.grabsimulation.view.fragment.main.OrderFragment;

import butterknife.ButterKnife;
import io.realm.Realm;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (!SessionManager.with(getApplicationContext()).IsUserLogin()) {
            this.doChangeActivity(getApplicationContext(), AuthActivity.class);
        } else {
            User.setCurrentUser(getApplicationContext(),
                    SessionManager.with(getApplicationContext()).GetUserLoggedIn());
        }

        /**
         * TODO: delete if not used in activity
         */
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawerToggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(drawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle.syncState();
        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View navHeader = navigationView.getHeaderView(0);
        TextView tvNavUsername = (TextView)navHeader.findViewById(R.id.nav_username);
        tvNavUsername.setText(User.currentUser.getUsername());
//        TextView tvNavRewards = (TextView) navHeader.findViewById(R.id.nav_rewards);
//        tvNavRewards.setText("186 Rewards | Rewards Member");
        //provide backStack listener
        getSupportFragmentManager().addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {
                    public void onBackStackChanged() {
                        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                            final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                            getSupportActionBar().setTitle(R.string.app_name);

                            // this animation is used to animate back arrow to hamburger
                            // 1,0 means from arrow to hamburger
                            // 0,1 means from hamburger to arrow
                            ValueAnimator anim = ValueAnimator.ofFloat(1, 0);

                            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                @Override
                                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                    float slideOffset = (Float) valueAnimator.getAnimatedValue();
                                    drawerToggle.onDrawerSlide(drawer, slideOffset);
                                }
                            });

                            anim.setInterpolator(new DecelerateInterpolator());

                            anim.setDuration(500);
                            anim.start();
                            drawerToggle.setDrawerIndicatorEnabled(true);

                            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                        } else {
                            drawerToggle.setDrawerIndicatorEnabled(false);
                        }
                    }
                });

        //set order fragment
        OrderFragment orderFragment = new OrderFragment();
        changeFragment(R.id.fragment_container, orderFragment, false, "");
    }

    /**
     * Listen for back button
     */
    @Override
    public void onBackPressed() {
        //get drawerLayout
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
            drawerToggle.setDrawerIndicatorEnabled(true);
        } else if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Listen for up button in the toolbar
     *
     * @return
     */
    @Override
    public boolean onSupportNavigateUp() {
        //get drawerLayout
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
            drawerToggle.setDrawerIndicatorEnabled(true);
        } else if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            drawer.openDrawer(GravityCompat.START);
        }
        return true;
    }

    /**
     * Listen for each item selected on navigation drawer
     * @param item the item in navigation drawer
     * @return
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_grab_pay) {
            GrabPayFragment grabPayFragment = new GrabPayFragment();
            changeFragment(R.id.fragment_container, grabPayFragment, true, "");
        }

        drawerToggle.setDrawerIndicatorEnabled(false);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }


}
