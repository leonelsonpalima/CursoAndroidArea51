package pe.area51.notepad;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class FragmentList extends Fragment {

    public static final String TAG = "ListFragment";

    private ArrayAdapter<Note> notesArrayAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_list, container, false);
        final ListView listViewElements = view.findViewById(R.id.listView);
        notesArrayAdapter = new NoteAdapter(getActivity());
        listViewElements.setAdapter(notesArrayAdapter);
        notesArrayAdapter.addAll(createTestNotes(100));
        return view;
    }

    private List<Note> createTestNotes(final int size) {
        final List<Note> testNotes = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            final Note note = new Note(
                    String.valueOf(i + 1),
                    "Title " + (i + 1),
                    getString(R.string.lorem_ipsum),
                    System.currentTimeMillis()
            );
            notesArrayAdapter.add(note);
        }
        return testNotes;
    }

    private static class NoteAdapter extends ArrayAdapter<Note> {

        private final LayoutInflater layoutInflater;

        public NoteAdapter(final Context context) {
            super(context, 0);
            layoutInflater = LayoutInflater.from(getContext());
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Log.d(TAG, "Position: " + position + "; convertView " + (convertView == null ? "== null" : "!= null"));
            final Note note = getItem(position);
            final View view;
            if (convertView == null) {
                view = layoutInflater.inflate(R.layout.element_note, parent, false);
            } else {
                view = convertView;
            }
            final TextView textViewTitle = view.findViewById(R.id.textViewNoteTitle);
            textViewTitle.setText(note.getTitle());
            return view;
        }
    }
}
