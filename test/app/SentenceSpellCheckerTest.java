package app;

import com.sun.nio.file.SensitivityWatchEventModifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class SentenceSpellCheckerTest {

  private SentenceSpellChecker sentenceSpellChecker;
  private SpellChecker spellChecker;

  @BeforeEach
  public void createNewSpellingCheckerObject(){
 
    spellChecker = mock(SpellChecker.class);
    when(spellChecker.isSpellingCorrect(anyString()))
      .thenReturn(true);
      
    sentenceSpellChecker = new SentenceSpellChecker(spellChecker);
  }

  @Test
  void canary(){
    assertTrue(true);
  }

  @Test
  void checkEmptyStringSpelling(){
    assertEquals("", sentenceSpellChecker.checkSpelling(""));
  }

  @Test
  void checkSpellingOneWord(){
    assertEquals("one", sentenceSpellChecker.checkSpelling("one"));
  }

  @Test
  void checkSpellingSentence(){
    assertEquals("two words",
      sentenceSpellChecker.checkSpelling("two words"));
  }

  @Test
  void checkOneWrongWord(){

    when(spellChecker.isSpellingCorrect("oen")).thenReturn(false);
    assertEquals("[oen]", sentenceSpellChecker.checkSpelling("oen"));
  }

  @Test
  void checkOneWrongWordInSentence(){
    when(spellChecker.isSpellingCorrect("oen")).thenReturn(false);
    assertEquals("[oen] two", sentenceSpellChecker.checkSpelling("oen two"));


  }

  @Test
  void checkAnotherWrongWordInSentence(){
    when(spellChecker.isSpellingCorrect("wto")).thenReturn(false);
    assertEquals("one [wto]", sentenceSpellChecker.checkSpelling("one wto"));
  }


  @Test
  void checkMultipleWrongWords(){
    when(spellChecker.isSpellingCorrect("oen")).thenReturn(false);
    when(spellChecker.isSpellingCorrect("wto")).thenReturn(false);

    assertEquals("[oen] [wto]",
      sentenceSpellChecker.checkSpelling("oen wto"));
  }

  @Test
  void checkIfSpellingChecked(){
    when(spellChecker.isSpellingCorrect("one"))
            .thenThrow(new RuntimeException("Unable to check spelling"));

    String result = sentenceSpellChecker.checkSpelling("one");

    assertEquals("Unable to check spelling", result);
  }
  @Test
  void spellCheckOneWordWebService(){
    SpellChecker spellChecker = new SpellCheckerWebService();
    assertTrue(spellChecker.isSpellingCorrect("cow"));
  }

  @Test
  void spellCheckOneIncorrectWordWebService(){
    SpellChecker spellChecker = new SpellCheckerWebService();
    assertFalse(spellChecker.isSpellingCorrect("brke"));
  }

  @Test
  void spellCheckWhenNetworkError(){
    when(spellChecker.isSpellingCorrect("one"))
            .thenThrow(new RuntimeException("Network Error"));

    String result = sentenceSpellChecker.checkSpelling("one");

    assertEquals("Network Error", result);
  }



}
