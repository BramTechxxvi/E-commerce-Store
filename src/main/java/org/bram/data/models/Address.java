package org.bram.data.models;

import lombok.Data;

@Data
public class Address {

    private String street;
    private String city;
    private String state;
    private String houseNumber;
    private String country;
}
