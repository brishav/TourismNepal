package brishavshakya.com.tourismnepal;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class DescriptionActivity extends AppCompatActivity {
    String Description,Image,Name;
    ImageView img;
    TextView title , desp;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.description_main);
        Intent intent = getIntent();
        Description = intent.getStringExtra("Description");
        Image = intent.getStringExtra("Image");
        Name = intent.getStringExtra("Name");
        img = findViewById(R.id.location_image);
        title = findViewById(R.id.location_name);
        desp = findViewById(R.id.location_description);
        title.setText(Name);
        Picasso.with(getApplicationContext())
                .load(Image)
                .into(img);
        desp.setText(Description);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bar, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:

                new AlertDialog.Builder(this)
                        .setTitle("Log Out")
                        .setMessage("Are you sure you wanna log out?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }})
                        .setNegativeButton(android.R.string.no, null).show();
                break;
            case R.id.setting:
                Toast.makeText(getApplicationContext(),"Setting menu",Toast.LENGTH_SHORT).show();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
