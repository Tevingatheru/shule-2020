package com.example.shule.contract;

import android.provider.BaseColumns;

public class ShuleDatabaseContract {
    public ShuleDatabaseContract() {
    }

    public static final class SubjectInfo implements BaseColumns{
        public static final String TABLE_NAME = "subject";
        public static final String COLUMN_SUBJECT_ID = "id";
        public static final String COLUMN_SUBJECT_NAME = "name";
        public static final String COLUMN_SUBJECT_GRADE_ID = "grade_id";

        public static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +_ID + "INTEGER_PRIMARY_KEY," + COLUMN_SUBJECT_ID + COLUMN_SUBJECT_NAME + COLUMN_SUBJECT_GRADE_ID + ")";
    }

    public static final class QuestionInfo implements BaseColumns{
        public static final String TABLE_NAME = "question";
        public static final String COLUMN_QUESTION_ID = "id";
        public static final String COLUMN_QUESTION_NAME = "question";
        public static final String COLUMN_QUESTION_TOPIC_ID = "topic_id";

        public static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("+_ID +"INTEGER_PRIMARY_KEY" + COLUMN_QUESTION_ID + COLUMN_QUESTION_NAME + COLUMN_QUESTION_TOPIC_ID + ")";
    }

    public static final class GradeInfo implements BaseColumns{
        public static final String TABLE_NAME = "grade";
        public static final String COLUMN_GRADE_ID = "id";
        public static final String COLUMN_GRADE_NAME = "grade";


        public static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("+_ID +"INTEGER_PRIMARY_KEY" + COLUMN_GRADE_ID + COLUMN_GRADE_NAME + ")";
    }

    public static final class TopicInfo implements BaseColumns{
        public static final String TABLE_NAME = "topic";
        public static final String COLUMN_TOPIC_ID = "id";
        public static final String COLUMN_TOPIC_NAME = "topic";
        public static final String COLUMN_GRADE_ID = "id";
        public static final String COLUMN_STATUS = "status";

        public static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +_ID + "INTEGER_PRIMARY_KEY" + COLUMN_TOPIC_ID + COLUMN_TOPIC_NAME + COLUMN_GRADE_ID + COLUMN_STATUS + ")";
    }


}
