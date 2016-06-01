package org.envisiontechllc.supertutor;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.envisiontechllc.supertutor.settings.AppContext;
import org.envisiontechllc.supertutor.data.NavManager;
import org.envisiontechllc.supertutor.subactivities.PersonalityActivity;
import org.envisiontechllc.supertutor.subactivities.dialogs.SettingsDialog;
import org.envisiontechllc.supertutor.subactivities.fragments.DashboardFragment;
import org.envisiontechllc.supertutor.subactivities.fragments.DiscussionFragment;
import org.envisiontechllc.supertutor.subactivities.fragments.LibraryFragment;
import org.envisiontechllc.supertutor.subactivities.fragments.profile.ProfileFragment;

public class SuperTutor extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;

    private AppContext data;
    private NavManager navManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        initComponents();
        initToolbar();
        initDrawer();

        navManager.commitFragment("DASHBOARD", new DashboardFragment());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            int count = getSupportFragmentManager().getBackStackEntryCount();
            if(count == 0){
                super.onBackPressed();
            } else {
                getSupportFragmentManager().popBackStack();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.super_tutor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id){
            case R.id.navHome:
                navManager.commitFragment("DASHBOARD", new DashboardFragment());
                break;
            case R.id.navLibrary:
                navManager.commitFragment("LIBRARY", new LibraryFragment());
                break;
            case R.id.navTest:
                Intent quizIntent = new Intent(this, PersonalityActivity.class);
                startActivity(quizIntent);
                break;
            case R.id.navBoard:
                navManager.commitFragment("DISCUSSION_BOARD", new DiscussionFragment());
                break;
            case R.id.navProfile:
                ProfileFragment profileFragment = new ProfileFragment();
                Bundle args = new Bundle();
                args.putSerializable("user", data.getCurrentUser());
                profileFragment.setArguments(args);
                navManager.commitFragment("PROFILE", profileFragment);
                break;
            case R.id.navEmail:
                navManager.startEmailActivity(new Intent(Intent.ACTION_SEND));
                break;
            case R.id.navTwitter:
                navManager.openTwitter();
                break;
            case R.id.navInstagram:
                navManager.openInstagram();
                break;
            case R.id.navLogout:
                navManager.userLogout(this);
                break;
            case R.id.navSettings:
                new SettingsDialog(this).show();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("DashboardFragment");
        setSupportActionBar(toolbar);
    }

    private void initDrawer(){
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initDrawerHeader();
    }

    private void initDrawerHeader(){
        View headerView = navigationView.getHeaderView(0);

        TextView username = (TextView)headerView.findViewById(R.id.navUsername);
        TextView email = (TextView)headerView.findViewById(R.id.navEmail);

        username.setText(data.getCurrentUser().getUsername());
        email.setText(data.getCurrentUser().getEmail());
    }

    private void initComponents(){
        data = AppContext.getContext();
        navManager = new NavManager(this, getSupportFragmentManager());
    }
}
