package core.beans;

import java.io.Serializable;

public class Company implements Serializable, Comparable<Company> {
    private static final long serialVersionUID = 1L;

    private long id;
    private String name;
    private String password;
    private String email;

    public Company() {
    }

    public Company(String name,
                   String password,
                   String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String compName) {
        this.name = compName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int compareTo(Company company) {
        return Long.compare(this.id, company.id);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Company)) {
            return false;
        }
        Company other = (Company) obj;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        return String.format("Company ID: %d," +
                        " Name: %s," +
                        " Password: %s," +
                        " Email: %s",
                id, name, password, email);
    }
}
