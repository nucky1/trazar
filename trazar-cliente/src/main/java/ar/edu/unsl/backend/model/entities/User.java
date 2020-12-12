package ar.edu.unsl.backend.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User
{
    private Integer id;
    private String name;
    private String username;
    private String email;
    private String phone;
    private String website;

    public Integer getId()
    {
        return this.id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getUserName()
    {
        return this.username;
    }

    public void setUserName(String username)
    {
        this.username = username;
    }

    public String getUsername()
    {
        return this.username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getEmail()
    {
        return this.email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPhone()
    {
        return this.phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getWebsite()
    {
        return this.website;
    }

    public void setWebsite(String website)
    {
        this.website = website;
    }
}