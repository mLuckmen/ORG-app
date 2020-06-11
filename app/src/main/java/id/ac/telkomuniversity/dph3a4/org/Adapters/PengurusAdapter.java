package id.ac.telkomuniversity.dph3a4.org.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import id.ac.telkomuniversity.dph3a4.org.ApiHelper.RetrofitClient;
import id.ac.telkomuniversity.dph3a4.org.Model.PengurusItem;
import id.ac.telkomuniversity.dph3a4.org.R;

public class PengurusAdapter extends ArrayAdapter<PengurusItem>  {
    private ArrayList<PengurusItem> pengurus;
    Context context;

    private static class ViewHolder{
        TextView nama;
        TextView nim;
        TextView jabatan;
        ImageView foto;
    }

    public PengurusAdapter(ArrayList<PengurusItem> data, Context context) {
        super(context, R.layout.layout_people_list, data);
        this.pengurus = data;
        this.context = context;
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // get data item for this position
        PengurusItem pengurusItem = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.layout_people_list, parent, false);
            viewHolder.nama = convertView.findViewById(R.id.nama);
            viewHolder.nim = convertView.findViewById(R.id.nim);
            viewHolder.jabatan = convertView.findViewById(R.id.jabatan);
            viewHolder.foto = convertView.findViewById(R.id.foto_list);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(context, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.nama.setText(pengurusItem.getNama());
        viewHolder.nim.setText(pengurusItem.getNim());
        viewHolder.jabatan.setText(pengurusItem.getJabatan());

        if (!pengurusItem.getFoto().equals("")){
            String img_url = RetrofitClient.IP_URL + "asset/images/foto/" + pengurusItem.getFoto();
            Glide.with(context).load(img_url).into(viewHolder.foto);
        }

        return convertView;
    }
}
