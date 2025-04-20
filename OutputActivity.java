

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class OutputActivity extends AppCompatActivity {

    private TextView tvOutput;
    private DatabaseHelper databaseHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output);


        tvOutput = findViewById(R.id.tv_output);


        databaseHelper = new DatabaseHelper(this);


        Cursor cursor = databaseHelper.getAllData();
        if (cursor.getCount() == 0) {
            tvOutput.setText("No bookings found.");
            return;
        }


        StringBuilder output = new StringBuilder();
        while (cursor.moveToNext()) {
            output.append("Booking ID: ").append(cursor.getInt(0)).append("\n");
            output.append("Name: ").append(cursor.getString(1)).append("\n");
            output.append("Contact: ").append(cursor.getString(2)).append("\n");
            output.append("Service: ").append(cursor.getString(3)).append("\n");
            output.append("City: ").append(cursor.getString(4)).append("\n");
            output.append("Date: ").append(cursor.getString(5)).append("\n");
            output.append("Time: ").append(cursor.getString(6)).append("\n\n");
        }

        tvOutput.setText(output.toString());
    }
}
