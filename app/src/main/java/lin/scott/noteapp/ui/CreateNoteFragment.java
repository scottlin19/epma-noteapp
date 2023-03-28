package lin.scott.noteapp.ui;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

import lin.scott.noteapp.R;
import lin.scott.noteapp.data.models.Note;
import lin.scott.noteapp.databinding.FragmentCreateNoteBinding;

import static lin.scott.noteapp.persistence.entity.NoteDB.MAX_TITLE_CHARS;
import static lin.scott.noteapp.persistence.entity.NoteDB.MAX_CONTENT_CHARS;

public class CreateNoteFragment extends Fragment {

    private FragmentCreateNoteBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Activity activity = getActivity();
        if (activity != null) {
            activity.setTitle(getString(R.string.edit_note));
        }

    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {

        binding = FragmentCreateNoteBinding.inflate(inflater, container, false);
        binding.editTitleTextInputLayout.setCounterMaxLength(MAX_TITLE_CHARS);
        binding.editNoteTextInputLayout.setCounterMaxLength(MAX_CONTENT_CHARS);
        binding.editTitleTextInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(MAX_TITLE_CHARS)});
        binding.editNoteTextInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(MAX_CONTENT_CHARS)});

        final Bundle bundle = this.getArguments();
        final Optional<Note> optionalNote = bundle != null ? Optional.ofNullable(bundle.getParcelable("note")) : Optional.empty();

        if(optionalNote.isPresent()) {
            final Note note = optionalNote.get();
            binding.editTitleTextInput.setText(note.getTitle());
            binding.editNoteTextInput.setText(note.getContent());
        }

        binding.saveBtn.setOnClickListener(l -> {
            final String title      = Objects.requireNonNull(binding.editTitleTextInput.getText()).toString();
            final String content    = Objects.requireNonNull(binding.editNoteTextInput.getText()).toString();

            if(!title.isEmpty() && !content.isEmpty()) {
                final Instant now = Instant.now();
                final Note newNote = optionalNote.isPresent() ?
                        optionalNote.get().withTitle(title).withContent(content).withModifiedAt(now) :
                        new Note()
                        .withTitle(title)
                        .withContent(content)
                        .withModifiedAt(now)
                        .withCreatedAt(now);

                Bundle result = new Bundle();
                result.putParcelable("newNote", newNote);
                getParentFragmentManager().setFragmentResult("newNote", result);
                NavHostFragment.findNavController(CreateNoteFragment.this).navigateUp();
            }
        });

        return binding.getRoot();
    }


    @Override
    public void onResume() {
        super.onResume();

        Activity activity = getActivity();
        if (activity != null) {
            activity.setTitle(getString(R.string.edit_note));
        }

    }

//    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        binding.createNewNoteInputText.setMovementMethod(new ScrollingMovementMethod());
//    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
