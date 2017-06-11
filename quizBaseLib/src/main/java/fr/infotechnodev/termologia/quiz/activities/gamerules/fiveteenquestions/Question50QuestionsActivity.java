package fr.infotechnodev.termologia.quiz.activities.gamerules.fiveteenquestions;

import android.content.Intent;
import android.os.Bundle;

import fr.infotechnodev.termologia.quiz.R;
import fr.infotechnodev.termologia.quiz.activities.FinishActivity;
import fr.infotechnodev.termologia.quiz.activities.QuestionActivity;

public class Question50QuestionsActivity extends QuestionActivity {

    private static final String TAG = Question50QuestionsActivity.class.getName();

    private Question50QuestionsActivity mActivity = this;

    private int mTotalPoints = 0;

    private boolean mAchievement_forgotToPlay = true;
    private boolean mAchievement_allGood = true;
    private boolean mAchievement_masterLooser = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPointsTextView.setText(getResources().getQuantityString(R.plurals.quiz_activity_question_totalpoints, Math.max(mTotalPoints, 1), mTotalPoints));
    }

    protected Intent insertExtraToFinishIntent(Intent i) {
        i = super.insertExtraToFinishIntent(i);

        i.putExtra(Finish50QuestionsActivity.KEY_ACHIEVEMENT_10QUESTIONS_FORGOTTOPLAY, mAchievement_forgotToPlay);
        i.putExtra(Finish50QuestionsActivity.KEY_ACHIEVEMENT_10QUESTIONS_ALLGOOD, mAchievement_allGood);
        i.putExtra(Finish50QuestionsActivity.KEY_ACHIEVEMENT_10QUESTIONS_MASTERLOOSER, mAchievement_masterLooser);
        i.putExtra(FinishActivity.KEY_QUESTION_POINTS, mTotalPoints);
        return i;
    }

    void cancelAchievementForgotToPlay() {
        mAchievement_forgotToPlay = false;
    }

    void cancelAchievementAllGood() {
        mAchievement_allGood = false;
    }

    void cancelAchievementMasterLooser() {
        mAchievement_masterLooser = false;
    }

    public boolean needCountDown() {
        return true;
    }

    public void onMidTime(int color) {
        super.onMidTime(color);

        launchGrowingText(getString(R.string.quiz_activity_question_hurryup), color);
    }

    public void onEndTime(int questionId) {
        super.onEndTime(questionId);

        cancelAchievementAllGood();
        cancelAchievementMasterLooser();

    }

    public void onRightAnswer(int questionId, int timeLeft, int timeLeftInMs, int player) {
        super.onRightAnswer(questionId, timeLeft, timeLeftInMs, player);

        launchGrowingText("" + timeLeft, getResources().getColor(R.color.holo_green_light));

        cancelAchievementMasterLooser();
    }

    public void onWrongAnswer(int questionId, int timeLeft, int timeLeftInMs, int player) {
        super.onWrongAnswer(questionId, timeLeft, timeLeftInMs, player);

        cancelAchievementAllGood();
    }

    public void onNextClick() {
        super.onNextClick();

        cancelAchievementForgotToPlay();
    }

    public void onAddPoint(int point, boolean addPointToOtherPlayer) {
        super.onAddPoint(point, addPointToOtherPlayer);

        mTotalPoints += point;
        mPointsTextView.setText(getResources().getQuantityString(R.plurals.quiz_activity_question_totalpoints, Math.max(mTotalPoints, 1), mTotalPoints));
        launchBounceView();
    }

    public void onNewQuestion(int questionNumber) {
        super.onNewQuestion(questionNumber);

        mQuestionIndexTextView.setText(getString(R.string
                .quiz_activity_question_50questions_questionnumber, questionNumber));
    }

    @Override
    public int getTotalTime() {
        return getResources().getInteger(R.integer.quiz_config_50questions_timeforanswerinsecond);
    }

    @Override
    public void finishGame() {
        Intent i = new Intent(this, Finish50QuestionsActivity.class);
        i = insertExtraToFinishIntent(i);
        startActivity(i);
        finish();
    }
}
