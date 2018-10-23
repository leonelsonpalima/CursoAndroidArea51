package pe.area51.notepad;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements FragmentList.FragmentListInterface {

    private final static String FRAGMENT_TAG_LIST = "listFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentList fragmentList;
        if (savedInstanceState == null) {
            /*
            Creamos los fragments e iniciamos la transacción cuando este Activity se cree
            por primera vez.
             */
            fragmentList = new FragmentList();
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, fragmentList, FRAGMENT_TAG_LIST)
                    .commit();
        } else {
            fragmentList = (FragmentList) fragmentManager.findFragmentByTag(FRAGMENT_TAG_LIST);
        }
        fragmentList.setOnNoteSelectedListener(this);
        /*
        Si el Activity tiene un estado anterior, no es necesario crear los fragments o ejecutar nuevamente
        la transacción, puesto que en el proceso de recreación de estados, se recrea también el estado
        del FragmentManager.
        */
    }

    @Override
    public void onNoteSelected(Note note) {
        Toast.makeText(this, note.getTitle(), Toast.LENGTH_SHORT).show();
    }
}
