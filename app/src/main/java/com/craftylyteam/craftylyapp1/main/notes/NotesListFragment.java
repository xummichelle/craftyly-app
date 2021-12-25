package com.craftylyteam.craftylyapp1.main.notes;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.craftylyteam.craftylyapp1.R;
import com.craftylyteam.craftylyapp1.main.prompt.FiltersFragment;
import com.craftylyteam.craftylyapp1.utils.Constants;
import com.craftylyteam.craftylyapp1.utils.themeUtils;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import smartdevelop.ir.eram.showcaseviewlib.GuideView;
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType;
import smartdevelop.ir.eram.showcaseviewlib.config.Gravity;


public class NotesListFragment extends Fragment implements View.OnClickListener {
//    firebase references
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference usersRef = db.collection(Constants.USERS);
    private DocumentReference currentUserRef;
    private CollectionReference historyRef;
    private CollectionReference notesRef;
    private FirebaseUser firebaseUser;

//    views
    private View view;
    private MaterialButton newNoteButton;
    private MaterialButton openAcceptListButton;
    private MaterialButton openRejectListButton;

//    note list recyclerview things
    private RecyclerView myNotesRecyclerView;
    private NotesListAdapter notesListAdapter;





    public NotesListFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_notes_list, container, false);
        firebaseUser = firebaseAuth.getCurrentUser();
        currentUserRef = usersRef.document(firebaseUser.getUid());
        notesRef = currentUserRef.collection(Constants.NOTES);

