package ivanpg93.dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.View;

public class DialogBuilder {

    public enum ListType {
        SINGLE_CHOICE,
        RADIO_BUTTON,
        CHECKBOX
    }

    protected String titleStr;
    protected Integer titleInt;
    protected String messageStr;
    protected Integer messageInt;
    protected String[] list = new String[0];
    protected ListType type = ListType.SINGLE_CHOICE;
    protected int selectedItem = 0;
    protected boolean[] checkedItems;
    protected String btnOKStr;
    protected Integer btnOKInt;
    protected String btnNOKStr;
    protected Integer btnNOKInt;
    protected String btnNeutralStr;
    protected Integer btnNeutralInt;
    protected DialogInterface.OnClickListener listenerOK;
    protected DialogInterface.OnClickListener listenerNOK;
    protected DialogInterface.OnClickListener listenerNeutral;
    protected DialogInterface.OnClickListener listenerList;
    protected DialogInterface.OnMultiChoiceClickListener listenerMultiChoiceList;
    protected DialogInterface.OnDismissListener onDismissListener;
    protected boolean cancelable = false;
    protected View customLayout;
    protected Context context;

    public void build(Context context) {
        this.context = context;
        Dialog.init(this);
    }

    public DialogBuilder title(String titleStr) {
        this.titleStr = titleStr;
        return this;
    }

    public DialogBuilder title(@StringRes int titleInt) {
        this.titleInt = titleInt;
        return this;
    }

    public DialogBuilder message(String messageStr) {
        this.messageStr = messageStr;
        return this;
    }

    public DialogBuilder message(@StringRes int messageInt) {
        this.messageInt = messageInt;
        return this;
    }

    public DialogBuilder list(String[] list) {
        this.list = list;
        return this;
    }

    public DialogBuilder type(ListType type) {
        this.type = type;
        return this;
    }

    public DialogBuilder selectedItem(int selectedItem) {
        this.selectedItem = selectedItem;
        return this;
    }

    public DialogBuilder btnOK(String btnOKStr) {
        this.btnOKStr = btnOKStr;
        return this;
    }

    public DialogBuilder btnOK(@StringRes int btnOKInt) {
        this.btnOKInt = btnOKInt;
        return this;
    }

    public DialogBuilder btnNOK(String btnNOKStr) {
        this.btnNOKStr = btnNOKStr;
        return this;
    }

    public DialogBuilder btnNOK(@StringRes int btnNOKInt) {
        this.btnNOKInt = btnNOKInt;
        return this;
    }

    public DialogBuilder btnNeutral(String btnNeutralStr) {
        this.btnNeutralStr = btnNeutralStr;
        return this;
    }

    public DialogBuilder btnNeutral(@StringRes int btnNeutralInt) {
        this.btnNeutralInt = btnNeutralInt;
        return this;
    }

    public DialogBuilder listenerOK(DialogInterface.OnClickListener listenerOK) {
        this.listenerOK = listenerOK;
        return this;
    }

    public DialogBuilder listenerNOK(DialogInterface.OnClickListener listenerNOK) {
        this.listenerNOK = listenerNOK;
        return this;
    }

    public DialogBuilder listenerNeutral(DialogInterface.OnClickListener listenerNeutral) {
        this.listenerNeutral = listenerNeutral;
        return this;
    }

    public DialogBuilder listenerList(DialogInterface.OnClickListener listenerList) {
        this.listenerList = listenerList;
        return this;
    }

    public DialogBuilder listenerMultiChoiceList(DialogInterface.OnMultiChoiceClickListener listenerMultiChoiceList) {
        this.listenerMultiChoiceList = listenerMultiChoiceList;
        return this;
    }

    public DialogBuilder onDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
        return this;
    }

    public DialogBuilder cancelable(boolean cancelable) {
        this.cancelable = cancelable;
        return this;
    }

    public DialogBuilder customLayout(View customLayout) {
        this.customLayout = customLayout;
        return this;
    }

    protected String getTitle() {
        return createOutput(titleStr, titleInt);
    }

    protected String getMessage() {
        return createOutput(messageStr, messageInt);
    }

    protected String getBtnOK() {
        return createOutput(btnOKStr, btnOKInt);
    }

    protected String getBtnNOK() {
        return createOutput(btnNOKStr, btnNOKInt);
    }

    protected String getBtnNeutral() {
        return createOutput(btnNeutralStr, btnNeutralInt);
    }

    private String createOutput(@Nullable String strVariable, @Nullable @StringRes Integer intVariable) {
        if (strVariable != null) {
            return strVariable;
        } else if (context != null && intVariable != null) {
            return context.getString(intVariable);
        } else {
            return "";
        }
    }
}
