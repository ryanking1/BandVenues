import org.junit.*;
import static org.junit.Assert.*;

public class VenueTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Venue.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfNamesAretheSame() {
    Venue firstVenue = new Venue("Venue 1", 1);
    Venue secondVenue = new Venue("Venue 1", 1);
    assertTrue(firstVenue.equals(secondVenue));
  }

  @Test
  public void save_returnsTrueIfNamesAretheSame() {
    Venue myVenue = new Venue("Venue 1", 1);
    myVenue.save();
    assertTrue(Venue.all().get(0).equals(myVenue));
  }

  @Test
  public void save_assignsIdToObject() {
    Venue myVenue = new Venue("Venue 1", 1);
    myVenue.save();
    Venue savedVenue = Venue.all().get(0);
    assertEquals(myVenue.getId(), savedVenue.getId());
  }

  @Test
  public void find_findsVenueInDatabase_true() {
    Venue myVenue = new Venue("Venue 11", 7);
    myVenue.save();
    Venue savedVenue = Venue.find(myVenue.getId());
    assertTrue(myVenue.equals(savedVenue));
  }

  @Test
  public void save_savesBandIdIntoDB_true() {
    Band myBand = new Band("Band 1");
    myBand.save();
    Venue myVenue = new Venue("Venue 1", myBand.getId());
    myVenue.save();
    Venue savedVenue = Venue.find(myVenue.getId());
    assertEquals(savedVenue.getBandId(), myBand.getId());
  }
}
