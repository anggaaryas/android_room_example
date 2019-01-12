package indonesia.angarsalabs.belajarroomdb.entity;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Angarsa Labs...!
 * Created by Angga on 13/08/2018.
 */
@Database(entities = {DataDiri.class} , version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DataDiriDAO dao();
    private static AppDatabase appDatabase;

    public static AppDatabase iniDb(Context context){
        if(appDatabase == null)
            appDatabase = Room.databaseBuilder(context, AppDatabase.class,
                "dbUser").allowMainThreadQueries().build();

        return appDatabase;
    }

    public static void destroyInstance() {
        appDatabase = null;
    }
}
