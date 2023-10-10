package com.example.waspractice2.counter;

// 싱글턴 객체이며 멀티스레드 환경에서 하나의 객체를 공유하게 되면 우리가 원치않는 결과가 나온다.
// RaceCondition 이란 여러 프로세스 혹은 스레드가 동시에 하나의 자원에 접근하기 위해 경쟁하는 상태를 뜻한다.
// 즉 여러 스레드가 하나의 자원에 동시에 접근하기 위해 경쟁을 하면서 의도하지 않은 결과가 나옴
// Thread Safety 하지않음.
public class RaceConditionDemo {
    public static void main(String[] args) { // psvm
        Counter counter = new Counter();
        Thread t1 = new Thread(counter, "Thread-1");
        Thread t2 = new Thread(counter, "Thread-2");
        Thread t3 = new Thread(counter, "Thread-3");

        t1.start();
        t2.start();
        t3.start();
    }
}
