/**
 * Created by robertdunn on 12/6/14.
 */
public class Constants
{
  public static final String API_KEY = "a2c9b857874a2be5";
  public static final String URL_BASE = "http://api.wunderground.com/api/" + API_KEY;
  public static final String RADAR_LOCAL_URL = "/radar/image.gif?maxlat=40.600&maxlon=-90.000&minlat=37.500&minlon=-95.000&smooth=1&noclutter=1&width=1300&height=750&rainsnow=1&timelabel=1&timelabel.x=10&timelabel.y=20&reproj.automerc=1&newmaps=1";
  public static final String CONDITIONS_URL = "/conditions/q/MO/Benton_City.json";
  public static final String HOURLY_URL = "/hourly/q/MO/Benton_City.json";
  public static final String FORECAST_URL = "/forecast10day/q/MO/Benton_City.json";
}
