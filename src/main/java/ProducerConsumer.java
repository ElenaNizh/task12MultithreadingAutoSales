import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerConsumer {

    private static final Lock lock = new ReentrantLock(true);
    private final Condition conditionMake = lock.newCondition(); // ожидаемое событие - изготовление авто
    private final Condition conditionBuy = lock.newCondition(); // ожидаемое событие - покупка машины

    private List<Car> carForSale = new ArrayList<>();
    // за один раз выпускается 1 автомобиль
    private final int LIMIT = 1;
    private final int sleepTimeForProduce = 1_500;
    private final int sleepTimeForConsume = 500;
    int numberOfSoldCars = 0;

    public void produce() throws InterruptedException {
        try {
            lock.lock();
            // while (numberOfSoldCars < 10) {
            while (numberOfSoldCars < Main.ALL_LIMIT) {
                // while (carForSale.size() < Main.ALL_LIMIT) {
                while (carForSale.size() == LIMIT) {
                    conditionBuy.await();
                }
                Thread.sleep(sleepTimeForProduce);
                carForSale.add(new Car("Toyota"));
                System.out.println("Производитель " + Thread.currentThread().getName() + " выпустил " + carForSale.size() + " авто");
                conditionMake.signal();
            }
        } finally {
            lock.unlock();
        }
    }

    public void consume() throws InterruptedException {
        while (carForSale.size() < Main.ALL_LIMIT) {
            try {
                lock.lock();
                Thread.sleep(sleepTimeForConsume);
                System.out.println(Thread.currentThread().getName() + " вошел в салон");
                if (carForSale.size() == 0) {
                    System.out.println("Машины нет");
                }
                conditionMake.await();

                Thread.sleep(sleepTimeForConsume);
                numberOfSoldCars++;

                System.out.print(Thread.currentThread().getName() + " уехал на новеньком авто. ");
                System.out.println("Куплено " + numberOfSoldCars + " авто");
                if (carForSale.size() == 0) {
                    System.out.println("Машин нет");
                }
                System.out.println("Машин нет, покупатели ждут выпуска");
                carForSale.remove(0);
                conditionBuy.signal();
            } finally {
                lock.unlock();
            }
        }
    }
}