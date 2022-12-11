package Helpers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertiesHelper {

    public static Properties getBaseProps() throws IOException {
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String testConfigPath = rootPath + "test.properties";
        Properties testProps = new Properties();
        testProps.load(new FileInputStream(testConfigPath));
        return testProps;
    }
}
