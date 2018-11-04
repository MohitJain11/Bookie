package com.example.mohit.b3;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.example.mohit.b3.Adapter.AdapterCategories;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class UserHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView recyclerViewCategory,recyclerViewNewBooks,recyclerViewTopBooks;
    private List<Integer> imageViewList;
    private AdapterCategories adapterCategories;
    private ViewFlipper viewFlipper;
    private GestureDetector gestureDetector;
    private String ImageUrl[]={"https://ak2.picdn.net/shutterstock/videos/14967505/thumb/1.jpg",
        "https://image.freepik.com/free-vector/fantastic-background-of-children-playing-together_23-2147608068.jpg",
        "https://www.colourbox.com/preview/1282705-colorful-child-hand-prints-on-white-background.jpg"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerViewCategory=findViewById(R.id.recycle_all_category);
        recyclerViewNewBooks=findViewById(R.id.recycle_all_new_books);
        recyclerViewTopBooks=findViewById(R.id.recycle_all_top_books);
        viewFlipper=findViewById(R.id.flipper);
        imageViewList=new ArrayList<>();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        imagesAddInList();
        for (String image:ImageUrl)
        {
            setFlipperImage(image);
        }

    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);

        return super.onTouchEvent(event);
    }
    private void setFlipperImage(String s) {
        ImageView image = new ImageView(UserHomeActivity.this);
        Picasso.get().load(s).into(image);
        viewFlipper.addView(image);
        viewFlipper.setFlipInterval(4000);
        viewFlipper.setAutoStart(true);
        // animation
        viewFlipper.setInAnimation(UserHomeActivity.this, android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(UserHomeActivity.this, android.R.anim.slide_out_right);
        CustomGestureDetector customGestureDetector = new CustomGestureDetector();
        gestureDetector = new GestureDetector(this, customGestureDetector);


    }

    class CustomGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            // Swipe left (next)
            if (e1.getX() > e2.getX()) {
                viewFlipper.showNext();
            }

            // Swipe right (previous)
            if (e1.getX() < e2.getX()) {
                viewFlipper.showPrevious();
            }

            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }
    private void imagesAddInList() {
        imageViewList.add(R.drawable.ic_launcher_background);
        imageViewList.add(R.drawable.ic_launcher_background);
        imageViewList.add(R.drawable.ic_launcher_background);
        imageViewList.add(R.drawable.ic_launcher_background);
        //recyclerViewCategory.addItemDecoration(new DividerItemDecoration(UserHomeActivity.this, LinearLayoutManager.HORIZONTAL));
        adapterCategories=new AdapterCategories(UserHomeActivity.this,imageViewList);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(UserHomeActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCategory.setLayoutManager(horizontalLayoutManager);
        adapterCategories.notifyDataSetChanged();
        recyclerViewCategory.setAdapter(adapterCategories);
        LinearLayoutManager horizontalLayoutManager1 = new LinearLayoutManager(UserHomeActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewNewBooks.setLayoutManager(horizontalLayoutManager1);
        recyclerViewNewBooks.setAdapter(adapterCategories);
        LinearLayoutManager horizontalLayoutManager2 = new LinearLayoutManager(UserHomeActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewTopBooks.setLayoutManager(horizontalLayoutManager2);
        recyclerViewTopBooks.setAdapter(adapterCategories);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
