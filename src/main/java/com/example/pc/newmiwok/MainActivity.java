package com.example.pc.newmiwok;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_main);

        // Find the view pager that will allow the user to swipe between fragments
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);

        // Set the adapter onto the view pager
        viewPager.setAdapter(new CategoryAdapter(this, getSupportFragmentManager()));
        viewPager.setPageTransformer(true, new DeapthPageTransform());

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_view);
        tabLayout.setupWithViewPager(viewPager);


        //DISPLAY ALL ACTIVITY ON SCREEN BY NAME
//        //Find number
//        TextView numberView = (TextView) findViewById(R.id.numbers);
//        numberView.setOnClickListener(new View.OnClickListener() {
//
//            //Set Intent @NumberActivity
//            @Override
//            public void onClick(View v) {
//                Intent numberIntent = new Intent(MainActivity.this,NumberActivity.class);
//                Toast.makeText(getBaseContext(),"Open list of Number",Toast.LENGTH_SHORT).show();
//                startActivity(numberIntent);
//            }
//        });
    }
}
