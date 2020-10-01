package com.jmiranda.challenges;

import java.util.concurrent.locks.ReentrantLock;

public class Challenge {

    public static void main(String[] args) {
        /*** USING synchronized keyword. ***/
        //BankAccount bankAccount = new BankAccount("12345-678", 1000.00);

        /*** USING ReentrantLock ***/
        ReentrantLock reentrantLock = new ReentrantLock();
        BankAccount bankAccount = new BankAccount("12345-678", 1000.0, reentrantLock);
        System.out.println(bankAccount);

        // Primary
        Thread primary = new Thread(() -> {
            bankAccount.deposit(300.00);
            bankAccount.withdraw(50.00);
        });
        // Joint
        Thread joint = new Thread(() -> {
            bankAccount.deposit(203.75);
            bankAccount.withdraw(100.00);
        });

        primary.start();
        bankAccount.printAccountNumber();
        joint.start();
        bankAccount.printAccountNumber();

        new Thread(() -> {
            try {
                Thread.sleep(10);
                System.out.println("Print thread...");
                System.out.println(bankAccount);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
