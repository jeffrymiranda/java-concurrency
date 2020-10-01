package com.jmiranda.challenges;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BankAccount {

    /*** USING synchronized keyword. ***/
    /*private final String accountNumber;
    private double balance;

    public BankAccount(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public synchronized void deposit(double amount) {
        this.balance += amount;
    }

    public void withdraw(double amount) {
        synchronized (this) {
            this.balance -= amount;
        }
    }*/

    /*** USING ReentrantLock Object ***/
    private final String accountNumber;
    private double balance;
    private final Lock lock;

    public BankAccount(String accountNumber, double balance, ReentrantLock lock) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.lock = lock;
    }

    public void deposit(double amount) {
        boolean status = false; // Local variables are threadsafe.
        try {
            if (this.lock.tryLock(1000, TimeUnit.MILLISECONDS)) {
                //this.lock.lock();
                try {
                    this.balance += amount;
                    status = true;
                } finally {
                    this.lock.unlock();
                }
            } else {
                System.out.println("Could not get the lock");
            }
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
        System.out.println("Transaction status = " + status);
    }

    public void withdraw(double amount) {
        boolean status = false;
        try {
            if (this.lock.tryLock(1000, TimeUnit.MILLISECONDS)) {
                //this.lock.lock();
                try {
                    this.balance -= amount;
                    status = true;
                } finally {
                    this.lock.unlock();
                }
            } else {
                System.out.println("Could not get the lock");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Transaction status = " + status);
    }

    /**
     * Because the threads only read the account number, thread interference isn't an issue.
     * It would be a mistake to synchronize the getAccountNumber, printAccountNumber or toString methods.
     * We'd over synchronizing the code, and in applications with a large number of threads, this can have
     * a noticeable negative impact on performance
     */

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public void printAccountNumber() {
        System.out.println(this.accountNumber);
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "accountNumber='" + accountNumber + '\'' +
                ", balance=" + balance +
                '}';
    }
}
