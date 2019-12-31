package com.android.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {

	static String userLogin;
	static String userPassword;

	EditText loginEditText;
	EditText passwordEditText;

	CollectionReference usersCollection = FirebaseFirestore.getInstance().collection("users");
	Map<String, Object> user = new HashMap<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_log_in);

		loginEditText = findViewById(R.id.editTextLogin);
		passwordEditText = findViewById(R.id.editTextPassword);

		findViewById(R.id.buttonSignUp).setOnClickListener(this);
		findViewById(R.id.buttonLogIn).setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

			case R.id.buttonSignUp:

				if (loginEditText.getText().length() != 0 && passwordEditText.getText().length() != 0) {

					final String loginSignUp = String.valueOf(loginEditText.getText());
					final String passwordSignUp = String.valueOf(passwordEditText.getText());
					final ArrayList<String> geography = new ArrayList<>(Arrays.asList("X", "X", "X", "X", "X", "X", "X", "X", "X", "X"));

					usersCollection.document(loginSignUp).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
						@Override
						public void onComplete(@NonNull Task<DocumentSnapshot> task) {

							if (task.isSuccessful()) {

								DocumentSnapshot document = task.getResult();

								if (document.exists()) {

									Toast.makeText(getApplicationContext(), "That user already signed up.", Toast.LENGTH_SHORT).show();

								} else {

									user.put("login", loginSignUp);
									user.put("password", passwordSignUp);
									user.put("GEOGRAPHY", geography);

									usersCollection.document(loginSignUp)
										.set(user)
										.addOnSuccessListener(new OnSuccessListener<Void>() {
											@Override
											public void onSuccess(Void aVoid) {

												Toast.makeText(getApplicationContext(), "User signed up successfully.", Toast.LENGTH_SHORT).show();

												userLogin = loginSignUp;
												userPassword = passwordSignUp;
												startActivity(new Intent(getApplicationContext(), MainActivity.class));

											}
										})
										.addOnFailureListener(new OnFailureListener() {
											@Override
											public void onFailure(@NonNull Exception e) {

												Toast.makeText(getApplicationContext(), "Cannot sign up.", Toast.LENGTH_SHORT).show();

											}
										});

								}

							} else {

								Toast.makeText(getApplicationContext(), "Failed.", Toast.LENGTH_SHORT).show();

							}
						}
					});

				} else {

					Toast.makeText(this, "Fill the form.", Toast.LENGTH_SHORT).show();

				}

				break;

			case R.id.buttonLogIn:

				if (loginEditText.getText().length() != 0 && passwordEditText.getText().length() != 0) {

					final String loginLogIn = String.valueOf(loginEditText.getText());
					final String passwordLogIn = String.valueOf(passwordEditText.getText());

					usersCollection.document(loginLogIn).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
						@Override
						public void onComplete(@NonNull Task<DocumentSnapshot> task) {

							if (task.isSuccessful()) {

								DocumentSnapshot document = task.getResult();

								if (document.exists()) {

									if (document.get("password").equals(passwordLogIn)) {

										Toast.makeText(getApplicationContext(), "Successfully logged.", Toast.LENGTH_SHORT).show();

										userLogin = loginLogIn;
										userPassword = passwordLogIn;
										startActivity(new Intent(getApplicationContext(), MainActivity.class));

									} else {

										Toast.makeText(getApplicationContext(), "Wrong password.", Toast.LENGTH_SHORT).show();

									}

								} else {

									Toast.makeText(getApplicationContext(), "User not found.", Toast.LENGTH_SHORT).show();

								}

							} else {

								Toast.makeText(getApplicationContext(), "Failed.", Toast.LENGTH_SHORT).show();

							}
						}
					});

				} else {

					Toast.makeText(this, "Fill the form.", Toast.LENGTH_LONG).show();

				}

				break;

		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}
}
