import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

private static final String INPUT_FILE_NAME = ".\\inputs\\D2Input.txt";

void main() {
    int ch;
    FileReader fr = null;
    StringBuffer sb = new StringBuffer();
    List<String> inputs = new ArrayList<>();
    List<List<Integer>> parsedInputs = new ArrayList<>();
    int safeReports = 0;

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
        String[] parts = input.split(" ");
        List<Integer> partList = new ArrayList<>();
        for (String part : parts) {
            partList.add(Integer.valueOf(part));
        }
        parsedInputs.add(partList);
    }

    for (List<Integer> report : parsedInputs) {
        boolean isSafe = true;
        boolean isIncreasing = false;
        boolean dampenerHit = false;
        boolean firstLevelSkipped = false;
        for (int i = 0; i < report.size() - 1; i++) {
            Integer currentNum = report.get(i);
            Integer nextNum = report.get(i + 1);
            boolean dampenerHitCurNum = false;

            if (i == 0) {
                isIncreasing = currentNum < nextNum;
                isSafe = !currentNum.equals(nextNum);
            } else if (i == 1 && firstLevelSkipped) {
                isIncreasing = currentNum < nextNum;
                isSafe = !currentNum.equals(nextNum);
            } else if (isIncreasing) {
                isSafe = currentNum < nextNum && !currentNum.equals(nextNum);
            } else {
                isSafe = currentNum > nextNum && !currentNum.equals(nextNum);
            }

            if (!isSafe && i == 0) {
                dampenerHit = true;
                firstLevelSkipped = true;
                continue;
            }

            if (!isSafe && !dampenerHit && isIncreasing) {
                currentNum = report.get(i - 1);
                isSafe = currentNum < nextNum && !currentNum.equals(nextNum);
                dampenerHitCurNum = true;
            } else if (!isSafe && !dampenerHit) {
                currentNum = report.get(i - 1);
                isSafe = currentNum > nextNum && !currentNum.equals(nextNum);
                dampenerHitCurNum = true;
            }

            if (!isSafe && (dampenerHitCurNum || dampenerHit)) {
                break;
            }

            Integer distance = isIncreasing ? nextNum - currentNum : currentNum - nextNum;
            isSafe = distance <= 3;

            if (!isSafe && i == 0) {
                dampenerHit = true;
                firstLevelSkipped = true;
                continue;
            }

            if (!isSafe && !dampenerHitCurNum) {
                distance = isIncreasing ? nextNum - currentNum : currentNum - nextNum;
                dampenerHitCurNum = true;
                isSafe = distance <= 3;
            }
            
            if (!isSafe && (dampenerHitCurNum || dampenerHit)) {
                break;
            }

            dampenerHit = dampenerHitCurNum;
        }

        safeReports += isSafe ? 1 : 0;
    }

    System.out.println("Safe Reports: " + safeReports);
}