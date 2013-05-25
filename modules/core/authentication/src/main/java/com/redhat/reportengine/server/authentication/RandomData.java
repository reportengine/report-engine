package com.redhat.reportengine.server.authentication;

import java.util.Random;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Apr 03, 2013
 */
public class RandomData {
	private static final char[] pool = {
        'a','b','c','d','e','f','g',
        'h','i','j','k','l','m','n',
        'o','p','q','r','s','t','u',
        'v','w','x','y','z',
        'A','B','C','D','E','F','G',
        'H','I','J','K','L','M','N',
        'O','P','Q','R','S','T','U',
        'V','W','X','Y','Z',
        '0','1','2','3','4',
        '5','6','7','8','9'};

    private Random rnd;

    public RandomData () { rnd = new Random(); }

    public char getChar() { return pool[rnd.nextInt(pool.length)]; }
    
    public String getString(int maxSize){
    	Random rand = new Random();
    	return getStringFixedSize(rand.nextInt(50) + 1);
    }
    
    public String getStringFixedSize(int size) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++)
            sb.append(getChar());
        return new String(sb);
    }
}
