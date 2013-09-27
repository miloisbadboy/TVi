package com.chiemtinhapp;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.os.AsyncTask;

public class DataFetchingOperation extends
		AsyncTask<String, Void, ArrayList<String[]>> {

	@Override
	public ArrayList<String[]> doInBackground(String... params) {
		try {
			// url initialize
			URL url = new URL((String) params[0]);
			URLConnection conn = url.openConnection();

			// document initialize
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(conn.getInputStream());

			// normalize XML document
			doc.getDocumentElement().normalize();

			NodeList nodeList = doc.getElementsByTagName("item");
			ArrayList<String[]> data = new ArrayList<String[]>();

			// parse node list
			for (int i = 0; i < nodeList.getLength(); i++) {

				Node node = nodeList.item(i);

				if (node.getNodeType() == Node.ELEMENT_NODE) {

					Element element = (Element) node;
					
					String eTitle = element.getElementsByTagName("title")
							.item(0).getTextContent();
					String eValue = element.getElementsByTagName("value")
							.item(0).getTextContent();

					data.add(new String[] { eTitle, eValue });
				}
			}

			return data;
		} catch (Exception e) {
			return null;
		}
	} // doInBackground
} // class DataFetchingOperation