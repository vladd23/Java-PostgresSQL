package ro.ubbcluj.map.domain.validators;


import ro.ubbcluj.map.domain.Utilizator;

import java.util.List;
import java.util.Objects;

public class UtilizatorValidator implements Validator<Utilizator> {
    @Override
    public void validate(Utilizator user) throws ValidationException {
        //TODO: implement method validate
        String errors = null;
        if(Objects.equals(user.getFirstName(), user.getLastName())) {
            errors +="Numele si prenumele nu pot fi identice";
            throw new ValidationException(errors);
        }
        if (user.getId2() == null) {
            errors +="Id-ul nu poate fi null";
            throw new ValidationException(errors);

        }
    }
}
