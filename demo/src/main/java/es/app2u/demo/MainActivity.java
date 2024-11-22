package es.app2u.demo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import es.app2u.dialogs.Dialog;
import es.app2u.dialogs.DialogBuilder;

public class MainActivity extends AppCompatActivity implements Adapter.DialogsAdapterInterface {

    private static final String TAG = "Demo";
    private static final int DELAY = 2000;
    private String [] list;
    private View customLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button goAuxiliaryButton = findViewById(R.id.goAuxiliaryButton);
        goAuxiliaryButton.setOnClickListener(v -> {
            finish();
            Intent intent = new Intent(MainActivity.this, AuxiliaryActivity.class);
            startActivity(intent);
        });

        // Add Dialogs List
        List <String> dialogsList = new ArrayList<>();
        dialogsList.add("Progress Dialog");
        dialogsList.add("Update Progress Dialog");
        dialogsList.add("Progress Bar Dialog");
        dialogsList.add("Dialog amb Title i Message");
        dialogsList.add("Dialog 3 buttons i listeners");
        dialogsList.add("Llista simple");
        dialogsList.add("Llista amb Radiobutons");
        dialogsList.add("Multiselectable llista");
        dialogsList.add("Error Dialog");
        dialogsList.add("Alert Dialog");
        dialogsList.add("Success Dialog");
        dialogsList.add("Loading Dialog");
        dialogsList.add("Custom Layout");
        dialogsList.add("Altres");

        //Dialog list
        list = new String[3];
        list[0] = "vaca";
        list[1] = "pollo";
        list[2] = "ovella";

        //Custom View Dialog
        customLayout = getLayoutInflater().inflate(R.layout.custom_dialog_layout, null);


        final Adapter adapter = new Adapter(dialogsList, this);
        RecyclerView recyclerView = findViewById(R.id.demo_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        // Set to false if you don't want to be aware of context
        Dialog.setCheckContextState(true);

    }

    //================================================================================
    //region Dialog Adapter Interface

    @Override
    public void onClick(int position) {
        Dialog.cancelProgressDialog();
        Dialog.cancelProgressBarDialog();
        final Handler handler = new Handler(Looper.getMainLooper());

        switch (position){
            case 0:
                Dialog.showProgressDialog(this, "Progress Dialog");
                handler.postDelayed(Dialog::cancelProgressDialog, DELAY);
                break;
            case 1:
                Dialog.showProgressDialog(this, "Progress Dialog");
                handler.postDelayed(() ->  Dialog.showProgressDialog(this, "Progress Dialog 2"), DELAY);
                handler.postDelayed(Dialog::cancelProgressDialog, DELAY*2);
                break;
            case 2:
                Dialog.showProgressBarDialog(this, "Progress Bar Dialog", 30, 100, (dialogInterface, i) -> Dialog.cancelProgressBarDialog());
                handler.postDelayed(() ->  Dialog.showProgressBarDialog(this, "Progress Bar Dialog 2", 60, 100,
                        (dialogInterface, i) -> Dialog.cancelProgressBarDialog()), DELAY);
                break;

            case 3: // Title + Message
                 Dialog.showDialog(this, "title", "Message");
                break;
            case 4:
                threeButtonDialog();
                break;
            case 5:
                simpleListDialog();
                break;
            case 6:
                radioButtonListDialog();
                break;
            case 7:
               multiChoiceListDialog();
                break;
            case 8:
                Dialog.showErrorDialog(this, "És un error dialog");
                break;
            case 9:
                Dialog.showAlertDialog(this,"És un alert dialog");
                break;
            case 10:
                Dialog.showSuccessDialog(this,"És un success dialog");
                break;
            case 11:
                Dialog.showLoadingDialog(this);
                handler.postDelayed(() ->  Dialog.showLoadingDialog(this), DELAY);
                handler.postDelayed(Dialog::cancelLoadingDialog, DELAY*2);
                break;
            case 12:
                customViewDialog();
                break;
            default:
               fullCustomizedDialog();
        }
    }

    private void threeButtonDialog() {
        new DialogBuilder()
                .title("title")
                .message("Message")
                .btnOK("ok")
                .btnNOK("cancel")
                .btnNeutral("remind me later")
                .listenerOK((dialog, which) -> Log.d(TAG, "Ok pressed"))
                .listenerNOK((dialog, which) -> Log.d(TAG, "Cancel pressed"))
                .listenerNeutral((dialog, which) -> Log.d(TAG, "Neutral pressed"))
                .cancelable(false)
                .build(this);
    }

    private void simpleListDialog() {
        new DialogBuilder()
                .title("title")
                .list(list)
                .listenerList((dialog, which) -> Log.d(TAG, "Pressed position: " + which) )
                .cancelable(false)
                .build(this);
    }

    private void radioButtonListDialog() {
        new DialogBuilder()
                .title("title")
                .type(DialogBuilder.ListType.RADIO_BUTTON)
                .selectedItem(1)
                .list(list)
                .listenerList((dialog, which) -> Log.d(TAG, "Pressed position: " + which) )
                .btnOK("ok")
                .btnNOK("cancel")
                .cancelable(false)
                .build(this);
    }

    private void multiChoiceListDialog() {
        new DialogBuilder()
                .title("title")
                .type(DialogBuilder.ListType.CHECKBOX)
                .list(list)
                .listenerMultiChoiceList((DialogInterface dialog, int which, boolean isChecked) -> Log.d(TAG, "Pressed position: " + which + " is selected: "+ isChecked))
                .btnOK("ok")
                .btnNOK("cancel")
                .cancelable(false)
                .build(this);
    }

    private void customViewDialog() {
        new DialogBuilder()
                .title("title")
                .message("Message")
                .customLayout(customLayout)
                .btnOK("Ok")
                .listenerOK((dialog, which) -> {
                    EditText editText = customLayout.findViewById(R.id.editText);
                    Log.d(TAG, editText.getText().toString());
                })
                .cancelable(false)
                .build(this);
    }

    private void fullCustomizedDialog() {
        new DialogBuilder()
                .title("title")
                .type(DialogBuilder.ListType.RADIO_BUTTON)
                .list(list)
                .selectedItem(2)
                .customLayout(customLayout)
                .btnOK("ok")
                .btnNOK("cancel")
                .btnNeutral("remind me later")
                .listenerOK((dialog, which) -> Log.d(TAG, "Ok pressed"))
                .listenerNOK((dialog, which) -> Log.d(TAG, "Cancel pressed"))
                .listenerNeutral((dialog, which) -> Log.d(TAG, "Neutral pressed"))
                .listenerList((dialog, which) -> Log.d(TAG, "Pressed position: " + which) )
                .onDismissListener(dialog -> Log.d(TAG, "Dismissed"))
                .cancelable(false)
                .build(this);
    }


    //endregion
    //================================================================================


}
