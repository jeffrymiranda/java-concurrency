package com.jmiranda.challenges;

public class Challenge9 {

    // Deadlock detected when the studyTime lock the Student object on line 33.
    // And the handInAssignment lock the Tutor object on line 68.
    // Neither of the thread never release the lock that the other thread is waiting for.
    public static void main(String[] args) {
        final NewTutor tutor = new NewTutor();
        final NewStudent student = new NewStudent(tutor);
        tutor.setStudent(student);

        // The tutorThread gets the lock for the Tutor object because the studyTime method is synchronizing the it on line 29.
        Thread tutorThread = new Thread(tutor::studyTime);

        // The studentThread gets the lock for the Student object because the handInAssignment method is synchronizing the it on line 67.
        Thread studentThread = new Thread(student::handInAssignment);

        tutorThread.start();
        studentThread.start();
    }
}

class NewTutor {
    private NewStudent student;

    public void setStudent(NewStudent student) {
        this.student = student;
    }

    public void studyTime() {

        synchronized (this) {
            System.out.println("Tutor has arrived");
            //synchronized (student) {
                try {
                    // wait for student to arrive
                    this.wait();
                } catch (InterruptedException e) {

                }
            synchronized (student) {
                student.startStudy();
                System.out.println("Tutor is studying with student");
            }
        }
    }

    public void getProgressReport() {
        // get progress report
        System.out.println("Tutor gave progress report");
    }
}

class NewStudent {

    private NewTutor tutor;

    NewStudent(NewTutor tutor) {
        this.tutor = tutor;
    }

    public void startStudy() {
        // study
        System.out.println("Student is studying");
    }

    public void handInAssignment() {
        synchronized (tutor) {
            tutor.getProgressReport();
            //synchronized (this) {
                System.out.println("Student handed in assignment");
                tutor.notifyAll();
            //}
        }
    }
}