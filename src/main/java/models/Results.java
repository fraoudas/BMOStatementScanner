package models;

import java.text.DecimalFormat;
import java.util.HashMap;

public class Results {

    Float janResults;
    Float febResults;
    Float marResults;
    Float aprResults;
    Float mayResults;
    Float junResults;
    Float julResults;
    Float augResults;
    Float sepResults;
    Float octResults;
    Float novResults;
    Float decResults;

    public Results(HashMap<String, Float> results) {
        DecimalFormat df = new DecimalFormat("0.00");

        janResults = Float.valueOf(df.format(results.get("01").doubleValue()));
        febResults = Float.valueOf(df.format(results.get("02").doubleValue()));
        marResults = Float.valueOf(df.format(results.get("03").doubleValue()));
        aprResults = Float.valueOf(df.format(results.get("04").doubleValue()));
        mayResults = Float.valueOf(df.format(results.get("05").doubleValue()));
        junResults = Float.valueOf(df.format(results.get("06").doubleValue()));
        julResults = Float.valueOf(df.format(results.get("07").doubleValue()));
        augResults = Float.valueOf(df.format(results.get("08").doubleValue()));
        sepResults = Float.valueOf(df.format(results.get("09").doubleValue()));
        octResults = Float.valueOf(df.format(results.get("10").doubleValue()));
        novResults = Float.valueOf(df.format(results.get("11").doubleValue()));
        decResults = Float.valueOf(df.format(results.get("12").doubleValue()));
    }

    public Float getJanResults() {
        return janResults;
    }
    public Float getFebResults() {
        return febResults;
    }
    public Float getMarResults() {
        return marResults;
    }
    public Float getAprResults() {
        return aprResults;
    }
    public Float getMayResults() {
        return mayResults;
    }
    public Float getJunResults() {
        return junResults;
    }
    public Float getJulResults() {
        return julResults;
    }
    public Float getAugResults() {
        return augResults;
    }
    public Float getSepResults() {
        return sepResults;
    }
    public Float getOctResults() {
        return octResults;
    }
    public Float getNovResults() {
        return novResults;
    }
    public Float getDecResults() {
        return decResults;
    }

}
