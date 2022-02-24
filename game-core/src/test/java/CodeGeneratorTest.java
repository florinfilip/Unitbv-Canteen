import java.util.Random;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

public class CodeGeneratorTest {

  @Test
  public void givenUsingPlainJava_whenGeneratingRandomStringUnbounded_thenCorrect() {
    int leftLimit = 48; // numeral '0'
    int rightLimit = 122; // letter 'z'
    int targetStringLength = 6;
    Random random = new Random();

    String generatedString =
        random
            .ints(leftLimit, rightLimit + 1)
            .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
            .limit(targetStringLength)
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString();
    generatedString = generatedString.toUpperCase();

    System.out.println(generatedString);
  }

  @Test
  public void givenUsingApache_whenGeneratingRandomAlphanumericString_thenCorrect() {
    String generatedString = RandomStringUtils.randomAlphanumeric(6);
    generatedString = generatedString.toUpperCase();
    System.out.println(generatedString);
  }
}
