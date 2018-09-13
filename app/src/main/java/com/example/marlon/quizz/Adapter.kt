package com.example.marlon.quizz

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.question_layout.view.*


class Adapter(private val questionnaire: Questionnaire, private val questionnaireAnswers: QuestionnaireAnswers) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface QuestionnaireAnswers {
        fun selectedAnswer(question: String, answer: String)
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    inner class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private lateinit var questionText: TextView
        private lateinit var options: RadioGroup

        fun bind() {
            questionText = itemView.findViewById(R.id.question)
            options = itemView.findViewById(R.id.options)


            options.setOnCheckedChangeListener { group: RadioGroup, checkedId: Int ->
                val answer = group.findViewById<RadioButton>(checkedId)
                questionnaireAnswers.selectedAnswer(questionText.text.toString(), answer.text.toString())
            }
//            option1.setOnClickListener{
//                questionnaireAnswers.selectedAnswer(questionText.text.toString(), option1.text.toString())
//            }
//            option2.setOnClickListener { questionnaireAnswers.selectedAnswer(questionText.text.toString(), option2.text.toString()) }
//            option3.setOnClickListener { questionnaireAnswers.selectedAnswer(questionText.text.toString(), option3.text.toString()) }
//            option4.setOnClickListener { questionnaireAnswers.selectedAnswer(questionText.text.toString(), option4.text.toString()) }
//            // Move view logic to here from onBindViewHolder
        }
    }


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): Adapter.MyViewHolder {
        // create a new view
        val view = LayoutInflater.from(parent.context).inflate(R.layout.question_layout, parent, false)
        // set the view's size, margins, paddings and layout parameters
        return MyViewHolder(view = view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // - get element from your dataSet at this position
        // - replace the contents of the view with that element
        if (holder is MyViewHolder) {
            holder.bind()
        }
        bindView(holder, position)
    }

    // Set values to the view and check options if has restored a previously state
    private fun bindView(holder: RecyclerView.ViewHolder, position: Int): RecyclerView.ViewHolder {
        // Set the question
        val questionText: TextView = holder.itemView.question
        // Set the options
        val option1: RadioButton = holder.itemView.option1
        val option2: RadioButton = holder.itemView.option2
        val option3: RadioButton = holder.itemView.option3
        val option4: RadioButton = holder.itemView.option4
        questionText.text = questionnaire.questions[position].question
        option1.text = questionnaire.questions[position].options[0]
        option2.text = questionnaire.questions[position].options[1]
        option3.text = questionnaire.questions[position].options[2]
        option4.text = questionnaire.questions[position].options[3]
        // set the selected answer and set color
        if (!questionnaire.answers.isEmpty()) {
            when {
                questionnaire.answers[position] == option1.text -> {
                    option1.isChecked = true
                    showResults(option1, position)
                }
                questionnaire.answers[position] == option2.text -> {
                    option2.isChecked = true
                    showResults(option2, position)
                }
                questionnaire.answers[position] == option3.text -> {
                    option3.isChecked = true
                    showResults(option3, position)
                }
                questionnaire.answers[position] == option4.text -> {
                    option4.isChecked = true
                    showResults(option4, position)
                }
            }

        }
        return holder
    }

    //If the answer is correct, ser green the color text, is the answer is wrong, set red the color text
    private fun showResults(answer: RadioButton, position: Int) {
        if (questionnaire.questions[position].isCorrect(selectedOption = answer.text.toString())) {
            answer.setTextColor(Color.GREEN)
        } else {
            answer.setTextColor(Color.RED)
        }
    }

    // Return the size of your dataSet (invoked by the layout manager)
    override fun getItemCount() = questionnaire.questions.size
}



