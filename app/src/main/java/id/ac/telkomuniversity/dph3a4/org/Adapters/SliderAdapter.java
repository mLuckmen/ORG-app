package id.ac.telkomuniversity.dph3a4.org.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import id.ac.telkomuniversity.dph3a4.org.R;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    // Arrays
    public int[] slide_images = {
            R.drawable.picture_1_org_manage,
            R.drawable.picture_2_org_activity,
            R.drawable.picture_3_org_events
    };

    public String[] slide_headings = {
            "ORGANIZATION MANAGER",
            "ORGANIZATION ACTIVITY",
            "ORGANIZATION EVENTS"
    };

    public String[] slide_desc = {
            "Mengatur kegiatan berbagai organisasi di dalam atau di luar kampus Telkom University.",
            "Memberikan informasi mengenai aktivitas dari setiap organisasi yang diikuti.",
            "Memberikan informasi kegiatan organisasi dan pendaftaran kegiatan organisasi."
    };


    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.layout_slider, container, false);

        ImageView slideImageView = view.findViewById(R.id.slide_image);
        TextView slideHeading = view.findViewById(R.id.slide_heading);
        TextView slideDesc = view.findViewById(R.id.slide_desc);

        slideImageView.setImageResource(slide_images[position]);
        slideHeading.setText(slide_headings[position]);
        slideDesc.setText(slide_desc[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((RelativeLayout)object);
    }
}
