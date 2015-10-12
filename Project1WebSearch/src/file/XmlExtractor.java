package file;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import demo.Article;

public class XmlExtractor {

	public static ArrayList<Article> getArticles(String fileContent) {
		ArrayList<Article> articles = new ArrayList<>();
		// Get the DOM Builder Factory

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		// Get the DOM Builder

		DocumentBuilder builder;
		Document document;
		try {
			builder = factory.newDocumentBuilder();

			// Load and Parse the XML document

			// document contains the complete XML as a Tree.

			document = builder.parse(Constants.FILE_LOCATION_ON_DISK + fileContent);

			// Iterating through the nodes and extracting the data.

			NodeList nodeList = document.getDocumentElement().getChildNodes();

			for (int i = 0; i < nodeList.getLength(); i++) {

				// We have encountered an <employee> tag.

				Node node = nodeList.item(i);

				if (node instanceof Element) {

					Article article = new Article();

					// article.title =
					// node.getAttributes().getNamedItem("id").getNodeValue();

					NodeList childNodes = node.getChildNodes();

					for (int j = 0; j < childNodes.getLength(); j++) {

						Node cNode = childNodes.item(j);

						// Identifying the child tag of employee encountered.

						if (cNode instanceof Element) {

							String content = cNode.getLastChild().

							getTextContent().trim();

							switch (cNode.getNodeName()) {
							case "TITLE":
								article.title = content;
								break;
							case "BODY":
								article.content = content;
								break;
							}

						}

					}

					articles.add(article);

				}

			}
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return articles;
	}

	public static void main(String[] args) throws Exception {

		// Get the DOM Builder Factory

		DocumentBuilderFactory factory =

		DocumentBuilderFactory.newInstance();

		// Get the DOM Builder

		DocumentBuilder builder = factory.newDocumentBuilder();

		// Load and Parse the XML document

		// document contains the complete XML as a Tree.

		Document document =

		builder.parse(

		ClassLoader.getSystemResourceAsStream("xml/employee.xml"));

		List<Employee> empList = new ArrayList<>();

		// Iterating through the nodes and extracting the data.

		NodeList nodeList = document.getDocumentElement().getChildNodes();

		for (int i = 0; i < nodeList.getLength(); i++) {

			// We have encountered an <employee> tag.

			Node node = nodeList.item(i);

			if (node instanceof Element) {

				Employee emp = new Employee();

				emp.id = node.getAttributes().

				getNamedItem("id").getNodeValue();

				NodeList childNodes = node.getChildNodes();

				for (int j = 0; j < childNodes.getLength(); j++) {

					Node cNode = childNodes.item(j);

					// Identifying the child tag of employee encountered.

					if (cNode instanceof Element) {

						String content = cNode.getLastChild().

						getTextContent().trim();

						switch (cNode.getNodeName()) {

						case "firstName":

							emp.firstName = content;

							break;

						case "lastName":

							emp.lastName = content;

							break;

						case "location":

							emp.location = content;

							break;

						}

					}

				}

				empList.add(emp);

			}

		}

		// Printing the Employee list populated.

		for (Employee emp : empList) {

			System.out.println(emp);

		}

	}

}

class Employee {

	String id;

	String firstName;

	String lastName;

	String location;

	@Override

	public String toString() {

		return firstName + " " + lastName + "(" + id + ")" + location;

	}

}
