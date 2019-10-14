import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
  public static void main(String[] args) {

    if (args.length < 1) {
      printUsageInfo();
    } else {
      if (args[0].equals("-l")) {
        listTasks();
      } else if (args[0].equals("-a")) {
        if (args.length < 2) {
          System.out.println("Unable to add: no task provided");
        } else {
          addNewTask(args[1]);
        }
      } else if (args[0].equals("-r")) {
        removeTask(Integer.valueOf(args[1]));
      }
    }

//    listTasks();
//    addNewTask();
//    listTasks();
    //   printUsageInfo();

    // System.out.println(Arrays.deepToString(args));
//    listTasks();
//    removeTask(2);
  }

  public static void printUsageInfo() {
    System.out.println("Command Line Todo application");
    System.out.println("=============================");
    System.out.println();
    System.out.println("Command line arguments:");
    System.out.println("    -l   Lists all the tasks");
    System.out.println("    -a   Adds a new task");
    System.out.println("    -r   Removes a task");
    System.out.println("    -c   Completes a task");
  }

  public static void listTasks() {
    List<String> lines = readFile();

    if (lines.size() == 0) {
      System.out.println("No todos for today! :)");
    } else {
      List<String> linesWithNumbers = new ArrayList<>();
      int counter = 0;
      for (int i = 0; i < lines.size(); i++) {
        counter++;
        linesWithNumbers.add(counter + " - " + lines.get(i));
      }
      for (int i = 0; i < linesWithNumbers.size(); i++) {
        System.out.println(linesWithNumbers.get(i));
      }
    }
  }

  public static void addNewTask(String taskToAdd) {
    List<String> lines = readFile();
    lines.add(taskToAdd);
    writeFile(lines);
  }

  public static void removeTask(int taskToRemove) {
    List<String> lines = readFile();
    lines.remove(taskToRemove - 1);
    writeFile(lines);
  }

  public static List<String> readFile() {
    Path path = Paths.get("todo-list.txt");
    List<String> lines = new ArrayList<>();

    try {
      lines = Files.readAllLines(path);
    } catch (IOException e) {
      System.out.println("file cannot be found");
    }
    return lines;
  }

  public static void writeFile(List<String> linesToWrite) {
    Path path = Paths.get("todo-list.txt");

    try {
      Files.write(path, linesToWrite);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
