package fr.polytech.ig5.mnm.offerms;

import static org.junit.jupiter.api.Assertions.assertEquals;

import fr.polytech.ig5.mnm.offerms.models.*;
import fr.polytech.ig5.mnm.offerms.utils.Recommendation;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = OfferMsApplicationTests.class)
class OfferMsApplicationTests {

	@Test
	public void similarOfferAndCriteria() {
		Offer offer = new Offer(
				"Serveur",
				"Montpellier",
				LocalDate.parse("2023-05-01"),
				LocalDate.parse("2023-08-31"),
				ContractType.CDD,
				JobType.FULL_TIME,
				1200
		);
		Criteria criteria = new Criteria(
				"Serveur",
				ContractType.CDD,
				JobType.FULL_TIME,
				SectorType.ARTS,
				1200,
				LocalDate.parse("2023-05-01"),
				LocalDate.parse("2023-08-31"),
				"Montpellier"
		);

		Boolean res = Recommendation.isRecommended(offer, criteria);

		assertEquals(res, true);
	}

	@Test
	public void notReallySimilarButShouldBeRecommended() {
		Offer offer = new Offer(
				"Femme de ménage",
				"Montpellier",
				LocalDate.parse("2023-05-01"),
				LocalDate.parse("2023-08-31"),
				ContractType.CDD,
				JobType.FULL_TIME,
				1400
		);
		Criteria criteria = new Criteria(
				"Serveur",
				ContractType.CDD,
				JobType.FULL_TIME,
				SectorType.ARTS,
				1200,
				LocalDate.parse("2023-05-01"),
				LocalDate.parse("2023-08-31"),
				"Montpellier"
		);

		Boolean res = Recommendation.isRecommended(offer, criteria);

		assertEquals(res, true);
	}

	@Test
	public void differentOfferAndCriteriaShouldNotBeRecommended() {

		Offer offer = new Offer(
				"Autre",
				"Narbonne",
				LocalDate.parse("2023-05-01"),
				LocalDate.parse("2023-08-31"),
				ContractType.CDI,
				JobType.PARTIAL_TIME,
				1000
		);
		Criteria criteria = new Criteria(
				"Serveur",
				ContractType.CDD,
				JobType.FULL_TIME,
				SectorType.ARTS,
				1200,
				LocalDate.parse("2023-05-01"),
				LocalDate.parse("2023-08-31"),
				"Montpellier"
		);

		Boolean res = Recommendation.isRecommended(offer, criteria);

		assertEquals(res, false);
	}

	@Test
	public void dateCantMatchShouldNotBeRecommended() {

		Offer offer = new Offer(
				"Autre",
				"Narbonne",
				LocalDate.parse("2023-07-01"),
				LocalDate.parse("2023-08-31"),
				ContractType.CDI,
				JobType.PARTIAL_TIME,
				1000
		);
		Criteria criteria = new Criteria(
				"Serveur",
				ContractType.CDD,
				JobType.FULL_TIME,
				SectorType.ARTS,
				1200,
				LocalDate.parse("2023-05-01"),
				LocalDate.parse("2023-08-31"),
				"Montpellier"
		);

		Boolean res = Recommendation.isRecommended(offer, criteria);

		assertEquals(res, false);
	}

	@Test
	public void differentOfferAndCriteriaShould£NotBeRecommended() {

		Offer offer = new Offer(
				"Autre",
				"Montpellier",
				LocalDate.parse("2023-05-01"),
				LocalDate.parse("2023-08-31"),
				ContractType.CDI,
				JobType.PARTIAL_TIME,
				1000
		);

		Criteria criteria = new Criteria(
				"Serveur",
				ContractType.CDD,
				JobType.FULL_TIME,
				SectorType.ARTS,
				1200,
				LocalDate.parse("2023-05-01"),
				LocalDate.parse("2023-08-31"),
				"Montpellier"
		);

		Boolean res = Recommendation.isRecommended(offer, criteria);

		assertEquals(res, false);
	}

	@Test
	public void getRecommendations() {

		Offer offer = new Offer(
				"Autre",
				"Montpellier",
				LocalDate.parse("2023-05-01"),
				LocalDate.parse("2023-08-31"),
				ContractType.CDI,
				JobType.PARTIAL_TIME,
				1000
		);
		Offer offer1 = new Offer(
				"Serveur",
				"Montpellier",
				LocalDate.parse("2023-05-01"),
				LocalDate.parse("2023-08-31"),
				ContractType.CDD,
				JobType.FULL_TIME,
				1200
		);
		Offer offer2 = new Offer(
				"Femme de ménage",
				"Montpellier",
				LocalDate.parse("2023-05-01"),
				LocalDate.parse("2023-08-31"),
				ContractType.CDD,
				JobType.FULL_TIME,
				1400
		);

		List<Offer> offers = new ArrayList<>();
		offers.add(offer);
		offers.add(offer1);
		offers.add(offer2);

		Criteria criteria = new Criteria(
				"Serveur",
				ContractType.CDD,
				JobType.FULL_TIME,
				SectorType.ARTS,
				1200,
				LocalDate.parse("2023-05-01"),
				LocalDate.parse("2023-08-31"),
				"Montpellier"
		);

		List<Offer> recommendedOffers = Recommendation.getRecommendedOffers(offers,criteria);

		assertEquals(recommendedOffers.size(), 2);
	}

}
