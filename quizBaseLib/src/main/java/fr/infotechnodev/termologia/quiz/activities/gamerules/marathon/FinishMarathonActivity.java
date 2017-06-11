package fr.infotechnodev.termologia.quiz.activities.gamerules.marathon;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import fr.infotechnodev.termologia.quiz.R;
import fr.infotechnodev.termologia.quiz.activities.FinishActivity;
import fr.infotechnodev.termologia.quiz.activities.QuestionActivity;
import fr.infotechnodev.termologia.quiz.elements.QuestionList;

public class FinishMarathonActivity extends FinishActivity {

    private static final String TAG = FinishMarathonActivity.class.getName();

    private FinishMarathonActivity mActivity = this;

    @Override
    protected void initView() {
        super.initView();
        String textScore = getString(R.string.quiz_activity_finish_score1, mPoints);
        ((TextView) findViewById(R.id.activity_finish_textview_score)).setText(
                getResources().getQuantityString(R.plurals.quiz_activity_finish_totalpoints,
                        Math.max(mPoints, 1),
                        textScore));
    }

    @Override
    public void onRestartClick(View v) {
        Intent i = new Intent(this, QuestionMarathonActivity.class);
        i.putExtra(QuestionActivity.KEY_QUESTION_LIST, (ArrayList<QuestionList.Question>) QuestionList.getInstance(this).getNextMarathon());
        i.putExtra(QuestionActivity.KEY_GAMERULES, mGameRules);
        startActivity(i);
        finish();
    }

    @Override
    protected String getShareText() {
        return "" + mPoints;
    }
}