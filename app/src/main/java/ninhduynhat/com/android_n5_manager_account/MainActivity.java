package ninhduynhat.com.android_n5_manager_account;

import android.os.Bundle;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import ninhduynhat.com.android_n5_manager_account.View.Cong_No_Fragment;
import ninhduynhat.com.android_n5_manager_account.View.HomeFragment;
import ninhduynhat.com.android_n5_manager_account.View.Plan_Fragment;
import ninhduynhat.com.android_n5_manager_account.View.RegisterFragment;
import ninhduynhat.com.android_n5_manager_account.View.Statistical_Fragment;

public class MainActivity extends AppCompatActivity {

    protected long thoatungdung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Ánh xạ BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);

        // Thiết lập sự kiện khi chọn các mục điều hướng
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int enterAnimation = R.anim.enter_from_right;
            int exitAnimation = R.anim.exit_to_left;

            if (item.getItemId() == R.id.navigation_home) {
                selectedFragment = new HomeFragment();
            } else if (item.getItemId() == R.id.navigation_statistical) {
                selectedFragment = new Statistical_Fragment();
            } else if (item.getItemId() == R.id.navigation_plan) {
                selectedFragment = new Plan_Fragment();
            } else if (item.getItemId() == R.id.navigation_register) {
                selectedFragment = new RegisterFragment();
                enterAnimation = R.anim.enter_from_left;
                exitAnimation = R.anim.exit_to_right;
            } else if (item.getItemId() == R.id.navigation_cong_no) {
                selectedFragment = new Cong_No_Fragment();
            }

            if (selectedFragment != null) {
                openFragment(selectedFragment, enterAnimation, exitAnimation);
            }
            return true;
        });

        // Chọn mục mặc định khi khởi động
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);
    }

    @Override
    public void onBackPressed() {
        if (thoatungdung + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            return;
        } else {
            Toast.makeText(this, "Nhấn lần nữa để thoát ứng dụng", Toast.LENGTH_SHORT).show();
        }
        thoatungdung = System.currentTimeMillis();
    }

    private void openFragment(Fragment fragment, int enterAnimation, int exitAnimation) {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(enterAnimation, exitAnimation)
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}
