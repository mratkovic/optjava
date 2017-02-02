package hr.fer.zemris.trisat;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Parser class that parses {@link SATFormula} given as cnf file.
 * 
 * @author marko
 *
 */
public class Parser {

    /**
     * Method that reads file given as path and parses {@link SATFormula}.
     *
     * @param filePath
     *            path to file on file system
     * @return parsed {@link SATFormula}
     * @throws IllegalArgumentException
     *             if file could not be parsed
     */
    public static SATFormula parseFile(final String filePath) {
        ListIterator<String> it = getCnfLines(filePath).listIterator();

        String[] parts = it.next().split("\\s+");
        if (!parts[0].equals("p") || parts.length != 4) {
            throw new IllegalArgumentException("Expected 'p cnf n_var n_clauses' line");
        }

        int nVariables = parseInt(parts[2]);
        int nClauses = parseInt(parts[3]);

        List<Clause> clauses = new ArrayList<>();
        it.forEachRemaining(i -> clauses.add(new Clause(parseClauseLine(i, nVariables))));

        if (nClauses != clauses.size()) {
            throw new IllegalArgumentException("Invalid clause number");
        }
        return new SATFormula(nVariables, clauses.toArray(new Clause[nClauses]));
    }

    /**
     * Util method that parses given line as clause. Line should consist only of
     * variable indices. Negative indices represent complemented variable. Line
     * terminator in 0.
     *
     * @param line
     *            string representing clause
     * @param nVariables
     *            number of variables in whole function
     * @return
     */
    private static int[] parseClauseLine(final String line, final int nVariables) {
        List<Integer> parts = Arrays.stream(line.split("\\s+")).map(p -> parseInt(p)).collect(Collectors.toList());

        int lastIndex = parts.size() - 1;
        int lastValue = parts.get(lastIndex);
        if (lastValue != 0) {
            throw new IllegalArgumentException("Expected 0 as clause terminator in line: " + line);
        }

        int[] indices = new int[lastIndex];
        for (int i = 0; i < parts.size() - 1; ++i) {
            int var = parts.get(i);
            if (var < -nVariables || var > nVariables || var == 0) {
                throw new IllegalArgumentException("Expected literal index or negative literal index in line:" + line);
            }

            indices[i] = var;
        }
        return indices;

    }

    /**
     * Util method that tries to parse int.
     *
     * @param line
     *            string representation of int value
     * @return int value
     * @throws IllegalArgumentException
     *             if given string could not be parsed
     */
    private static int parseInt(final String line) {
        int x;
        try {
            x = Integer.parseInt(line);
            return x;
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Expected Integer values in cnf file, got: " + line);
        }

    }

    /**
     * Util method used to skip comments and extra lines in cnf file.
     *
     * @param filePath
     *            path to file containing formula
     * @return list of lines containing formulas
     */
    private static List<String> getCnfLines(final String filePath) {
        Scanner sc;
        try {
            sc = new Scanner(new File(filePath));
        } catch (FileNotFoundException ex) {
            throw new IllegalArgumentException("File given by path '" + filePath + "' could not be found");
        }

        List<String> data = new ArrayList<>();
        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();

            if (line.startsWith("c")) {
                continue;

            } else if (line.startsWith("%")) {
                break;
            }
            data.add(line);
        }
        sc.close();
        return data;
    }
}
