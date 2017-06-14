package com.qubic.grabsimulation.view.activity;

import android.os.Bundle;

import com.qubic.grabsimulation.R;
import com.qubic.grabsimulation.api.model.entity.Locations;
import com.qubic.grabsimulation.api.model.entity.Track;
import com.qubic.grabsimulation.api.model.entity.User;
import com.qubic.grabsimulation.api.model.session.SessionManager;
import com.qubic.grabsimulation.view.fragment.auth.LoginButtonFragment;

import java.util.ArrayList;

import io.realm.Realm;

public class AuthActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        Realm.init(getApplicationContext());
        User.addNewUser(new User("qubic@gmail.com", "t", "081390390390"));
        User.addNewUser(new User("testing@gmail.com", "0999999999","testing"));

        Locations mikroskilLocation = new Locations("STMIK Mikroskil", "Jalan Thamrin 140B"
                , 0.0, 3.5874999, 98.6909642);

        Locations cpLocation = new Locations("Center Point", "Jalan Thamrin 140B"
                , 0.0, 3.5914693, 98.6811486);

        Track.addNewTrack(
                new Track(mikroskilLocation, cpLocation,
                new ArrayList<String> () {{
                    add("{t{ToqjyQdA[PE");
                    add("cr{TqrjyQDd@Dv@@z@?BDrA?p@DNJJFDVBTHLDHFHLHLVj@Rj@HZBFBVNrABPPvAPtAD`@@DJv@Np@");
                    add("ai{TytiyQsH~BcB\\wGlB");
                    add("q~{TmliyQX`AZtA^nAJ`@DNTdA");
                    add("sz{TmaiyQVG@?");
                    add("yy{TuaiyQp@|BVfA");
                    add("ow{To{hyQFLOBA?CAeAf@w@\\{Ap@gA\\w@PMDIDGDGDEFKPA@");
                    add("sd|TcthyQ[Z]VGDEDGFMFKFe@L");
                }}, 1.8));

        /**
         * TODO: Make more locations and tracks
         */

        if (SessionManager.with(getApplicationContext()).IsUserLogin()) {
            this.doChangeActivity(getApplicationContext(), MainActivity.class);
        }

        LoginButtonFragment fragment = new LoginButtonFragment();
        changeFragment(R.id.fragment_container, fragment, false, "");
    }
}
