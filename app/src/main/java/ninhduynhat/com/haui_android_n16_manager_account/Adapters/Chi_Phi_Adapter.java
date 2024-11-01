package ninhduynhat.com.haui_android_n16_manager_account.Adapters;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ninhduynhat.com.haui_android_n16_manager_account.Database.DatabaseHelper;
import ninhduynhat.com.haui_android_n16_manager_account.Model.ExpensesObject;
import ninhduynhat.com.haui_android_n16_manager_account.Model.LoaiChiPhi;
import ninhduynhat.com.haui_android_n16_manager_account.R;
import ninhduynhat.com.haui_android_n16_manager_account.View.sua_chi_phi;

public class Chi_Phi_Adapter extends RecyclerView.Adapter<Chi_Phi_Adapter.chi_PhiViewHolder>{
//    private Context mContext;
    private List<ExpensesObject> mListKhoanChi;
    private Chi_Phi_Adapter chiPhiAdapter;
    private LoaiChiPhi loaiChiPhi_1[];
    private Context mContext;
    private DatabaseHelper databaseHelper;

    public void setData(Context mContext,List<ExpensesObject> mListKhoanChi){
        this.mListKhoanChi=mListKhoanChi;
        this.mContext=mContext;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public chi_PhiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chi_phi,parent,false);
        return new chi_PhiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull chi_PhiViewHolder holder, int position) {
        ExpensesObject chiphi =mListKhoanChi.get(position);
        if(chiphi==null){
            return;
        }
        NumberFormat numberFormat = NumberFormat.getInstance();
        String formattedNumber = numberFormat.format(chiphi.getAmountSpent());
        //đẩy lên view
        holder.loai_chi_phi.setText(chiphi.getExpensesType());
        holder.mo_ta_chi_phi.setText(chiphi.getDescription());
        holder.gia_chi_phi.setText(formattedNumber+" VND");

        int vitri=position;

        holder.layout_item_chiPhi.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupMenu popupMenu= new PopupMenu(mContext,v, Gravity.END);
                popupMenu.getMenuInflater().inflate(R.menu.context_menu_chi_phi,popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getItemId()==R.id.sua_chiPhi) {
//                            openFeedbackDialog_suachiphi_1(Gravity.CENTER,chiphi.getExpensesID());
                            Intent intent= new Intent(mContext, sua_chi_phi.class);
                            intent.putExtra("UserId",chiphi.getExpensesID());
                            mContext.startActivity(intent);
                        }
//                        } else if (item.getItemId()==R.id.xoa_chi_phi) {
//                            openFeedbackDialog_xoaKhoanChi(Gravity.CENTER,vitri);
//
//                        }
                        return false;
                    }
                });
                return false;
            }
        });

    }




    private void openFeedbackDialog_xoaKhoanChi(int gravity,int vitrixoa){
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_xoakhoanchi);
        Window window = dialog.getWindow();
        if(window==null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAtrubus= window.getAttributes();
        windowAtrubus.gravity=gravity;
        window.setAttributes(windowAtrubus);

        TextView tenKhoanChiMuonXoa;
        Button thoatDialog4,nhanXoaKhoanChi;
        thoatDialog4=dialog.findViewById(R.id.thoatDialog4);
        nhanXoaKhoanChi=dialog.findViewById(R.id.nhanXoaKhoanChi);
        tenKhoanChiMuonXoa=dialog.findViewById(R.id.tenKhoanChiMuonXoa);


//        KhoanChi khoanChi = mListKhoanChi.get(vitrixoa);
//        tenKhoanChiMuonXoa.setText(khoanChi.getKhoanchi());


        thoatDialog4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        nhanXoaKhoanChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItem(vitrixoa);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void deleteItem(int posisions){
        mListKhoanChi.remove(posisions);
        notifyItemRemoved(posisions);
        notifyItemRangeChanged(posisions,mListKhoanChi.size());
    }

    @Override
    public int getItemCount() {
        if(mListKhoanChi !=null){
            return mListKhoanChi.size();
        }
        return 0;
    }

    public class chi_PhiViewHolder extends RecyclerView.ViewHolder{
        private CardView layout_item_chiPhi;
        private TextView loai_chi_phi,mo_ta_chi_phi,gia_chi_phi;

        public chi_PhiViewHolder(@NonNull View itemView) {
            super(itemView);
            loai_chi_phi=itemView.findViewById(R.id.loai_chi_phi);
            mo_ta_chi_phi=itemView.findViewById(R.id.mo_ta_chi_phi);
            gia_chi_phi=itemView.findViewById(R.id.gia_chi_phi);
            layout_item_chiPhi=itemView.findViewById(R.id.layout_item_chiPhi);
        }
    }

}
