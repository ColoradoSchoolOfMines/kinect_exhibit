package edu.mines.csci598.recycler.splashscreen.weather;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import static junit.framework.Assert.*;

public class WeatherXMLParserTest {
    InputStream weatherStream;
    InputStream badWeatherStream;
    private static final String API_KEY = "b665b08214224103123010";
    private static final String BAD_API_KEY = "b665b08203123010";
    private static final String LOCAL_ZIP = "80401";

    @Before
    public void setup() throws IOException {
        weatherStream = getClass().getResourceAsStream("weather.xml");
        badWeatherStream = getClass().getResourceAsStream("weather-missing.xml");
    }

    @Test
    public void testParseXMLFile() throws IOException, SAXException, ParserConfigurationException {
        WeatherInformation information = LocalAreaWeather.parseWeatherXML(weatherStream);
        assertEquals(11, information.getTemperature());
        assertEquals(0.0, information.getPrecipitation());
        assertEquals(47, information.getHumidity());
        assertEquals(360, information.getWindDegree());
        assertEquals(9, information.getWindSpeed());
        assertEquals(10, information.getVisibility());
        assertEquals(1019, information.getPressure());
        assertEquals(0, information.getCloudCover());
    }

    @Test
    public void testParseIncompleteXMLFile() throws IOException, SAXException, ParserConfigurationException {
        WeatherInformation information = LocalAreaWeather.parseWeatherXML(badWeatherStream);
        assertEquals(0.0, information.getPrecipitation());
        assertEquals(47, information.getHumidity());
        assertEquals(360, information.getWindDegree());
        assertEquals(9, information.getWindSpeed());
        assertEquals(10, information.getVisibility());
        assertEquals(1019, information.getPressure());
        assertEquals(0, information.getCloudCover());
    }

    @Test
    @Ignore("Game box does not have internet connection at the moment")
    public void shouldNotFindData() throws IOException, SAXException, ParserConfigurationException {
        URL weatherUrl = new URL("http://free.worldweatheronline.com/feed/weather.ashx?key="+BAD_API_KEY+"&q="+LOCAL_ZIP+"&num_of_days=1&format=xml");
        URLConnection weatherConnection = weatherUrl.openConnection();
        InputStream weatherStream = weatherConnection.getInputStream();
        assertNull(LocalAreaWeather.parseWeatherXML(weatherStream));
    }


    @Test
    @Ignore("Game box does not have internet connection at the moment")
    public void shouldFindData() throws IOException, SAXException, ParserConfigurationException {
        URL weatherUrl = new URL("http://free.worldweatheronline.com/feed/weather.ashx?key="+API_KEY+"&q="+LOCAL_ZIP+"&num_of_days=1&format=xml");
        URLConnection weatherConnection = weatherUrl.openConnection();
        InputStream weatherStream = weatherConnection.getInputStream();
        assertNotNull(LocalAreaWeather.parseWeatherXML(weatherStream));
    }
}

