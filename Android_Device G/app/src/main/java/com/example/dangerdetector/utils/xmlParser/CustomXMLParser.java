package com.example.dangerdetector.utils.xmlParser;

import android.util.Xml;

import com.example.dangerdetector.data.TimeStep;
import com.example.dangerdetector.data.Vehicle;
import com.example.dangerdetector.data.XMLFeed;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CustomXMLParser {

    private static final String ns = null;

    // instantiate parser
    public XMLFeed parse(InputStream inputStream) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(inputStream, null);
            parser.nextTag();
            return readXmlFeed(parser);
        } catch (XmlPullParserException xmlPullParserException) {
            xmlPullParserException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } finally {
            inputStream.close();
        }
        return null;
    }

    // read the xml feed
    private XMLFeed readXmlFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        List<TimeStep> timeStepList = new ArrayList<>();
        TimeStep timeStep;

        parser.require(XmlPullParser.START_TAG, ns, "fcd-export");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("timestep")) {
                timeStep = readTimeStepFeed(parser);
                timeStepList.add(timeStep);
            } else {
                skip(parser);
            }
        }
        XMLFeed xmlFeed = new XMLFeed();
        xmlFeed.setTimeStepList(timeStepList);
        return xmlFeed;
    }

    // read the time-step tag
    private TimeStep readTimeStepFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "timestep");
        Vehicle vehicle = null;
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("vehicle")) {
                vehicle = readVehicle(parser);
            } else {
                skip(parser);
            }
        }
        return new TimeStep(vehicle);
    }

    // read the Vehicle Tag
    private Vehicle readVehicle(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "vehicle");
        String xmlTag = parser.getName();
        String latitude = "";
        String longitude = "";

        if (xmlTag.equals("vehicle")) {
            latitude = parser.getAttributeValue(ns, "x");
            longitude = parser.getAttributeValue(ns, "y");
            parser.nextTag();
        } else {
            skip(parser);
        }
        parser.require(XmlPullParser.END_TAG, ns, "vehicle");
        return new Vehicle(latitude, longitude);
    }

    // skip unnecessary tags
    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }

        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

}
