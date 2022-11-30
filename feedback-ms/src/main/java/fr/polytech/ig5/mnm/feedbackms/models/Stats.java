package fr.polytech.ig5.mnm.feedbackms.models;

import lombok.Data;

@Data
public class Stats {
    public double avgRating;
    public int nbOfFeedbacks;

    public Stats(double avgRating, int nbOfFeedbacks) {
        this.avgRating = avgRating;
        this.nbOfFeedbacks = nbOfFeedbacks;
    }
}
