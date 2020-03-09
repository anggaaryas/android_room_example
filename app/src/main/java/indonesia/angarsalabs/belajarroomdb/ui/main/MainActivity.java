package indonesia.angarsalabs.belajarroomdb.ui.main;

import android.content.DialogInterface;
import android.os.Build;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

/*import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;*/

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.List;

import indonesia.angarsalabs.belajarroomdb.R;
import indonesia.angarsalabs.belajarroomdb.entity.AppDatabase;
import indonesia.angarsalabs.belajarroomdb.entity.DataDiri;
import indonesia.angarsalabs.belajarroomdb.ui.main.holder.MainAdapter;

public class MainActivity extends AppCompatActivity implements MainContract.view {
    private AppDatabase appDatabase;
    private MainPresenter presenter;
    private MainAdapter adapter;

    private Button btnOK;
    private RecyclerView recyclerView;
    private EditText tvNama, tvAlamat;
    private RadioButton rbLaki, rbPerempuan;
    private RadioGroup radioGroup;

    private char gender;
    private boolean edit = false;
    private int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appDatabase = AppDatabase.iniDb(getApplicationContext());

        btnOK = findViewById(R.id.btn_submit);
        btnOK.setOnClickListener(this);
        tvNama = findViewById(R.id.et_nama);
        tvAlamat = findViewById(R.id.et_alamat);
        rbLaki = findViewById(R.id.rb_laki);
        rbPerempuan = findViewById(R.id.rb_perempuan);
        recyclerView = findViewById(R.id.rc_main);
        radioGroup = findViewById(R.id.rg_main);

     /*   FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(this);
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);
        flexboxLayoutManager.setJustifyContent(JustifyContent.FLEX_START);

        recyclerView.setLayoutManager(flexboxLayoutManager);*/

     // TODO: Bisa pakai flexbox, bisa pakai Linear Layout Manager. Silahkan dicoba coba

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        presenter = new MainPresenter(this);

        presenter.readData(appDatabase);
    }

    @Override
    public void successAdd() {
        Toast.makeText(this, "Berhasil", Toast.LENGTH_SHORT).show();
        presenter.readData(appDatabase);
    }

    @Override
    public void successDelete() {
        Toast.makeText(this, "Berhasil menghapus data", Toast.LENGTH_SHORT).show();
        presenter.readData(appDatabase);
    }

    @Override
    public void resetForm() {
        tvNama.setText("");
        tvAlamat.setText("");
        radioGroup.clearCheck();
        btnOK.setText("submit");
    }

    @Override
    public void getData(List<DataDiri> list) {
        // Setelah mendapat data dari Database, tampilkan ulang ke recycle view
        adapter = new MainAdapter(this, list, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void editData(DataDiri item) {
        tvNama.setText(item.getName());
        tvAlamat.setText(item.getAdress());
        id = item.getId();

        // Jika data sebelumnya Laki-laki, check yg laki laki, kalau tidak, check yang perenpuan
        if(String.valueOf(item.getGender()).equals("L")){
            rbLaki.setChecked(true);
        } else rbPerempuan.setChecked(true);

        // var edit untuk memberi tahu bahwa button nya sedang mode edit
        // cek penggunaannya dibagian fungsi onClick
        edit = true;
        btnOK.setText("Update");
    }

    @Override
    public void deleteData(final DataDiri item) {
        // Saat RecycleView di pencet lama, akan muncul dialog nontifikasi
        // apakah ingin dihapus atau tidak
        // menggunakan Allert dialog

        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle("Menghapus Data")
                .setMessage("Anda yakin ingin menghapus data ini?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        resetForm();
                        presenter.deleteData(item, appDatabase);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    public void onClick(View view) {
        if(view == btnOK){
            if(tvNama.getText().toString().equals("") || tvAlamat.getText().toString().equals("") || radioGroup.getCheckedRadioButtonId() == -1) {
                Toast.makeText(this, "Harap isi semua data", Toast.LENGTH_SHORT).show();
            } else {

                if (rbLaki.isChecked()) {
                    gender = 'L';
                } else if (rbPerempuan.isChecked()) {
                    gender = 'P';
                }

                if(!edit) presenter.insertData(tvNama.getText().toString(), tvAlamat.getText().toString(), gender, appDatabase);
                else{
                    // Jika mode edit, panggil fungsi edit DB
                    presenter.editData(tvNama.getText().toString(), tvAlamat.getText().toString(), gender, id, appDatabase);
                    edit = false;
                }
                resetForm();
            }
        }
    }
}
