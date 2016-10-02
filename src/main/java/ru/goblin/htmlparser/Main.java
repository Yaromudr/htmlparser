package ru.goblin.htmlparser;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Александр on 02.10.2016.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        if(args.length<1){
            System.out.println("Enter parameters and try again");
            System.exit(0);
        }
        Properties properties = new LoaderSettings("htmlparser.properties").getProperties();
        Calculate calc = new Calculate(properties.getProperty("url"), properties.getProperty("css_class"));
        calc.readText();
        calc.parse(args);
        calc.sortResult();
        calc.printResults();
    }
}

