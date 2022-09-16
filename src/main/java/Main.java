public class Main {

    static int ALL_LIMIT = 10; // салон должен продать максимум 10 авто

    public static void main(String[] args) throws InterruptedException {
        final ProducerConsumer produceConsume = new ProducerConsumer();
        final int sleepTimeForThreads = 3_500;

        ThreadGroup group = new ThreadGroup("Группа");

        new Thread(group, () -> {
            try {
                produceConsume.produceCar();
            } catch (InterruptedException e) {
                Thread.currentThread().isInterrupted();
            }
        }, "Toyota").start();

        // Thread.sleep(sleepTimeForThreads);

        while (produceConsume.numberOfSoldCars < ALL_LIMIT) {
            new Thread(group, () -> {
                try {
                    produceConsume.consumeCar();
                } catch (InterruptedException e) {
                    Thread.currentThread().isInterrupted();
                }
            }, "Покупатель1").start();

            Thread.sleep(sleepTimeForThreads);

            new Thread(group, () -> {
                try {
                    produceConsume.consumeCar();
                } catch (InterruptedException e) {
                    Thread.currentThread().isInterrupted();
                }
            }, "Покупатель2").start();

            Thread.sleep(sleepTimeForThreads);

            new Thread(group, () -> {
                try {
                    produceConsume.consumeCar();
                } catch (InterruptedException e) {
                    Thread.currentThread().isInterrupted();
                }
            }, "Покупатель3").start();
            //Thread.sleep(sleepTimeForThreads);
        }
        group.interrupt();
    }
}