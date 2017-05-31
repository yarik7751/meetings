package com.elatesoftware.meetings.ui.activity.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.elatesoftware.meetings.util.AndroidUtils;

import butterknife.ButterKnife;

public class BaseActivity extends AppCompatActivity {

    protected ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(this);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        AndroidUtils.hideKeyboard(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        progressDialog.dismiss();
    }

    /**
     * Смена фрагмента в FrameLayout на Activity
     * @param fragment - объект фрагмента
     * @param tag - тег
     * @param add - добавлять ли фрагмент в стек
     * @param anim - использовать ли анимацию
     * @param res - id FrameLayout
     */
    protected void onSwitchFragment(Fragment fragment, String tag, boolean add, boolean anim, int res) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction tr = fm.beginTransaction();
        if(anim) {
            tr.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        }
        tr.replace(res, fragment, tag);
        if (add) {
            //todo 4
            try {
                tr.addToBackStack(tag);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        tr.commit();
    }

    /**
     * показать Toast сообщение из ресурсов
     * @param strRes - строка в ресурсах
     */
    protected void showMessage(@StringRes int strRes) {
        Toast.makeText(this, strRes, Toast.LENGTH_SHORT).show();
    }

    /**
     * показать Toast сообщение
     * @param strRes - текст сообщения
     */
    protected void showMessage(String strRes) {
        Toast.makeText(this, strRes, Toast.LENGTH_SHORT).show();
    }

    /**
     * Получение цвета по ресурсу
     * @param res
     * @return
     */
    protected int getColorRes(int res) {
        return getResources().getColor(res);
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
