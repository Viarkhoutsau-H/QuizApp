package com.android.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

	CollectionReference themesCollection = FirebaseFirestore.getInstance().collection("themes");

	Button TextButtonTheme0;
	Button TextButtonTheme1;
	Button TextButtonTheme2;
	Button TextButtonTheme3;
	Button TextButtonTheme4;
	Button TextButtonTheme5;
	Button TextButtonTheme6;
	Button TextButtonTheme7;
	Button TextButtonTheme8;
	Button TextButtonTheme9;

	TextView userTextView;

	static String theme;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		userTextView = findViewById(R.id.textViewUser);
		userTextView.setText(String.format("Welcome, %s", LogInActivity.userLogin));

		TextButtonTheme0 = findViewById(R.id.buttonTheme0);
		TextButtonTheme1 = findViewById(R.id.buttonTheme1);
		TextButtonTheme2 = findViewById(R.id.buttonTheme2);
		TextButtonTheme3 = findViewById(R.id.buttonTheme3);
		TextButtonTheme4 = findViewById(R.id.buttonTheme4);
		TextButtonTheme5 = findViewById(R.id.buttonTheme5);
		TextButtonTheme6 = findViewById(R.id.buttonTheme6);
		TextButtonTheme7 = findViewById(R.id.buttonTheme7);
		TextButtonTheme8 = findViewById(R.id.buttonTheme8);
		TextButtonTheme9 = findViewById(R.id.buttonTheme9);

		themesCollection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
			@Override
			public void onComplete(@NonNull Task<QuerySnapshot> task) {

				if (task.isSuccessful()) {

					ArrayList<String> buttonNames = new ArrayList<>();

					for (QueryDocumentSnapshot document : task.getResult()) {

						buttonNames.add(document.getId());

					}

					textButtonFill(buttonNames);

				} else {

					Toast.makeText(getApplicationContext(), "Failed.", Toast.LENGTH_SHORT).show();

				}
			}
		});

		findViewById(R.id.buttonTheme0).setOnClickListener(this);
		findViewById(R.id.buttonTheme1).setOnClickListener(this);
		findViewById(R.id.buttonTheme2).setOnClickListener(this);
		findViewById(R.id.buttonTheme3).setOnClickListener(this);
		findViewById(R.id.buttonTheme4).setOnClickListener(this);
		findViewById(R.id.buttonTheme5).setOnClickListener(this);
		findViewById(R.id.buttonTheme6).setOnClickListener(this);
		findViewById(R.id.buttonTheme7).setOnClickListener(this);
		findViewById(R.id.buttonTheme8).setOnClickListener(this);
		findViewById(R.id.buttonTheme9).setOnClickListener(this);
		findViewById(R.id.buttonSelectorBack).setOnClickListener(this);

	}

	public void textButtonFill(ArrayList names) {

		TextButtonTheme0.setText(names.get(0).toString());
		TextButtonTheme1.setText(names.get(1).toString());
		TextButtonTheme2.setText(names.get(2).toString());
		TextButtonTheme3.setText(names.get(3).toString());
		TextButtonTheme4.setText(names.get(4).toString());
		TextButtonTheme5.setText(names.get(5).toString());
		TextButtonTheme6.setText(names.get(6).toString());
		TextButtonTheme7.setText(names.get(7).toString());
		TextButtonTheme8.setText(names.get(8).toString());
		TextButtonTheme9.setText(names.get(9).toString());

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

			case R.id.buttonTheme0:

				Toast.makeText(this, "In developing..." + "\n" + "To play select the geography theme", Toast.LENGTH_SHORT).show();

				break;

			case R.id.buttonTheme1:

				Toast.makeText(this, "In developing..." + "\n" + "To play select the geography theme", Toast.LENGTH_SHORT).show();

				break;

			case R.id.buttonTheme2:

				Toast.makeText(this, "In developing..." + "\n" + "To play select the geography theme", Toast.LENGTH_SHORT).show();

				break;

			case R.id.buttonTheme3:

				theme = TextButtonTheme3.getText().toString();

				startActivity(new Intent(this, SelectorActivity.class));

				break;

			case R.id.buttonTheme4:

				Toast.makeText(this, "In developing..." + "\n" + "To play select the geography theme", Toast.LENGTH_SHORT).show();

				break;

			case R.id.buttonTheme5:

				Toast.makeText(this, "In developing..." + "\n" + "To play select the geography theme", Toast.LENGTH_SHORT).show();

				break;

			case R.id.buttonTheme6:

				Toast.makeText(this, "In developing..." + "\n" + "To play select the geography theme", Toast.LENGTH_SHORT).show();

				break;

			case R.id.buttonTheme7:

				Toast.makeText(this, "In developing..." + "\n" + "To play select the geography theme", Toast.LENGTH_SHORT).show();

				break;

			case R.id.buttonTheme8:

				Toast.makeText(this, "In developing..." + "\n" + "To play select the geography theme", Toast.LENGTH_SHORT).show();

				break;

			case R.id.buttonTheme9:

				Toast.makeText(this, "In developing..." + "\n" + "To play select the geography theme", Toast.LENGTH_SHORT).show();

				break;

			case R.id.buttonSelectorBack:

				finish();

				break;

		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}
}