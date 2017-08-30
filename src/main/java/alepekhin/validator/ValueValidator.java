package alepekhin.validator;

import java.lang.annotation.ElementType;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;

import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.HibernateValidatorConfiguration;
import org.hibernate.validator.cfg.ConstraintMapping;
import org.hibernate.validator.cfg.defs.EmailDef;
import org.hibernate.validator.cfg.defs.LengthDef;
import org.hibernate.validator.cfg.defs.NotBlankDef;
import org.hibernate.validator.cfg.defs.NotEmptyDef;
import org.hibernate.validator.cfg.defs.PatternDef;

/**
 * Validates value according implemented constraints. 
 * <br/>How to add new validator:
 * <br/>- add name to ValidatorTypes
 * <br/>- add new case to switch
 */
public class ValueValidator {
	
	public static enum ValidatorTypes {
			CreditCardNumber, Currency, DecimalMax, DecimalMin, Digits, DurationMax, DurationMin,
			Email, Length, Max, Min, Negative, NegativeOrZero, NotBlank, NotEmpty, NotNull, Null,
			Past, PastOrPresent, Pattern, Positive, PositiveOrZero, Range, Size, URL
	}

	public String value;

	public ValueValidator(String value) {
		this.value = value;
	}

	public Set<ConstraintViolation<ValueValidator>> validate(ValidatorTypes validatorType, Object... params) {
		HibernateValidatorConfiguration configuration = Validation.byProvider(HibernateValidator.class).configure();
		ConstraintMapping mapping = configuration.createConstraintMapping();
		switch (validatorType) {
		case Length:
			mapping.type(ValueValidator.class).property("value", ElementType.FIELD)
					.constraint(new LengthDef().min((Integer) params[0]).max((Integer) params[1]));
			break;
		case NotEmpty:
			mapping.type(ValueValidator.class).property("value", ElementType.FIELD).constraint(new NotEmptyDef());
			break;
		case NotBlank:
			mapping.type(ValueValidator.class).property("value", ElementType.FIELD).constraint(new NotBlankDef());
			break;
		case Email:
			mapping.type(ValueValidator.class).property("value", ElementType.FIELD).constraint(new EmailDef());
			break;
		case Pattern:
			mapping.type(ValueValidator.class).property("value", ElementType.FIELD)
					.constraint(new PatternDef().regexp((String) params[0]));
			break;
		default:
			throw new IllegalStateException(validatorType.name() + " is not implemented yet");
		}
		return configuration.addMapping(mapping).buildValidatorFactory().getValidator().validateProperty(this, "value");
	}

}
