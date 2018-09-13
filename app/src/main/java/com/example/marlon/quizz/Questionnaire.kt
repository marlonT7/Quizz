package com.example.marlon.quizz

import android.os.Parcel
import android.os.Parcelable

data class Questionnaire(val questions: ArrayList<Question>,
                         var correctAnswers: Int = 0,
                         // Selected options for each question
                         var answers: ArrayList<String> = arrayListOf()) : Parcelable {
    // Create an answer for each question
    fun fillVoidAnswers(){
        // Set "Not selected element" for default
        questions.forEach { answers.add("Not selected element") }
    }
    constructor(parcel: Parcel) : this(
            parcel.createTypedArrayList(Question),
            parcel.readInt(),
            parcel.createStringArrayList())

    // Counts the correct answers
    fun countCorrects(): Int {
        correctAnswers=0
        questions.forEach {
            if (it.isCorrect(answers[questions.indexOf(it)])) {
                correctAnswers++
            }
        }
        return correctAnswers
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(questions)
        parcel.writeInt(correctAnswers)
        parcel.writeStringList(answers)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Questionnaire> {
        override fun createFromParcel(parcel: Parcel): Questionnaire {
            return Questionnaire(parcel)
        }

        override fun newArray(size: Int): Array<Questionnaire?> {
            return arrayOfNulls(size)
        }
    }
}
