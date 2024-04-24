

import CLI.DictionaryCLI;

public class Main {
    public static void main(String[] args) {
        try {
            DictionaryCLI cli = new DictionaryCLI();
            cli.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}