package ninhduynhat.com.haui_android_n16_manager_account.Model;

import ninhduynhat.com.haui_android_n16_manager_account.R;

public enum LoaiChiPhi {
    FOOD_BEVERAGE("Thực phẩm & Đồ uống", R.drawable.icon_doan_douong),
    HEALTH("Sức khỏe", R.drawable.icon_suckhoe),
    HOUSING("Chi phí nhà ở", R.drawable.icon_tiennhao),
    INVESMENT("Đầu tư", R.drawable.icon_dautu_spiner),
    TRANSPORT("Chi phí đi lại", R.drawable.icon_chi_phi_di_lai),
    TRAVEL("Du lịch", R.drawable.icon_diz_du_lich),
    UNCATEGORIZED("Chi phí khác", R.drawable.icon_question);
    public final String tenchiphi;
    public final int drawable;
    LoaiChiPhi(String tenchiphi, int drawable) {
        this.tenchiphi = tenchiphi;
        this.drawable = drawable;
    }
    public String getTenchiphi(){
        return tenchiphi;
    }

}
