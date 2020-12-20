package com.server.attendance.util;

public class ErrorMessages {

    private ErrorMessages() {
    }

    public static final String INVALID_CREDENTIALS = "Invalid EmailId or Password";

    public static final String DUPLICATE_EMAIL = "Email already exists";

    public static final String HOD_EMAIL_EXISTS = "HOD with that email already exists";

    public static final String STUDENT_EMAIL_EXISTS = "Student with that email already exists";

    public static final String CONTACT_ADMIN = "Please contact admin";

    public static final String UNAUTHENTICATED = "Sorry, you are authenticated to view this page";

    public static final String HOD_DOES_NOT_EXISTS = "HOD with that email does not exist";

    public static final String TEACHER_DOES_NOT_EXISTS = "Teacher with that email does not exist";

    public static final String STUDENT_DOES_NOT_EXISTS = "Student with that email does not exist";

    public static final String COURSE_NOT_FOUND = "Course does not exists";

    public static final String COURSE_ERROR = "Either course with that ID exists or incorrect Teacher email";

    public static final String FATHER_EXISTS_FOR_STUDENT = "Father as Parent already exists for the Student";

    public static final String MOTHER_EXISTS_FOR_STUDENT = "Mother as Parent already exists for the Student";

    public static final String FATHER_DOES_NOT_EXISTS = "Father does not exists for the Student";

    public static final String MOTHER_DOES_NOT_EXISTS = "Mother does not exists for the Student";

    public static final String INPUT_EMPTY = "Inputs cannot be empty";

    public static final String COURSE_TEACHER_NOT_MATCHED = "Sorry, only teachers associated with their Course can schedule Lectures";

    public static final String TIMING_ERROR = "Start-time cannot be greater than End-time";

    public static final String DATES_ERROR = "Check Lecture date and Lecture timing dates again";

    public static final String LECTURE_NOT_FOUND = "Lecture with that Id does not exist";

    public static final String OTP_NOT_MATCH = "OTP does not match";

    public static final String QUESTIONS_DOES_NOT_EXIST = "Questions for given Lecture does not exist";

    public static final String QUESTIONS_NOT_FOUND = "Questions with that Id does not exist";
}
