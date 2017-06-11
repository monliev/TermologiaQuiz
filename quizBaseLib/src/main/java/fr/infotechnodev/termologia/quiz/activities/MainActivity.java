package fr.infotechnodev.termologia.quiz.activities;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import fr.infotechnodev.termologia.quiz.R;
import fr.infotechnodev.termologia.quiz.activities.main.MainFragment;
import fr.infotechnodev.termologia.quiz.activities.main.SettingsFragment;
import fr.infotechnodev.termologia.quiz.backoffice.pojo.Player;
import fr.infotechnodev.termologia.quiz.utils.game.AchievementUtils;
import fr.infotechnodev.termologia.quiz.utils.game.BaseActivity;
import fr.infotechnodev.termologia.quiz.views.OnClickPreference.OnClickPreferenceListener;

public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getName();

    public final int REQUEST_ACHIEVEMENTS = 42;
    public final int REQUEST_LEADERBOARD = 43;

    private MainActivity mActivity = this;

    private ViewPager mViewPager;

    private MainFragment mMainFragment;
    private SettingsFragment mSettingsFragment;

    private Player mPlayer;

    private AdView mAdView;
    private InterstitialAd interstitial;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        mViewPager = (ViewPager) getLayoutInflater().inflate(R.layout.activity_main, null);

        initSettings();
        initMain();

        //mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        //mAdView.loadAd(adRequest);

        interstitial=new InterstitialAd(this);
        interstitial.setAdUnitId("ca-app-pub-2953879827169015/6896749889"); // Interstitial quiz
        //interstitial.setAdUnitId("ca-app-pub-3940256099942544/1033173712"); // test AdUnit
        interstitial.loadAd(adRequest);
        //displayMyAd();

        mViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return mSettingsFragment;
                    case 1:
                        return mMainFragment;
                    default:
                        return null;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        });

        setContentView(mViewPager);
        mViewPager.setCurrentItem(1, false);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mMainFragment.onMainViewPagerScrolled(position, positionOffsetPixels);
            }
        });
    }

    public void displayMyAd() {
        if (interstitial.isLoaded()) {
            interstitial.show();
        }
    }

    private void initMain() {
        mMainFragment = (MainFragment) Fragment.instantiate(this, MainFragment.class.getName());
    }

    private void initSettings() {
        mSettingsFragment = (SettingsFragment) Fragment.instantiate(this, SettingsFragment.class.getName());
    }

    @Override
    protected void onResume() {
        super.onResume();

        mSettingsFragment.checkPrefs();
    }

    @Override
    public void onBackPressed() {
        if (mViewPager.getCurrentItem() != 1) {
            mViewPager.setCurrentItem(1, true);
        } else {
            super.onBackPressed();
        }
    }

    public void onSettingsClick(View v) {
        mViewPager.setCurrentItem(0, true);
    }

    public void onStartClick(View v) {
        mMainFragment.onStartClick(v);
        displayMyAd();
    }

    public Player getPlayer() {
        return mPlayer;
    }

    public SettingsFragment getSettingsFragment() {
        return mSettingsFragment;
    }

    /**
     * Called to notify us that sign in failed. Notice that a failure in sign in is not necessarily due to an error; it might be that the user never signed in,
     * so our attempt to automatically sign in fails because the user has not gone through the authorization flow. So our reaction to sign in failure is to show
     * the sign in button. When the user clicks that button, the sign in process will start/resume.
     */
    @Override
    protected void showConnexionFailed() {
        // Sign-in has failed. So show the user the sign-in button so they can click the "Sign-in" button.
        mMainFragment.showSignInBar();
        mSettingsFragment.enablePref(getString(R.string.quiz_settings_selectable_signout_key), false);
    }

    /**
     * Called to notify us that sign in succeeded. We react by loading the loot from the cloud and updating the UI to show a sign-out button.
     * @param currentPlayer
     */
    @Override
    public void showConnexionSucceeded(com.google.android.gms.games.Player currentPlayer) {
        // Sign-in worked!
        mMainFragment.showSignOutBar();
        AchievementUtils.pushAchievementSignIn(this);

        mPlayer = new Player(currentPlayer);
        mSettingsFragment.enablePref(getString(R.string.quiz_settings_selectable_signout_key), true);


        mSettingsFragment.setSignoutListener(new OnClickPreferenceListener() {

            @Override
            public void onClick() {
                signOutButtonClicked();
            }

        });
    }

    public void bounceStartButton(Boolean bounce) {
        mMainFragment.bounceStartButton(bounce);
    }


}
