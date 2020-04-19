package app;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SentenceSpellChecker{
  private SpellChecker spellCheckerService;

  public SentenceSpellChecker(SpellChecker spellChecker)
  {
    spellCheckerService = spellChecker;
  }

  public String checkSpelling(String sentence) {
      try {
          return Stream.of(sentence.split(" "))
                  .map(word -> {
                      return spellCheckerService.isSpellingCorrect(word) ? word + " " : ("[" + word + "] ");
                  })
                  .collect(Collectors.joining()).trim();
      }
      catch (RuntimeException ex){
          return ex.getMessage();
      }
  }
}
