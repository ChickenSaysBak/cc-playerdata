package me.chickensaysbak.ccplayerdata.model;

public enum Rank {

    NONE, EGG, CHICK, CHICKEN, ELDER, CLOUDIAN;

    public static Rank fromString(String str) throws IllegalArgumentException {

        if (str.matches("\\d+")) {

            int ordinal = Integer.parseInt(str);
            Rank[] values = Rank.values();

            if (ordinal >= 0 && ordinal < values.length) return values[ordinal];

        }

        return Rank.valueOf(str.toUpperCase());

    }

}
