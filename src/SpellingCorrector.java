import java.io.*;
        import java.util.*;


class SpellingCorrector {
    public static void main(String[] args) {

        String testcase = "testcase2";

        //read every line of vocab.txt
        ArrayList <String> dictionary = readDictionary(testcase);

        //read sentence.txt
        ArrayList <String> sentence = readSentence(testcase);

        //
        int desiredLevenshteinDistance = readLevenshteinDistance(testcase);

        //flag each word not in the dictionary

        //to store misspelled word en their correction
        Map<String, ArrayList<String>> misspelledWordsCorrectionsMap = linearSpellingCorrector(dictionary, sentence, desiredLevenshteinDistance);

        //print
        printResults(misspelledWordsCorrectionsMap);

        //write to text file
        writeResultsToFile(misspelledWordsCorrectionsMap);

    }

    public static Map<String, ArrayList<String>> linearSpellingCorrector(ArrayList<String> dictionary, ArrayList<String> sentence, int desiredLevenshteinDistance){
        Map<String, ArrayList<String>> misspelledWordsCorrectionsMap = new LinkedHashMap<String, ArrayList<String>>();


        for (int i = 0; i < sentence.size(); i++){

            ArrayList<String> correctionsList = new ArrayList<String>();

            String searchedWord = sentence.get(i);

            if (dictionary.contains(searchedWord)){
//                System.out.println("the Word '"+searchedWord + "' is in the Dictionary.");
            }

            else {
                String misspelledWord = searchedWord;
//                System.out.println("the Word '"+searchedWord + "' is NOT in the Dictionary.");

                LevenshteinDistance levenshteinDistanceCalculator = new LevenshteinDistance();

//                int levenTest = levenshteinDistanceCalculator.computeLevenshteinDistance("algoithms", "algorithms");
//                System.out.println("Leven Test value: "+ levenTest);

                for (int j=0; j<dictionary.size(); j++){

                    int lvshtnD = levenshteinDistanceCalculator.computeLevenshteinDistance(misspelledWord, dictionary.get(j));

                    if (lvshtnD <= desiredLevenshteinDistance){
                        //add a possible correction
                        correctionsList.add(dictionary.get(j));
//                      System.out.println("'"+misspelledWord + "' is close to '"+dictionary.get(j)+"'");
                    }

                }
                misspelledWordsCorrectionsMap.put(misspelledWord,correctionsList);
            }
        }

        return misspelledWordsCorrectionsMap;
    }

    public static ArrayList <String> readDictionary(String testCaseStr){
        //read every line of vocab.txt, max 400 000 words of 30 characters
        ArrayList <String> dictionaryArrayList = new ArrayList<String>();

        try {

            File dictionaryFile = new File("testCases/"+ testCaseStr + "/vocab.txt");
            FileReader dictionaryFileReader = new FileReader(dictionaryFile);
            BufferedReader dictionaryBufferedReader = new BufferedReader(dictionaryFileReader);
            StringBuffer dictionary = new StringBuffer();
            String definedWordPerLine;
            while ((definedWordPerLine = dictionaryBufferedReader.readLine()) != null) {
//                dictionary.append(definedWordPerLine);
//                dictionary.append("\n");
                dictionaryArrayList.add(definedWordPerLine);
            }
            dictionaryFileReader.close();


//            System.out.println("Contents of dictionary file:");
//            System.out.println(Arrays.toString(dictionaryArrayList.toArray()));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return dictionaryArrayList;
    }

    public static ArrayList <String> readSentence(String testCaseStr){
        ArrayList <String> sentenceArrayList = new ArrayList<String>();
        try {

            File sentenceFile = new File("testCases/"+ testCaseStr + "/sentence.txt");
            FileReader sentenceFileReader = new FileReader(sentenceFile);
            BufferedReader sentenceBufferedReader = new BufferedReader(sentenceFileReader);
//            StringBuffer sentenceStringBuffer = new StringBuffer();
            String sentenceLine;

            while ((sentenceLine = sentenceBufferedReader.readLine()) != null) {
                // break the line into individual words
                // the String array contains each word of the current line
                sentenceArrayList = new ArrayList<String>(Arrays.asList(sentenceLine.split(" +")));


                //to lower case
                ListIterator<String> iterator = sentenceArrayList.listIterator();
                    while (iterator.hasNext())
                    {
                        iterator.set(iterator.next().toLowerCase());
                    }


//                sentenceStringBuffer.append(sentenceLine);
//                sentenceStringBuffer.append("\n");
            }

            sentenceFileReader.close();

//            System.out.println("Contents of sentence file:");
//            System.out.println(Arrays.toString(sentenceArrayList.toArray()));



        } catch (IOException e) {
            e.printStackTrace();
        }
        return sentenceArrayList;
    }

    public static int readLevenshteinDistance (String testCaseStr){
        int levenshteinDistance=0;
        try {

            File distanceFile = new File("testCases/"+ testCaseStr + "/MaxDistance.txt");
            FileReader distanceFileReader = new FileReader(distanceFile);
            BufferedReader distanceBufferedReader = new BufferedReader(distanceFileReader);
//            StringBuffer distanceStringBuffer = new StringBuffer();
            String distanceLine;

            while ((distanceLine = distanceBufferedReader.readLine()) != null) {
                levenshteinDistance = Integer.parseInt(distanceLine);

            }

            distanceFileReader.close();

//            System.out.print("Contents of MaxDistance file: ");
//            System.out.println(levenshteinDistance);



        } catch (IOException e) {
            e.printStackTrace();
        }
        return levenshteinDistance;
    }

    private static String arrayListToString(ArrayList<String> anArray) {
        String strArrayList ="";
        for (int i = 0; i < anArray.size(); i++) {
            if (i > 0) {
//                  add a comma space before the next correction
                strArrayList += ", ";
            }

//              add the correction
            strArrayList += anArray.get(i);


        }
        return strArrayList;
    }

    public static void writeResultsToFile(Map<String, ArrayList<String>> resultMap) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("MisspelledWords.txt", "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (resultMap.isEmpty()){
            writer.println("0");
        }
        else {
            for (Map.Entry<String, ArrayList<String>> entry : resultMap.entrySet()) {
                String mispelledWord = entry.getKey();
                ArrayList<String> correctionList = entry.getValue();
                writer.println(mispelledWord + ": " + arrayListToString(correctionList));
            }
        }
        writer.close();
    }

    public static void printResults (Map<String, ArrayList<String>> resultMap) {
        for (Map.Entry<String, ArrayList<String>> entry : resultMap.entrySet()) {
            String mispelledWord = entry.getKey();
            ArrayList<String> correctionList = entry.getValue();
            System.out.println(mispelledWord + ": " + arrayListToString(correctionList));
        }
    }

}