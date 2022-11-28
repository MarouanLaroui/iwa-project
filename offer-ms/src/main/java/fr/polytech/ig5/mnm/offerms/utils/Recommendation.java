package fr.polytech.ig5.mnm.offerms.utils;

import fr.polytech.ig5.mnm.offerms.models.*;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import java.time.LocalDate;
import java.util.List;

@Component
public class Recommendation {

    public List<Offer> getRecommendedOffers(List<Offer> offers, Criteria criteria){

        offers.removeIf( offer -> isRecommended(offer, criteria));

        return offers;
    }
/*

    private ContractType contractType;
    private JobType jobType;
    private SectorType sector;
    private int salaryExpectation;
    private LocalDate startingDate;
    private LocalDate endDate;
    private String location;

    private String title;
    x private String description;
    - private String location;
    - private LocalDate startingDate;
    - private LocalDate endDate;
    - private ContractType contractType;
    - private JobType jobType;
    - private int salary;
    x private Boolean needDrivingLicence;

 */
    private Boolean isRecommended(Offer offer, Criteria criteria){
        int points = 0;

        // TODO: manage compare string with title and joblabel
        if(similarWord(offer.getTitle(), criteria.getJobLabel())){
            points += 4;
        }

        // TODO: mettre une marge de quelque jours (négociation des contrats possible)
        if(offer.getStartingDate().isBefore(criteria.getStartingDate())
                || offer.getEndDate().isAfter(criteria.getEndDate())){
            return false;
        }

        if(offer.getCompanyId().equals(criteria.getContractType())){
            points += 2;
        }

        if(offer.getLocation().equals(criteria.getLocation())){
            points += 3;
        }

        if(offer.getJobType().equals(criteria.getJobType())){
            points += 1;
        }

        if(offer.getSalary() >= criteria.getSalaryExpectation()){
            points += 1;
        }

        return points >= 7; // /11 points total
    }

    private boolean similarWord(String msg, String target) {

        String tab[] = msg.split("\\s");
        boolean isSimilar = false;
        int k = 0;
        while (k<tab.length && !isSimilar) {
            int a = 0;
            double b = target.length()*(0.9);

            for(int i=0; i < tab[k].length(); i++) {
                for(int j=0; j< target.length(); j++) {
                    if(String.valueOf(tab[k].charAt(i)).equals(String.valueOf(target.charAt(j))))
                        a+=1 ;
                }
            }

            if(a >= b)
                isSimilar= true;
            else
                isSimilar= false;
            k++;
        }

        return isSimilar;
    }

}
