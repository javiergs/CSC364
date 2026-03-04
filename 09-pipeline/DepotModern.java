package pipeline;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Modern version of Depot that delegates thread-safety and blocking behavior
 * to Java's built-in BlockingQueue (instead of manual Lock/Condition code).
 *
 * @author javiergs
 * @version 1.0
 */
public class DepotModern {

  private final BlockingQueue<String> items = new LinkedBlockingQueue<>();

  public static final String STOP_CODE = "---STOP---";

  public void put(String item) throws InterruptedException {
    items.put(item);
  }

  public String take() throws InterruptedException {
    return items.take();
  }
}