package id.ac.telkomuniversity.dph3a4.org.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import id.ac.telkomuniversity.dph3a4.org.Model.KegiatanItem;
import id.ac.telkomuniversity.dph3a4.org.R;

public class KegiatanAdapter extends RecyclerView.Adapter<KegiatanAdapter.MyViewHolder> {
    private static final String DATA_KEGIATAN = "dataKegiatan";
    public static final String DATA_EXTRA = "dataExtra";
    private Context context;
    private List<KegiatanItem> dataKegiatan = new ArrayList<>();

    // Constructor
    public KegiatanAdapter(Context context, List<KegiatanItem> dataKegiatan) {
        this.context = context;
        this.dataKegiatan = dataKegiatan;
    }

    // 1 Menyambungkan layout item
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.layout_item_kegiatan, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    // 3 set data
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvNamaKegiatan.setText(dataKegiatan.get(position).getNamaKegiatan());
        holder.tvTanggalPelaksanaan.setText(dataKegiatan.get(position).getWaktu());
        holder.tvTempatPelaksanaan.setText(dataKegiatan.get(position).getTempat());

        String img_url = "http://10.0.2.2/pa/asset/images/" + dataKegiatan.get(position).getFoto();

//        Glide.with(context).load(img_url).into(holder.ivPosterKegiatan);

    }

    @Override
    public int getItemCount() {
        return dataKegiatan.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPosterKegiatan;
        TextView tvNamaKegiatan, tvTanggalPelaksanaan, tvTempatPelaksanaan;
        Button btnDaftar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNamaKegiatan = itemView.findViewById(R.id.tvNamaKegiatan);
            tvTanggalPelaksanaan = itemView.findViewById(R.id.tvTanggalPelaksanaan);
            tvTempatPelaksanaan = itemView.findViewById(R.id.tvTempatPelaksanaan);
            ivPosterKegiatan = itemView.findViewById(R.id.ivPosterKegiatan);
        }
    }

//    public String ChangeDateFormat(String tanggal) {
//        Locale id = new Locale("in", "ID");
//        String pattern = "EEEE, dd MMMM yyyy";
//        SimpleDateFormat sdf = new SimpleDateFormat(pattern, id);
//        DateFormatSymbols dfs = new DateFormatSymbols(id);
//        String[] days = dfs.getWeekdays();
//        String newDays[] = new String[days.length];
//
//        dfs.setWeekdays(newDays);
//        String[] longMonth = dfs.getMonths();
//        String months[] = new String[longMonth.length];
//
//        dfs.setMonths(months);
//        sdf = new SimpleDateFormat(pattern, dfs);
//        String output = sdf.format(tanggal);
//        return output;
//    }
}
