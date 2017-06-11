package fr.infotechnodev.termologia.quiz.activities.gamerules.fiveteenquestions;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.games.Player;

import java.util.ArrayList;

import fr.infotechnodev.termologia.quiz.R;
import fr.infotechnodev.termologia.quiz.activities.FinishActivity;
import fr.infotechnodev.termologia.quiz.activities.QuestionActivity;
import fr.infotechnodev.termologia.quiz.elements.QuestionList;
import fr.infotechnodev.termologia.quiz.utils.game.AchievementUtils;
import fr.infotechnodev.termologia.quiz.utils.game.LeaderBoardUtils;

public class Finish50QuestionsActivity extends FinishActivity {

    private static final String TAG = Finish50QuestionsActivity.class.getName();

    public static final String KEY_ACHIEVEMENT_10QUESTIONS_FORGOTTOPLAY = "fr.infotechnodev" +
            ".termologia.termologia.activities.FinishActivity.KeyAchievement50QuestionsForgottoplay";
    public static final String KEY_ACHIEVEMENT_10QUESTIONS_ALLGOOD = "fr.infotechnodev.termologia" +
            ".termologia.activities.FinishActivity.KeyAchievement50QuestionsAllgood";
    public static final String KEY_ACHIEVEMENT_10QUESTIONS_MASTERLOOSER = "fr.infotechnodev" +
            ".termologia.termologia.activities.FinishActivity.KeyAchievement50QuestionsMasterlooser";

    private Finish50QuestionsActivity mActivity = this;

    @Override
    protected void initView() {
        super.initView();

        String textScore = getString(R.string.quiz_activity_finish_score2, mPoints, (50 * Integer
                .valueOf(mGameRules.getValue1())));

        ((TextView) findViewById(R.id.activity_finish_textview_score)).setText(
                getResources().getQuantityString(R.plurals.quiz_activity_finish_totalpoints,
                        Math.max(mPoints, 1),
                        textScore));
    }

    @Override
    public void onRestartClick(View v) {
        Intent i = new Intent(this, Question50QuestionsActivity.class);
        i.putExtra(QuestionActivity.KEY_QUESTION_LIST, (ArrayList<QuestionList.Question>)
                QuestionList.getInstance(this).getNext50Questions());
        i.putExtra(QuestionActivity.KEY_GAMERULES, mGameRules);
        startActivity(i);
        finish();
    }

    @Override
    protected String getShareText() {
        return "" + mPoints + " / " + (50 * Integer.valueOf(mGameRules.getValue1()));
    }

    protected void showConnexionSucceeded(Player currentPlayer) {
        LeaderBoardUtils.push10QuestionsScore(this, mPoints);
        if (mPoints >= 250) {
            AchievementUtils.pushAchievement10QuestionsGreatPlayer(this);
        }
        if (getIntent().getBooleanExtra(KEY_ACHIEVEMENT_10QUESTIONS_FORGOTTOPLAY, false)) {
            AchievementUtils.pushAchievement10QuestionsForgotToPlay(this);
        }
        if (getIntent().getBooleanExtra(KEY_ACHIEVEMENT_10QUESTIONS_ALLGOOD, false)) {
            AchievementUtils.pushAchievement10QuestionsAllGood(this);
        }
        if (getIntent().getBooleanExtra(KEY_ACHIEVEMENT_10QUESTIONS_MASTERLOOSER, false)) {
            AchievementUtils.pushAchievement10QuestionsMasterLooser(this);
        }
    }
}