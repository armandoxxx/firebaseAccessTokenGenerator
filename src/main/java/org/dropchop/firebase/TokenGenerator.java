package org.dropchop.firebase;


import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TokenGenerator
{

    private static InputStream getFileFromResourceAsStream(String fileName) {

        ClassLoader classLoader = TokenGenerator.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }

    }

    private static List<String> readScopes(File scopesFile) {
        ArrayList<String> result = new ArrayList<>();
        try {
            try (FileReader f = new FileReader(scopesFile)) {
                StringBuffer sb = new StringBuffer();
                while (f.ready()) {
                    char c = (char) f.read();
                    if (c == '\n') {
                        result.add(sb.toString());
                        sb = new StringBuffer();
                    } else {
                        sb.append(c);
                    }
                }
                if (sb.length() > 0) {
                    result.add(sb.toString());
                }
            }
        } catch (IOException e) {
            System.out.println("[ERROR] cannot read scopes file [" + scopesFile.getAbsolutePath() + "]");
        }
        return result;
    }


    public static void main( String[] args )
    {
        try {
            if (args == null || args.length == 0) {
                System.out.println("[ERROR] Read README.md");
                return;
            }
            String fileName = args[0];
            String scopesFileName = "scopes.txt";
            if (args.length > 1) {
                scopesFileName = args[1];

            }
            File configFile = new File(fileName);
            File scopesFile = new File(scopesFileName);
            if (!configFile.exists()) {
                System.out.println("[ERROR] File " + fileName + " does not exist");
                return;
            }
            if (!scopesFile.exists()) {
                System.out.println("[ERROR] Scopes file " + scopesFileName + " does not exist");
                return;
            }
            System.out.println("Got file name argument value [" + fileName + "]");
            System.out.println("Got scopes file name argument value [" + scopesFileName + "]");
            GoogleCredentials googleCredential = null;
            googleCredential = GoogleCredentials
                .fromStream(new FileInputStream(configFile))
                .createScoped(readScopes(scopesFile));
            AccessToken token = googleCredential.refreshAccessToken();
            System.out.println("Access token:");
            System.out.println("Bearer " + token.getTokenValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
