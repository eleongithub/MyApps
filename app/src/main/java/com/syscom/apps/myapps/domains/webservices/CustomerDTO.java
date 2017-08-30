package com.syscom.apps.myapps.domains.webservices;

import java.io.Serializable;

/**
 * Classe des utilisateurs
 *
 * Created by Eric LEGBA on 10/01/17.
 * @author  Eric LEGBA
 */
public class CustomerDTO implements Serializable{

    private Long id;
    private String name ;
    private String firstName;
    private String mail;
    private String phone;
    private String password;
    private RoleDTO roleDTO;

    public CustomerDTO(){

    }

    /**
     *
     * Builder pour construire les attributs de l'objet
     *
     * @author Eric LEGBA
     *
     */
    public static class Builder {
        private Long id;
        private String name;
        private String firstName;
        private String mail;
        private String phone;
        private String password;
        private RoleDTO roleDTO;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder mail(String mail) {
            this.mail = mail;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder roleDTO(RoleDTO roleDTO) {
            this.roleDTO = roleDTO;
            return this;
        }
        public CustomerDTO build() {
            return new CustomerDTO(this);
        }

    }

    /**
     *
     * Constructeur de l'lobjet à partir du builder.
     *
     * @param builder {@link Builder}
     */
    private CustomerDTO(Builder builder) {
        id = builder.id;
        name = builder.name;
        firstName = builder.firstName;
        mail = builder.mail;
        password = builder.password;
        phone = builder.phone;
        roleDTO = builder.roleDTO;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoleDTO getRoleDTO() {
        return roleDTO;
    }

    public void setRoleDTO(RoleDTO roleDTO) {
        this.roleDTO = roleDTO;
    }

}
