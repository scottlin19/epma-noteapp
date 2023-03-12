package lin.scott.noteapp.ui.view_model;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import lin.scott.noteapp.data.models.Note;
import lin.scott.noteapp.data.repository.NoteRepository;

public class NoteViewModel extends AndroidViewModel {

    private final NoteRepository mRepository;

    private final LiveData<List<Note>> mAllNotes;

    public NoteViewModel(Application application) {
        super(application);
        mRepository = new NoteRepository(application);
        mAllNotes   = mRepository.getAllNotes();
    }

    public LiveData<List<Note>> getAllNotes() { return mAllNotes; }

    public void insert(Note note) { mRepository.insert(note); }

    public void update(Note note) {
        mRepository.update(note);
    }
}