package edu.bluejack16_2.familysdaily;


import android.app.DatePickerDialog;
import android.app.Dialog;
import java.util.Calendar;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import edu.bluejack16_2.familysdaily.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {


    public DatePickerFragment() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = dayOfMonth+"/"+month+"/"+year;
        try {
            EditText etDOB = (EditText) getActivity().findViewById(R.id.etDOB);
            etDOB.setText(date);
        }catch(Exception e){
            try {
                EditText etDOB = (EditText) getActivity().findViewById(R.id.etAddNotesExpiredDateExpired);
                etDOB.setText(date);
            }catch(Exception e2){
                try {
                    EditText etDOB = (EditText) getActivity().findViewById(R.id.etAddScheduleDate);
                    etDOB.setText(date);
                }catch(Exception e3){
                    Toast.makeText(getContext(), "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
