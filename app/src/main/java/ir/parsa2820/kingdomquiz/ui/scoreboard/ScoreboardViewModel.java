package ir.parsa2820.kingdomquiz.ui.scoreboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ScoreboardViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ScoreboardViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}