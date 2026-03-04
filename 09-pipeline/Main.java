package pipeline;

import java.util.ArrayList;
import java.util.List;

/**
 * Example of a pipeline pattern with generators, processors, and storers.
 *
 * @author javiergs
 * @version 1.0
 */
public class Main {

  private static final int NUMBER_OF_GENERATORS = 1;
  private static final int NUMBER_OF_PROCESSORS = 3;
  private static final int NUMBER_OF_STORERS = 5;

  public static void main(String[] args) throws InterruptedException {

    List<Thread> teamGenerators = new ArrayList<>();
    List<Thread> teamProcessors = new ArrayList<>();
    List<Thread> teamStorers = new ArrayList<>();
    Depot q1 = new Depot();
    Depot q2 = new Depot();

    System.out.println("🏁 Starting generators, processors, and storers.");
    startgenerators(teamGenerators, q1);
    startProcessors(teamProcessors, q1, q2);
    startStorers(teamStorers, q2);

    System.out.println("⏳ Waiting for generators to finish.");
    joinTeam(teamGenerators);
    System.out.println("✅ generators finished.");

    System.out.println("✋🏼Stopping processors.");
    sendStop(q1, teamProcessors.size());
    System.out.println("⏳ Waiting for processors to finish.");
    joinTeam(teamProcessors);
    System.out.println("✅ Processors finished.");

    System.out.println("✋🏼Stopping storers.");
    sendStop(q2, teamStorers.size());
    System.out.println("⏳ Waiting for storers to finish.");
    joinTeam(teamStorers);
    System.out.println("✅ Storers finished.");
  }

  private static void startgenerators(List<Thread> teamgenerators, Depot q1) {
    for (int i = 0; i < NUMBER_OF_GENERATORS; i++) {
      String name = "generator-" + (i + 1);
      Thread t = new Thread(new Generator(name, q1));
      teamgenerators.add(t);
      t.start();
    }
  }

  private static void startProcessors(List<Thread> teamProcessors, Depot q1, Depot q2) {
    for (int i = 0; i < NUMBER_OF_PROCESSORS; i++) {
      String name = "Processor-" + (i + 1);
      Thread t = new Thread(new Processor(name, q1, q2));
      teamProcessors.add(t);
      t.start();
    }
  }

  private static void startStorers(List<Thread> teamStorers, Depot q2) {
    for (int i = 0; i < NUMBER_OF_STORERS; i++) {
      String name = "Storer-" + (i + 1);
      Thread t = new Thread(new Reporter(name, q2));
      teamStorers.add(t);
      t.start();
    }
  }

  private static void joinTeam(List<Thread> team) throws InterruptedException {
    for (Thread t : team)  t.join();
  }

  private static void sendStop(Depot q, int howMany) throws InterruptedException {
    for (int i = 0; i < howMany; i++)  q.put(Depot.STOP_CODE);
  }

}