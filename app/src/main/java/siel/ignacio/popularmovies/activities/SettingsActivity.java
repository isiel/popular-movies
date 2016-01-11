package siel.ignacio.popularmovies.activities;

import android.app.Activity;
import android.os.Bundle;

import siel.ignacio.popularmovies.fragments.SettingsFragment;

/**
 * Created by ignacio on 01/11/16.
 */
public class SettingsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();

    }

}
