package parser;

import model.Node;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class TreeParser {

    private final List<Node> trees;
    private final RecursiveLineParser parser;
    private final Tokenizer tokenizer;

    public TreeParser() {
        trees = new ArrayList<>();
        parser = new RecursiveLineParser();
        tokenizer = new Tokenizer();
    }

    public List<Node> parseTreeFile(String fileName) {
        trees.clear();

        if (readFileFromCommandLineInSameFolder(fileName)) return trees;

        return readFileFromResourcesDirectory(fileName);
    }

    private boolean readFileFromCommandLineInSameFolder(String fileName) {
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            stream.map(s -> formatInputTree(s))
                    .map(tokenizer::scan)
                    .map(parser::parseLineToTree)
                    .forEach(trees::add);
            return true;
        } catch (IOException e) {
            System.err.println("File " + fileName + " not found in this folder");
        }
        return false;
    }

    private List<Node> readFileFromResourcesDirectory(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());
        try (Scanner scanner = new Scanner(file)) {

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.equals("")) break;
                line = formatInputTree(line);
                trees.add(parser.parseLineToTree(tokenizer.scan(line)));
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return trees;
    }

    public String formatInputTree(String input) {
        return input.substring(2, input.length() - 2);
    }
}
