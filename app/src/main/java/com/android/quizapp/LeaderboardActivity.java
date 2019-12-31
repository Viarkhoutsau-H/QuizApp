package com.android.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class LeaderboardActivity extends AppCompatActivity {

	CollectionReference usersCollection = FirebaseFirestore.getInstance().collection("users");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_leaderboard);

		usersCollection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
			@Override
			public void onComplete(@NonNull Task<QuerySnapshot> task) {

				if (task.isSuccessful()) {

					ArrayList<String> userNames = new ArrayList<>();
					ArrayList<String> userScores = new ArrayList<>();

					for (QueryDocumentSnapshot document : task.getResult()) {

						String scores = document.get("GEOGRAPHY").toString().replace("[", "").replace("]", "");
						String[] scoresArray = data.split(", ");


						userNames.add(document.getId());

					}

					scoreFill();

				} else {

					Toast.makeText(getApplicationContext(), "Failed.", Toast.LENGTH_SHORT).show();

				}
			}
		});


		Button buttonBack = findViewById(R.id.buttonLeaderboardBack);

		buttonBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				startActivity(new Intent(LeaderboardActivity.this, MainActivity.class));

			}
		});


	}

	public void scoreFill() {


	}

	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}
}
