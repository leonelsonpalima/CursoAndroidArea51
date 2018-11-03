package pe.area51.notepad;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pe.area51.notepad.domain.Note;
import pe.area51.notepad.domain.NotesRepository;

public class ViewModelList extends ViewModel {

    private final NotesRepository notesRepository;
    private final MutableLiveData<List<Note>> fetchAllNotesResponse;
    private final MutableLiveData<Note> createNoteResponse;

    private final List<Note> notes;


    public ViewModelList(NotesRepository notesRepository) {
        this.notesRepository = notesRepository;
        fetchAllNotesResponse = new MutableLiveData<>();
        createNoteResponse = new MutableLiveData<>();
        notes = new ArrayList<>();
    }


    public void fetchAllNotes() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                final List<Note> notes = notesRepository.getAllNotes();
                //Debemos llamar a postValue ya que no estamos en el Main Thread
                notes.clear();
                notes.addAll(notes);
                fetchAllNotesResponse.postValue(ViewModelList.this.notes);
            }
        });
    }

    public void createNote(final String title,
                           final String content,
                           final long creationTimestamp) {

        final Note note = new Note(
                title,
                content,
                creationTimestamp
        );
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                final Note createdNote = notesRepository.createNote(note);
                notes.add(createdNote);
                fetchAllNotesResponse.postValue(notes);
                createNoteResponse.postValue(createdNote);

            }
        });

    }

    public LiveData<List<Note>> getFetchAllNotesResponse() {
        return fetchAllNotesResponse;
    }

    public LiveData<Note> getCreateNoteResponse() {
        return createNoteResponse;
    }
}
