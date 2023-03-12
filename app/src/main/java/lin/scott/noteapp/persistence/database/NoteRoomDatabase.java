package lin.scott.noteapp.persistence.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lin.scott.noteapp.persistence.InstantConverter;
import lin.scott.noteapp.persistence.dao.NoteDao;
import lin.scott.noteapp.persistence.entity.NoteDB;

@Database(entities = {NoteDB.class}, version = 1, exportSchema = false)
@TypeConverters({InstantConverter.class})
public abstract class NoteRoomDatabase extends RoomDatabase {

    private static final String NOTE_DATABASE = "note_database";
    public abstract NoteDao noteDao();

    private static volatile NoteRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static NoteRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (NoteRoomDatabase.class) {
                if (INSTANCE == null) {
                    context.deleteDatabase(NOTE_DATABASE);
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    NoteRoomDatabase.class, NOTE_DATABASE)
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private final static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                NoteDao dao = INSTANCE.noteDao();
                dao.deleteAll();

                NoteDB note = new NoteDB("Hello", "World", Instant.now(), Instant.now());
                dao.insert(note);
                note = new NoteDB("Title placeholder", "Content", Instant.now(), Instant.now());
                dao.insert(note);
                note = new NoteDB("3rd note", "awesome!!!!", Instant.now(), Instant.now());
                dao.insert(note);
            });
        }
    };


}
