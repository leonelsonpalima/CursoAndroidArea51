package pe.area51.notepad.android.ui.content;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;

import pe.area51.notepad.R;
import pe.area51.notepad.domain.Note;

public class FragmentContent extends Fragment {

    private static final String KEY_ARG_NOTE_ID = "noteID";

    private TextView textViewDate;
    private TextView textViewContent;

    private String noteId;

    public static FragmentContent newInstance(final String noteId) {
        final FragmentContent contentFragment = new FragmentContent();
        final Bundle arguments = new Bundle();
        arguments.putString(KEY_ARG_NOTE_ID, noteId);
        contentFragment.setArguments(arguments);
        return contentFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle arguments = getArguments();
        checkArguments(arguments);
        noteId = arguments.getString(KEY_ARG_NOTE_ID);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_content, container, false);
        textViewDate = view.findViewById(R.id.textViewNoteDate);
        textViewContent = view.findViewById(R.id.textViewNoteContent);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showNote();
        //getActivity().setTitle(note.getTitle());
    }

    private static void checkArguments(final Bundle arguments) {
        if (arguments != null &&
                arguments.containsKey(KEY_ARG_NOTE_ID)) {
            return;
        }
        throw new RuntimeException("Fragment doesn't have needed arguments. Call newInstance() static creation method.");
    }

    private void showNote() {
//        final Date noteDate = new Date(note.getCreationTimestamp());
//        final DateFormat dateFormat = DateFormat.getInstance();
//        textViewDate.setText(dateFormat.format(noteDate));
//        textViewContent.setText(note.getContent());
        textViewContent.setText(noteId);
    }
}
