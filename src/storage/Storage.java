package storage;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.api.services.vision.v1.model.EntityAnnotation;
import com.google.common.collect.ImmutableList;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;

public class Storage {
	private ArrayList<String> x0List;
	private ArrayList<String> x1List;
	private ArrayList<String> y0List;
	private ArrayList<String> y1List;
	private ArrayList<String> wordIDs;
	private ArrayList<String> OCRValueList;
	private String[] lineList;
	private String imageRealPath;
	private String imageLocalPath;
	private String wholeContent;
	private String name;
	private String phone;
	private String fax;
	private String email;
	private String address;

	public Storage() {
	}

	public void init() {
		x0List = new ArrayList<String>();
		x1List = new ArrayList<String>();
		y0List = new ArrayList<String>();
		y1List = new ArrayList<String>();
		wordIDs = new ArrayList<String>();
		OCRValueList = new ArrayList<String>();
		imageRealPath = "";
		imageLocalPath = "";
		imageRealPath = "";
		imageLocalPath = "";
		wholeContent = "";
		name = "Not Found";
		phone = "Not Found";
		fax = "Not Found";
		email = "Not Found";
		address = "Not Found";
	}

	public void setupImageRealPath(String realPath) {
		imageRealPath = realPath;
	}

	public String getImageRealPath() {
		return imageRealPath;
	}

	public void setupImageLocalPath(String localPath) {
		imageLocalPath = localPath;
	}

	public String getImageLocalPath() {
		return imageLocalPath;
	}

	public String OCR(String resourceFolder) {
		String str = "";
		TextApp app;
		try {
			app = new TextApp(TextApp.getVisionService(resourceFolder), null /* index */);
			List<ImageText> image = app.detectText(ImmutableList.<Path>of(Paths.get(imageLocalPath)));
			List<EntityAnnotation> textAnnotations = image.get(0).textAnnotations();
			str = textAnnotations.toString();
			System.out.println(str);
			JSONParser parser = new JSONParser();
			JSONArray jsonArr = (JSONArray) parser.parse(str);
			for (int i = 1; i < jsonArr.size(); i++) {
				JSONObject word = (JSONObject) jsonArr.get(i);
				wordIDs.add(i + "");
				OCRValueList.add(word.get("description").toString());
				JSONObject boundingPoly = (JSONObject) word.get("boundingPoly");
				JSONArray vertices = (JSONArray) boundingPoly.get("vertices");
				JSONObject topleft = (JSONObject) vertices.get(0);
				x0List.add(topleft.get("x").toString());
				y0List.add(topleft.get("y").toString());
				JSONObject bottomright = (JSONObject) vertices.get(2);
				x1List.add(bottomright.get("x").toString());
				y1List.add(bottomright.get("y").toString());
			}
			JSONObject allContent = (JSONObject) jsonArr.get(0);
			wholeContent = allContent.get("description").toString();
			lineList = wholeContent.split("\n");
		} catch (IOException | GeneralSecurityException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}

	public void NLP(String resourceFolder) {
		String serializedClassifier = resourceFolder + "english.muc.7class.distsim.crf.ser.gz";
		try {
			AbstractSequenceClassifier<CoreLabel> classifier = CRFClassifier.getClassifier(serializedClassifier);
			for (int i = 0; i < lineList.length; i++) {
				String str = lineList[i];
				if (str.toLowerCase().contains("fax")) {
					fax = str.replaceAll("\\D", "");
				} else if (str.toLowerCase().contains("tel") || str.toLowerCase().contains("phone")
						|| str.toLowerCase().contains("cell") || str.toLowerCase().contains("mobile")) {
					phone = str.replaceAll("\\D", "");
				} else if (str.contains("@")||str.toLowerCase().contains("email")||str.toLowerCase().contains("e-mail")) {
					email = str.toLowerCase().replaceAll(":", "").replaceAll(":", "").replace("email", "");
				} else {
					str = str.replaceAll("& ", "&amp; ");
					str = str.replaceAll("&([^;]+(?!(?:\\w|;)))", "&amp;$1");
					str = str.replaceAll("<", ". &lt;");
					str = str.replaceAll("\"", "&quot;");
					str = str.replaceAll("'", "&apos;");
					str = str.replaceAll(">", ". &gt;");
					String xml = "<root>" + classifier.classifyToString(str, "xml", true) + "</root>";
					Document doc = DocumentHelper.parseText(xml);
					Element rootElt = doc.getRootElement();
					@SuppressWarnings("rawtypes")
					Iterator iter = rootElt.elementIterator("wi");
					while (iter.hasNext()) {
						Element recordEle = (Element) iter.next();
						if (recordEle.attributeValue("entity").equals("PERSON")) {
							if (name.equals("Not Found")) {
								name =  recordEle.getText();
							} else {
								name = name + " " + recordEle.getText();
							}
								
						}
					}
					if (strcontainsAddressKeyword(str) || strcontainsZipcode(str)) {
						if (address.equals("Not Found")) {
							address =  str;
						} else {
							address = address + " " + str;
						}
					}

				}

			}
		} catch (ClassCastException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		;
	}

	public boolean strcontainsZipcode(String str) {
		String[] words = str.split(" ");
		for (String word : words) {
			System.out.println(word);
			System.out.println(word.length());
			System.out.println(word.replace("\\d", "").length());
			if (word.length() == 5 && word.replaceAll("\\d", "").length() == 0) {
				System.out.println("yes");
				return true;
			}
		}
		return false;
	}

	public boolean strcontainsAddressKeyword(String str) {
		String[] words = str.split(" ");
		for (String word : words) {
			word = word.toLowerCase().replaceAll("\\W", "");
			if (word.equals("blvd") || word.equals("rd")
					|| word.equals("road") || word.equals("st")
					|| word.equals("street") || word.equals("ave")
					|| word.equals("avenue")) {
				return true;
			}
		}
		return false;
	}

	// utility
	public ArrayList<String> getX0List() {
		return x0List;
	}

	public ArrayList<String> getX1List() {
		return x1List;
	}

	public ArrayList<String> getY0List() {
		return y0List;
	}

	public ArrayList<String> getY1List() {
		return y1List;
	}

	public ArrayList<String> getOCRValueList() {
		return OCRValueList;
	}

	public ArrayList<String> getWordIDs() {
		return wordIDs;
	}

	public String getName() {
		return name;
	}


	public String getAddress() {
		return address;
	}

	public String getPhone() {
		return phone;
	}

	public String getEmail() {
		return email;
	}

	public String getFax() {
		return fax;
	}

}
