package pl.ante.portfolioanteapp.model.projection.utils;

import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;

import java.time.Year;
import java.util.Objects;

public class YearTypeDescriptor extends AbstractTypeDescriptor<Year> {

    public static final YearTypeDescriptor INSTANCE = new YearTypeDescriptor();

    public YearTypeDescriptor() {
        super(Year.class);
    }

    @Override
    public boolean areEqual(
            Year one,
            Year another) {
        return Objects.equals(one, another);
    }

    @Override
    public String toString(final Year value) {
        return value.toString();
    }

    @Override
    public Year fromString(final String string) {
        return Year.parse(string);
    }

    @Override
    public <X> X unwrap(
            Year value,
            Class<X> type,
            WrapperOptions options) {

        if (value == null) {
            return null;
        }

        if (String.class.isAssignableFrom(type)) {
            return (X) toString(value);
        }

        if (Number.class.isAssignableFrom(type)) {
            Short numericValue = (short) value.getValue();
            return (X) numericValue;
        }

        throw unknownUnwrap(type);

    }


    @Override
    public <X> Year wrap(
            X value,
            WrapperOptions options) {

        if (value == null) {
            return null;
        }

        if (value instanceof String) {
            return fromString((String) value);
        }

        if (value instanceof Number) {
            short numericalValue = ((Number) value).shortValue();
            return Year.of(numericalValue);
        }

        throw unknownWrap(value.getClass());


    }
}
