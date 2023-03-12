package lin.scott.noteapp.persistence.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import lin.scott.noteapp.data.models.Note;
import lin.scott.noteapp.persistence.entity.NoteDB;

@Dao
public interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(NoteDB note);

    @Update
    void update(NoteDB note);

    @Query("DELETE FROM note_table")
    void deleteAll();

    @Query("SELECT * FROM note_table ORDER BY modifiedAt DESC")
    LiveData<List<Note>> getNotes();
}
