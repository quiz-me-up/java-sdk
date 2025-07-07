package io.github.cnadjim.customer;


import io.github.quizmeup.sdk.eventflow.annotation.AggregateIdentifier;

import java.time.LocalDate;

public interface CustomerCommand  {

    record CreateCustomerCommand(@AggregateIdentifier String id, String name) implements CustomerCommand {
    }

    record UpdateCustomerNameCommand(@AggregateIdentifier String id, String newName) implements CustomerCommand {
    }

    record UpdateCustomerBirthdayCommand(@AggregateIdentifier String id, LocalDate newBirthDay) implements CustomerCommand {
    }

    record DeleteCustomerCommand(@AggregateIdentifier String id) implements CustomerCommand {
    }
}
