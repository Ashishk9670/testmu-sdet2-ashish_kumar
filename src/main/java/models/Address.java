package models;

public final class Address {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String country;
    private final String city;
    private final String addressLine1;
    private final String postalCode;
    private final String phone;

    private Address(Builder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.email = builder.email;
        this.country = builder.country;
        this.city = builder.city;
        this.addressLine1 = builder.addressLine1;
        this.postalCode = builder.postalCode;
        this.phone = builder.phone;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String firstName() {
        return firstName;
    }

    public String lastName() {
        return lastName;
    }

    public String email() {
        return email;
    }

    public String country() {
        return country;
    }

    public String city() {
        return city;
    }

    public String addressLine1() {
        return addressLine1;
    }

    public String postalCode() {
        return postalCode;
    }

    public String phone() {
        return phone;
    }

    public static final class Builder {
        private String firstName;
        private String lastName;
        private String email;
        private String country;
        private String city;
        private String addressLine1;
        private String postalCode;
        private String phone;

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder country(String country) {
            this.country = country;
            return this;
        }

        public Builder city(String city) {
            this.city = city;
            return this;
        }

        public Builder addressLine1(String addressLine1) {
            this.addressLine1 = addressLine1;
            return this;
        }

        public Builder postalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Address build() {
            return new Address(this);
        }
    }
}
