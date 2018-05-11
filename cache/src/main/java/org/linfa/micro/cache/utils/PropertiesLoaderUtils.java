package org.linfa.micro.cache.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Properties;

@Slf4j
public class PropertiesLoaderUtils {
//    private static Logger logger = LoggerFactory.getLogger(PropertiesLoaderUtils.class);
    private static ResourceLoader resourceLoader = new DefaultResourceLoader();
    private final Properties properties;

    public PropertiesLoaderUtils(String... resourcesPaths)
    {
        this.properties = loadProperties(resourcesPaths);
    }

    public Properties getProperties()
    {
        return this.properties;
    }

    private String getValue(String key)
    {
        String systemProperty = System.getProperty(key);
        if (systemProperty != null) {
            return systemProperty;
        }
        if (this.properties.containsKey(key)) {
            return this.properties.getProperty(key);
        }
        return "";
    }

    public String getProperty(String key)
    {
        String value = getValue(key);
        if (value == null) {
            throw new NoSuchElementException();
        }
        return value;
    }

    public String getProperty(String key, String defaultValue)
    {
        String value = getValue(key);
        return value != null ? value : defaultValue;
    }

    public Integer getInteger(String key)
    {
        String value = getValue(key);
        if (value == null) {
            throw new NoSuchElementException();
        }
        return Integer.valueOf(value);
    }

    public Integer getInteger(String key, Integer defaultValue)
    {
        String value = getValue(key);
        return value != null ? Integer.valueOf(value) : defaultValue;
    }

    public Double getDouble(String key)
    {
        String value = getValue(key);
        if (value == null) {
            throw new NoSuchElementException();
        }
        return Double.valueOf(value);
    }

    public Double getDouble(String key, Integer defaultValue)
    {
        String value = getValue(key);
        return Double.valueOf(value != null ? Double.valueOf(value).doubleValue() : defaultValue.intValue());
    }

    public Boolean getBoolean(String key)
    {
        String value = getValue(key);
        if (value == null) {
            throw new NoSuchElementException();
        }
        return Boolean.valueOf(value);
    }

    public Boolean getBoolean(String key, boolean defaultValue)
    {
        String value = getValue(key);
        return Boolean.valueOf(value != null ? Boolean.valueOf(value).booleanValue() : defaultValue);
    }

    private Properties loadProperties(String... resourcesPaths)
    {
        Properties props = new Properties();

        String[] arr$ = resourcesPaths;int len$ = arr$.length;
        for (int i$ = 0; i$ < len$;)
        {
            String location = arr$[i$];

            InputStream is = null;
            try
            {
                Resource resource = resourceLoader.getResource(location);
                is = resource.getInputStream();
                props.load(is);
                if (is != null) {
                    try
                    {
                        is.close();
                    }
                    catch (IOException e) {}
                }
                i$++;
            }
            catch (IOException ex)
            {
                log.info("Could not load properties from path:" + location + ", " + ex.getMessage());
            }
            finally
            {
                if (is != null) {
                    try
                    {
                        is.close();
                    }
                    catch (IOException e) {}
                }
            }
        }
        return props;
    }
}
