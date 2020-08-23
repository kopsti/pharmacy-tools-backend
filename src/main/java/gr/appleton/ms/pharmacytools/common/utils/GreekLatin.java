package gr.appleton.ms.pharmacytools.common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * An utility class to generate greek words to greeklish.
 */
public final class GreekLatin {

    private GreekLatin() {
    }

    /**
     * Greek 2 latin string.
     *
     * @param greekWord the greek word
     * @return the string
     */
    public static String greek2latin(final String greekWord) {

        final StringBuilder sb = new StringBuilder();

        if (greekWord != null && !greekWord.equals("")) {
            final String greekWordToLowerCase = greekWord.toLowerCase();
            final Map<String, String> values = new HashMap<>();

            values.put("α", "a");
            values.put("ά", "a");
            values.put("β", "b");
            values.put("γ", "g");
            values.put("δ", "d");
            values.put("ε", "e");
            values.put("έ", "e");
            values.put("ζ", "z");
            values.put("η", "i");
            values.put("ή", "i");
            values.put("θ", "th");
            values.put("ι", "i");
            values.put("ί", "i");
            values.put("ϊ", "i");
            values.put("ΐ", "i");
            values.put("κ", "k");
            values.put("λ", "l");
            values.put("μ", "m");
            values.put("ν", "n");
            values.put("ξ", "ks");
            values.put("ο", "o");
            values.put("ό", "o");
            values.put("π", "p");
            values.put("ρ", "r");
            values.put("σ", "s");
            values.put("ς", "s");
            values.put("τ", "t");
            values.put("υ", "y");
            values.put("ύ", "y");
            values.put("φ", "f");
            values.put("χ", "x");
            values.put("ψ", "ps");
            values.put("ω", "w");
            values.put("ώ", "w");

            for (int i = 0; i < greekWordToLowerCase.length(); i++) {
                if (values.containsKey(String.valueOf(greekWordToLowerCase.charAt(i)))) {
                    sb.append(values.get(String.valueOf(greekWordToLowerCase.charAt(i))));
                } else {
                    sb.append(greekWordToLowerCase.charAt(i));
                }
            }
        }
        return sb.toString();
    }

}
