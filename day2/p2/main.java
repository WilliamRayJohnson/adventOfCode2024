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
        int calcResult = calcSafeReport(report);
        boolean isSafe;

        if (calcResult >= 0) {
            List<Integer> repWOCurr = new ArrayList<>(report);
            repWOCurr.remove(calcResult);
            List<Integer> repWONext = new ArrayList<>(report);
            repWONext.remove(calcResult + 1);
            boolean isCurrSafe = calcSafeReport(repWOCurr) == -1;
            boolean isNextSafe = calcSafeReport(repWONext) == -1;
            isSafe = isCurrSafe || isNextSafe;
        } else {
            isSafe = true;
        }

        safeReports += isSafe ? 1 : 0;
    }

    System.out.println("Safe Reports: " + safeReports);
}

int calcSafeReport(List<Integer> report) {
    boolean isSafe;
    boolean isIncreasing = false;
    for (int i = 0; i < report.size() - 1; i++) {
        Integer currentNum = report.get(i);
        Integer nextNum = report.get(i + 1);

        if (i == 0) {
            isIncreasing = currentNum < nextNum;
            isSafe = !currentNum.equals(nextNum);
        } else if (isIncreasing) {
            isSafe = currentNum < nextNum && !currentNum.equals(nextNum);
        } else {
            isSafe = currentNum > nextNum && !currentNum.equals(nextNum);
        }

        if (!isSafe) {
            return i;
        }

        Integer distance = isIncreasing ? nextNum - currentNum : currentNum - nextNum;
        isSafe = distance <= 3;

        if (!isSafe) {
            return i;
        }
    }

    return -1;
}