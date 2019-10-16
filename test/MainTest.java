import org.junit.Assert;
import org.junit.Test;


public class MainTest {
  String arg1 = "12";
  String arg2 = "apple";
  Main main = new Main();

  @Test
  public void numberChecker_True() {
    Assert.assertTrue(main.numberChecker(arg1));
  }

  @Test
  public void numberChecker_False() {
    Assert.assertFalse(main.numberChecker(arg2));
  }
}