package edu.bluejack16_2.familysdaily;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import edu.bluejack16_2.familysdaily.models.Note;

/**
 * Created by fidel on 31-Jul-17.
 */

public class NotesListViewAdapter extends BaseAdapter {

    ArrayList<Note> notes;
    ArrayList<String> noteKeys;
    Context context;

    public class ViewHolder{
        TextView tvNoteDetail;
    }

    public NotesListViewAdapter(Context context){
        notes = new ArrayList<>();
        noteKeys = new ArrayList<>();
        this.context = context;
    }

    public void refresh(){
        notes.clear();
    }

    public void add(Note note, String noteKey){
        notes.add(note);
        noteKeys.add(noteKey);
    }

    @Override
    public int getCount() {
        return notes.size();
    }

    @Override
    public Note getItem(int position) {
        return notes.get(position);
    }

    public String getKey(int position){
        return noteKeys.get(position);
    }

    public ArrayList<Note> getNotes() {
        return notes;
    }

    public void setNotes(ArrayList<Note> notes) {
        this.notes = notes;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mainViewHolder = null;

        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.listview_row_notes, parent, false);

            ViewHolder viewHolder = new ViewHolder();

            viewHolder.tvNoteDetail = (TextView) convertView.findViewById(R.id.tvNoteDetail);

            viewHolder.tvNoteDetail.setText(notes.get(position).getNotesDetail());

            convertView.setTag(viewHolder);
        }
        else{
            mainViewHolder = (ViewHolder)convertView.getTag();
            mainViewHolder.tvNoteDetail.setText(notes.get(position).getNotesDetail());
        }

        return convertView;
    }
}
