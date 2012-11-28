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

public class LocalAreaWeather {
    private static final String API_KEY = "b665b08214224103123010";
    private static final String LOCAL_ZIP = "80401";

    public static WeatherInformation retrieveWeatherInformation() {
        try {
            URL weatherUrl = new URL("http://free.worldweatheronline.com/feed/weather.ashx?key="+API_KEY+"&q="+LOCAL_ZIP+"&num_of_days=1&format=xml");
            URLConnection weatherConnection = weatherUrl.openConnection();
            InputStream weatherStream = weatherConnection.getInputStream();
            return parseWeatherXML(weatherStream);
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
        catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        }
        catch (SAXException spe) {
            spe.printStackTrace();
        }
        return null;
    }

    public static WeatherInformation parseWeatherXML(InputStream weatherStream) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = docBuilder.parse(weatherStream);
        doc.getDocumentElement().normalize();
        NodeList conditions = doc.getElementsByTagName("current_condition");

        String windDegree;
        String windSpeed;
        String cloudCover;
        String precipitation;
        String temperature;
        String pressure;
        String humidity;
        String visibility;

        for (int node = 0; node < conditions.getLength(); node++) {
            Node condition = conditions.item(node);

            if (condition.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element)condition;

                windDegree = getTagValue("winddirDegree", element);
                windSpeed = getTagValue("windspeedMiles", element);
                pressure = getTagValue("pressure", element);
                humidity = getTagValue("humidity", element);
                visibility = getTagValue("visibility", element);
                cloudCover = getTagValue("cloudcover", element);
                precipitation = getTagValue("precipMM", element);
                temperature = getTagValue("temp_C", element);

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
        }
        return null;
    }

    private static String getTagValue(String sTag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();

        Node nValue = nlList.item(0);

        return nValue.getNodeValue();
    }
}
