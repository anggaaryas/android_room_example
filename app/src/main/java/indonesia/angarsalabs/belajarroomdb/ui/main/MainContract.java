package indonesia.angarsalabs.belajarroomdb.ui.main;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;

import java.util.List;

import indonesia.angarsalabs.belajarroomdb.entity.AppDatabase;
import indonesia.angarsalabs.belajarroomdb.entity.DataDiri;

/**
 * Angarsa Labs...!
 * Created by Angga on 13/08/2018.
 */
public interface MainContract {

    // inrterface view digunakan untuk kodingan Activity
    interface view extends View.OnClickListener{
        void successAdd();
        void successDelete();
        void resetForm();
        void getData(List<DataDiri> list);
        void editData(DataDiri item);
        void deleteData(DataDiri item);
    }

    // interfaace presenter digunakan untuk kodingan database nya
    interface presenter {
        void insertData(String nama, String alamat, char gender, AppDatabase database);
        void readData(AppDatabase database);
        void editData(String nama, String alamat, char gender, int id, AppDatabase database);
        void deleteData(DataDiri dataDiri, AppDatabase database);
    }
}
