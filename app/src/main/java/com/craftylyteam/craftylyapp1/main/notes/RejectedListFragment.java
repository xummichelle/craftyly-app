package com.craftylyteam.craftylyapp1.main.notes;

import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.craftylyteam.craftylyapp1.R;
import com.craftylyteam.craftylyapp1.main.prompt.Prompt;
import com.craftylyteam.craftylyapp1.utils.Constants;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class RejectedListFragment extends Fragment {
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference usersRef = db.collection(Constants.USERS);
    private DocumentReference currentUserRef;
    private CollectionReference historyRef;
    private FirebaseUser firebaseUser;

    private View view;
    private ImageButton rejectedListArrowNavButton;

    //    recyclerview things
    private RecyclerView rejectedListRecyclerView;
    private RejectedListAdapter rejectedListAdapter;


    public RejectedListFragment() {
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
        view = inflater.inflate(R.layout.fragment_rejected_list, container, false);
        firebaseUser = firebaseAuth.getCurrentUser();
        currentUserRef = usersRef.document(firebaseUser.getUid());
        historyRef = currentUserRef.collection(Constants.HISTORY);

        rejectedListArrowNavButton = (ImageButton) view.findViewById(R.id.rejected_list_arrow_nav_button);
        rejectedListArrowNavButton.setOnClickListener(v -> backButtonClicked());
        setUpRejectedListRecyclerView();


        return view;
    }

    private void setUpRejectedListRecyclerView(){
        Query query = historyRef.whereEqualTo(Constants.HISTORY_ACCEPTED, false)
                .orderBy(Constants.HISTORY_TIMESTAMP, Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<Prompt> rejectedListOptions = new FirestoreRecyclerOptions.Builder<Prompt>()
                .setQuery(query, Prompt.class)
                .build();

        rejectedListAdapter = new RejectedListAdapter(rejectedListOptions);
        rejectedListRecyclerView = (RecyclerView) view.findViewById(R.id.rejected_list_recycler_view);
        rejectedListRecyclerView.setHasFixedSize(true);
        LinearLayoutManager rejectedListLayoutManager = new LinearLayoutManager(getActivity());
        rejectedListRecyclerView.setLayoutManager(rejectedListLayoutManager);
        rejectedListRecyclerView.setAdapter(rejectedListAdapter);
        DividerItemDecoration noteListDividerItemDecoration = new DividerItemDecoration(rejectedListRecyclerView.getContext(),
                rejectedListLayoutManager.getOrientation());
        Drawable d = getResources().getDrawable(R.drawable.note_list_divider, getContext().getTheme());
        noteListDividerItemDecoration.setDrawable(d);
        rejectedListRecyclerView.addItemDecoration(noteListDividerItemDecoration);

//        swipe to delete
        setUpItemTouchHelper();
        setUpAnimationDecoratorHelper();
    }

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

                rejectedListAdapter.deleteItem(viewHolder.getAdapterPosition());


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
        }).attachToRecyclerView(rejectedListRecyclerView);
    }

    private void setUpAnimationDecoratorHelper() {
        rejectedListRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {

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

    private void backButtonClicked() {
        getActivity().getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onStart() {
        super.onStart();
        rejectedListAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        rejectedListAdapter.stopListening();
    }
}