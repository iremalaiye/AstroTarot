package com.example.astrotarot;
//sevinckocak
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.astrotarot.horoscopeFragment.AquariusFragment;
import com.example.astrotarot.horoscopeFragment.AriesFragment;
import com.example.astrotarot.horoscopeFragment.CancerFragment;
import com.example.astrotarot.horoscopeFragment.CapricornFragment;
import com.example.astrotarot.horoscopeFragment.GeminiFragment;
import com.example.astrotarot.horoscopeFragment.LeoFragment;
import com.example.astrotarot.horoscopeFragment.LibraFragment;
import com.example.astrotarot.horoscopeFragment.PiscesFragment;
import com.example.astrotarot.horoscopeFragment.SagittairusFragment;
import com.example.astrotarot.horoscopeFragment.ScorpioFragment;
import com.example.astrotarot.horoscopeFragment.TaurusFragment;
import com.example.astrotarot.horoscopeFragment.VirgoFragment;
import com.example.astrotarot.zodiacFragment.VPAdapter;
import com.google.android.material.tabs.TabLayout;

public class ZodiacSection extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_zodiac_section);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewpager);
        backButton = findViewById(R.id.backButton);

        tabLayout.setupWithViewPager(viewPager);

        VPAdapter vpAdapter = new VPAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpAdapter.addFragment(new AriesFragment(),"ARIES");
        vpAdapter.addFragment(new LeoFragment(),"LEO");
        vpAdapter.addFragment(new SagittairusFragment(),"SAGITTARIUS");
        vpAdapter.addFragment(new TaurusFragment(),"TAURUS");
        vpAdapter.addFragment(new VirgoFragment(),"VIRGO");
        vpAdapter.addFragment(new CapricornFragment(),"CAPRICORN");
        vpAdapter.addFragment(new GeminiFragment(),"GEMINI");
        vpAdapter.addFragment(new LibraFragment(),"LIBRA");
        vpAdapter.addFragment(new AquariusFragment(),"AQUARIUS");
        vpAdapter.addFragment(new CancerFragment(),"CANCER");
        vpAdapter.addFragment(new ScorpioFragment(),"SCORPIO");
        vpAdapter.addFragment(new PiscesFragment(),"PISCES");

        viewPager.setAdapter(vpAdapter);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(ZodiacSection.this,FortuneSection.class);
                startActivity(in);
                finish();
            }
        });

    }

}