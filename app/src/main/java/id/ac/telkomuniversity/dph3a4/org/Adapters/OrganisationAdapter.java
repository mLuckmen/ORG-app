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

import java.util.ArrayList;
import java.util.List;

import id.ac.telkomuniversity.dph3a4.org.Model.DataItem;
import id.ac.telkomuniversity.dph3a4.org.Model.DataItem;
import id.ac.telkomuniversity.dph3a4.org.R;

public class OrganisationAdapter extends RecyclerView.Adapter<OrganisationAdapter.MyViewHolder> {
    private Context context;
    private List<DataItem> dataOrganisasi = new ArrayList<>();

    // Constructor

    public OrganisationAdapter(Context context, List<DataItem> dataOrganisasi) {
        this.context = context;
        this.dataOrganisasi = dataOrganisasi;
    }

    // 1 Menyambungkan layout item
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.layout_item_organisasi, viewGroup,false);
        return new MyViewHolder(itemView);
    }

    // 3 set data
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvNamaOrganisasi.setText(dataOrganisasi.get(position).getNamaOrganisasi());
        holder.tvDeskripsi.setText(dataOrganisasi.get(position).getDeskripsi());
        holder.tvKetua.setText(dataOrganisasi.get(position).getKetua());
        Glide.with(context).load("http://10.0.2.2/pa/asset/images/ormawa/" + dataOrganisasi.get(position).getLogo()).into(holder.ivLogo);
    }

    // jumlah data
    @Override
    public int getItemCount() {
        return dataOrganisasi.size();
    }

    // 2 mengenalkan komponen dalam item
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvNamaOrganisasi, tvDeskripsi, tvKetua;
        ImageView ivLogo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNamaOrganisasi = itemView.findViewById(R.id.tvNamaOrganisasi);
            tvDeskripsi = itemView.findViewById(R.id.tvDescOrg);
            tvKetua = itemView.findViewById(R.id.tvNamaKetua);
            ivLogo = itemView.findViewById(R.id.logoOrganisasi);

        }
    }

    // extend kelas ini

}
