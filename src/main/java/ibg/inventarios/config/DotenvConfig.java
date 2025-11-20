package ibg.inventarios.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DotenvConfig implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        try (BufferedReader reader = new BufferedReader(new FileReader(".env"))) {
            Map<String, Object> properties = new HashMap<>();
            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.startsWith("#") && line.contains("=")) {
                    int equalIndex = line.indexOf('=');
                    String key = line.substring(0, equalIndex).trim();
                    String value = line.substring(equalIndex + 1).trim();

                    if ((value.startsWith("\"") && value.endsWith("\"")) ||
                        (value.startsWith("'") && value.endsWith("'"))) {
                        value = value.substring(1, value.length() - 1);
                    }

                    properties.put(key, value);
                }
            }

            environment.getPropertySources().addFirst(new MapPropertySource("dotenv", properties));

        } catch (IOException e) {
            System.err.println("Error al leer .env: " + e.getMessage());
        }
    }
}
