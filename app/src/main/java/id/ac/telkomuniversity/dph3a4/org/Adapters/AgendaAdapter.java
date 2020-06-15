package id.ac.telkomuniversity.dph3a4.org.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import id.ac.telkomuniversity.dph3a4.org.Model.AgendaOrganisasiItem;
import id.ac.telkomuniversity.dph3a4.org.R;

public class AgendaAdapter extends RecyclerView.Adapter<AgendaAdapter.MyViewHolder> {
    public static final String DATA_AGENDA = "dataAgenda";
    public static final String DATA_EXTRA = "dataExtra";
    private Context context;
    private List<AgendaOrganisasiItem> dataAgenda = new ArrayList<>();

    public AgendaAdapter(Context context, List<AgendaOrganisasiItem> dataAgenda) {
        this.context = context;
        this.dataAgenda = dataAgenda;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.layout_item_agenda, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd", new Locale("id", "ID"));
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(dataAgenda.get(position).getTanggal());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String tanggalAgenda = simpleDateFormat.format(date);
        holder.tvTgl.setText(tanggalAgenda);

        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("MMM", new Locale("id", "ID"));
        Date date2 = null;
        try {
            date2 = new SimpleDateFormat("yyyy-MM-dd").parse(dataAgenda.get(position).getTanggal());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String bulanAgenda = simpleDateFormat2.format(date2);
        holder.tvBln.setText(bulanAgenda.toUpperCase());

        holder.tvOrg.setText(dataAgenda.get(position).getNamaOrganisasi() + " - " + dataAgenda.get(position).getPerihal());
        holder.tvWaktu.setText(dataAgenda.get(position).getWaktu() + ", " + dataAgenda.get(position).getTempat());
        holder.tvKategori.setText(dataAgenda.get(position).getKategori());
    }

    @Override
    public int getItemCount() {
        return dataAgenda.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTgl, tvBln, tvOrg, tvKategori, tvWaktu;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTgl = itemView.findViewById(R.id.tglAgenda);
            tvBln = itemView.findViewById(R.id.blnAgenda);
            tvOrg = itemView.findViewById(R.id.orgAgenda);
            tvKategori = itemView.findViewById(R.id.kategoriAgenda);
            tvWaktu = itemView.findViewById(R.id.tempatAgenda);
        }
    }
}
