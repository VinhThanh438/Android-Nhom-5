package ninhduynhat.com.haui_android_n16_manager_account.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ninhduynhat.com.haui_android_n16_manager_account.Model.LoaiChiPhi;
import ninhduynhat.com.haui_android_n16_manager_account.R;

public class ChiPhiSpiner_Adapter extends ArrayAdapter<LoaiChiPhi> {

    public ChiPhiSpiner_Adapter(@NonNull Context context, LoaiChiPhi loaiChiPhi[]) {
        super(context, 0,loaiChiPhi);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable
    View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }
    @Override
    public View getDropDownView(int position, @Nullable
    View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }
    private View initView(int position, View convertView,
                          ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_loai_chi_phi_spinner, parent, false);
        }
        ImageView imageView = convertView.findViewById(R.id.image_view_spiner);
        TextView textViewName = convertView.findViewById(R.id.text_view_spiner);
        LoaiChiPhi currentItem = getItem(position);

        if (currentItem != null) {
            imageView.setImageResource(currentItem.drawable);
            textViewName.setText(currentItem.tenchiphi);
        }
        return convertView;
    }
}
