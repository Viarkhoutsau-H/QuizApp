package com.android.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

import static android.graphics.drawable.Drawable.createFromPath;


public class SelectorActivity extends AppCompatActivity implements View.OnClickListener {

	CollectionReference usersCollection = FirebaseFirestore.getInstance().collection("users");
	CollectionReference themesCollection = FirebaseFirestore.getInstance().collection("themes");

	StorageReference imageBackground = FirebaseStorage.getInstance().getReference().child(MainActivity.theme + ".jpg");
	File localFile = File.createTempFile(MainActivity.theme, "jpg");

	TextView themeInfoTextView;
	TextView quizScoreTextView0;
	TextView quizScoreTextView1;
	TextView quizScoreTextView2;
	TextView quizScoreTextView3;
	TextView quizScoreTextView4;
	TextView quizScoreTextView5;
	TextView quizScoreTextView6;
	TextView quizScoreTextView7;
	TextView quizScoreTextView8;
	TextView quizScoreTextView9;

	static String quizNumber;
	static int numberOfQuestions;

	public SelectorActivity() throws IOException {
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_selector);

		themeInfoTextView = findViewById(R.id.textViewThemeInfo);
		quizScoreTextView0 = findViewById(R.id.textViewQuizScore0);
		quizScoreTextView1 = findViewById(R.id.textViewQuizScore1);
		quizScoreTextView2 = findViewById(R.id.textViewQuizScore2);
		quizScoreTextView3 = findViewById(R.id.textViewQuizScore3);
		quizScoreTextView4 = findViewById(R.id.textViewQuizScore4);
		quizScoreTextView5 = findViewById(R.id.textViewQuizScore5);
		quizScoreTextView6 = findViewById(R.id.textViewQuizScore6);
		quizScoreTextView7 = findViewById(R.id.textViewQuizScore7);
		quizScoreTextView8 = findViewById(R.id.textViewQuizScore8);
		quizScoreTextView9 = findViewById(R.id.textViewQuizScore9);

		themeInfoTextView.setText(MainActivity.theme);

