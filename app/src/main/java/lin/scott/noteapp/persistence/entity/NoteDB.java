package lin.scott.noteapp.persistence.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.Instant;

import lin.scott.noteapp.data.models.Note;

@Entity(tableName = "note_table")
public class NoteDB {

    public static final int MAX_TITLE_CHARS    = 15;
    public static final int MAX_CONTENT_CHARS  = 50;
    @PrimaryKey(autoGenerate = true)
    private Long id;

    @NonNull
    private String title;

    @NonNull
    private String content;

    @NonNull
    private Instant createdAt;

    @NonNull
    private Instant modifiedAt;

    public NoteDB(){}
    public NoteDB(@NonNull String title, @NonNull String content, @NonNull Instant createdAt, @NonNull Instant modifiedAt) {
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static NoteDB fromNote(final Note note) {
        return new NoteDB()
                .withId(note.getId())
                .withTitle(note.getTitle())
                .withContent(note.getContent())
                .withCreatedAt(note.getCreatedAt())
                .withModifiedAt(note.getModifiedAt());
    }

    public Note toNote() {
        return new Note(id, title, content, createdAt, modifiedAt);
    }

    //////////////////////////////////////////////////
    // GETTERS AND SETTERS
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public NoteDB withId(Long id) {
        setId(id);
        return this;
    }

    @NonNull
    public String getTitle() {
        return title;
    }
    public void setTitle(@NonNull String title) {
        this.title = title;
    }
    public NoteDB withTitle(@NonNull String title) {
        setTitle(title);
        return this;
    }

    @NonNull
    public String getContent() {
        return content;
    }
    public void setContent(@NonNull String content) {
        this.content = content;
    }
    public NoteDB withContent(@NonNull String content) {
        setContent(content);
        return this;
    }

    @NonNull
    public Instant getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(@NonNull Instant createdAt) {
        this.createdAt = createdAt;
    }
    public NoteDB withCreatedAt(@NonNull Instant createdAt) {
        setCreatedAt(createdAt);
        return this;
    }

    @NonNull
    public Instant getModifiedAt() {
        return modifiedAt;
    }
    public void setModifiedAt(@NonNull Instant modifiedAt) {
        this.modifiedAt = modifiedAt;
    }
    public NoteDB withModifiedAt(@NonNull Instant modifiedAt) {
        setModifiedAt(modifiedAt);
        return this;
    }

}
