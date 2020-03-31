package id.ac.telkomuniversity.dph3a4.org.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import id.ac.telkomuniversity.dph3a4.org.Adapters.SliderAdapter;
import id.ac.telkomuniversity.dph3a4.org.R;

public class OnboardingActivity extends AppCompatActivity {

    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;

    private TextView[] mDots;

    private SliderAdapter sliderAdapter;

    private Button btnNext;
    private Button btnSkip;

    private int mCurrentPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        mSlideViewPager = findViewById(R.id.SliderView);
        mDotLayout = findViewById(R.id.slideIndicator);

        btnNext = findViewById(R.id.btn_next);
        btnSkip = findViewById(R.id.btn_skip);

        sliderAdapter = new SliderAdapter(this);

        mSlideViewPager.setAdapter(sliderAdapter);

        addDotsIndicator(0);

        mSlideViewPager.addOnPageChangeListener(viewListener);

        // OnClickListener

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentPage == mDots.length - 1) {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();

                    SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean("firstStart", false);
                    editor.apply();
                } else {
                    mSlideViewPager.setCurrentItem(mCurrentPage + 1);
                }
            }
        });

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();

                SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("firstStart", false);
                editor.apply();
//                if (mCurrentPage == 0) {
//                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
//                    finish();
//                } else {
//                    mSlideViewPager.setCurrentItem(mCurrentPage - 1);
//                }
            }
        });
    }

    public void addDotsIndicator(int position) {
        mDots = new TextView[3];
        mDotLayout.removeAllViews();

        for (int i = 0; i < mDots.length; i++) {
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226"));
            mDots[i].setTextSize(40);
            mDots[i].setTextColor(getResources().getColor(R.color.TransparentGray));

            mDotLayout.addView(mDots[i]);
        }

        if (mDots.length > 0) {
            mDots[position].setTextColor(getResources().getColor(R.color.colorPrimary));
        }

    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            addDotsIndicator(i);

            mCurrentPage = i;

//            Log.d("Page", "mCurrentPage = " + mCurrentPage);

            if (i == 0) {
                btnNext.setEnabled(true);
                btnSkip.setEnabled(false);
                btnSkip.setVisibility(View.VISIBLE);

                btnNext.setText("Next");
                btnSkip.setText("Skip");
            } else if (i == mDots.length - 1) {
                btnNext.setEnabled(true);
                btnSkip.setEnabled(true);
                btnSkip.setVisibility(View.VISIBLE);

                btnNext.setText("Finish");
                btnSkip.setText("Skip");
            } else {
                btnNext.setEnabled(true);
                btnSkip.setEnabled(true);
                btnSkip.setVisibility(View.VISIBLE);

                btnNext.setText("Next");
                btnSkip.setText("Skip");
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