		themesCollection.document(MainActivity.theme).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
			@Override
			public void onComplete(@NonNull Task<DocumentSnapshot> task) {

				if (task.isSuccessful()) {

					final DocumentSnapshot documentTheme = task.getResult();

					if (documentTheme.exists()) {

						usersCollection.document(LogInActivity.userLogin).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
							@Override
							public void onComplete(@NonNull Task<DocumentSnapshot> task) {

								if (task.isSuccessful()) {

									DocumentSnapshot documentUser = task.getResult();

									if (documentUser.exists()) {

										textViewFill(documentUser.get(MainActivity.theme), documentTheme.get("numberOfQuestionsPerQuiz"));

									} else {

										Toast.makeText(getApplicationContext(), "No user found.", Toast.LENGTH_SHORT).show();

									}

								} else {

									Toast.makeText(getApplicationContext(), "Failed.", Toast.LENGTH_SHORT).show();

								}
							}
						});

					} else {

						Toast.makeText(getApplicationContext(), "No theme found.", Toast.LENGTH_SHORT).show();

					}

				} else {

					Toast.makeText(getApplicationContext(), "Failed.", Toast.LENGTH_SHORT).show();

				}
			}
		});

		imageBackground.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

				getWindow().setBackgroundDrawable(createFromPath(localFile.getAbsolutePath()));

			}
		}).addOnFailureListener(new OnFailureListener() {
			@Override
			public void onFailure(@NonNull Exception e) {

				Toast.makeText(SelectorActivity.this, "Network issue.", Toast.LENGTH_SHORT).show();

			}
		});

		findViewById(R.id.buttonQuiz0).setOnClickListener(this);
		findViewById(R.id.buttonQuiz1).setOnClickListener(this);
		findViewById(R.id.buttonQuiz2).setOnClickListener(this);
		findViewById(R.id.buttonQuiz3).setOnClickListener(this);
		findViewById(R.id.buttonQuiz4).setOnClickListener(this);
		findViewById(R.id.buttonQuiz5).setOnClickListener(this);
		findViewById(R.id.buttonQuiz6).setOnClickListener(this);
		findViewById(R.id.buttonQuiz7).setOnClickListener(this);
		findViewById(R.id.buttonQuiz8).setOnClickListener(this);
		findViewById(R.id.buttonQuiz9).setOnClickListener(this);
		findViewById(R.id.buttonSelectorBack).setOnClickListener(this);

	}

	public void textViewFill(Object objScores, Object objNumberOfQuestions) {

		String scores = objScores.toString().replace("[", "").replace("]", "");
		String[] scoresArray = scores.split(", ");

		String numberOfQuestions = objNumberOfQuestions.toString().replace("[", "").replace("]", "");
		String[] numberOfQuestionsArray = numberOfQuestions.split(", ");

		quizScoreTextView0.setText(String.format("%s/%s", scoresArray[0], numberOfQuestionsArray[0]));
		quizScoreTextView1.setText(String.format("%s/%s", scoresArray[1], numberOfQuestionsArray[1]));
		quizScoreTextView2.setText(String.format("%s/%s", scoresArray[2], numberOfQuestionsArray[2]));
		quizScoreTextView3.setText(String.format("%s/%s", scoresArray[3], numberOfQuestionsArray[3]));
		quizScoreTextView4.setText(String.format("%s/%s", scoresArray[4], numberOfQuestionsArray[4]));
		quizScoreTextView5.setText(String.format("%s/%s", scoresArray[5], numberOfQuestionsArray[5]));
		quizScoreTextView6.setText(String.format("%s/%s", scoresArray[6], numberOfQuestionsArray[6]));
		quizScoreTextView7.setText(String.format("%s/%s", scoresArray[7], numberOfQuestionsArray[7]));
		quizScoreTextView8.setText(String.format("%s/%s", scoresArray[8], numberOfQuestionsArray[8]));
		quizScoreTextView9.setText(String.format("%s/%s", scoresArray[9], numberOfQuestionsArray[9]));

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

			case R.id.buttonQuiz0:

				quizNumber = "QUIZ0";
				String[] tempNumber0 = quizScoreTextView0.getText().toString().split("/");
				numberOfQuestions = Integer.valueOf(tempNumber0[1]) - 1;

				startActivity(new Intent(this, GameplayActivity.class));

				break;

			case R.id.buttonQuiz1:

				Toast.makeText(this, "In developing..." + "\n" + "To play select first quiz", Toast.LENGTH_SHORT).show();

				break;

			case R.id.buttonQuiz2:

				Toast.makeText(this, "In developing..." + "\n" + "To play select first quiz", Toast.LENGTH_SHORT).show();

				break;

			case R.id.buttonQuiz3:

				Toast.makeText(this, "In developing..." + "\n" + "To play select first quiz", Toast.LENGTH_SHORT).show();

				break;

			case R.id.buttonQuiz4:

				Toast.makeText(this, "In developing..." + "\n" + "To play select first quiz", Toast.LENGTH_SHORT).show();

				break;

			case R.id.buttonQuiz5:

				Toast.makeText(this, "In developing..." + "\n" + "To play select first quiz", Toast.LENGTH_SHORT).show();

				break;

			case R.id.buttonQuiz6:

				Toast.makeText(this, "In developing..." + "\n" + "To play select first quiz", Toast.LENGTH_SHORT).show();

				break;

			case R.id.buttonQuiz7:

				Toast.makeText(this, "In developing..." + "\n" + "To play select first quiz", Toast.LENGTH_SHORT).show();

				break;

			case R.id.buttonQuiz8:

				Toast.makeText(this, "In developing..." + "\n" + "To play select first quiz", Toast.LENGTH_SHORT).show();

				break;

			case R.id.buttonQuiz9:

				Toast.makeText(this, "In developing..." + "\n" + "To play select first quiz", Toast.LENGTH_SHORT).show();

				break;

			case R.id.buttonSelectorBack:

				startActivity(new Intent(this, MainActivity.class));

				break;

		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}
}