package com.craftylyteam.craftylyapp1.main.prompt;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.craftylyteam.craftylyapp1.R;
import com.craftylyteam.craftylyapp1.main.prompt.settings.SettingsActivity;
import com.craftylyteam.craftylyapp1.utils.Constants;
import com.craftylyteam.craftylyapp1.utils.themeUtils;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

import smartdevelop.ir.eram.showcaseviewlib.GuideView;
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType;
import smartdevelop.ir.eram.showcaseviewlib.config.Gravity;


public class PromptFragment extends Fragment implements CardStackListener {

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private GoogleSignInClient googleSignInClient;
//    reference to firestore database and other firestore references
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference usersRef = db.collection(Constants.USERS);
    private CollectionReference filtersRef = db.collection(Constants.FILTERS);
    private DocumentReference currentUserRef;
    private CollectionReference historyRef;
    private FirebaseUser firebaseUser;

    private View view;
    private ImageButton settingsImageButton;
    private ImageButton goToFilterImageButton;
    private Dialog lightbulbReminderDialog;

//    prompt card stack view things
    private CardStackView promptCardStackView;
    private List<String> promptsList;
    private PromptCardAdapter promptCardAdapter;
    private CardStackLayoutManager promptCardLayoutManager;
    private PromptViewModel promptViewModel;
    private int lastPrompt;

//    history recycler view things
    private RecyclerView historyRecyclerView;
    private HistoryAdapter historyAdapter;
    private LinearLayoutManager historyLayoutManager;
    Timestamp currentTimestamp;

//    shared preferences
    private SharedPreferences sharedPreferences;
    private Set<String> filtersSet;
    private String[] filtersArray;
    private List<String> wantedFiltersList = new ArrayList<>();


    public PromptFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_prompt, container, false);

        wantedFiltersList.clear();
//        get shared preferences
        sharedPreferences = getActivity().getSharedPreferences(Constants.SHARED_PREFS, MODE_PRIVATE);
        filtersSet = new HashSet<String>(sharedPreferences.getStringSet(Constants.FILTERS_UNCHECKED, new HashSet<String>()));
//        unwanted filters set is cast to array
        filtersArray = filtersSet.stream().toArray(String[]::new);
        Log.d("sharedPrefPromptFrag", String.valueOf(filtersArray) + String.valueOf(filtersSet));
//        create the list of filters we want using the list of filters we don't want
        for (String s: Constants.ALL_FILTERS_ARRAY) {
            wantedFiltersList.add(s);
        }
        if (filtersArray != null){
            if (filtersArray.length != 0) {
                for (String s: filtersArray) {
                    wantedFiltersList.remove(s);
                }
            }
        }

        checkIfFirstLaunch();

//        get current user
        firebaseUser = firebaseAuth.getCurrentUser();
//        reference to current user document
        currentUserRef = usersRef.document(firebaseUser.getUid());
