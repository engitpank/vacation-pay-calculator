package ru.beresta.svs.vacationpay.service.factory;

import org.springframework.lang.Nullable;
import ru.beresta.svs.vacationpay.model.Country;
import ru.beresta.svs.vacationpay.model.UnsupportedCountryException;

public abstract class AbstractByCountryFactory<T> {

    public T get(Country country) {
        T providers = getEntity(country);
        if (providers == null) {
            throw new UnsupportedCountryException("Unsupported  for country: " + country.name());
        }
        return providers;
    }

    protected abstract @Nullable T getEntity(Country country);
}