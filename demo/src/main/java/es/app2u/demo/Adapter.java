package es.app2u.demo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;



public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    public interface DialogsAdapterInterface{
        void onClick(int position);
    }

    private List <String> dialogsTitleList;
    private DialogsAdapterInterface observer;

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

    //================================================================================
    //region ViewHolder
    //================================================================================

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txvTitle;

        ViewHolder(View v) {
            super(v);
            txvTitle = v.findViewById(R.id.dialog_demo_cell_title);
        }

        private void bind(final String title) {
            txvTitle.setText(title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    observer.onClick(getAdapterPosition());
                }
            });
        }
    }
    //endregion
}
