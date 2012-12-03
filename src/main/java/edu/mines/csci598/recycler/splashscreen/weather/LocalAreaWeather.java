package edu.mines.csci598.recycler.splashscreen.weather;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Logger;

public class LocalAreaWeather {

    private static final Logger log = Logger.getLogger(LocalAreaWeather.class.getName());
    private static final String API_KEY = "b665b08214224103123010";
    private static final String LOCAL_ZIP = "80401";
    private static final String BASE_URL = "http://free.worldweatheronline.com/feed/weather.ashx";

    public static WeatherInformation retrieveWeatherInformation() {
        try {
            URL weatherUrl = new URL(BASE_URL+"?key="+API_KEY+"&q="+LOCAL_ZIP+"&num_of_days=1&format=xml");
            URLConnection weatherConnection = weatherUrl.openConnection();
            InputStream weatherStream = weatherConnection.getInputStream();
            return parseWeatherXML(weatherStream);
        }
        catch (IOException ioe) {
            log.severe("IOException occured: " + ioe);
        }
        catch (ParserConfigurationException pce) {
            log.severe("ParserConfigurationException occured: " + pce);
        }
        catch (SAXException spe) {
            log.severe("SAX Parser Exception occured: " + spe);
        }
        return null;
    }

    public static WeatherInformation parseWeatherXML(InputStream weatherStream) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = docBuilder.parse(weatherStream);
        doc.getDocumentElement().normalize();
        NodeList conditions = doc.getElementsByTagName("current_condition");

        for (int node = 0; node < conditions.getLength(); node++) {
            Node condition = conditions.item(node);

            if (condition.getNodeType() == Node.ELEMENT_NODE) {
                return buildWeatherObject((Element) condition);
            }
        }
        return null;
    }

    private static WeatherInformation buildWeatherObject(Element condition) {
        String windDegree;
        String windSpeed;
        String pressure;
        String humidity;
        String visibility;
        String cloudCover;
        String precipitation;
        String temperature;

        windDegree = getTagValue("winddirDegree", condition);
        windSpeed = getTagValue("windspeedMiles", condition);
        pressure = getTagValue("pressure", condition);
        humidity = getTagValue("humidity", condition);
        visibility = getTagValue("visibility", condition);
        cloudCover = getTagValue("cloudcover", condition);
        precipitation = getTagValue("precipMM", condition);
        temperature = getTagValue("temp_C", condition);

        return new WeatherInformation(
                Integer.valueOf(windSpeed),
                Integer.valueOf(visibility),
                Integer.valueOf(windDegree),
                Integer.valueOf(cloudCover),
                Double.valueOf(precipitation),
                Integer.valueOf(temperature),
                Integer.valueOf(pressure),
                Integer.valueOf(humidity)
        );
    }

    private static String getTagValue(String sTag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
        Node nValue = nlList.item(0);
        return nValue.getNodeValue();
    }
}
