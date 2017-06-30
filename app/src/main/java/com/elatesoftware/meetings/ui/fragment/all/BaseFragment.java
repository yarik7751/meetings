package com.elatesoftware.meetings.ui.fragment.all;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.elatesoftware.meetings.util.AndroidUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BaseFragment extends Fragment {

    protected Unbinder unbinder;

    protected ProgressDialog progressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        hideSoftKeyboard();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        unbinder = ButterKnife.bind(this, view);
        AndroidUtils.hideKeyboard(getActivity());
    }

    @Override
    public void onPause() {
        super.onPause();
        hideSoftKeyboard();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(unbinder != null) unbinder.unbind();
        progressDialog.dismiss();
    }

    /**
     * получить строку из ресурсов
     * @param res - id ресурса
     * @return - строка
     */
    protected String getStringRes(int res) {
        return getResources().getString(res);
    }

    /**
     * получить цвет по id
     * @param res
     * @return
     */

    protected int getColor(int res) {
        return getResources().getColor(res);
    }

    /**
     * скрыть клавиатуру
     */
    public void hideSoftKeyboard() {
        if (getActivity().getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
     * Показать клавиатуру на view
     * @param view
     */
    public void showSoftKeyboard(View view) {
        if (getActivity() != null && view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager)
                    getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.showSoftInput(view, 0);
        }
    }

    /**
     * Показать клавиатуру на editText
     * @param editText
     */
    public void showSoftKeyboard(EditText editText) {
        if (getActivity() != null && editText != null) {
            InputMethodManager inputMethodManager = (InputMethodManager)
                    getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    /**
     * жив ли фрагмент
     * @return
     */
    protected boolean isFragmentAlive() {
        return getActivity() != null && isAdded() && !isDetached() && !isRemoving();
    }

    /**
     * показать Toast сообщение из ресурсов
     * @param strRes - строка в ресурсах
     */
    protected void showMessage(@StringRes int strRes) {
        if (isFragmentAlive()) {
            Toast.makeText(getContext(), strRes, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * показать Toast сообщение
     * @param strRes - текст сообщения
     */
    protected void showMessage(String strRes) {
        if (isFragmentAlive()) {
            Toast.makeText(getContext(), strRes, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Устанавливает сообщение в диалог ожидания
     * @param message
     */
    protected void setProgressDialogMessage(String message) {
        progressDialog.setMessage(message);
    }

    /**
     * Показывает диалог ожидания
     */
    protected void showProgressDialog() {
        progressDialog.show();
    }

    /**
     * скрывает диалог ожидания
     */
    protected void hideProgressDialog() {
        progressDialog.hide();
    }
}
