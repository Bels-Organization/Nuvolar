package amazon.utils;

import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonUtils {
    public JsonUtils() {
    }

    /**
     * Reads a Json file and returns its contents as JSONObject
     * @param filePathAndName Path and name of the Json file we want to read and parse
     * @return Returns the JSONObject with the data of the read file
     * @throws IOException
     * @throws ParseException
     */
    public static JSONObject readJsonDataset(String filePathAndName) throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        Object obj = jsonParser.parse(new FileReader(filePathAndName));
        return (JSONObject)obj;
    }

    /**
     * Reads a Json file and returns its contents as a two dimension array of objects, to be used in a dataProvider function
     * The Json must contain an array of 'cases', each case must have all the attributes as Strings, and all the cases must have the same items in the same order
     * @param filePathAndName Path and name of the Json file we want to read and parse
     * @return Returns a two dimension array of objects, to be used in a dataProvider function
     * @throws IOException
     * @throws ParseException
     */
//    public static Object[][] readJsonDataset (String filePathAndName) throws IOException, ParseException {
//        JSONObject jsonObject = readJson(filePathAndName);
//        JSONArray cases = (JSONArray) jsonObject.get("cases");
//        Object[][] dataset = new String [cases.size()][((JSONObject) cases.get(0)).size()];
//
//        for (int i=0; i<cases.size(); i++) {
//            if (cases.get(i) instanceof JSONObject) {
//                JSONObject useCase = (JSONObject) cases.get(i);
//                for (int j=0; j<useCase.keySet().size(); j++) {
//                    dataset[i][j] = useCase.get(useCase.keySet().toArray()[j].toString()).toString();
//                }
//            }
//        }
//
//        return dataset;
//    }
}