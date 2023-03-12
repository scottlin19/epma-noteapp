package lin.scott.noteapp.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;

import lin.scott.noteapp.R;
import lin.scott.noteapp.data.models.Note;
import lin.scott.noteapp.databinding.FragmentListNotesBinding;
import lin.scott.noteapp.ui.view_model.NoteViewModel;

public class ListNotesFragment extends Fragment {

    public static final int NEW_NOTE_FRAGMENT_REQUEST_CODE = 1;

    private FragmentListNotesBinding binding;

    private NoteViewModel noteViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Activity activity = getActivity();
        if (activity != null) {
            activity.setTitle(getString(R.string.your_notes));
        }

        getParentFragmentManager().setFragmentResultListener("newNote", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                Note newNote = result.getParcelable("newNote");
                if(newNote.getId() == null) {
                    noteViewModel.insert(newNote);
                } else {
                    noteViewModel.update(newNote);
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {

        binding = FragmentListNotesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.createNewNoteBtn.setOnClickListener(v ->
                NavHostFragment.findNavController(ListNotesFragment.this).navigate(R.id.action_listNotesFragment_to_createNoteFragment)
        );

        final NoteListAdapter adapter = new NoteListAdapter(new NoteListAdapter.noteDiff());
        binding.listNotesRecyclerview.setAdapter(adapter);
        binding.listNotesRecyclerview.setLayoutManager(new GridLayoutManager(getActivity(),2));


        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        // Update the cached copy of the notes in the adapter.
        noteViewModel.getAllNotes().observe(this, adapter::submitList);
    }

    @Override
    public void onResume() {
        super.onResume();

        Activity activity = getActivity();
        if (activity != null) {
            activity.setTitle(getString(R.string.your_notes));
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}
