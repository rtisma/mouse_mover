package com.roberttisma.tools.mouse_mover.utils;

import com.roberttisma.tools.mouse_mover.serialization.Deserializer;
import com.roberttisma.tools.mouse_mover.serialization.Serializer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.roberttisma.tools.mouse_mover.utils.Checks.checkNotNull;

public class Files {

  public static List<String> readStringLines(File file) throws IOException {
    checkNotNull(file);
    FileReader fr = new FileReader(file);   //reads the file
    BufferedReader br = new BufferedReader(fr);  //creates a buffering character input stream
    List<String> out = new ArrayList<String>();    //constructs a string buffer with no characters
    String line;
    while((line=br.readLine())!=null) {
      out.add(line);      //appends line to string buffer
    }
    fr.close();    //closes the stream
    return out;
  }

  public static <T> List<T> readLines(File file, Deserializer<T, String> deserializer) throws IOException {
    checkNotNull(deserializer);
    List<T> out = new ArrayList<T>();
    for (String line : readStringLines(file)){
      out.add(deserializer.deserialize(line));
    }
    return out;
  }

  public static <T> void writeLines(List<T> objects, Serializer<T, String> serializer, File outputFile) throws IOException {
    checkNotNull(serializer);
    List<String> out = new ArrayList<String>();
    for (T o : objects){
      out.add(serializer.serialize(o));
    }
    writeStringLines(out, outputFile);
  }
  public static void writeStringLines(List<String> lines, File outputFile) throws IOException {
    provisionFile(outputFile);
    FileWriter myWriter = new FileWriter(outputFile);
    for (String line : lines){
      myWriter.write(line+"\n");
    }
    myWriter.close();
  }

  public static void provisionFile(File file){
    file.getParentFile().mkdirs();
  }



}
