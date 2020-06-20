package id.ac.telkomuniversity.dph3a4.org.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import id.ac.telkomuniversity.dph3a4.org.ApiHelper.RetrofitClient;
import id.ac.telkomuniversity.dph3a4.org.Model.DataPresensiItem;
import id.ac.telkomuniversity.dph3a4.org.R;

public class HistoriKegiatanAdapter extends RecyclerView.Adapter<HistoriKegiatanAdapter.MyViewHolder> {
    public static final String DATA_PRESENSI = "dataPresensi";
    public static final String DATA_EXTRA = "dataExtra";
    private Context context;
    private List<DataPresensiItem> dataPresensi = new ArrayList<>();

    public HistoriKegiatanAdapter(Context context, List<DataPresensiItem> dataPresensi) {
        this.context = context;
        this.dataPresensi = dataPresensi;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.layout_item_histori, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String pattern = "EEEE, d MMMM yyyy HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, new Locale("id", "ID"));
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dataPresensi.get(position).getWaktuSubmit());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String tanggalPresensi = simpleDateFormat.format(date);

        holder.tvKegiatanHistori.setText(dataPresensi.get(position).getNamaKegiatan());
        holder.tvTempatHistori.setText(dataPresensi.get(position).getTempat());
        holder.tvWaktuHistori.setText(tanggalPresensi);

        if (!dataPresensi.get(position).getFoto().equals("")){
            String img_url = RetrofitClient.IP_URL + "asset/images/" + dataPresensi.get(position).getFoto();
            Glide.with(context).load(img_url).into(holder.ivPosterHistori);
        }
    }

    @Override
    public int getItemCount() {
        return dataPresensi.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPosterHistori;
        TextView tvKegiatanHistori, tvTempatHistori, tvWaktuHistori;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPosterHistori = itemView.findViewById(R.id.ivPosterHistori);
            tvKegiatanHistori = itemView.findViewById(R.id.tvKegiatanHistori);
            tvTempatHistori = itemView.findViewById(R.id.tvTempatHistori);
            tvWaktuHistori = itemView.findViewById(R.id.tvWaktuHistori);
        }
    }
}
