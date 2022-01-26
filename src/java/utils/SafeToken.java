package utils;

import Random.String.Generator.RandomString;
import hibernate.mapping.Tokens;
import hibernate.query.TokensQuery;

public class SafeToken {

    public static String generateToken(long userId) {
        RandomString randomString = new RandomString();
        TokensQuery tokensQuery = new TokensQuery();
        Tokens tokens = new Tokens(userId, randomString.RandomString(255), System.currentTimeMillis());
        if (tokensQuery.getToken(userId) != null) {
            tokensQuery.updateToken(tokens);
        } else {
            tokensQuery.saveToken(tokens);
        }
        return tokens.getToken();
    }

    public static boolean isValidToken(long userId, String token) {
        TokensQuery tokensQuery = new TokensQuery();
        Tokens tokens = tokensQuery.getToken(userId);
        return !(tokens == null || !tokens.getToken().equals(token) || (tokens.getTimestmp() < (System.currentTimeMillis() - (1000 * 60 * 1))));
    }
}
