package pe.area51.notepad;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pe.area51.notepad.domain.Note;
import pe.area51.notepad.domain.NotesRepository;

public class ViewModelList extends ViewModel {

    private final NotesRepository notesRepository;
    private final MediatorLiveData<List<Note>> fetchAllNotesResponse;
    private final MutableLiveData<Note> createNoteResponse;

    //private final List<Note> notes;


    public ViewModelList(NotesRepository notesRepository) {
        this.notesRepository = notesRepository;
        fetchAllNotesResponse = new MediatorLiveData<>();
        createNoteResponse = new MutableLiveData<>();
       // notes = new ArrayList<>();
    }


    public void fetchAllNotes() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                final List<Note> result = notesRepository.getAllNotes();
                //Debemos llamar a postValue ya que no estamos en el Main Thread
                //notes.clear();
                //notes.addAll(notes);
                fetchAllNotesResponse.addSource((LiveData<List<Note>>) result, new Observer<List<Note>>() {
                    @Override
                    public void onChanged(@Nullable List<Note> notes) {
                        fetchAllNotesResponse.setValue(notes);
                    }
                });
                fetchAllNotesResponse.postValue(result);
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
                //notes.add(createdNote);
                //fetchAllNotesResponse.postValue(notes);
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
