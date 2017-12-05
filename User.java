import java.io.Serializable;


public class User implements Serializable
{
    private int id;
    private String name;
    private String password;
    
    public User(int id, String name, String password)
    {
        this.id = id;
        this.name = name;
        this.password = password;
    }
    
    public int getID()
    {
        return id;
    }
    
    public String getPassword()
    {
        return password;
    }
    
    public String getName()
    {
        return name;
    }
    
    public boolean authentification(int id, String password)
    {
        return this.id == id && this.password.equals(password);
    }
    
    public String toString()
    {
       return "Id: " + getID() + " Name: " + getName();
    }
}
