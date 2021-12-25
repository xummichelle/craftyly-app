package com.craftylyteam.craftylyapp1.main.prompt;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.Set;

public class PromptViewModel extends ViewModel {
    private PromptRepository promptRepository;

    public PromptViewModel() {
        promptRepository = new PromptRepository();
    }

    LiveData<List<String>> getPromptListLiveData(List<String> wantedFiltersList) {

        return promptRepository.getPromptListMutableLiveData(wantedFiltersList);
    }
}