//        reference to history subcollection of user
        historyRef = currentUserRef.collection(Constants.HISTORY);

        //        settings button
        settingsImageButton = (ImageButton) view.findViewById(R.id.settings_image_button);
        settingsImageButton.setOnClickListener(v -> openSettingActivity());
        goToFilterImageButton = (ImageButton) view.findViewById(R.id.go_to_filter_image_button);
        goToFilterImageButton.setOnClickListener(v -> openFiltersFragment());


        promptsList = new ArrayList<>();
        promptCardStackView = (CardStackView) view.findViewById(R.id.prompt_card_stack_view);

        themeUtils.onPromptFragmentCreateSetTheme(getActivity(), view);

        initPromptListViewModel();
        setUpHistoryRecyclerView();
        setUpPromptCardStackView();
        initGoogleSignInClient();


        return view;

    }

    private void checkIfFirstLaunch() {
        if (sharedPreferences.getBoolean(Constants.FIRST_RUN, true)) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(Constants.FIRST_RUN, false);
            editor.apply();
            Log.d("firstrun", "should hopefully be false now?");

            showIntroTutorial("Welcome!", "Click anywhere to proceed\nwith a quick tour", R.id.welcome_header_image_view, 1);
        } else{
//            only if it is NOT the first launch, then set up the bulb reminder. this is to avoid
//              the tutorial and the reminder views overlapping and being weird
            setUpBulbReminder();
        }

    }

    private void finishPromptIntroTutorial(String title, String text, int viewId){
        new GuideView.Builder(getActivity())
                .setTitle(title)
                .setContentText(text)
                .setGravity(Gravity.auto) //optional
                .setDismissType(DismissType.anywhere) //optional - default DismissType.targetView
//                difference between this method and showIntroTutorial() is that this one accesses a view from main activity
                .setTargetView(getActivity().findViewById(viewId))
                .setContentTextSize(12)//optional
                .setTitleTextSize(14)//optional
                .setGuideListener(view ->
                    showIntroTutorial("That's all!\nWe hope you enjoy using Craftyly!",
                            "(click anywhere to finish)",
                            R.id.welcome_header_image_view, 7))

                .build()
                .show();
    }

    private void showIntroTutorial(String title, String text, int viewId, final int type) {
        new GuideView.Builder(getActivity())
                .setTitle(title)
                .setContentText(text)
                .setGravity(Gravity.auto) //optional
                .setDismissType(DismissType.anywhere) //optional - default DismissType.targetView
                .setTargetView(view.findViewById(viewId))
                .setContentTextSize(12)//optional
                .setTitleTextSize(14)//optional
                .setGuideListener(view -> {
                    switch(type) {
                        case 1:
                            showIntroTutorial("Swipe the prompt towards \nthe green arrow\nto accept the prompt",
                                    "You'll be able to edit this choice\nlater in the notes section",
                                    R.id.accept_arrow_image_view, 2);
                            break;
                        case 2:
                            showIntroTutorial("Swipe the prompt towards\nthe red arrow\nto reject the prompt",
                                    "You'll be able to edit this choice\nlater in the notes section as well",
                                    R.id.reject_arrow_image_view, 3);
                            break;
                        case 3:
                            showIntroTutorial("A history of your accepted/rejected\nprompts will be shown here",
                                    "",
                                    R.id.history_recycler_view, 4);
                            break;
                        case 4:
                            showIntroTutorial("You can filter your\nprompts here",
                                    "Customize the prompts to your interest",
                                    R.id.go_to_filter_image_button, 5);
                            break;
                        case 5:
                            showIntroTutorial("You can access your\nsettings here",
                                    "Edit information regards\nto your account here",
                                    R.id.settings_image_button, 6);
                            break;
                        case 6:
                            finishPromptIntroTutorial("You can access the\nnotes page here",
                                    "You can swap back to the\nprompt generator at any time as well",
                                    R.id.bottom_app_bar);

                            break;
                        default:
                            break;
                    }
                })

                .build()
                .show();
    }

    private void setUpBulbReminder() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String currentDate = sdf.format(new Date());
        if (sharedPreferences.getString(Constants.LAST_LAUNCH_DATE, "nodate").contains(currentDate)){
//            date matches. user has already launched the app once today, so do nothing
        } else{
//            show dialog
            openLightbulbMessageDialog();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(Constants.LAST_LAUNCH_DATE, currentDate);
            editor.apply();
        }
    }

    private void openLightbulbMessageDialog(){
        lightbulbReminderDialog = new Dialog(getActivity());
        lightbulbReminderDialog.setContentView(R.layout.lightbulb_reminder_layout_dialog);
        lightbulbReminderDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        TODO: change the remind message dialog's message to a different one (cycle through a list)
        TextView reminderMsgTextView = lightbulbReminderDialog.findViewById(R.id.reminder_msg_text_view);
        reminderMsgTextView.setText("Creating masterpieces takes time. Don't stress, take a deep breath, and remember to stay hydrated :)");
        ImageButton imageButtonCloseReminderDialog = lightbulbReminderDialog.findViewById(R.id.image_button_close_reminder_dialog);
        imageButtonCloseReminderDialog.setOnClickListener(v -> lightbulbReminderDialog.dismiss());
        lightbulbReminderDialog.show();
    }

    private void openFiltersFragment() {
        FiltersFragment filtersFragment= new FiltersFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(((ViewGroup)getView().getParent()).getId(), filtersFragment, "")
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit();
    }

    private void setUpHistoryRecyclerView() {
        Query query = historyRef.orderBy(Constants.HISTORY_TIMESTAMP, Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<Prompt> historyOptions = new FirestoreRecyclerOptions.Builder<Prompt>()
                .setQuery(query, Prompt.class)
                .build();

        historyRecyclerView = view.findViewById(R.id.history_recycler_view);
        historyAdapter = new HistoryAdapter(historyOptions, historyRecyclerView);

        historyLayoutManager = new LinearLayoutManager(getActivity());
        historyRecyclerView.setLayoutManager(historyLayoutManager);
        historyRecyclerView.setAdapter(historyAdapter);
        historyRecyclerView.setHasFixedSize(true);
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(historyRecyclerView.getContext(),
                historyLayoutManager.getOrientation());
        Drawable d = getResources().getDrawable(R.drawable.historydivider, getContext().getTheme());
        mDividerItemDecoration.setDrawable(d);
        historyRecyclerView.addItemDecoration(mDividerItemDecoration);
    }

    @Override
    public void onStart() {
        super.onStart();
        historyAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        historyAdapter.stopListening();
    }

    private void openSettingActivity() {
        Intent intent = new Intent(getActivity(), SettingsActivity.class);
        getActivity().startActivity(intent);

    }

    private void initPromptListViewModel() {
        promptViewModel = new ViewModelProvider(getActivity()).get(PromptViewModel.class);
    }


    private void setUpPromptCardStackView() {

        promptViewModel.getPromptListLiveData(wantedFiltersList).observe(getActivity(), promptsList -> {
            Collections.shuffle(promptsList);
            promptCardAdapter = new PromptCardAdapter(promptsList);
            promptCardStackView.setAdapter(promptCardAdapter);
            String itemcount = String.valueOf(promptsList.size());
            Log.d("listsize", itemcount);
        });

        promptCardLayoutManager = new CardStackLayoutManager(getActivity(), this);
        promptCardLayoutManager.setStackFrom(StackFrom.Bottom);
        promptCardLayoutManager.setVisibleCount(3);
        promptCardLayoutManager.setTranslationInterval(8f);
        promptCardLayoutManager.setCanScrollVertical(false);
        promptCardStackView.setLayoutManager(promptCardLayoutManager);



    }

//    create new prompt object and add it to the history collection in firestore.
    private void updateHistory(String description, boolean accepted, Timestamp timestamp) {
        String mDescription = description;
        boolean mAccepted = accepted;
        Timestamp mTimestamp = timestamp;
        Prompt aNewPrompt = new Prompt(mDescription, mAccepted, mTimestamp);

        historyRef.add(aNewPrompt);
        Log.d("updateHistory", String.valueOf(aNewPrompt));
    }

    private void initGoogleSignInClient() {
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .build();
        googleSignInClient = GoogleSignIn.getClient(getActivity(), googleSignInOptions);
    }



//    when cardstackview is swiped, get the current timestamp, and update history
    @Override
    public void onCardSwiped(Direction direction) {
        switch (direction) {
            case Right:
                if (lastPrompt == promptCardAdapter.getItemCount()) {
                    lastPrompt = 0;
                }
                currentTimestamp = Timestamp.now();
                Log.d("QRightpreLastPrompt", promptCardAdapter.getmPromptsList().get(lastPrompt));
                updateHistory(promptCardAdapter.getmPromptsList().get(lastPrompt), true, currentTimestamp);
                lastPrompt++;
                break;
            case Left:
                if (lastPrompt == promptCardAdapter.getItemCount()) {
                    lastPrompt = 0;
                }
                currentTimestamp = Timestamp.now();
                Log.d("QLeftpreLastPrompt", promptCardAdapter.getmPromptsList().get(lastPrompt));
                updateHistory(promptCardAdapter.getmPromptsList().get(lastPrompt), false, currentTimestamp);
                lastPrompt++;
                break;
            default:
                break;
        }

    }

    @Override
    public void onCardDragging(Direction direction, float ratio) {

    }

    @Override
    public void onCardRewound() {

    }

    @Override
    public void onCardCanceled() {

    }

    @Override
    public void onCardAppeared(View view, int position) {

    }

    @Override
    public void onCardDisappeared(View view, int position) {

    }
}