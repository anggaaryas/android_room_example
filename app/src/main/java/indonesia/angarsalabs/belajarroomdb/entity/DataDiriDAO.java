package indonesia.angarsalabs.belajarroomdb.entity;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Angarsa Labs...!
 * Created by Angga on 13/08/2018.
 */
@Dao
public interface DataDiriDAO {
    @Insert
    Long insertData(DataDiri dataDiri);

    @Query("Select * from user_db")
    List<DataDiri> getData();

    @Update
    int updateData(DataDiri item);

    @Delete
    void deleteData(DataDiri item);
}
