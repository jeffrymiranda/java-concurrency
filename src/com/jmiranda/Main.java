package com.jmiranda;

import static com.jmiranda.ThreadColor.*;

public class Main {

    public static void main(String[] args) {
        System.out.println(ANSI_PURPLE + "Hello from the main thread.");

        Thread anotherThread = new AnotherThread();
        anotherThread.setName("== Another Thread ==");
        anotherThread.start();

        // using lambda expresion
        new Thread(() -> System.out.println(ANSI_GREEN + "Hello from the anonymous class thread")).start();

        Thread myNewRunnableThread = new Thread(new MyRunnable());
        myNewRunnableThread.start();

        Thread myRunnableThread = new Thread(new MyRunnable() {// or Thread myRunnableThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(ANSI_RED + "Hello from the anonymous class's implementation of run()");
            }
        });

        myRunnableThread.start();

        System.out.println(ANSI_PURPLE + "Hello again from the main thread.");
    }
}
