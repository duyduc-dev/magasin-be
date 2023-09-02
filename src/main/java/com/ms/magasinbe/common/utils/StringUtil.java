package com.ms.magasinbe.common.utils;

import java.text.Normalizer;

public class StringUtil {

  private static final int MAX_SLUG_LENGTH = 256;

  public static String slugify(final String s) {
    //algorithm used in https://github.com/slugify/slugify/blob/master/core/src/main/java/com/github/slugify/Slugify.java
    var input = s.toLowerCase().replaceAll("đ", "d");
    final String intermediateResult = Normalizer
            .normalize(input, Normalizer.Form.NFD)
            .replaceAll("[^\\p{ASCII}]", "")
            .replaceAll("[^-_a-zA-Z0-9]", "-")
            .replaceAll("\\s+", "-")
            .replaceAll("[-]+", "-").replaceAll("^-", "")
            .replaceAll("-$", "").toLowerCase();
    return intermediateResult.substring(0,
            Math.min(MAX_SLUG_LENGTH, intermediateResult.length()));
  }
}
