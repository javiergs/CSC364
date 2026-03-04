package pipeline;

/**
 * Generator is the first stage in the pipeline.
 * It produces a finite number of items and puts them into the output queue.
 *
 * @author javiergs
 * @version 1.0
 */
public class Generator implements Runnable {

  private final String name;
  private final Depot depot;

  public Generator(String name, Depot out) {
    this.name = name;
    this.depot = out;
  }

  @Override
  public void run() {
    try {
      for (int i = 1; i <= 5; i++) {
        String item = name + "-item-" + i;
        depot.put(item);
        System.out.println("\t -- 🔥Generator " + name + " -> produced " + item);
        Thread.sleep(1000 * Math.round(0.5 + Math.random()));
      }
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

}

