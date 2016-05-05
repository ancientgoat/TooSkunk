package com.premierinc.common.rule;

import com.premierinc.rule.utils.SkTimer;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelCompilerMode;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 *
 */
public class SkSpelTest {

	@Test
	public void simpleTest() {

		GregorianCalendar c = new GregorianCalendar();
		c.set(1856, 7, 9);

		// The constructor arguments are name, birthday, and nationality.
		Inventor tesla = new Inventor("Nikola Tesla", c.getTime(), "Serbian");
		Map<String, Object> internalMap = new HashMap<String, Object>() {{
			put("name", "Nikola Tesla");
			put("birthdate", c.getTime());
			put("nationality", "Serbian");
		}};

		ExpressionParser parser = new SpelExpressionParser();
		Expression exp = parser.parseExpression("name");
		String name = (String) exp.getValue(tesla);
		System.out.println("********** Name : " + name);

		Expression exp2 = parser.parseExpression("['name']");
		name = (String) exp2.getValue(internalMap);
		System.out.println("********** Name : " + name);
	}

	@Test
	public void simpleExpressionTest() {

		// The constructor arguments are name, birthday, and nationality.
		Map<String, Object> internalMap = new HashMap<String, Object>() {{
			put("a", 3);
			put("b", 100);
			put("c", 17L);
		}};

		ExpressionParser parser = new SpelExpressionParser();
		Expression exp = parser.parseExpression("['a'] + ['b'] + ['c']");
		BigDecimal answer = exp.getValue(internalMap, BigDecimal.class);
		System.out.println("********** Answer : " + answer);
		Assert.assertEquals(answer, BigDecimal.valueOf(120), "Should be 120");
	}

	@Test
	public void nextExpressionTest() {

		List<Integer> intList = new ArrayList<Integer>() {{
			add(10);
			add(100);
			add(1000);
			add(10000);
			// add(100000);
			// add(1000000);
		}};

		startNextExpressionTest(intList, "--- OFF ---", SpelCompilerMode.OFF);
		startNextExpressionTest(intList, "--- MIXED ---", SpelCompilerMode.MIXED);
		startNextExpressionTest(intList, "--- IMMEDIATE ---", SpelCompilerMode.IMMEDIATE);
	}

	/**
	 *
	 * @param intList
	 * @param inDescription
	 * @param inSpelCompilerMode
	 */
	private void startNextExpressionTest(final List<Integer> intList, final String inDescription,
			final SpelCompilerMode inSpelCompilerMode) {
		final SpelParserConfiguration config = new SpelParserConfiguration(inSpelCompilerMode, this.getClass()
				.getClassLoader());
		System.out.println(inDescription);
		intList.forEach(i -> runNextExpressionTest(config, i));
	}

	/**
	 *
	 * @param inConfig
	 * @param loopNumber
	 */
	private void runNextExpressionTest(SpelParserConfiguration inConfig, int loopNumber) {
		IntStream range = IntStream.range(1, loopNumber);
		SkTimer timer = new SkTimer();
		range.forEach(i -> {
			// The constructor arguments are name, birthday, and nationality.
			Map<String, Object> internalMap = new HashMap<String, Object>() {{
				put("a", 3);
				put("b", 100);
				put("c", 17L);
			}};
			ExpressionParser parser = new SpelExpressionParser(inConfig);
			parser.parseExpression("['MILK']")
					.setValue(internalMap, 80);

			Expression exp = parser.parseExpression("['a'] + ['b'] + ['c'] + ['MILK']");
			BigDecimal answer = exp.getValue(internalMap, BigDecimal.class);
			// System.out.println("********** Answer : " + answer);
			Assert.assertEquals(answer, BigDecimal.valueOf(200), "Should be 200");
		});
		System.out.println(String.format("(%10d) Timer : %d", loopNumber, timer.stopAndDiff()));
	}

	/**
	 *
	 */
	public static class Inventor {

		private String name;
		private String nationality;
		private String[] inventions;
		private Date birthdate;
		private PlaceOfBirth placeOfBirth;

		public Inventor(String name, String nationality) {
			GregorianCalendar c = new GregorianCalendar();
			this.name = name;
			this.nationality = nationality;
			this.birthdate = c.getTime();
		}

		public Inventor(String name, Date birthdate, String nationality) {
			this.name = name;
			this.nationality = nationality;
			this.birthdate = birthdate;
		}

		public Inventor() {
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getNationality() {
			return nationality;
		}

		public void setNationality(String nationality) {
			this.nationality = nationality;
		}

		public Date getBirthdate() {
			return birthdate;
		}

		public void setBirthdate(Date birthdate) {
			this.birthdate = birthdate;
		}

		public PlaceOfBirth getPlaceOfBirth() {
			return placeOfBirth;
		}

		public void setPlaceOfBirth(PlaceOfBirth placeOfBirth) {
			this.placeOfBirth = placeOfBirth;
		}

		public void setInventions(String[] inventions) {
			this.inventions = inventions;
		}

		public String[] getInventions() {
			return inventions;
		}
	}

	public static class PlaceOfBirth {

		private String city;
		private String country;

		public PlaceOfBirth(String city) {
			this.city = city;
		}

		public PlaceOfBirth(String city, String country) {
			this(city);
			this.country = country;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String s) {
			this.city = s;
		}

		public String getCountry() {
			return country;
		}

		public void setCountry(String country) {
			this.country = country;
		}

	}
}
