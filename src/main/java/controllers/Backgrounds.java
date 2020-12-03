package controllers;

import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import server.Main;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Path("Backgrounds/")
@Consumes(MediaType.MULTIPART_FORM_DATA)
@Produces(MediaType.APPLICATION_JSON)

public class Backgrounds {
    @GET
    @Path("get/{BackgroundID}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String GetBackground(@PathParam("BackgroundID") Integer BackgroundID) {
        System.out.println("Invoked Background.Backgroundget() with BackgroundID " + BackgroundID);
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT BackgroundID, BackgroundImage FROM Backgrounds WHERE BackgroundID = ?");
            ps.setInt(1, BackgroundID);
            ResultSet results = ps.executeQuery();
            JSONObject response = new JSONObject();
            if (results.next()== true) {
                response.put("BackgroundID", results.getInt(1));
                response.put("BackgroundImage", results.getString(2));
            }
            return response.toString();
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"Error\": \"Unable to get item, please see server console for more info.\"}";
        }
    }
}
