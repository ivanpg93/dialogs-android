package es.ivanpg93.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Dialog {

    private static final int PROGRESS_BAR_MAX = 100;

    private static ProgressDialog progressDialog;
    private static ProgressDialog progressBarDialog;
    private static AlertDialog loadingDialog;

    //Change this variable to true if you what to be aware of context when show or dismiss dialogs. Theoretically, if
    // you control the // livecycle you don't need this check
    private static boolean checkContextState = true;

    private Dialog() {
        throw new IllegalStateException("Utility class");
    }

    protected static void init(DialogBuilder builder) {

        // Avoid to show if the activity is finishing
        if (checkContextState && !isContextAlive(builder.context)) {
            return;
        }

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(builder.context);
        alertBuilder.setTitle(builder.getTitle());
        alertBuilder.setCancelable(builder.cancelable);
        alertBuilder.setPositiveButton(builder.getBtnOK(), builder.listenerOK);
        alertBuilder.setNegativeButton(builder.getBtnNOK(), builder.listenerNOK);
        alertBuilder.setNeutralButton(builder.getBtnNeutral(), builder.listenerNeutral);
        alertBuilder.setOnDismissListener(builder.onDismissListener);

        // Its incompatible to have a dialog with a list and a message at the same time.
        boolean isDialogWithAList = builder.list.length != 0;
        if (!isDialogWithAList) {
            alertBuilder.setMessage(builder.getMessage());
        } else {

            switch (builder.type) {
                case SINGLE_CHOICE:
                    alertBuilder.setItems(builder.list, builder.listenerList);
                    break;
                case RADIO_BUTTON:
                    alertBuilder.setSingleChoiceItems(builder.list, builder.selectedItem, builder.listenerList);
                    break;
                case CHECKBOX:
                    boolean[] checkedItems = builder.checkedItems;

                    if (checkedItems == null) {
                        checkedItems = new boolean[builder.list.length];
                    }

                    alertBuilder.setMultiChoiceItems(builder.list, checkedItems, builder.listenerMultiChoiceList);
                    break;
            }
        }

        View layout = builder.customLayout;

        if (layout == null) {
            alertBuilder.create().show();
            return;
        }

        if (layout.getParent() != null) {
            ((ViewGroup) layout.getParent()).removeView(layout);
        }

        alertBuilder.setView(layout);
        alertBuilder.create().show();
    }

    //================================================================================
    //region Configuration

    public static void setCheckContextState(boolean checkContextState) {
        Dialog.checkContextState = checkContextState;
    }

    //endregion
    //================================================================================
    //region Progress Dialog

    public static void showProgressDialog(Context context, String message) {

        // Avoid to show if the activity is finishing
        if (checkContextState && !isContextAlive(context)) {
            return;
        }

        // if there is a progress dialog showing, only update message
        if (isDialogShowing(progressDialog)) {
            progressDialog.setMessage(message);
            return;
        }

        // Creates a new dialog for passed context
        progressDialog = ProgressDialog.show(context, null, message, true);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
    }

    public static void showProgressDialog(Context context) {
        showProgressDialog(context, context.getString(R.string.dialog_loading));
    }

    public static void cancelProgressDialog() {
        cancelDialog(progressDialog);
    }

    //endregion
    //================================================================================
    //region Loading Dialog

    public static void showLoadingDialog(Context context) {

        // if there is a progress dialog showing, don't show again. If the activity is finishing don't show either
        if (checkContextState && !isContextAlive(context) || isDialogShowing(loadingDialog)) {
            return;
        }

        AlertDialog.Builder dialogBuilder = new AlertDialog
                .Builder(context, R.style.loadingDialogStyle)
                .setCancelable(false);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.fragment_loading_dialog, null);
        dialogBuilder.setView(dialogView);
        loadingDialog = dialogBuilder.create();
        loadingDialog.show();
    }

    public static void cancelLoadingDialog() {
        cancelDialog(loadingDialog);
    }

    //endregion
    //================================================================================
    //region Progress Bar Dialog

    public static void showProgressBarDialog(Context context, String message, int progress, int max,
                                             @Nullable DialogInterface.OnClickListener listenerCancel) {

        if (checkContextState && !isContextAlive(context)) {
            return;
        }

        // if it is showing update values
        if (isDialogShowing(progressBarDialog)) {
            progressBarDialog.setProgress(progress);
            progressBarDialog.setMessage(message);

            if (listenerCancel != null) {
                progressBarDialog.setButton(DialogInterface.BUTTON_NEGATIVE,
                        context.getString(R.string.dialog_cancel), listenerCancel);
            }

            return;
        }


        progressBarDialog = new ProgressDialog(context);
        progressBarDialog.setIndeterminate(false);
        progressBarDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressBarDialog.setCancelable(false);
        progressBarDialog.setMax(PROGRESS_BAR_MAX);
        progressBarDialog.setCanceledOnTouchOutside(false);


        double progressValue = (progress / (max * 1.0)) * PROGRESS_BAR_MAX;
        progress = (int) progressValue;
        progressBarDialog.setProgress(progress);
        progressBarDialog.setMessage(message);

        if (listenerCancel != null) {
            progressBarDialog.setButton(DialogInterface.BUTTON_NEGATIVE,
                    context.getString(R.string.dialog_cancel), listenerCancel);
        }

        progressBarDialog.show();
    }

    public static void cancelProgressBarDialog() {
        cancelDialog(progressBarDialog);
    }

    //endregion
    //================================================================================
    //region Predefined Alert

    public static void showDialog(Context context, String title, String message) {
        new DialogBuilder()
                .title(title)
                .message(message)
                .btnOK(android.R.string.ok)
                .build(context);
    }


    public static void showErrorDialog(Context context, String message) {
        showDialog(context, context.getString(R.string.dialog_error), message);
    }

    public static void showAlertDialog(Context context, String message) {
        showDialog(context, context.getString(R.string.dialog_warning), message);
    }

    public static void showSuccessDialog(Context context, String message) {
        showDialog(context, context.getString(R.string.dialog_success), message);
    }

    //endregion
    //================================================================================
    //region private

    private static void cancelDialog(android.app.Dialog dialog) {
        //check if dialog is showing.
        if (dialog != null && dialog.isShowing()) {

            //get the Context object that was used to create the dialog
            Context context = ((ContextWrapper) dialog.getContext()).getBaseContext();

            //if the Context used here was an activity AND it hasn't been finished or destroyed
            //then dismiss it
            if (checkContextState && !isContextAlive(context)) {
                return;
            }

            dialog.cancel();
        }
    }

    // Used to check if a dialog can be shown o can be canceled
    private static boolean isContextAlive(Context context) {

        if (!(context instanceof Activity)) {
            return true;
        }

        boolean isNotFinishing = !((Activity) context).isFinishing();
        boolean isNotDestroyed = !((Activity) context).isDestroyed();

        return isNotFinishing && isNotDestroyed;
    }

    private static boolean isDialogShowing(@Nullable AlertDialog dialog) {
        return dialog != null && dialog.isShowing();
    }
}
