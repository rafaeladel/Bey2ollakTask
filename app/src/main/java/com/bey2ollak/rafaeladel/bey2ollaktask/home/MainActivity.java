package com.bey2ollak.rafaeladel.bey2ollaktask.home;

import android.content.Context;
import android.os.PersistableBundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bey2ollak.rafaeladel.bey2ollaktask.R;
import com.bey2ollak.rafaeladel.bey2ollaktask.home.view.HomeFragment;

public class MainActivity extends AppCompatActivity {
    public final String PLACES_FRAGMENT = "places.fragment";
    public final String CURRENT_SEARCH_QUERY = "search_query";
    private Toolbar toolbar;
    private MenuItem searchActionItem;
    private boolean isSearchOpened;
    private EditText searchEditText;
    private String searchQuery;
    private HomeFragment homeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            homeFragment = HomeFragment.getInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.home_frame_layout, homeFragment, PLACES_FRAGMENT).commit();
        } else {
            homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag(PLACES_FRAGMENT);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String currentSearchQuery = savedInstanceState.getString(CURRENT_SEARCH_QUERY);
        if (currentSearchQuery != null && currentSearchQuery.length() > 0) {
            isSearchOpened = false;
            searchQuery = currentSearchQuery;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (searchEditText != null && searchEditText.getText().toString().trim().length() > 0) {
            outState.putString(CURRENT_SEARCH_QUERY, searchEditText.getText().toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        searchActionItem = menu.findItem(R.id.action_search);
        //restoring search state after configuration change
        if (searchQuery != null) {
            handleSearch();
            searchEditText.setText(searchQuery);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_search:
                handleSearch();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void handleSearch() {
        ActionBar action = getSupportActionBar(); //get the actionbar

        if (isSearchOpened) { //test if the search is open

            action.setDisplayShowCustomEnabled(false); //disable a custom view inside the actionbar
            action.setDisplayShowTitleEnabled(true); //show the title in the action bar

            //hides the keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(searchEditText.getWindowToken(), 0);

            //add the search icon in the action bar
            searchActionItem.setIcon(ContextCompat.getDrawable(this, android.R.drawable.ic_menu_search));
            searchEditText.getText().clear();

            isSearchOpened = false;
        } else { //open the search entry

            action.setDisplayShowCustomEnabled(true); //enable it to display a
            // custom view in the action bar.
            action.setCustomView(R.layout.search_bar);//add the custom view
            action.setDisplayShowTitleEnabled(false); //hide the title
            searchEditText = (EditText) action.getCustomView().findViewById(R.id.edtSearch); //the text editor


            //this is a listener to do a search when the user clicks on search button
            searchEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    String query = editable.toString();
                    homeFragment.doSearch(query);
                }
            });

            searchEditText.requestFocus();

            //open the keyboard focused in the edtSearch
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(searchEditText, InputMethodManager.SHOW_IMPLICIT);

            //add the close icon
            searchActionItem.setIcon(ContextCompat.getDrawable(this, android.R.drawable.ic_menu_close_clear_cancel));

            isSearchOpened = true;
        }
    }
}
