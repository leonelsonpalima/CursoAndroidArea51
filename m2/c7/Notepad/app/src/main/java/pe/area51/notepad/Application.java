package pe.area51.notepad;

import android.arch.persistence.db.framework.FrameworkSQLiteOpenHelperFactory;
import android.arch.persistence.room.Room;

import io.requery.android.database.sqlite.RequerySQLiteOpenHelperFactory;
import pe.area51.notepad.data.ram_database.RamDatabase;
import pe.area51.notepad.data.room.RoomDatabase;
import pe.area51.notepad.data.room.RoomNotesRepository;
import pe.area51.notepad.data.sqlite.DatabaseManager;
import pe.area51.notepad.data.sqlite.SqLiteDatabase;
import pe.area51.notepad.domain.NotesRepository;

public class Application extends android.app.Application {

    private NotesRepository notesRepository;
    private ViewModelFactory viewModelFactory;

    @Override
    public void onCreate() {
        super.onCreate();
        //RAM Database
        //notesRepository = new RamDatabase();
        //SQLite
        /*notesRepository = new SqLiteDatabase(new DatabaseManager(
                this,
                "notes" //La extensión no es necesaria. Este es el nombre del archivo de base de datos.
        ));*/
        //Room Database
        final RoomDatabase roomDatabase = Room.databaseBuilder(
                this,
                RoomDatabase.class,
                "notes-room"
        ).openHelperFactory(new RequerySQLiteOpenHelperFactory())
                .build();
        notesRepository = new RoomNotesRepository(roomDatabase);

        viewModelFactory = new ViewModelFactory(notesRepository);
    }

    public NotesRepository getNotesRepository() {
        return notesRepository;
    }

    public ViewModelFactory getViewModelFactory() {
        return viewModelFactory;
    }
}
