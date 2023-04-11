package com.redacode.redacode;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.assertj.core.api.AbstractBigDecimalAssert;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;



class RedacodeApplicationTests {
	class Calculator {
		int add(int a, int b)
		{
			return a + b;
		}
	}

	Calculator underTest = new Calculator();


	//	@Test is a Junit annotation it tells that this method is a test method
	@Test
	void itShouldAddTwoNumbers() {
		//given
		int a = 34;
		int b = 45;
		//when
		int result = underTest.add(a, b);
		//then
		int expected = 79;
		Assertions.assertEquals(result, expected);
	}
}
