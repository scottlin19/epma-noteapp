package lin.scott.noteapp.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.time.Instant;

public class Note implements Parcelable {

    private Long id;
    @NonNull
    private String title;
    @NonNull
    private String content;
    @NonNull
    private Instant createdAt;
    @NonNull
    private Instant modifiedAt;


    public Note(){}

    public Note(final long id, @NonNull final String title, @NonNull final String content, @NonNull final Instant createdAt, @NonNull final Instant modifiedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    protected Note(Parcel in) {
        this.id = in.readLong();
        this.title = in.readString();
        this.content = in.readString();
        this.createdAt = Instant.ofEpochMilli(in.readLong());
        this.modifiedAt = Instant.ofEpochMilli(in.readLong());
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(content);
        dest.writeLong(createdAt.toEpochMilli());
        dest.writeLong(modifiedAt.toEpochMilli());
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public Long getId() {
        return id;
    }
    public void setId(final Long id) {
        this.id = id;
    }
    public Note withId(final Long id) {
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
    public Note withTitle(@NonNull String title) {
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
    public Note withContent(@NonNull String content) {
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
    public Note withCreatedAt(@NonNull Instant createdAt) {
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
    public Note withModifiedAt(@NonNull Instant modifiedAt) {
        setModifiedAt(modifiedAt);
        return this;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                ", modifiedAt=" + modifiedAt +
                '}';
    }
}
