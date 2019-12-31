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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class GameplayActivity extends AppCompatActivity implements View.OnClickListener {

	TextView questionTextView;

	Button textButtonAnswer0;
	Button textButtonAnswer1;
	Button textButtonAnswer2;
	Button textButtonAnswer3;

	CollectionReference questionsCollection = FirebaseFirestore.getInstance().collection(MainActivity.theme);
	CollectionReference usersCollection = FirebaseFirestore.getInstance().collection("users");

	int currentQuestion = 0;

	String[] answersArray;
	String[] buttonTextArray0;
	String[] buttonTextArray1;
	String[] buttonTextArray2;
	String[] buttonTextArray3;
	String[] questionsArray;

	String[] userAnswersArray = new String[SelectorActivity.numberOfQuestions + 1];
	int score = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gameplay);

		questionTextView = findViewById(R.id.textViewQuestion);

		textButtonAnswer0 = findViewById(R.id.buttonAnswer0);
		textButtonAnswer1 = findViewById(R.id.buttonAnswer1);
		textButtonAnswer2 = findViewById(R.id.buttonAnswer2);
		textButtonAnswer3 = findViewById(R.id.buttonAnswer3);

		questionsCollection.document(SelectorActivity.quizNumber).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
			@Override
			public void onComplete(@NonNull Task<DocumentSnapshot> task) {

				if (task.isSuccessful()) {

					DocumentSnapshot document = task.getResult();

					if (document.exists()) {

						quizCreate(document.get("answers"), document.get("buttonText0"), document.get("buttonText1"), document.get("buttonText2"), document.get("buttonText3"), document.get("questions"));

					} else {

						Toast.makeText(getApplicationContext(), "No theme collection found.", Toast.LENGTH_SHORT).show();

					}

				} else {

					Toast.makeText(getApplicationContext(), "Failed.", Toast.LENGTH_SHORT).show();

				}
			}
		});

		findViewById(R.id.buttonAnswer0).setOnClickListener(this);
		findViewById(R.id.buttonAnswer1).setOnClickListener(this);
		findViewById(R.id.buttonAnswer2).setOnClickListener(this);
		findViewById(R.id.buttonAnswer3).setOnClickListener(this);
		findViewById(R.id.buttonGameplayBack).setOnClickListener(this);

	}

	public void quizCreate(Object objAnswers, Object objButtonText0, Object objButtonText1, Object objButtonText2, Object objButtonText3, Object objQuestions) {

		String answers = objAnswers.toString().replace("[", "").replace("]", "");
		answersArray = answers.split(", ");

		String buttonText0 = objButtonText0.toString().replace("[", "").replace("]", "");
		buttonTextArray0 = buttonText0.split(", ");

		String buttonText1 = objButtonText1.toString().replace("[", "").replace("]", "");
		buttonTextArray1 = buttonText1.split(", ");

		String buttonText2 = objButtonText2.toString().replace("[", "").replace("]", "");
		buttonTextArray2 = buttonText2.split(", ");

		String buttonText3 = objButtonText3.toString().replace("[", "").replace("]", "");
		buttonTextArray3 = buttonText3.split(", ");

		String questions = objQuestions.toString().replace("[", "").replace("]", "");
		questionsArray = questions.split("/, ");

		quiz(false);

	}

	public void quiz(boolean finalQuestion) {

		if (finalQuestion) {

			findViewById(R.id.buttonAnswer0).setVisibility(View.INVISIBLE);
			findViewById(R.id.buttonAnswer1).setVisibility(View.INVISIBLE);
			findViewById(R.id.buttonAnswer2).setVisibility(View.INVISIBLE);
			findViewById(R.id.buttonAnswer3).setVisibility(View.INVISIBLE);
			findViewById(R.id.buttonGameplayBack).setVisibility(View.VISIBLE);

			for (int i = 0; i <= 9; i++) {

				if (answersArray[i].equals(userAnswersArray[i]))
					score++;

			}

			questionTextView.setText("Your score is " + score + "/" + (SelectorActivity.numberOfQuestions + 1));
			updateUserScore();

		} else {

			questionTextView.setText(questionsArray[currentQuestion]);
			textButtonAnswer0.setText(buttonTextArray0[currentQuestion]);
			textButtonAnswer1.setText(buttonTextArray1[currentQuestion]);
			textButtonAnswer2.setText(buttonTextArray2[currentQuestion]);
			textButtonAnswer3.setText(buttonTextArray3[currentQuestion]);

		}
	}

	public void updateUserScore() {

		usersCollection.document(LogInActivity.userLogin).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
			@Override
			public void onComplete(@NonNull Task<DocumentSnapshot> task) {

				if (task.isSuccessful()) {

					DocumentSnapshot document = task.getResult();

					if (document.exists()) {

						int quizNumber = Integer.valueOf(SelectorActivity.quizNumber.replace("QUIZ", ""));
						String data = document.get(MainActivity.theme).toString().replace("[", "").replace("]", "");
						String[] dataArray = data.split(", ");

						if (dataArray[quizNumber].equals("X")) {

							dataArray[quizNumber] = String.valueOf(score);
							ArrayList<String> dataArrayList = new ArrayList<>(Arrays.asList(dataArray));
							usersCollection.document(LogInActivity.userLogin)
								.update(MainActivity.theme, dataArrayList)
								.addOnSuccessListener(new OnSuccessListener<Void>() {
									@Override
									public void onSuccess(Void aVoid) {

										Toast.makeText(getApplicationContext(), "Score set.", Toast.LENGTH_SHORT).show();

									}
								})
								.addOnFailureListener(new OnFailureListener() {
									@Override
									public void onFailure(@NonNull Exception e) {

										Toast.makeText(getApplicationContext(), "Failed.", Toast.LENGTH_SHORT).show();

									}
								});

						} else {

							if (Integer.valueOf(dataArray[quizNumber]) < score) {

								dataArray[quizNumber] = String.valueOf(score);
								ArrayList<String> dataArrayList = new ArrayList<>(Arrays.asList(dataArray));
								usersCollection.document(LogInActivity.userLogin)
									.update(MainActivity.theme, dataArrayList)
									.addOnSuccessListener(new OnSuccessListener<Void>() {
										@Override
										public void onSuccess(Void aVoid) {

											Toast.makeText(getApplicationContext(), "Score updated.", Toast.LENGTH_SHORT).show();

										}
									})
									.addOnFailureListener(new OnFailureListener() {
										@Override
										public void onFailure(@NonNull Exception e) {

											Toast.makeText(getApplicationContext(), "Failed.", Toast.LENGTH_SHORT).show();

										}
									});

							} else {

								Toast.makeText(getApplicationContext(), "The score is not broken or the maximum is reached.", Toast.LENGTH_SHORT).show();

							}
						}

					} else {

						Toast.makeText(getApplicationContext(), "User not found.", Toast.LENGTH_SHORT).show();

					}

				} else {

					Toast.makeText(getApplicationContext(), "Failed.", Toast.LENGTH_SHORT).show();

				}
			}
		});
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

			case R.id.buttonAnswer0:

				userAnswersArray[currentQuestion] = "0";

				if (currentQuestion == SelectorActivity.numberOfQuestions) {

					quiz(true);

				} else {

					currentQuestion++;
					quiz(false);

				}

				break;

			case R.id.buttonAnswer1:

				userAnswersArray[currentQuestion] = "1";

				if (currentQuestion == SelectorActivity.numberOfQuestions) {

					quiz(true);

				} else {

					currentQuestion++;
					quiz(false);

				}

				break;

			case R.id.buttonAnswer2:

				userAnswersArray[currentQuestion] = "2";

				if (currentQuestion == SelectorActivity.numberOfQuestions) {

					quiz(true);

				} else {

					currentQuestion++;
					quiz(false);

				}

				break;

			case R.id.buttonAnswer3:

				userAnswersArray[currentQuestion] = "3";

				if (currentQuestion == SelectorActivity.numberOfQuestions) {

					quiz(true);

				} else {

					currentQuestion++;
					quiz(false);

				}

				break;

			case R.id.buttonGameplayBack:

				startActivity(new Intent(this, SelectorActivity.class));

				break;

		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}
}