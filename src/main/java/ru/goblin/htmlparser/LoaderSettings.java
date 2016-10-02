package ru.goblin.htmlparser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.security.CodeSource;
import java.util.Properties;

public class LoaderSettings {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    private InputStream inputStream;
    private Properties properties;

    private String propFileName;

    /**
     * При создании этого объекта происходит чтение файла настроек и передача объекта properties в Main
     * Файл настроек должен лежать около запускаемого jar-файла, чтоб не пересобирать jar-файл при изменении настроек
     */
    public LoaderSettings(String propFileName) {
        this.propFileName = propFileName;
        loadSettings();
    }

    private void loadSettings() {
        LOG.info("Start load properties");
        try {
            //получаем адрес где мы находимся
            CodeSource src = getClass().getProtectionDomain().getCodeSource();
            URL url;
            if (src != null) {
                //получаем урл с пропертями
                url = new URL(src.getLocation(), propFileName);
                inputStream = new FileInputStream(url.getFile());
            }


            properties = new Properties();
            properties.load(inputStream);


        } catch (Throwable e) {
            LOG.error("Check properties file: {}", e);
        }
        LOG.info("Finish load properties");
    }

    public Properties getProperties() {
        return properties;
    }
}
