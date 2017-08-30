package alepekhin.validator;

import java.util.Set;

import javax.validation.ConstraintViolation;

import org.junit.Test;

public class ValidatorTest {

	@Test
	public void test1() {
		printErrors(new ValueValidator(null).validate(ValueValidator.ValidatorTypes.NotEmpty));
		// must not be empty
	}

	@Test
	public void test2() {
		printErrors(new ValueValidator("").validate(ValueValidator.ValidatorTypes.NotBlank));
		// must not be blank
	}

	@Test
	public void test3() {
		printErrors(new ValueValidator("abc").validate(ValueValidator.ValidatorTypes.Length, 1, 2));
		// length must be between 1 and 2
	}

	@Test
	public void test4() {
		printErrors(new ValueValidator("abc").validate(ValueValidator.ValidatorTypes.Email));
		// must be a well-formed email address
	}

	@Test
	public void test5() {
		printErrors(new ValueValidator("abc@a").validate(ValueValidator.ValidatorTypes.Email));
		// OK
	}

	@Test
	public void test6() {
		printErrors(new ValueValidator("abc@a").validate(ValueValidator.ValidatorTypes.Pattern, "\\d+"));
		// must match "\d+"
	}

	@Test
	public void test7() {
		printErrors(new ValueValidator("123456").validate(ValueValidator.ValidatorTypes.Pattern, "\\d+"));
		// OK
	}

	private void printErrors(Set<ConstraintViolation<ValueValidator>> errors) {
		if (!errors.isEmpty()) {
			errors.forEach(p -> System.out.println(p.getMessage()));
		} else {
			System.out.println("OK");
		}
	}

}
