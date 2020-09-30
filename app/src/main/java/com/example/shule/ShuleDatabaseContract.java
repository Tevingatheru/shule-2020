package com.example.shule;

public class ShuleDatabaseContract {
    public ShuleDatabaseContract() {
    }

    public static final class SubjectInfo {
        public static final String TABLE_NAME = "subject";
        public static final String COLUMN_SUBJECT_ID = "id";
        public static final String COLUMN_SUBJECT_NAME = "name";
        public static final String COLUMN_GRADE_ID = "grade_id";

        public static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + COLUMN_SUBJECT_ID + COLUMN_SUBJECT_NAME + COLUMN_GRADE_ID + ")";
    }
}
