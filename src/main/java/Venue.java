import java.util.List;
import org.sql2o.*;

public class Venue {
  private int id;
  private int bandId;
  private String name;

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public int getVenueId() {
    return venueId;
  }

  public Venu(String name, int bandId) {
    this.name = name;
    this.bandId = bandId;
  }

  public static List<Venue> all() {
    String sql = "SELECT * FROM venuess";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Venue.class);
    }
  }

  @Override
  public boolean equals(Object otherVenue){
    if (!(otherVenue instanceof Venue)) {
      return false;
    } else {
      Venue newVenue = (Venue) otherVenue;
      System.out.println(this.getBandId());
      System.out.println(newVenue.getBandId());
      return this.getName().equals(newVenue.getName()) &&
             this.getId() == newVenue.getId() &&
             this.getBandId() == newVenue.getBandId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO venues(name, bandId) VALUES (:name, :bandId)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", name)
        .addParameter("bandId", bandId)
        .executeUpdate()
        .getKey();
    }
  }

  public static Venue find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM venues where id=:id";
      Venue venue = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Venue.class);
      return venue;
    }
  }

  public static void delete(int id) {
    String sql = "DELETE FROM venues WHERE id=:id";
    try(Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
        .addParameter("id", id)
        .executeUpdate();
    }
  }
}
