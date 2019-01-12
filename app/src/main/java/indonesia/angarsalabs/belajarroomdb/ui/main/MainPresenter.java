package indonesia.angarsalabs.belajarroomdb.ui.main;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import indonesia.angarsalabs.belajarroomdb.entity.AppDatabase;
import indonesia.angarsalabs.belajarroomdb.entity.DataDiri;

/**
 * Angarsa Labs...!
 * Created by Angga on 13/08/2018.
 */
public class MainPresenter implements MainContract.presenter {
    private MainContract.view view;

    public MainPresenter(MainContract.view view) {
        this.view = view;
    }

    class InsertData extends AsyncTask<Void, Void, Long>{
        private AppDatabase database;
        private DataDiri dataDiri;

        public InsertData(AppDatabase database, DataDiri dataDiri) {
            this.database = database;
            this.dataDiri = dataDiri;
        }

        @Override
        protected Long doInBackground(Void... voids) {
            return database.dao().insertData(dataDiri);
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
            view.successAdd();
        }

    }

    @Override
    public void insertData(String nama, String alamat, char gender,
                           final AppDatabase database) {
        final DataDiri dataDiri = new DataDiri();
        dataDiri.setAdress(alamat);
        dataDiri.setGender(gender);
        dataDiri.setName(nama);
        new InsertData(database, dataDiri).execute();
    }

    @Override
    public void readData(AppDatabase database) {
        List list;
        list = database.dao().getData();
        view.getData(list);
    }

    class EditData extends AsyncTask<Void, Void, Integer> {
        private AppDatabase database;
        private DataDiri dataDiri;

        public EditData(AppDatabase database, DataDiri dataDiri) {
            this.database = database;
            this.dataDiri = dataDiri;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            return database.dao().updateData(dataDiri);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            Log.d("integer db", "onPostExecute: " + integer);
            view.successAdd();
        }
    }

    @Override
    public void editData(String nama, String alamat, char gender, int id,
                         final AppDatabase database) {
        final DataDiri dataDiri = new DataDiri();
        dataDiri.setAdress(alamat);
        dataDiri.setGender(gender);
        dataDiri.setName(nama);
        dataDiri.setId(id);

        new EditData(database, dataDiri).execute();
    }

    class DeleteData extends AsyncTask<Void, Void, Void>{
        private AppDatabase database;
        private DataDiri dataDiri;

        public DeleteData(AppDatabase database, DataDiri dataDiri) {
            this.database = database;
            this.dataDiri = dataDiri;
        }

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

    }

    @Override
    public void deleteData(final DataDiri dataDiri,
                           final AppDatabase database) {
        new DeleteData(database, dataDiri).execute();
    }

}
