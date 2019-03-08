package services;

import core.enums.ClientType;

public class AnonymousUser {

    private String name;
    private String password;
    private ClientType clientType;

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public ClientType getClientType() {
        return clientType;
    }
}
