package model;

public class Levenshtein {

    private static int MIN_LENGTH = 1;

    static int distance(String str1, String str2){
        if(str1 == null || str2 == null){
            throw new IllegalArgumentException("Invalid arguments");
        }
        if(str1.length() < MIN_LENGTH){
            return str2.length();
        }
        if(str2.length() < MIN_LENGTH){
            return str1.length();
        }
        int[][] matrix = new int[str1.length()][str2.length()];

        for(int i =0; i<str1.length(); i++){        // Fill first row
            matrix[i][0] = i;
        }

        for(int j=0; j<str2.length(); j++){         // Fill first column
            matrix[0][j] = j;
        }

        int cost;

        for(int i=1; i<str1.length(); i++){
            for(int j=1; j<str2.length(); j++){
                cost = (str1.charAt(i) == str2.charAt(j))? 0:1;         // Set cost value
                matrix[i][j] = minimum(matrix[i][j-1] + 1, matrix[i-1][j] + 1, matrix[i-1][j-1] + cost);
            }
        }
        return matrix[str1.length()-1][str2.length()-1];
    }

    private static int minimum(int a, int b, int c){
        return Math.min(a, Math.min(b,c));
    }

    static double normalizedSimilarity(String s1, String s2){
        return 1-1.0*distance(s1,s2)/Math.max(s1.length(),s2.length());
    }

}
