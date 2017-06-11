package fr.infotechnodev.termologia.quiz.activities.gamerules.onetry;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.games.Player;

import fr.infotechnodev.termologia.quiz.R;
import fr.infotechnodev.termologia.quiz.activities.FinishActivity;
import fr.infotechnodev.termologia.quiz.elements.GameRulesList;
import fr.infotechnodev.termologia.quiz.utils.SharedPrefUtils;
import fr.infotechnodev.termologia.quiz.utils.game.LeaderBoardUtils;

public class FinishOneTryActivity extends FinishActivity {

    private static final String TAG = FinishOneTryActivity.class.getName();

    public static final String KEY_QUESTION_POINTS_MAX = "fr.infotechnodev.termologia.termologia.activities.FinishOneTryActivity.KeyQuestionPointsMax";
    public static final String KEY_QUESTION_DETAILLED_POINTS = "fr.infotechnodev.termologia.termologia.activities.FinishOneTryActivity.KeyQuestionDetailledPoints";

    private FinishOneTryActivity mActivity = this;

    private int mMaxPoints;
    private int mDetailledPoints;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPrefUtils.setShowTutorial(this, false);
    }

    @Override
    protected void initView() {
        super.initView();

        mRestartButton.setVisibility(View.GONE);

        mMaxPoints = getIntent().getIntExtra(KEY_QUESTION_POINTS_MAX, -1);
        mDetailledPoints = getIntent().getIntExtra(KEY_QUESTION_DETAILLED_POINTS, -1);

        String textScore = getString(R.string.quiz_activity_finish_score2, mPoints, mMaxPoints);

        ((TextView) findViewById(R.id.activity_finish_textview_score)).setText(
                getResources().getQuantityString(R.plurals.quiz_activity_finish_totalpoints,
                        Math.max(mPoints, 1),
                        textScore));
    }

    @Override
    protected String getShareText() {
        return "" + mPoints + " / " + mMaxPoints;
    }

    public void onBackClick(View v) {
        GameRulesList.resetInstance();
        super.onBackClick(v);
    }

    protected void showConnexionSucceeded(Player currentPlayer) {
        LeaderBoardUtils.pushOneTryScore(this, mDetailledPoints);
    }
}
