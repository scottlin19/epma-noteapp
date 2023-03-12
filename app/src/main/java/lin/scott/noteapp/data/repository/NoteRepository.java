package lin.scott.noteapp.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import lin.scott.noteapp.data.models.Note;
import lin.scott.noteapp.persistence.dao.NoteDao;
import lin.scott.noteapp.persistence.database.NoteRoomDatabase;
import lin.scott.noteapp.persistence.entity.NoteDB;

public class NoteRepository {

    private NoteDao mNoteDao;
    private LiveData<List<Note>> mAllNotes;

    // Note that in order to unit test the NoteRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public NoteRepository(Application application) {
        NoteRoomDatabase db = NoteRoomDatabase.getDatabase(application);
        mNoteDao = db.noteDao();
        mAllNotes = mNoteDao.getNotes();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Note>> getAllNotes() {
        return mAllNotes;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insert(Note note) {
        System.out.println(note);
        NoteRoomDatabase.databaseWriteExecutor.execute(() -> {
            mNoteDao.insert(NoteDB.fromNote(note));
        });
    }

    public void update(Note note) {
        NoteRoomDatabase.databaseWriteExecutor.execute(() -> mNoteDao.update(NoteDB.fromNote(note)));
    }
}