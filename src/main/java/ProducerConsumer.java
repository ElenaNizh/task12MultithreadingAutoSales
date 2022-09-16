import java.util.ArrayList;
import java.util.List;

public class ProducerConsumer {

    private List<Car> carForSale = new ArrayList<>();
    private final int LIMIT = 1; //  за один раз выпускается 1 автомобиль

    // Время на изготовление одного автомобиля
    private final int sleepTimeForProduce = 1500;

    private final int sleepTimeForConsume = 500;

    Car car = new Car("Toyota");
    int numberOfSoldCars = 0;

    public synchronized void produceCar() throws InterruptedException {
        while (carForSale.size() < Main.ALL_LIMIT) {
            Thread.sleep(sleepTimeForProduce);
            carForSale.add(car);
            System.out.println("Производитель " + Thread.currentThread().getName() + " выпустил " + carForSale.size() + " авто");
            wait(sleepTimeForConsume);
            notify();
        }
        wait();
    }

    public synchronized void consumeCar() throws InterruptedException {
        Thread.sleep(sleepTimeForConsume);
        System.out.println(Thread.currentThread().getName() + " зашел в автосалон");

        // Если машин нет, покупатель ждёт поступления автомобиля
        if (carForSale.size() == 0) {
            System.out.println("Машин нет");
            wait();
        }
        Thread.sleep(sleepTimeForConsume);
        numberOfSoldCars++;
        System.out.println(Thread.currentThread().getName() + " уехал на новеньком авто");
        System.out.println("Куплено " + numberOfSoldCars + " авто");

        try {
            carForSale.remove(0);
            //  carForSale.remove(carForSale.size()-carForSale.size());
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Внимание, машин больше не будет");
        }
    }
}