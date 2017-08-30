Sometimes we need to test String value against some constraints.
For example to test is it appropriate length or is it valid email address and so on.
Instead of direct check or inventing self made framework we can use Hibernate Validator.
It is oriented mainly on annotations so it's not obvious how to check a given value.
Class ValueValidator from this project do it.
For example, test "abc" lentgth with constraint min=1 max=2

Set<ConstraintViolation<ValueValidator>> errors = 
    new ValueValidator("abc").validate(ValueValidator.ValidatorTypes.Length, 1, 2);
    
In errors we will get "length must be between 1 and 2"  
errors will be empty if constraint is satisfied.

There are a lot of already implemented constraints in Hibernate Validator
and you can easily write a new one if needed. 