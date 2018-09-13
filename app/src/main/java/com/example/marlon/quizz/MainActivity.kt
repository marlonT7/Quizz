package com.example.marlon.quizz

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Button
import android.widget.TextView


class MainActivity : AppCompatActivity(), Adapter.QuestionnaireAnswers {
    override fun selectedAnswer(question: String, answer: String) {
        questionnaire.questions.forEach {
            if (it.question == question) {
                questionnaire.answers[questionnaire.questions.indexOf(it)] = answer
                if (it.isCorrect(answer)) {

                }
            }
        }
    }


    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    // Set questions to the questionnaire
    private val questions: ArrayList<Question> = arrayListOf(
            Question(question = "How many legs has the cat?",
                    answer = "4",
                    options = arrayListOf("5", "10", "4", "2")),
            Question(question = "What color is the bear?",
                    answer = "white",
                    options = arrayListOf("red", "purple", "yellow", "white")),
            Question(question = "What musical instrument played Paganini?",
                    answer = "violin",
                    options = arrayListOf("violin", "piano", "trumpet", "triangle")),
            Question(question = "Which of the following ph values represents a neutral condition?",
                    answer = "7.0",
                    options = arrayListOf("1.0", "6.0", "7.0", "8.0")),
            Question(question = "Which of these is an aerial vehicle? ",
                    answer = "helicopter",
                    options = arrayListOf("car", "bike", "helicopter", "boat")),
            Question(question = "Which of following planets is closer to the sun than the Earth",
                    answer = "Mercury",
                    options = arrayListOf("Mercury", "Saturn", "Moon", "Venus")),
            Question(question = "Who wrote The Masque of the Red Death",
                    answer = "Edgar Allan Poe",
                    options = arrayListOf("Stephen Edwin King", "Love Craft", "Edgar Allan Poe", "Stan Lee")),
            Question(question = "Which is the largest continent?",
                    answer = "Asia",
                    options = arrayListOf("America", "Asia", "Africa", "Europa")))

    private var questionnaire = Questionnaire(questions)


    override fun onCreate(savedInstanceState: Bundle?) {

        setContentView(R.layout.activity_main)
        super.onCreate(savedInstanceState)
        questionnaire.fillVoidAnswers()
        // Restores data if exist
        if (savedInstanceState != null) {
            with(savedInstanceState) {
                // Restore value of members from saved state
                questionnaire = getParcelable("keyQuestionnaire")
            }
            countCorrect()
        }
        viewManager = LinearLayoutManager(this)
        // Sets data to the recycler view
        viewAdapter = Adapter(questionnaire, this)
        recyclerView = findViewById<RecyclerView>(R.id.questionnaire_view).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)
            // use a linear layout manager
            layoutManager = viewManager
            // specify an viewAdapter (see also next example)
            adapter = viewAdapter
        }

        val finish = findViewById<Button>(R.id.finish)
        finish.setOnClickListener {
            countCorrect()
            viewAdapter.notifyDataSetChanged()
        }
    }

    private fun countCorrect() {
        val result = findViewById<TextView>(R.id.result)
        result.text = questionnaire.countCorrects().toString()
        //Snackbar.make(result, result.text, Snackbar.LENGTH_LONG).setAction("Action", null).show()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.run {
            putParcelable("keyQuestionnaire", questionnaire)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        savedInstanceState?.run {
            questionnaire = getParcelable("keyQuestionnaire")
        }

    }

}
