package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class SpellCheckerWebService implements SpellChecker {

    @Override
    public boolean isSpellingCorrect(String word) {
        String inputLine = "false";
        try {
            URL url = new URL("http://agile.cs.uh.edu/spell?check=" + word);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            inputLine = in.readLine();
        } catch (IOException e) { }
        return Boolean.parseBoolean(inputLine);
    }
}
