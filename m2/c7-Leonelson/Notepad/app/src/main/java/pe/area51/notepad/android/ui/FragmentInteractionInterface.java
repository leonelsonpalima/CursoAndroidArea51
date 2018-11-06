package pe.area51.notepad.android.ui;

import android.support.annotation.NonNull;

import pe.area51.notepad.domain.Note;

public interface FragmentInteractionInterface {

    void showNoteContent(@NonNull String noteId);

    void setTitle(final String title);

}

