package com.example.notes;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import org.w3c.dom.Text;
import java.text.DateFormat;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.Realm;
import io.realm.RealmResults;


public class mAdapter extends RecyclerView.Adapter<mAdapter.myviewHolder> {
    Context context;
    RealmResults<Note> notesList;

    public mAdapter(Context context, RealmResults<Note> notesList) {
        this.context = context;
        this.notesList = notesList;
    }

    @NonNull
    @Override
    public myviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new myviewHolder(LayoutInflater.from(context).inflate(R.layout.item_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull mAdapter.myviewHolder holder, int position) {
        Note note =  notesList.get(position);
        holder.titleOutput.setText(note.getTitle());
        holder.descOutput.setText(note.getDescription());
        String convDate = DateFormat.getDateInstance().format(note.timecreated);
        holder.timeOutput.setText(convDate);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupMenu menu = new PopupMenu(context,v);
                menu.getMenu().add("DELETE");
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getTitle().equals("DELETE")){
                            Realm realm = Realm.getDefaultInstance();
                            realm.beginTransaction();
                            note.deleteFromRealm();
                            realm.commitTransaction();
                            Toast.makeText(context,"Nota Borrada", Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }
                });
                        menu.show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public class myviewHolder extends RecyclerView.ViewHolder {
        TextView titleOutput;
        TextView descOutput;
        TextView timeOutput;


        public myviewHolder (@NonNull View itemView){
            super(itemView);
            titleOutput = itemView.findViewById(R.id.showtitle);
            descOutput = itemView.findViewById(R.id.shownote);
            timeOutput = itemView.findViewById(R.id.showdate);
        }
    }
}