//        buttons
        newNoteButton = (MaterialButton) view.findViewById(R.id.new_note_button);
        newNoteButton.setOnClickListener(this);
        openAcceptListButton = (MaterialButton) view.findViewById(R.id.open_accept_list_button);
        openAcceptListButton.setOnClickListener(this);
        openRejectListButton = (MaterialButton) view.findViewById(R.id.open_reject_list_button);
        openRejectListButton.setOnClickListener(this);

        themeUtils.onNotesListFragmentCreateSetTheme(getActivity(), view);
        setUpNoteListRecyclerView();

        checkIfFirstRunNotes();
        return view;
    }

    private void checkIfFirstRunNotes() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.SHARED_PREFS, MODE_PRIVATE);

        if (sharedPreferences.getBoolean(Constants.FIRST_RUN_NOTES, true)) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(Constants.FIRST_RUN_NOTES, false);
            editor.apply();
            showNotesTutorial("This is your notes page!",
                    "You can treat this as your own\nartist journal... Jot down ideas, write\n" +
                            "down stuff, do anything with it!", R.id.note_list_header_text_view, 1);
        }
    }

    private void showNotesTutorial(String title, String text, int viewId, final int type) {
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
                            showNotesTutorial("Here are your accepted prompts",
                                    "You can see all the prompts you accepted here", R.id.open_accept_list_button, 2);
                            break;
                        case 2:
                            showNotesTutorial("Here are your rejected prompts",
                                    "You can see all the prompts you rejected here", R.id.open_reject_list_button, 3);
                            break;
                        case 3:
                            showNotesTutorial("Create new notes...",
                                    "(click anywhere to continue)", R.id.new_note_button, 4);
                            break;
                        case 4:
                            showNotesTutorial("And see all the notes you created here!",
                                    "(click anywhere to finish tutorial)", R.id.my_notes_recyclerview, 5);
                            break;

                        default:
                            break;
                    }
                })

                .build()
                .show();
    }

    private void setUpNoteListRecyclerView() {
        Query query = notesRef.orderBy("timestamp", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<Note> noteOptions = new FirestoreRecyclerOptions.Builder<Note>()
                .setQuery(query, Note.class)
                .build();

        notesListAdapter = new NotesListAdapter(noteOptions);
        myNotesRecyclerView = view.findViewById(R.id.my_notes_recyclerview);
        myNotesRecyclerView.setHasFixedSize(true);
        LinearLayoutManager notesListLayoutManager = new LinearLayoutManager(getActivity());
        myNotesRecyclerView.setLayoutManager(notesListLayoutManager);
        myNotesRecyclerView.setAdapter(notesListAdapter);
        DividerItemDecoration noteListDividerItemDecoration = new DividerItemDecoration(myNotesRecyclerView.getContext(),
                notesListLayoutManager.getOrientation());
        Drawable d = getResources().getDrawable(R.drawable.note_list_divider, getContext().getTheme());
        noteListDividerItemDecoration.setDrawable(d);
        myNotesRecyclerView.addItemDecoration(noteListDividerItemDecoration);


        setUpItemTouchHelper();
        notesListAdapter.setOnItemClickListener(new NotesListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                String noteID = documentSnapshot.getId();
                Intent editExistingNoteIntent = new Intent(getActivity(), EditNoteActivity.class);
                editExistingNoteIntent.putExtra(Constants.EXTRA_NOTE_ID, noteID);
                getActivity().startActivity(editExistingNoteIntent);
            }
        });
        setUpAnimationDecoratorHelper();
    }

    //        set up itemtouchhelper. when swiping left, delete the note
    private void setUpItemTouchHelper(){
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT) {

            Drawable background;
            Drawable deleteOutlineIcon;
            int deleteOutlineIconMargin;
            boolean initiated;

            private void init() {
                background = new ColorDrawable(getResources().getColor(R.color.tangerine, null));
                deleteOutlineIcon = ContextCompat.getDrawable(getActivity(), R.drawable.delete_outline_icon);
//                deleteOutlineIcon.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                deleteOutlineIconMargin = (int) getActivity().getResources().getDimension(R.dimen.ic_clear_margin);
                initiated = true;
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Log.d("ooooooonSwiped", "onSwiped: noteslist swiped");
                notesListAdapter.deleteItem(viewHolder.getAdapterPosition());
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

                View itemView = viewHolder.itemView;

                /*if (dX > 0) {
                    background.setBounds(itemView.getLeft(), itemView.getTop(), itemView.getLeft() + ((int) dX), itemView.getBottom());
                } else if (dX < 0) {
                    background.setBounds(itemView.getRight() + ((int) dX), itemView.getTop(), itemView.getRight(), itemView.getBottom());
                } else {
                    background.setBounds(0, 0, 0, 0);
                }

                background.draw(c);*/
                if (viewHolder.getAdapterPosition() == -1) {
                    // not interested in those
                    return;
                }

                if (!initiated) {
                    init();
                }

                background.setBounds(itemView.getRight() + (int) dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
                background.draw(c);

                // draw x mark
                int itemHeight = itemView.getBottom() - itemView.getTop();
                int intrinsicWidth = deleteOutlineIcon.getIntrinsicWidth();
                int intrinsicHeight = deleteOutlineIcon.getIntrinsicWidth();

                int deleteOutlineIconLeft = itemView.getRight() - deleteOutlineIconMargin - intrinsicWidth;
                int deleteOutlineIconRight = itemView.getRight() - deleteOutlineIconMargin;
                int deleteOutlineIconTop = itemView.getTop() + (itemHeight - intrinsicHeight)/2;
                int deleteOutlineIconBottom = deleteOutlineIconTop + intrinsicHeight;
                deleteOutlineIcon.setBounds(deleteOutlineIconLeft, deleteOutlineIconTop, deleteOutlineIconRight, deleteOutlineIconBottom);

                deleteOutlineIcon.draw(c);

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }).attachToRecyclerView(myNotesRecyclerView);
    }

    private void setUpAnimationDecoratorHelper() {
        myNotesRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {

            // we want to cache this and not allocate anything repeatedly in the onDraw method
            Drawable background;
            boolean initiated;

            private void init() {
                background = new ColorDrawable(getResources().getColor(R.color.tangerine, null));
                initiated = true;
            }

            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

                if (!initiated) {
                    init();
                }

                // only if animation is in progress
                if (parent.getItemAnimator().isRunning()) {

                    // some items might be animating down and some items might be animating up to close the gap left by the removed item
                    // this is not exclusive, both movement can be happening at the same time
                    // to reproduce this leave just enough items so the first one and the last one would be just a little off screen
                    // then remove one from the middle

                    // find first child with translationY > 0
                    // and last one with translationY < 0
                    // we're after a rect that is not covered in recycler-view views at this point in time
                    View lastViewComingDown = null;
                    View firstViewComingUp = null;

                    // this is fixed
                    int left = 0;
                    int right = parent.getWidth();

                    // this we need to find out
                    int top = 0;
                    int bottom = 0;

                    // find relevant translating views
                    int childCount = parent.getLayoutManager().getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        View child = parent.getLayoutManager().getChildAt(i);
                        if (child.getTranslationY() < 0) {
                            // view is coming down
                            lastViewComingDown = child;
                        } else if (child.getTranslationY() > 0) {
                            // view is coming up
                            if (firstViewComingUp == null) {
                                firstViewComingUp = child;
                            }
                        }
                    }

                    if (lastViewComingDown != null && firstViewComingUp != null) {
                        // views are coming down AND going up to fill the void
                        top = lastViewComingDown.getBottom() + (int) lastViewComingDown.getTranslationY();
                        bottom = firstViewComingUp.getTop() + (int) firstViewComingUp.getTranslationY();
                    } else if (lastViewComingDown != null) {
                        // views are going down to fill the void
                        top = lastViewComingDown.getBottom() + (int) lastViewComingDown.getTranslationY();
                        bottom = lastViewComingDown.getBottom();
                    } else if (firstViewComingUp != null) {
                        // views are coming up to fill the void
                        top = firstViewComingUp.getTop();
                        bottom = firstViewComingUp.getTop() + (int) firstViewComingUp.getTranslationY();
                    }

                    background.setBounds(left, top, right, bottom);
                    background.draw(c);

                }
                super.onDraw(c, parent, state);
            }

        });
    }


    @Override
    public void onStart() {
        super.onStart();
        notesListAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        notesListAdapter.stopListening();
    }

    //    on click listener for buttons
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.new_note_button:
                Log.d("notelistfragment", "new note button pressed");
                Intent intentNewNote = new Intent(getActivity(), EditNoteActivity.class);
                getActivity().startActivity(intentNewNote);
                break;
            case R.id.open_accept_list_button:
                AcceptedListFragment acceptedListFragment= new AcceptedListFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(((ViewGroup)getView().getParent()).getId(), acceptedListFragment, "")
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.open_reject_list_button:
                RejectedListFragment rejectedListFragment = new RejectedListFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(((ViewGroup)getView().getParent()).getId(), rejectedListFragment, "")
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();
                break;
            default:
                break;
        }
    }
}