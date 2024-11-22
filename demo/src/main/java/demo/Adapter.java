package demo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ivanpg93.demo.R;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    public interface DialogsAdapterInterface{
        void onClick(int position);
    }

    private final List <String> dialogsTitleList;
    private final DialogsAdapterInterface observer;

    public Adapter (List <String> dialogsTitleList, DialogsAdapterInterface observer){
       this.dialogsTitleList = dialogsTitleList;
       this.observer = observer;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dialog_demo_cell, viewGroup,  false);
        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(dialogsTitleList.get(i));
    }

    @Override
    public int getItemCount() {
        return dialogsTitleList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView txvTitle;

        ViewHolder(View v) {
            super(v);
            txvTitle = v.findViewById(R.id.dialog_demo_cell_title);
        }

        private void bind(final String title) {
            txvTitle.setText(title);
            itemView.setOnClickListener(view -> observer.onClick(getAdapterPosition()));
        }
    }

}
