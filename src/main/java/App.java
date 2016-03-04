import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("bands", Band.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String bandName = request.queryParams("bandName");
      Band newBand = new Band(bandName);
      newBand.save();
      List<Band> bandList = newBand.all();
      model.put("bands", bandList);
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/delete/band/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int id = Integer.parseInt(request.params(":id"));
      Band.deleteBand(id);
      model.put("bands", Band.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Band band = Band.find(Integer.parseInt(request.params(":id")));
      List<Venue> venues = band.getVenues();
      model.put("band", band);
      model.put("venues", venues);
      model.put("template", "templates/addVenue.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int id = Integer.parseInt(request.queryParams("bandId"));
      Band band = Band.find(id);
      String venueName = request.queryParams("venueName");
      Venue newVenue = new Venue(venueName, id);
      newVenue.save();
      List<Venue> venues = band.getVenues();
      model.put("band", band);
      model.put("venues", venues);
      model.put("template", "templates/addVenue.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/delete/venue/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int id = Integer.parseInt(request.params(":id"));
      Venue.delete(id);
      Band band = Band.find(Integer.parseInt(request.queryParams("bandId")));
      List<Venue> venues = band.getVenues();
      model.put("band", band);
      model.put("venues", venues);
      model.put("template", "templates/addVenue.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/:id/update", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Band band = Band.find(Integer.parseInt(request.queryParams("bandId")));
      String newName = request.queryParams("updateBand");
      band.update(newName);
      List<Venue> venues = band.getVenues();
      model.put("venues", venues);
      model.put("band", band);
      model.put("template", "templates/addVenue.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/index/delete", (request, response) -> {
      Band.deleteAll();
      response.redirect("/");
      return null;
  });
  }
}
