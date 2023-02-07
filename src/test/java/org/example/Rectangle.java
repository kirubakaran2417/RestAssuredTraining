package org.example;

import java.io.*;

public class Rectangle implements Serializable {

    private double height;
    private double width;

    public Rectangle(double height, double width) {
        this.height = height;
        this.width = width;
    }

    public double area() {
        return height*width;
    }

    public double perimeter(){
        return 2*(height*width);
    }

    public static void serializetofile(Object Classobject,String file) throws IOException {
        FileOutputStream filestream=new FileOutputStream(file);
        ObjectOutputStream objectstream=new ObjectOutputStream(filestream);
        objectstream.writeObject(Classobject);
        objectstream.flush();
        objectstream.close();
        filestream.close();
    }

    public static void main(String[] args) throws IOException {
        Rectangle rect=new Rectangle(18,28);
        serializetofile(rect,"rectserialized");
}}
