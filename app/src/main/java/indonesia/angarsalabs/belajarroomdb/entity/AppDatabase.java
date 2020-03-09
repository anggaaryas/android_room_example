package indonesia.angarsalabs.belajarroomdb.entity;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
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
}
