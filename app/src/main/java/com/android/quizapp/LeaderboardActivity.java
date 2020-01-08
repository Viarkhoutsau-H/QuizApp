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

public class LeaderboardActivity extends AppCompatActivity {

	CollectionReference usersCollection = FirebaseFirestore.getInstance().collection("users");

	TextView leaderboardTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_leaderboard);

		leaderboardTextView = findViewById(R.id.textViewLeaderboard);

		usersCollection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
			@Override
			public void onComplete(@NonNull Task<QuerySnapshot> task) {

				if (task.isSuccessful()) {

					ArrayList<String> userNames = new ArrayList<>();
					ArrayList<Integer> userScores = new ArrayList<>();

					for (QueryDocumentSnapshot document : task.getResult()) {

						String scores = document.get("WITCHER").toString().replace("[", "").replace("]", "").replace("X", "0");
						String[] scoresArray = scores.split(", ");

						int score = 0;

						for (int i = 0; i < scoresArray.length; i++) {

							score = score + Integer.valueOf(scoresArray[i]);

						}

						userNames.add(document.getId());
						userScores.add(score);

					}

					scoreFill(userNames, userScores);

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

	public void scoreFill(ArrayList<String> name, ArrayList<Integer> score) {

		int length = name.size();
		String leaderboard = "";

		for (int i = 0; i < length; i++) {

			leaderboard = leaderboard + (i + 1) + ". " + name.get(i) + ", score is " + score.get(i) + "\n";

		}

		leaderboardTextView.setText(leaderboard);

	}

	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}
}
