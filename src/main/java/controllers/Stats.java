package controllers;

import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import server.Main;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Path("Stats/")
@Consumes(MediaType.MULTIPART_FORM_DATA)
@Produces(MediaType.APPLICATION_JSON)

public class Stats{
    @GET
    @Path("get")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String GetStats(@CookieParam("Token") String token) {
        //System.out.println("Invoked Stats.Statsget() with UserID " + UserID);
        try {
            PreparedStatement id = Main.db.prepareStatement("SELECT UserID FROM Users WHERE Token = ?");
            id.setString(1,token);
            ResultSet idRes = id.executeQuery();
            JSONArray stats = new JSONArray();
            if(idRes.next()){
                int UserID = idRes.getInt(1);
                PreparedStatement ps = Main.db.prepareStatement("SELECT StatID, UserID, Health, Stamina, Score, ProgressID FROM Stats WHERE UserID = ?");
                ps.setInt(1, UserID);
                ResultSet results = ps.executeQuery();
                JSONObject response = new JSONObject();
                while (results.next()) {
                    response.put("StatID", results.getInt(1));
                    response.put("UserID", results.getInt(2));
                    response.put("Health", results.getInt(3));
                    response.put("Stamina", results.getInt(4));
                    response.put("Score", results.getInt(5));
                    response.put("ProgressID", results.getInt(6));
                    stats.add(response);
                }
                JSONObject fin = new JSONObject();
                System.out.println(stats);
                fin.put("stats",stats);
                return fin.toString();
            }else{
                return "{\"Error\": \"No current login session found\"}";
            }

        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"Error\": \"Unable to get item, please see server console for more info.\"}";
        }
    }

    @POST
    @Path("save")
    public String StatsSave(@FormDataParam("UserID") Integer UserID, @FormDataParam("Health") Integer Health, @FormDataParam("Stamina") Integer Stamina, @FormDataParam("Score") Integer Score, @FormDataParam("ProgressID") Integer ProgressID) {
        try {
            System.out.println("Invoked Stats.StatsSave UserID=" + UserID);
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Stats SET Health = ?, Stamina = ?, Score = ?, ProgressID = ? WHERE UserID = ?");
            ps.setInt(1, UserID);
            ps.setInt(2, Health);
            ps.setInt(3, Stamina);
            ps.setInt(4, Score);
            ps.setInt(5, ProgressID);
            ps.execute();
            return "{\"OK\": \"Stats updated\"}";
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"Error\": \"Unable to update item, please see server console for more info.\"}";
        }
    }


}
