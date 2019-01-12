package indonesia.angarsalabs.belajarroomdb.ui.main;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import indonesia.angarsalabs.belajarroomdb.entity.AppDatabase;
import indonesia.angarsalabs.belajarroomdb.entity.DataDiri;

/**
 * Angarsa Labs...!
 * Created by Angga on 13/08/2018.
 */
public class MainPresenter implements MainContract.presenter {
    private MainContract.view view;
    List<DataDiri> list;

    public MainPresenter(MainContract.view view) {
        this.view = view;
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void insertData(String nama, String alamat, char gender, final AppDatabase database) {
        final DataDiri dataDiri = new DataDiri();
        dataDiri.setAdress(alamat);
        dataDiri.setGender(gender);
        dataDiri.setName(nama);
        new AsyncTask<Void, Void, Long>(){

            @Override
            protected Long doInBackground(Void... voids) {
                return database.dao().insertData(dataDiri);
            }

            @Override
            protected void onPostExecute(Long aLong) {
                super.onPostExecute(aLong);
                view.successAdd();
            }
        }.execute();
    }

    @Override
    public void readData(AppDatabase database) {
        list = new ArrayList<>();
        list.addAll(Arrays.asList(database.dao().getData()));
        view.getData(list);
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void editData(String nama, String alamat, char gender, int id, final AppDatabase database) {
        final DataDiri dataDiri = new DataDiri();
        dataDiri.setAdress(alamat);
        dataDiri.setGender(gender);
        dataDiri.setName(nama);
        dataDiri.setId(id);
        new AsyncTask<Void, Void, Integer>(){

            @Override
            protected Integer doInBackground(Void... voids) {
                return database.dao().updateData(dataDiri);
            }

            @Override
            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);
                view.successAdd();
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void deleteData(final DataDiri dataDiri, final AppDatabase database) {
        new AsyncTask<Void, Void, Void>(){

            @Override
            protected Void doInBackground(Void... voids) {
                database.dao().deleteData(dataDiri);
                return  null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                view.successDelete();
            }
        }.execute();
    }

}
