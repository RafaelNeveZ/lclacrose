package com.lacrose.lc.lclacrose;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.lacrose.lc.lclacrose.Adapter.WorkAdapter;
import com.lacrose.lc.lclacrose.Model.Clientes;
import com.lacrose.lc.lclacrose.Model.Obras;
import com.lacrose.lc.lclacrose.Model.Users;
import com.lacrose.lc.lclacrose.Util.FireBaseUtil;
import com.lacrose.lc.lclacrose.Util.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class WorkActivity extends MainActivity {
	private FirebaseAuth Auth;
	private final Context context = this;
	FirebaseFirestore database;
	CollectionReference user_works_ref;
	private ProgressBar spinner;
	public TextView textEmpty;
    public Spinner spinner_tipo_work;
    public int ondeEstou = 0;
	private final String TAG = "LOG";
    ArrayList<String> listaClientes;
    ArrayAdapter<String> spinnerAdapter;
    String clienteEscolhido= "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_work);
		spinner=(ProgressBar)findViewById(R.id.progressBar);
		textEmpty=(TextView) findViewById(R.id.empyt_text);
		Auth = FirebaseAuth.getInstance();
		database = FireBaseUtil.getFireDatabase();
        spinner_tipo_work = (Spinner)findViewById(R.id.spinner_tipo_work);
        listaClientes = new ArrayList<>();
        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_tipo_work.setAdapter(spinnerAdapter);


        getClientes();

        spinner_tipo_work.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                clienteEscolhido = spinnerAdapter.getItem(position);
                showListOfWorks(clienteEscolhido);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

	}

	private void getClientes(){
        showProgress(getString(R.string.get_clientes));
        DocumentReference user_ref = database.document(getString(R.string.user_tag)+"/"+Auth.getCurrentUser().getUid());
        user_ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                final Users user = documentSnapshot.toObject(Users.class);
                if(user.getFunc().equals(getString(R.string.diretor))){
                    CollectionReference cliente_ref = database.collection(getString(R.string.cliente_tag));
                    cliente_ref.whereEqualTo("isValid",true)
                            .addSnapshotListener(new EventListener<QuerySnapshot>() {

                                @Override
                                public void onEvent(@Nullable final QuerySnapshot querySnapshot,
                                                    @Nullable FirebaseFirestoreException e) {
                                    if (e != null) {
                                        Log.w(TAG, "Listen error", e);
                                        return;
                                    }

                                    if(querySnapshot.getDocumentChanges().size() >0) {
                                        for (DocumentChange change : querySnapshot.getDocumentChanges()) {

                                            if (change.getType() == DocumentChange.Type.ADDED) {
                                                final String clienteId = change.getDocument().getId();
                                                        DocumentReference cliente_ref = database.document(getString(R.string.cliente_tag) + "/" +clienteId );
                                                        cliente_ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                            @Override
                                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                                final Clientes cliente = documentSnapshot.toObject(Clientes.class);
                                                                spinnerAdapter.add(cliente.getNome());
                                                                spinnerAdapter.notifyDataSetChanged();
                                                                dismissProgress();
                                                            }
                                                        });

                                            }

                                        }

                                    }else{
                                        dismissProgress();
                                    }
                                }
                            });
                }else if(user.getFunc().equals(getString(R.string.gestor))){
                    CollectionReference cliente_ref = database.collection(getString(R.string.cliente_tag));
                    cliente_ref.whereEqualTo("isValid",true).whereEqualTo(getString(R.string.centro_de_custo),user.getCentro_de_custo())
                            .addSnapshotListener(new EventListener<QuerySnapshot>() {

                                @Override
                                public void onEvent(@Nullable final QuerySnapshot querySnapshot,
                                                    @Nullable FirebaseFirestoreException e) {
                                    if (e != null) {
                                        Log.w(TAG, "Listen error", e);
                                        return;
                                    }

                                    if(querySnapshot.getDocumentChanges().size() >0) {
                                        for (DocumentChange change : querySnapshot.getDocumentChanges()) {

                                            if (change.getType() == DocumentChange.Type.ADDED) {
                                                final String clienteId = change.getDocument().getId();
                                                DocumentReference cliente_ref = database.document(getString(R.string.cliente_tag) + "/" +clienteId );
                                                cliente_ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                        final Clientes cliente = documentSnapshot.toObject(Clientes.class);
                                                        spinnerAdapter.add(cliente.getNome());
                                                        spinnerAdapter.notifyDataSetChanged();
                                                        dismissProgress();

                                                    }
                                                });

                                            }

                                        }

                                    }else{
                                        dismissProgress();
                                    }
                                }
                            });
                }else{
                    if (user.getObras().size() > 0) {
                        for (String thisObra : user.getObras()) {
                            DocumentReference works_ref;
                            final String obraiD = thisObra;
                            works_ref = database.document(getString(R.string.work_tag) + "/" + obraiD);
                            works_ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    final Obras obra = documentSnapshot.toObject(Obras.class);
                                    obra.setId(obraiD);
                                    DocumentReference cliente_ref = database.document(getString(R.string.cliente_tag) + "/" + obra.getCliente());
                                    cliente_ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            final Clientes cliente = documentSnapshot.toObject(Clientes.class);
                                            boolean naoBota = false;
                                            for(int i =0;i<spinnerAdapter.getCount();i++) {
                                                if(spinnerAdapter.getItem(0).equals(cliente.getNome())){
                                                    naoBota = true;
                                                }
                                            }
                                            if(!naoBota){
                                                dismissProgress();
                                                spinnerAdapter.add(cliente.getNome());
                                                spinnerAdapter.notifyDataSetChanged();
                                            }

                                        }
                                    });

                                }
                            });

                        }

                    }else{
                        dismissProgress();
                    }
                }
            }
        });
    }

	private void showListOfWorks(String cliente_nome) {
        textEmpty.setVisibility(View.GONE);
        spinner.setVisibility(View.VISIBLE);
        final List<Obras> worksList = new ArrayList<>();
        final ListView workListView = (ListView) findViewById(R.id.normal_list);
        workListView.setDivider(null);
        final WorkAdapter workAdapter = new WorkAdapter(this, R.layout.item_work, worksList);
        workListView.setAdapter(workAdapter);
        if (!cliente_nome.equals("")) {
            DocumentReference user_ref = database.document(getString(R.string.user_tag) + "/" + Auth.getCurrentUser().getUid());
            user_ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    final Users user = documentSnapshot.toObject(Users.class);
                    if (user.getFunc().equals(getString(R.string.diretor))) {
                        CollectionReference works_ref = database.collection(getString(R.string.work_tag));
                        works_ref.whereEqualTo("isValid", true)
                                .addSnapshotListener(new EventListener<QuerySnapshot>() {

                                    @Override
                                    public void onEvent(@Nullable QuerySnapshot querySnapshot,
                                                        @Nullable FirebaseFirestoreException e) {
                                        if (e != null) {
                                            Log.w(TAG, "Listen error", e);
                                            return;
                                        }
                                        if (querySnapshot.getDocumentChanges().size() > 0) {
                                            for (DocumentChange change : querySnapshot.getDocumentChanges()) {
                                                if (change.getType() == DocumentChange.Type.ADDED) {
                                                    DocumentReference works_ref;
                                                    final String obraiD = change.getDocument().getId();
                                                    works_ref = database.document(getString(R.string.work_tag) + "/" + obraiD);
                                                    works_ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                        @Override
                                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                            final Obras obra = documentSnapshot.toObject(Obras.class);
                                                            obra.setId(obraiD);
                                                            DocumentReference cliente_ref = database.document(getString(R.string.cliente_tag) + "/" + obra.getCliente());
                                                            cliente_ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                                @Override
                                                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                                    final Clientes cliente = documentSnapshot.toObject(Clientes.class);
                                                                    obra.setClienteNome(cliente.getNome());
                                                                    if(obra.getClienteNome().equals(clienteEscolhido))
                                                                    worksList.add(obra);
                                                                    spinner.setVisibility(View.GONE);
                                                                    workAdapter.notifyDataSetChanged();
                                                                }
                                                            });

                                                        }
                                                    });

                                                }

                                            }
                                        } else {
                                            spinner.setVisibility(View.GONE);
                                            workAdapter.notifyDataSetChanged();
                                        }
                                    }
                                });
                    } else if (user.getFunc().equals(getString(R.string.gestor))) {
                        CollectionReference works_ref = database.collection(getString(R.string.work_tag));
                        works_ref.whereEqualTo(getString(R.string.centro_de_custo), user.getCentro_de_custo()).whereEqualTo("isValid", true)
                                .addSnapshotListener(new EventListener<QuerySnapshot>() {

                                    @Override
                                    public void onEvent(@Nullable QuerySnapshot querySnapshot,
                                                        @Nullable FirebaseFirestoreException e) {
                                        if (e != null) {
                                            Log.w(TAG, "Listen error", e);
                                            return;
                                        }
                                        if (querySnapshot.getDocumentChanges().size() > 0) {

                                            for (DocumentChange change : querySnapshot.getDocumentChanges()) {
                                                if (change.getType() == DocumentChange.Type.ADDED) {
                                                    DocumentReference works_ref;
                                                    final String obraiD = change.getDocument().getId();
                                                    works_ref = database.document(getString(R.string.work_tag) + "/" + obraiD);
                                                    works_ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                        @Override
                                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                            final Obras obra = documentSnapshot.toObject(Obras.class);
                                                            obra.setId(obraiD);
                                                            DocumentReference cliente_ref = database.document(getString(R.string.cliente_tag) + "/" + obra.getCliente());
                                                            cliente_ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                                @Override
                                                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                                    final Clientes cliente = documentSnapshot.toObject(Clientes.class);
                                                                    obra.setClienteNome(cliente.getNome());
                                                                    worksList.add(obra);
                                                                    spinner.setVisibility(View.GONE);
                                                                    workAdapter.notifyDataSetChanged();
                                                                }
                                                            });

                                                        }
                                                    });

                                                }

                                            }
                                        } else {
                                            spinner.setVisibility(View.GONE);
                                            workAdapter.notifyDataSetChanged();
                                        }
                                    }
                                });
                    } else {
                        if (user.getObras().size() > 0) {
                            for (String thisObra : user.getObras()) {
                                DocumentReference works_ref;
                                final String obraiD = thisObra;
                                works_ref = database.document(getString(R.string.work_tag) + "/" + obraiD);
                                works_ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        final Obras obra = documentSnapshot.toObject(Obras.class);
                                        obra.setId(obraiD);
                                        DocumentReference cliente_ref = database.document(getString(R.string.cliente_tag) + "/" + obra.getCliente());
                                        cliente_ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                final Clientes cliente = documentSnapshot.toObject(Clientes.class);
                                                obra.setClienteNome(cliente.getNome());
                                                worksList.add(obra);
                                                spinner.setVisibility(View.GONE);
                                                workAdapter.notifyDataSetChanged();
                                            }
                                        });

                                    }
                                });

                            }
                        } else {
                            spinner.setVisibility(View.GONE);
                            workAdapter.notifyDataSetChanged();
                        }
                    }

                }


            });
        } else {
            spinner.setVisibility(View.GONE);
            workAdapter.notifyDataSetChanged();
        }
    }

	//================MENU============================\\
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.work_menu, menu);
		return true;
	}

	public void logOut(MenuItem item) {
		final Dialog dialog = new Dialog(context);
		dialog.setContentView(R.layout.dialog_two_choice);
		dialog.setTitle(getString(R.string.dialog_logout_title));
		dialog.show();
		Button btCancel = (Button) dialog.findViewById(R.id.button_no);
		Button btLogOut = (Button) dialog.findViewById(R.id.button_yes);
		btLogOut.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Auth.signOut();
				Intent intent = new Intent(context,LoginActivity.class);
				context.startActivity(intent);
				finish();
			}
		});
		btCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
	}

	public void update(MenuItem item) {
		showListOfWorks(clienteEscolhido);
	}
}
