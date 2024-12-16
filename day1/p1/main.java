import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

private static final String INPUT_FILE_NAME = ".\\inputs\\D1P1Input.txt";

void main() {
    System.out.println("Day 1 Puzzle 1");

    int ch;
    FileReader fr = null;
    StringBuffer sb = new StringBuffer();
    List<String> inputs = new ArrayList<>();
    List<int[]> parsedInputs = new ArrayList<>();
    List<Integer> leftList = new ArrayList<>();
    List<Integer> rightList = new ArrayList<>();
    int totalDistance = 0;

    try {
        fr = new FileReader(INPUT_FILE_NAME);

        while ((ch = fr.read()) != -1) {
            if ((char) ch != '\n') {
                sb.append(((char) ch));
            } else {
                inputs.add(sb.toString());
                sb = new StringBuffer();
            }
        }

        fr.close();
    } catch (FileNotFoundException e) {
        System.out.println("File not found");
    } catch (IOException e) {
        System.out.println("IO Exception");
    }

    for (String input : inputs) {
        String[] parts = input.split("   ");
        int[] numbers = {Integer.parseInt(parts[0]), Integer.parseInt(parts[1])};
        parsedInputs.add(numbers);
    }

    for (int[] pair : parsedInputs) {
        leftList.add(pair[0]);
        rightList.add(pair[1]);
    }

    leftList.sort(Comparator.naturalOrder());
    rightList.sort(Comparator.naturalOrder());

    for (int i = 0; i < parsedInputs.size(); i++) {
        Integer leftNumber = leftList.get(i);
        Integer rightNumber = rightList.get(i);

        Integer distance = leftNumber - rightNumber;
        if (distance < 0) {
            distance = distance * -1;
        }

        totalDistance += distance;
    }

    System.out.println("Total Distance: " + totalDistance);
}