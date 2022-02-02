package com.diegotobalina.framework.customizable.entities.example.domain;

import com.diegotobalina.framework.core.crud.StaffitSpecification;
import com.diegotobalina.framework.core.search.SpecSearchCriteria;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ExampleSpecification extends StaffitSpecification<Example> {

    private transient SpecSearchCriteria specSearchCriteria;

    public ExampleSpecification(SpecSearchCriteria criteria) {
        setSpecSearchCriteria(criteria);
    }

    @Override
    public SpecSearchCriteria getCriteria() {
        return getSpecSearchCriteria();
    }
}
