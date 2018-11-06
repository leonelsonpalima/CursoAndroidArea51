package pe.area51.notepad.android.ui.content;

import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import pe.area51.notepad.domain.Note;
import pe.area51.notepad.domain.NotesRepository;

public class ViewModelContent extends ViewModel {

    private final NotesRepository notesRepository;
    private final MediatorLiveData<Note> fetchNoteByIdResponse;


    public ViewModelContent(NotesRepository notesRepository) {
        this.notesRepository = notesRepository;
        fetchNoteByIdResponse = new MediatorLiveData<>();
    }

    public void FetchNoteByIdResponse(@NonNull final String noteId) {
        AsyncTask.execute(() -> {
            fetchNoteByIdResponse.addSource(notesRepository.subscribeToNoteById(noteId),
                    note -> {
                        fetchNoteByIdResponse.setValue(note);
                    }
            );
        });
    }
}
