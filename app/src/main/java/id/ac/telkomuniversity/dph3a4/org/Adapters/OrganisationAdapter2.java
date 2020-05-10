package id.ac.telkomuniversity.dph3a4.org.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import id.ac.telkomuniversity.dph3a4.org.Activities.OrganisationActivity;
import id.ac.telkomuniversity.dph3a4.org.Model.OrganisationItem;
import id.ac.telkomuniversity.dph3a4.org.R;

public class OrganisationAdapter2 extends RecyclerView.Adapter<OrganisationAdapter2.MyViewHolder> {
    private static final String DATA_ORGANISASI = "dataOrganisasi";
    public static final String DATA_EXTRA = "dataExtra";
    private Context context;
    private List<OrganisationItem> dataOrganisasi = new ArrayList<>();

    // Constructor

    public OrganisationAdapter2(Context context, List<OrganisationItem> dataOrganisasi) {
        this.context = context;
        this.dataOrganisasi = dataOrganisasi;
    }

    // 1 Menyambungkan layout item
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.layout_item_organisasi_2, viewGroup,false);
        return new MyViewHolder(itemView);
    }

    // 3 set data
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvNamaOrganisasi.setText(dataOrganisasi.get(position).getNamaOrganisasi());
        holder.tvDeskripsi.setText(dataOrganisasi.get(position).getDeskripsi());
        holder.tvKetua.setText(dataOrganisasi.get(position).getKetua());

        String img_url = "http://10.0.2.2/pa/asset/images/ormawa/" + dataOrganisasi.get(position).getLogo();
//        String img_url = "http://192.168.1.11/pa/asset/images/ormawa/" + dataOrganisasi.get(position).getLogo();
        Glide.with(context).load(img_url).into(holder.ivLogo);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pindah = new Intent(context, OrganisationActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(DATA_ORGANISASI, Parcels.wrap(dataOrganisasi.get(position)));
                pindah.putExtra(DATA_EXTRA, bundle);
                context.startActivity(pindah);
            }
        });
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
