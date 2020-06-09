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

import java.util.ArrayList;
import java.util.List;

import id.ac.telkomuniversity.dph3a4.org.ApiHelper.RetrofitClient;
import id.ac.telkomuniversity.dph3a4.org.Model.BeritaItem;
import id.ac.telkomuniversity.dph3a4.org.R;

public class BeritaAdapter extends RecyclerView.Adapter<BeritaAdapter.MyViewHolder> {
    public static final String DATA_BERITA = "dataBerita";
    public static final String DATA_EXTRA = "dataExtra";
    private Context context;
    private List<BeritaItem> dataBerita = new ArrayList<>();

    public BeritaAdapter(Context context, List<BeritaItem> dataBerita) {
        this.context = context;
        this.dataBerita = dataBerita;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.layout_item_berita, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvJudulBerita.setText(dataBerita.get(position).getJudul());
        holder.tvIsiBerita.setText(dataBerita.get(position).getIsi());

        if (!dataBerita.get(position).getGambar().equals("")) {
            String img_url = RetrofitClient.IP_URL + "asset/images/" + dataBerita.get(position).getGambar();
            Glide.with(context).load(img_url).centerCrop().into(holder.ivFotoBerita);
        }
    }

    @Override
    public int getItemCount() {
        if (dataBerita.size() > 5) {
            return 2;
        } else {
            return dataBerita.size();
        }
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivFotoBerita;
        TextView tvJudulBerita, tvIsiBerita;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivFotoBerita = itemView.findViewById(R.id.ivFotoBerita);
            tvJudulBerita = itemView.findViewById(R.id.tvJudulBerita);
            tvIsiBerita = itemView.findViewById(R.id.tvIsiBerita);
        }
    }
}
