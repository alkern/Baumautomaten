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

    public TreeParser() {
        trees = new ArrayList<>();
    }

    public List<Node> parseTreeFile(String fileName) {
        trees.clear();
        RecursiveLineParser parser = new RecursiveLineParser();
        Tokenizer tokenizer = new Tokenizer();

        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            stream.map(tokenizer::scan).map(parser::parseLineToTree).forEach(trees::add);
            return trees;
        } catch (IOException e) {
            System.err.println("File " + fileName + " not found in this folder");
        }

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());
        try (Scanner scanner = new Scanner(file)) {

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                trees.add(parser.parseLineToTree(tokenizer.scan(line)));
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return trees;
    }
}
