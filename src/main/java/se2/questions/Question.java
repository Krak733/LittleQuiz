package se2.questions;

public abstract class Question implements QuestionInterface {

    protected String category;
    protected String questionText;



    public Question(String category, String questionText) {
        this.category = category;
        this.questionText = questionText;
    }

    public String getCategory() {
        return this.category;

    }

    public String getQuestionText() {
        return this.questionText;
    }


    @Override
    public String toString() {
        return "Question{" +
                "category='" + category + '\'' +
                ", questionText='" + questionText + '\'' +
                '}';
    }
}
