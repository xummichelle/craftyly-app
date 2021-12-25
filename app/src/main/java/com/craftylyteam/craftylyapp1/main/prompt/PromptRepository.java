package com.craftylyteam.craftylyapp1.main.prompt;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.craftylyteam.craftylyapp1.utils.Constants;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PromptRepository {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference filtersRef = db.collection(Constants.FILTERS);

    private DocumentReference docFilterRef;


    MutableLiveData<List<String>> getPromptListMutableLiveData(List<String> wantedFiltersList) {
        MutableLiveData<List<String>> promptListMutableLiveData = new MutableLiveData<>();
//        check the length of the filtersarray
        int filtersArrayLength = wantedFiltersList.size();
        List<String> promptsList = new ArrayList<>();
        List<Task> taskList = new ArrayList<>();
        Task filterTask;

//        for how many filters the user selected, get the corresponding document to that filter.
//        this document contains an array of all of the prompts related to that filter.
        for (int i = 0; i < filtersArrayLength; i++) {
            docFilterRef = filtersRef.document(wantedFiltersList.get(i));
            Log.d("sharedPrefPromptRepository", "wantedfilterslist.get : " + wantedFiltersList.get(i));
//            add task of getting firestore document to the list of tasks
            filterTask = docFilterRef.get();
            taskList.add(filterTask);
        }

        Task[] taskArray = taskList.stream().toArray(Task[]::new);
        Log.d("sharedPrefPromptRepository", "taskArraylength : " + String.valueOf(taskArray.length));

//        on success listener on all tasks in the list of tasks. this is done to combine the prompt arrays
//        from multiple documents into one big array
        Task combinedTask = Tasks.whenAllSuccess(taskArray).addOnSuccessListener(new OnSuccessListener<List<Object>>() {
            @Override
            public void onSuccess(List<Object> objects) {
                for (Object document: objects) {
                    DocumentSnapshot docRef = (DocumentSnapshot) document;
                    promptsList.addAll((List<String>)docRef.get("prompts"));
                }
                Log.d("sharedPrefpromptlist", String.valueOf(promptsList.size()));
                promptListMutableLiveData.setValue(promptsList);

            }
        });
        return promptListMutableLiveData;
    }
}
