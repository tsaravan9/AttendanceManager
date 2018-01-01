package bk.attendancemanager.HomeActivityAndFragments;

import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;
import bk.attendancemanager.Constants;
import bk.attendancemanager.R;
import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;
import yalantis.com.sidemenu.interfaces.Resourceble;
import yalantis.com.sidemenu.interfaces.ScreenShotable;
import yalantis.com.sidemenu.model.SlideMenuItem;
import yalantis.com.sidemenu.util.ViewAnimator;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements ViewAnimator.ViewAnimatorListener {

    CoursesFragment coursesFragment;
    AboutFragment aboutFragment;
    ProFragment proFragment;
    ReportsFragment reportsFragment;
    SettingsFragment settingsFragment;
    HelpFragment helpFragment;
    FeedbackFragment feedbackFragment;
    StudentsFragment studentsFragment;
    DrawerLayout drawerLayout;
    List<SlideMenuItem> list = new ArrayList<>();
    ActionBarDrawerToggle drawerToggle;
    LinearLayout leftDrawer;
    ViewAnimator viewAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initializeFragments();
        performFragTransaction(coursesFragment);
        drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        leftDrawer = findViewById(R.id.left_drawer);
        leftDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawers();
            }
        });
        setupToolbar();
        createMenuList();
        viewAnimator = new ViewAnimator<>(this, list, coursesFragment, drawerLayout, this);
    }

    private void createMenuList() {
        SlideMenuItem menuItem0 = new SlideMenuItem(Constants.CLOSE.name(), R.drawable.ic_close);
        list.add(menuItem0);
        SlideMenuItem menuItem = new SlideMenuItem(Constants.COURSES.name(), R.drawable.ic_courses);
        list.add(menuItem);
        SlideMenuItem menuItem2 = new SlideMenuItem(Constants.REPORTS.name(), R.drawable.ic_reports);
        list.add(menuItem2);
        SlideMenuItem menuItem3 = new SlideMenuItem(Constants.STUDENTS.name(), R.drawable.ic_students);
        list.add(menuItem3);
        SlideMenuItem menuItem4 = new SlideMenuItem(Constants.SETTINGS.name(), R.drawable.ic_settings);
        list.add(menuItem4);
        SlideMenuItem menuItem5 = new SlideMenuItem(Constants.MESSAGE_TO_DEVELOPER.name(), R.drawable.ic_message);
        list.add(menuItem5);
        SlideMenuItem menuItem6 = new SlideMenuItem(Constants.GET_PRO_VERSION.name(), R.drawable.ic_pro);
        list.add(menuItem6);
        SlideMenuItem menuItem7 = new SlideMenuItem(Constants.HELP.name(), R.drawable.ic_help);
        list.add(menuItem7);
        SlideMenuItem menuItem8 = new SlideMenuItem(Constants.ABOUT.name(), R.drawable.ic_about);
        list.add(menuItem8);
        SlideMenuItem menuItem9 = new SlideMenuItem(Constants.RATE.name(), R.drawable.ic_rate);
        list.add(menuItem9);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                drawerLayout,         /* DrawerLayout object */
                toolbar,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                leftDrawer.removeAllViews();
                leftDrawer.invalidate();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (slideOffset > 0.6 && leftDrawer.getChildCount() == 0)
                    viewAnimator.showMenuContent();
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
    }

    private void initializeFragments() {
        coursesFragment = new CoursesFragment();
        aboutFragment = new AboutFragment();
        proFragment = new ProFragment();
        settingsFragment = new SettingsFragment();
        reportsFragment = new ReportsFragment();
        helpFragment = new HelpFragment();
        feedbackFragment = new FeedbackFragment();
        studentsFragment = new StudentsFragment();
    }

    private void performFragTransaction(Fragment replaceable) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frag_space, replaceable).commit();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public ScreenShotable onSwitch(Resourceble slideMenuItem, ScreenShotable screenShotable, int position) {
        switch (slideMenuItem.getName()) {
            case "Close":
                return screenShotable;
            default:
                return replaceFragment(slideMenuItem, screenShotable, position);
        }
    }

    private ScreenShotable replaceFragment(Resourceble slideMenuItem, ScreenShotable screenShotable, int position) {
        View view = findViewById(R.id.frag_space);
        int finalRadius = Math.max(view.getWidth(), view.getHeight());
        SupportAnimator animator = ViewAnimationUtils.createCircularReveal(view, 0, position, 0, finalRadius);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION);

        findViewById(R.id.content_overlay).setBackgroundDrawable(new BitmapDrawable(getResources(), screenShotable.getBitmap()));
        animator.start();
        String cur = slideMenuItem.getName();
        if (cur.equals(Constants.COURSES.name())) {
            CoursesFragment contentFragment = new CoursesFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.frag_space, contentFragment).commit();
            return contentFragment;
        } else if (cur.equals(Constants.REPORTS.name())) {
            ReportsFragment contentFragment = new ReportsFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.frag_space, contentFragment).commit();
            return contentFragment;
        } else if (cur.equals(Constants.STUDENTS.name())) {
            StudentsFragment contentFragment = new StudentsFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.frag_space, contentFragment).commit();
            return contentFragment;
        } else if (cur.equals(Constants.SETTINGS.name())) {
            SettingsFragment contentFragment = new SettingsFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.frag_space, contentFragment).commit();
            return contentFragment;
        } else if (cur.equals(Constants.MESSAGE_TO_DEVELOPER.name())) {
            FeedbackFragment contentFragment = new FeedbackFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.frag_space, contentFragment).commit();
            return contentFragment;
        } else if (cur.equals(Constants.GET_PRO_VERSION.name())) {
            ProFragment contentFragment = new ProFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.frag_space, contentFragment).commit();
            return contentFragment;
        } else if (cur.equals(Constants.HELP.name())) {
            HelpFragment contentFragment = new HelpFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.frag_space, contentFragment).commit();
            return contentFragment;
        } else {
            AboutFragment contentFragment = new AboutFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.frag_space, contentFragment).commit();
            return contentFragment;
        }
        //todo Add link to play store after uploading. In simple words handle rate click event
    }

    @Override
    public void disableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(false);
    }

    @Override
    public void enableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerLayout.closeDrawers();
    }

    @Override
    public void addViewToContainer(View view) {
        leftDrawer.addView(view);
    }
}
