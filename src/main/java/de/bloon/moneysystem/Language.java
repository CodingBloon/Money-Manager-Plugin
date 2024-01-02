package de.bloon.moneysystem;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import javax.security.auth.login.CredentialException;
import java.io.IOException;

public class Language {

    private Languagee languagee;
    public Language(Languagee languagee) {
        this.languagee = languagee;
    }


    public String getMessage(String message) throws IOException, InvalidConfigurationException {

        if(languagee == Languagee.GERMAN) {
            YamlConfiguration yml = new YamlConfiguration();
            yml.load("de-de.yml");
            //yml.get(message);
            return String.valueOf(yml.get(message));
        }

        if(languagee == Languagee.ENGLISH) {
            YamlConfiguration yml = new YamlConfiguration();
            yml.load("en-us.yml");
            return String.valueOf(yml.get(message));
        }

        return "Error";
    }

    public enum Languagee {
        GERMAN,
        ENGLISH
    }

}
