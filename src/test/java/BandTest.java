import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;

public class BandTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();


  @Test
  public void all_emptyAtFirst() {
    assertEquals(Band.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfNamesAretheSame() {
    Band firstBand = new Band("Band 1");
    Band secondBand = new Band("Band 1");
    assertTrue(firstBand.equals(secondBand));
  }

  @Test
  public void save_savesIntoDatabase_true() {
    Band myBand = new Band("Band 1");
    myBand.save();
    assertTrue(Band.all().get(0).equals(myBand));
  }

  @Test
  public void find_findBandInDatabase_true() {
    Band myBand = new Band("Band 1");
    myBand.save();
    Band savedBand = Band.find(myBand.getId());
    assertTrue(myBand.equals(savedBand));
  }

  @Test
  public void getVenues_retrievesALlVenuesFromDatabase_venues() {
    Band myBand = new Band("Band 1");
    myBand.save();
    Venue firstVenue = new Venue("Venue 1", myBand.getId());
    firstVenue.save();
    Venue secondVenue = new Venue("Venue 2", myBand.getId());
    secondVenue.save();
    Venue[] venues = new Venue[] { firstVenue, secondVenue };
    assertTrue(myBand.getVenues().containsAll(Arrays.asList(venues)));
  }
}
