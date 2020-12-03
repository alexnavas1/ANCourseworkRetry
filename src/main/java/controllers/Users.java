package controllers;

import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import server.Main;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Path("Users/")
@Consumes(MediaType.MULTIPART_FORM_DATA)
@Produces(MediaType.APPLICATION_JSON)

public class Users{
    @GET
    @Path("list")
    public String UsersList() {
        System.out.println("Invoked Users.UsersList()");
        JSONArray response = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT UserID, Username, Password, FirstName, LastName, Admin FROM Users");
            ResultSet results = ps.executeQuery();
            while (results.next()==true) {
                JSONObject row = new JSONObject();
                row.put("UserID", results.getInt(1));
                row.put("Username", results.getString(2));
                row.put("Password", results.getString(3));
                row.put("FirstName", results.getString(4));
                row.put("LastName", results.getString(5));
                row.put("Admin", results.getBoolean(6));
                response.add(row);
            }
            return response.toString();
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"Error\": \"Unable to list items.  Error code xx.\"}";
        }
    }

    @POST
    @Path("create")
    public String UserCreate(@FormDataParam("Username") String Username, @FormDataParam("Password") String Password, @FormDataParam("FirstName") String FirstName, @FormDataParam("LastName") String LastName, @FormDataParam("AdminKey") String AdminKey) {
        try {
            System.out.println("Invoked Users.UserCreate Username=" + Username);
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Users (Username, Password, FirstName, LastName, AdminKey) VALUES (?, ?, ?, ?, ?)");
            ps.setString(1, Username);
            ps.setString(2, Password);
            ps.setString(3, FirstName);
            ps.setString(4, LastName);
            ps.setString(5, AdminKey);
            ps.execute();
            return "{\"OK\": \"User created\"}";
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"Error\": \"Unable to create user, please see server console for more info.\"}";
        }
    }
}

