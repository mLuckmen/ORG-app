package id.ac.telkomuniversity.dph3a4.org.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.parceler.Parcels;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import id.ac.telkomuniversity.dph3a4.org.Activities.DaftarKegiatanActivity;
import id.ac.telkomuniversity.dph3a4.org.ApiHelper.RetrofitClient;
import id.ac.telkomuniversity.dph3a4.org.Model.KegiatanItem;
import id.ac.telkomuniversity.dph3a4.org.R;

public class KegiatanAdapter extends RecyclerView.Adapter<KegiatanAdapter.MyViewHolder> {
    public static final String DATA_KEGIATAN = "dataKegiatan";
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
        String pattern = "EEEE, d MMMM yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, new Locale("id", "ID"));
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(dataKegiatan.get(position).getWaktu());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String tanggalKegiatan = simpleDateFormat.format(date);

        holder.tvNamaKegiatan.setText(dataKegiatan.get(position).getNamaKegiatan());
        holder.tvTanggalPelaksanaan.setText(tanggalKegiatan);
        holder.tvTempatPelaksanaan.setText(dataKegiatan.get(position).getTempat());
        holder.tvHargaTiket.setText("Harga tiket : Rp." + dataKegiatan.get(position).getHarga());

        if (!dataKegiatan.get(position).getFoto().equals("")) {
            String img_url = RetrofitClient.IP_URL + "asset/images/" + dataKegiatan.get(position).getFoto();
            Glide.with(context).load(img_url).into(holder.ivPosterKegiatan);
        }

        holder.btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pindah = new Intent(context, DaftarKegiatanActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(DATA_KEGIATAN, Parcels.wrap(dataKegiatan.get(position)));
                pindah.putExtra(DATA_EXTRA, bundle);
                context.startActivity(pindah);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataKegiatan.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPosterKegiatan;
        TextView tvNamaKegiatan, tvTanggalPelaksanaan, tvTempatPelaksanaan, tvHargaTiket;
        Button btnDaftar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNamaKegiatan = itemView.findViewById(R.id.tvNamaKegiatan);
            tvTanggalPelaksanaan = itemView.findViewById(R.id.tvTanggalPelaksanaan);
            tvTempatPelaksanaan = itemView.findViewById(R.id.tvTempatPelaksanaan);
            ivPosterKegiatan = itemView.findViewById(R.id.ivPosterKegiatan);
            tvHargaTiket = itemView.findViewById(R.id.tvHargaTiket);
            btnDaftar = itemView.findViewById(R.id.btnPesan1);
        }
    }

}
