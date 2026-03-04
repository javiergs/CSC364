
package pipeline;

/**
 * Reporter thread that takes items from the input depot and simulates storing them.
 * It stops when it receives the STOP code.
 *
 * @author javiergs
 * @version 1.0
 */
public class Reporter implements Runnable {

  private final String name;
  private final Depot in;

  public Reporter(String name, Depot in) {
    this.name = name;
    this.in = in;
  }

  @Override
  public void run() {
    try {
      while (true) {
        String item = in.take();
        if (Depot.STOP_CODE.equals(item)) break;
        System.out.println("\t -- 📦 Reporter " + name + " -> stored " + item);
        // sleep a random time to simulate variable processing time
        Thread.sleep(1000 * Math.round(0.5 + Math.random()));
      }
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

}
