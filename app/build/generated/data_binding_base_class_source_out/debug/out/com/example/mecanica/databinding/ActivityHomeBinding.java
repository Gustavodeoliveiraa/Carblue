// Generated by view binder compiler. Do not edit!
package com.example.mecanica.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.mecanica.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityHomeBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button addFuncionario;

  @NonNull
  public final Button addcar;

  @NonNull
  public final Button addclienteclick;

  @NonNull
  public final Button avaliacao;

  @NonNull
  public final BottomNavigationView bottomNavigation;

  @NonNull
  public final Button controleCaixa;

  @NonNull
  public final Button descServico;

  @NonNull
  public final Button esttoque;

  @NonNull
  public final Button getEstoque;

  @NonNull
  public final LinearLayout label1;

  @NonNull
  public final Button produtividade;

  @NonNull
  public final GridLayout seuConteinerDeFragment;

  private ActivityHomeBinding(@NonNull ConstraintLayout rootView, @NonNull Button addFuncionario,
      @NonNull Button addcar, @NonNull Button addclienteclick, @NonNull Button avaliacao,
      @NonNull BottomNavigationView bottomNavigation, @NonNull Button controleCaixa,
      @NonNull Button descServico, @NonNull Button esttoque, @NonNull Button getEstoque,
      @NonNull LinearLayout label1, @NonNull Button produtividade,
      @NonNull GridLayout seuConteinerDeFragment) {
    this.rootView = rootView;
    this.addFuncionario = addFuncionario;
    this.addcar = addcar;
    this.addclienteclick = addclienteclick;
    this.avaliacao = avaliacao;
    this.bottomNavigation = bottomNavigation;
    this.controleCaixa = controleCaixa;
    this.descServico = descServico;
    this.esttoque = esttoque;
    this.getEstoque = getEstoque;
    this.label1 = label1;
    this.produtividade = produtividade;
    this.seuConteinerDeFragment = seuConteinerDeFragment;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityHomeBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityHomeBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_home, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityHomeBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.addFuncionario;
      Button addFuncionario = ViewBindings.findChildViewById(rootView, id);
      if (addFuncionario == null) {
        break missingId;
      }

      id = R.id.addcar;
      Button addcar = ViewBindings.findChildViewById(rootView, id);
      if (addcar == null) {
        break missingId;
      }

      id = R.id.addclienteclick;
      Button addclienteclick = ViewBindings.findChildViewById(rootView, id);
      if (addclienteclick == null) {
        break missingId;
      }

      id = R.id.avaliacao;
      Button avaliacao = ViewBindings.findChildViewById(rootView, id);
      if (avaliacao == null) {
        break missingId;
      }

      id = R.id.bottom_navigation;
      BottomNavigationView bottomNavigation = ViewBindings.findChildViewById(rootView, id);
      if (bottomNavigation == null) {
        break missingId;
      }

      id = R.id.controleCaixa;
      Button controleCaixa = ViewBindings.findChildViewById(rootView, id);
      if (controleCaixa == null) {
        break missingId;
      }

      id = R.id.descServico;
      Button descServico = ViewBindings.findChildViewById(rootView, id);
      if (descServico == null) {
        break missingId;
      }

      id = R.id.esttoque;
      Button esttoque = ViewBindings.findChildViewById(rootView, id);
      if (esttoque == null) {
        break missingId;
      }

      id = R.id.getEstoque;
      Button getEstoque = ViewBindings.findChildViewById(rootView, id);
      if (getEstoque == null) {
        break missingId;
      }

      id = R.id.label1;
      LinearLayout label1 = ViewBindings.findChildViewById(rootView, id);
      if (label1 == null) {
        break missingId;
      }

      id = R.id.produtividade;
      Button produtividade = ViewBindings.findChildViewById(rootView, id);
      if (produtividade == null) {
        break missingId;
      }

      id = R.id.seuConteinerDeFragment;
      GridLayout seuConteinerDeFragment = ViewBindings.findChildViewById(rootView, id);
      if (seuConteinerDeFragment == null) {
        break missingId;
      }

      return new ActivityHomeBinding((ConstraintLayout) rootView, addFuncionario, addcar,
          addclienteclick, avaliacao, bottomNavigation, controleCaixa, descServico, esttoque,
          getEstoque, label1, produtividade, seuConteinerDeFragment);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
