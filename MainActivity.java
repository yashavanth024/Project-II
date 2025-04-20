

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private EditText etName, etContact;
    private Spinner spinnerService;
    private AutoCompleteTextView actvCity;
    private TextView tvSelectedDate, tvSelectedTime;
    private Button btnDatePicker, btnTimePicker, btnSubmit;

    private String selectedDate = "", selectedTime = "";
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        etName = findViewById(R.id.et_name);
        etContact = findViewById(R.id.et_contact);
        spinnerService = findViewById(R.id.spinner_service);
        actvCity = findViewById(R.id.actv_city);
        tvSelectedDate = findViewById(R.id.tv_selected_date);
        tvSelectedTime = findViewById(R.id.tv_selected_time);
        btnDatePicker = findViewById(R.id.btn_date_picker);
        btnTimePicker = findViewById(R.id.btn_time_picker);
        btnSubmit = findViewById(R.id.btn_submit);


        databaseHelper = new DatabaseHelper(this);


        String[] services = {"Interior Design", "Painting", "Furniture Setup", "Landscaping"};
        ArrayAdapter<String> serviceAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, services);
        spinnerService.setAdapter(serviceAdapter);


        String[] cities = {"New York", "Los Angeles", "Chicago", "Houston", "Phoenix"};
        ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, cities);
        actvCity.setAdapter(cityAdapter);


        btnDatePicker.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this,
                    (view, year1, month1, dayOfMonth) -> {
                        selectedDate = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
                        tvSelectedDate.setText("Selected Date: " + selectedDate);
                    },
                    year, month, day);
            datePickerDialog.show();
        });


        btnTimePicker.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    this,
                    (view, hourOfDay, minute1) -> {
                        selectedTime = hourOfDay + ":" + String.format("%02d", minute1);
                        tvSelectedTime.setText("Selected Time: " + selectedTime);
                    },
                    hour, minute, true);
            timePickerDialog.show();
        });


        btnSubmit.setOnClickListener(v -> {
            String name = etName.getText().toString();
            String contact = etContact.getText().toString();
            String service = spinnerService.getSelectedItem().toString();
            String city = actvCity.getText().toString();


            if (name.isEmpty() || contact.isEmpty() || city.isEmpty() || selectedDate.isEmpty() || selectedTime.isEmpty()) {
                Toast.makeText(this, "Please fill all the fields!", Toast.LENGTH_SHORT).show();
            } else {

                boolean isInserted = databaseHelper.insertData(name, contact, service, city, selectedDate, selectedTime);
                if (isInserted) {
                    Toast.makeText(this, "Booking Saved Successfully!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(this, OutputActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Failed to Save Booking!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
