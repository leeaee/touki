package cn.touki.util;

import java.util.Random;

/**
 * The utility to generate a set of random code.
 *
 */
public class RandomCode {

    //Properties
    /**
     * 仅在数字中生成随机码
     */
    public static final int DIGIT_ONLY = 0;

    /**
     * 仅在字母中生成随机码
     */
    public static final int ALPHABET_ONLY = 1;

    /**
     * 在数字和字母中生成随机码
     */
    public static final int DIGIT_ALPHABET = 2;

    //Constructor

    //Methods
    public static String getRandString(int length, int charScope) {
        if (length <= 0) {
            return "";
        }

        int randScope, randBase;
        switch (charScope) {
            case DIGIT_ONLY:
                randScope = 9;
                randBase = 0;
                break;
            case ALPHABET_ONLY:
                randScope = 25;
                randBase = 10;
                break;
            case DIGIT_ALPHABET:
                randScope = 35;
                randBase = 0;
                break;
            default:
                randScope = 9;
                randBase = 0;
                break;
        }

        StringBuffer randStr = new StringBuffer(length);
        for (int i = 0; i < length; i++) {
            Random rand = new Random();
            int charOffset = randBase + rand.nextInt(randScope);

            //Avoid Letter I, O, and Q so that users won't get confused
            if (charOffset == 18 || charOffset == 24 || charOffset == 26) {
                i--;
                continue;
            }

            char randChar;
            if (charOffset < 10) { // Generate a digit;
                randChar = (char) (48 + charOffset);
            }
            else {                 // Generate a alphabet char
                randChar = (char) (65 + charOffset - 10);
            }

            randStr.append(randChar);
        }

        return randStr.toString();
    }

}
