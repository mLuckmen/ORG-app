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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import id.ac.telkomuniversity.dph3a4.org.Model.AnggotaItem;
import id.ac.telkomuniversity.dph3a4.org.R;

public class AnggotaAdapter extends ArrayAdapter<AnggotaItem> {
    private ArrayList<AnggotaItem> anggota;
    Context context;

    private static class ViewHolder{
        TextView nama, nim, jabatan;
        ImageView foto;
    }

    public AnggotaAdapter(ArrayList<AnggotaItem> data, Context context){
        super(context, R.layout.layout_people_list, data);
        this.anggota = data;
        this.context = context;
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AnggotaItem anggotaItem = getItem(position);
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

        viewHolder.nama.setText(anggotaItem.getNama());
        viewHolder.nim.setText(anggotaItem.getNim());
        viewHolder.jabatan.setText(anggotaItem.getJabatan());

        String img_url = "http://10.0.2.2/pa/asset/images/foto/" + anggotaItem.getFoto();
//        String img_url = "http://192.168.1.11/pa/asset/images/foto/" + pengurusItem.getFoto(); // Kenari Tony_plus
//        String img_url = "http://org-web.ml/pa/asset/images/foto/" + pengurusItem.getFoto();
        Glide.with(context).load(img_url).into(viewHolder.foto);

        return convertView;
    }
}
