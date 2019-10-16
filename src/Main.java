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
      if (!args[0].equals("-l") && !args[0].equals("-a") && !args[0].equals("-r") && !args[0].equals("-c") && !args[0].equals("-la")) {
        System.out.println("Unsupported argument");
        System.out.println();
        printUsageInfo();
      } else if (args[0].contains("-l")) {
        listTasks(args[0]);
      } else if (args[0].equals("-a")) {
        if (args.length < 2) {
          System.out.println("Unable to add: no task provided");
        } else {
          addNewTask(args[1]);
        }
      } else if (args[0].equals("-r")) {
        if (args.length < 2) {
          System.out.println("Unable to remove: no index provided");
        } else if (numberChecker(args[1]) == false) {
          System.out.println("Unable to remove: index is not a number");
        } else if ((Integer.valueOf(args[1])) > taskCounter()) {
          System.out.println("Unable to remove: index is out of bound");
        } else {
          removeTask(Integer.valueOf(args[1]));
        }
      } else if (args[0].equals("-c")) {
        if (args.length < 2) {
          System.out.println("Unable to check: no index provided");
        } else if (numberChecker(args[1]) == false) {
          System.out.println("Unable to check: index is not a number");
        } else if (Integer.valueOf(args[1]) > taskCounter()) {
          System.out.println("Unable to check: index is out of bound");
        } else {
          checkTask(Integer.valueOf(args[1]));
        }
      }
    }
    // System.out.println(Arrays.deepToString(args));
  }

  public static void printUsageInfo() {
    System.out.println("Command Line Todo application");
    System.out.println("=============================");
    System.out.println();
    System.out.println("Command line arguments:");
    System.out.println("    -l   Lists undone tasks only");
    System.out.println("    -la  Lists all the tasks");
    System.out.println("    -a   Adds a new task");
    System.out.println("    -r   Removes a task");
    System.out.println("    -c   Completes a task");
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

  public static void listTasks(String arg) {
    List<String> lines = readFile();

    if (lines.size() == 0) {
      System.out.println("No todos for today! :)");
    } else {
      List<String> linesWithNumbers = new ArrayList<>();
      int counter = 0;
      int xCounter = 0;
      for (int i = 0; i < lines.size(); i++) {
        counter++;
        if (arg.equals("-la")) {
          if (lines.get(i).split(" ")[0].equals("[x]")) {
            linesWithNumbers.add(counter + " - " + lines.get(i));
            xCounter++;
          } else {
            linesWithNumbers.add(counter + " - [ ] " + lines.get(i));
          }
        } else if (arg.equals("-l")) {
          if (!lines.get(i).split(" ")[0].equals("[x]")) {
            linesWithNumbers.add(counter + " - [ ] " + lines.get(i));
          } else {
            xCounter++;
          }
        }
      }
      if ((arg.equals("-l") && counter == xCounter)) {
        System.out.println("there is no undone task");
      }
      for (int i = 0; i < linesWithNumbers.size(); i++) {
        System.out.println(linesWithNumbers.get(i));
      }
    }
  }

  public static int taskCounter() {
    List<String> lines = readFile();
    int counter = lines.size();
    return counter;
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

  public static boolean numberChecker(String input) {
    for (int i = 0; i < input.length(); i++) {
      if (!Character.isDigit(input.charAt(i))) {
        return false;
      }
    }
    return true;
  }

  public static void checkTask(int taskToCheck) {
    List<String> lines = readFile();
    if (!lines.get(taskToCheck - 1).split(" ")[0].equals("[x]")) {
      lines.set(taskToCheck - 1, "[x] " + lines.get(taskToCheck - 1));
      writeFile(lines);
    }
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
