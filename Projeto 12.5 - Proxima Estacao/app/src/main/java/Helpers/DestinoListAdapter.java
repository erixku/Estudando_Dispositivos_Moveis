package Helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pequitro.R;

import java.util.List;

import Models.PercursoModel;

public class DestinoListAdapter extends ArrayAdapter<PercursoModel> {
    private Context mContext;
    private int mResource;

    public DestinoListAdapter(@NonNull Context context, int resource, @NonNull List<PercursoModel> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // 1. Obter o objeto de dados para esta posição
        PercursoModel percurso = getItem(position);

        // 2. Se a view não estiver sendo reutilizada, inflá-la
        // Isso é uma otimização para reutilizar views e economizar recursos
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
        }

        // 3. Obter referências aos TextViews no layout do item
        TextView textViewDestino = convertView.findViewById(R.id.textViewDestino);
        TextView textViewContagemDestino = convertView.findViewById(R.id.textViewContagemDestino);

        // 4. Preencher os TextViews com os dados do objeto PercursoModel
        if (percurso != null) {
            textViewDestino.setText("Destino: " + percurso.getDestino());
            textViewContagemDestino.setText("Contagem: " + percurso.getContagem());
        }

        // 5. Retornar a view preenchida para exibição
        return convertView;
    }
}
