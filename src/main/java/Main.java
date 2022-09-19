public class Main {
    static int ALL_LIMIT = 10;

    public static void main(String[] args) {

        final ProducerConsumer produceConsume = new ProducerConsumer();
        final int sleepTimeProduceConsume = 500;
        //static int ALL_LIMIT = 10;


        ThreadGroup group = new ThreadGroup("Группа");

        new Thread(group, () -> {
            try {
                produceConsume.consume();
            } catch (InterruptedException e) {
                Thread.currentThread().isInterrupted();
            }
        }, "Покупатель1").start();

        new Thread(group, () -> {
            try {
                produceConsume.consume();
            } catch (InterruptedException e) {
                Thread.currentThread().isInterrupted();
            }
        }, "Покупатель2").start();

        new Thread(group, () -> {
            try {
                produceConsume.consume();
            } catch (InterruptedException e) {
                Thread.currentThread().isInterrupted();
            }
        }, "Покупатель3").start();

        new Thread(group, () -> {
            try {
                Thread.sleep(sleepTimeProduceConsume);
                produceConsume.produce();
            } catch (InterruptedException e) {
                Thread.currentThread().isInterrupted();
            }
        }, "Toyota").start();
    }
}

