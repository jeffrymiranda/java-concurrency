package com.jmiranda.challenges;

public class Challenge8 {

    // Deadlock problem detected. Neither thread will ever release the lock that the other thread is waiting for.
    public static void main(String[] args) {
        Tutor tutor = new Tutor();
        Student student = new Student(tutor);
        tutor.setStudent(student);

        // The tutorThread gets the lock for the Tutor object because the studyTime method is synchronized.
        Thread tutorThread = new Thread(tutor::studyTime);

        // The studentThread gets the lock for the Student object because the handInAssignment method is synchronized.
        Thread studentThread = new Thread(student::handInAssignment);

        tutorThread.start();
        studentThread.start();
    }
}

class Tutor {
    private Student student;

    public synchronized void setStudent(Student student) {
        this.student = student;
    }

    public synchronized void studyTime() {
        System.out.println("Tutor has arrived");
        try {
            // wait for student to arrive and hand in assignment
            Thread.sleep(300);
        } catch (InterruptedException e) {

        }
        // here the studentThread is holding the lock for the Student object, so the tutorThread blocks (Deadlock is being produced) because startStudy is synchronized.
        student.startStudy();
        System.out.println("Tutor is studying with student");
    }

    // We'd over synchronizing the code, and in applications with a large number of threads, this can have a noticeable negative impact on performance
    // Not required to synchronize since we are not writing data to any instance variable.
    public /*synchronized*/ void getProgressReport() {
        // get progress report
        System.out.println("Tutor gave progress report");
    }
}

class Student {

    private Tutor tutor;

    Student(Tutor tutor) {
        this.tutor = tutor;
    }

    // We'd over synchronizing the code, and in applications with a large number of threads, this can have a noticeable negative impact on performance
    // Not required to synchronize since we are not writing data to any instance variable.
    public /*synchronized*/ void startStudy() {
        // study
        System.out.println("Student is studying");
    }

    public synchronized void handInAssignment() {
        // here the tutorThread is holding the lock for the Tutor object, so the studentThread blocks (Deadlock is being produced) because getProgressReport is synchronized.
        tutor.getProgressReport();
        System.out.println("Student handed in assignment");
    }
}
