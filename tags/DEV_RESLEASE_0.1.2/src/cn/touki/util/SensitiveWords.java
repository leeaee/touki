package cn.touki.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A class to deal with sensitive words.
 * <p/>
 * The property {@code charset} is used to set the characeter set when reading words list file, be sure to set this
 * before you read the words file.
 * <p/>
 * Initialize this class with {@link #setSourceFile} or {@link #setSourceFiles} method, and check words with {@link
 * #isValid} method.
 *
 * @author <A href="mailto:gregory@konlink.com">Gregory Song</A>
 * @version $Revision: 1.1.1.1 $
 * @since 2.0
 */
public class SensitiveWords {

    //Properties
    private Set<String> words;
    private String charset;

    //Constructor
    public SensitiveWords() {
        words = new HashSet<String>();
    }
    
    //Methods
    /**
     * Set the source file of sensitive words, which should be placed in the class path, with one word in each line.
     *
     * @param fileName the name of the file which contains the sensitive words.
     * @see #setSourceFiles
     */
    public void setSourceFile(String fileName) throws IOException {
        List<String> fileNames = new ArrayList<String>();
        fileNames.add(fileName);

        setSourceFiles(fileNames);
    }

    /**
     * Set a group of files to get word source, which should be placed in the class path.
     *
     * @param fileNames the list of filenames
     * @see #setSourceFile
     */
    public void setSourceFiles(List<String> fileNames) throws IOException {
        InputStreamReader insr;
        ClassLoader cl = Thread.currentThread().getContextClassLoader();

        //Read each file and fetch words
        for (String fileName : fileNames) {
            String trimmedFileName = fileName.trim();

            //get an InputSteamReader with default charset
            if (this.charset == null) {
                insr = new InputStreamReader(cl.getResourceAsStream(trimmedFileName));
            }
            //get an InputSteamReader with specified charset
            else {
                insr = new InputStreamReader(cl.getResourceAsStream(trimmedFileName), charset);
            }

            //Get an buffered reader so that we can read line at one time.
            BufferedReader reader = new BufferedReader(insr);
            String line;
            while ((line = reader.readLine()) != null) {
                words.add(line);
            }
        }
    }

    /**
     * Get the sensitive word collection with in a HashSet.
     *
     * @return the HashSet which contains 0 or more words in string.
     */
    public Set<String> getWords() {
        return words;
    }

    /**
     * Set the sensitive with a HashSet
     *
     * @param words the sensitive words to be set
     */
    public void setWords(Set<String> words) {
        this.words = words;
    }

    /**
     * Get current charset of the source files.
     *
     * @return charset name
     */
    public String getCharset() {
        return charset;
    }

    /**
     * Set the charset of the source files to be read.
     *
     * @param charset the charset name
     */
    public void setCharset(String charset) {
        this.charset = charset;
    }

    /**
     * Check whether the {@code wordToBeCheck} is a valid word.
     *
     * @param wordToBeCheck the word to be check
     * @return {@code true} on valid and {@code false} when it contains sensitive word(s).
     */
    public boolean isValid(String wordToBeCheck) {
        for (String sensitive : words) {
            if (wordToBeCheck.indexOf(sensitive) >= 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * Check whether the {@code wordsToBeCheck} are valid words.
     *
     * @param wordsToBeCheck the words in an string arrey to be check
     * @return {@code true} on valid and {@code false} when at least one of them contains sensitive word(s).
     */
    public boolean isValid(String[] wordsToBeCheck) {
        for (String wordToBeCheck : wordsToBeCheck) {
            if (!isValid(wordToBeCheck)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Check whether the {@code wordToBeCheck} is a valid word.
     *
     * @param wordToBeCheck the word to be check
     * @throws SensitiveWordsException when wordToBeCheck contains sensitive words.
     */
    public void checkValid(String wordToBeCheck) {
        for (String sensitive : words) {
            if (wordToBeCheck.indexOf(sensitive) >= 0) {
                throw new SensitiveWordsException(sensitive);
            }
        }
    }

    /**
     * Check whether the {@code wordsToBeCheck} are valid words.
     *
     * @param wordsToBeCheck the words in an string arrey to be check
     * @throws SensitiveWordsException when any of the words contains sensitive words.
     */
    public void checkValid(String[] wordsToBeCheck) {
        for (String wordToBeCheck : wordsToBeCheck) {
            checkValid(wordToBeCheck);
        }
    }

}
