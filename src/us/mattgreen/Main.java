package us.mattgreen;

import java.util.Scanner;

public class Main {

    private final static FileInput cardAccts = new FileInput("movie_cards.csv");
    private final static FileInput cardPurchases = new FileInput("movie_purchases.csv");
    private final static FileInput movieRating = new FileInput("movie_rating.csv");
    private static String line = "";
    private static String ratingLine = "";

    public static void main(String[] args) {
        String line;
        String[] fields;
        int[] nums = new int[3];
        boolean first = true;
        boolean firstRating = true;
        System.out.format("%8s  %-18s  %6s  %6s   %8s\n","Account","Name", "Movies", "Points" , "Rating");
        while ((line = cardAccts.fileReadLine()) != null) {
            fields = line.split(",");
            findPurchases(first, fields[0], nums);
            first = false;
            findRating(firstRating, fields[0], nums);
            firstRating = false;
            System.out.format("%8s  %-18s  %6s  %6s   %8s\n",fields[0],fields[1], nums[0], nums[1], nums[2]);
        }

    }

    public static void findPurchases(boolean first, String acct, int[] nums) {
        nums[0] = 0;
        nums[1] = 0;
        String[] fields;
        boolean done = false;
        if (first) {
            line = cardPurchases.fileReadLine();
        }
        while ((line != null) && !(done)) {
            fields = line.split(",");
            if (fields[0].compareTo(acct) > 0) {
                done = true;
            }
            else if (fields[0].equals(acct)) {
                nums[0]++;
                nums[1] += Integer.parseInt(fields[2]);
                line = cardPurchases.fileReadLine();
            }

        }
    }

    public static void findRating(boolean firstRating, String acct, int[] nums) {
        int count = 0;
        nums[2] = 0;
        //nums[1] = 0;
        String[] fields;
        boolean done = false;
        if (firstRating) {
            ratingLine = movieRating.fileReadLine();
        }
        while ((ratingLine != null) && !(done)) {
            fields = ratingLine.split(",");
            if (fields[0].compareTo(acct) > 0) {
                done = true;
            }
            else if (fields[0].equals(acct)) {
                count++;
                nums[2] += Integer.parseInt(fields[1]);
                ratingLine = movieRating.fileReadLine();
            }

        }
        if (count > 0)
        {
            nums[2] /= count;
        }
    }
}