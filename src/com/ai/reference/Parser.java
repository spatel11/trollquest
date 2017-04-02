package com.ai.reference;

import java.io.IOException;
import java.util.EnumSet;
import java.util.regex.Matcher;


/**
 * taken from p1 spec code by Terran Lane
 * @author Terran Lane
 *
 * @param <S>
 * @param <T>
 */
public interface Parser<S,T extends Enum<T> & HasPattern> {
	
	public Token<T> consume() throws ParseException;
	public Token<T> consume(EnumSet<T> expectedClass) throws ParseException;
	public Token<T> consume(T expected) throws ParseException;
	public Token<T> consume(T expected, Matcher ePat) throws ParseException;
	public void consumeUntil(T until) throws ParseException;
	public void consumeUntil(T until, Matcher untilPat) throws ParseException;
	public boolean expect(EnumSet<T> expected);
	public boolean expect(T expected);
	public boolean expect(T expected, Matcher ePat);
	public void parse(S db) throws ParseException;
	public void shutDown() throws IOException;

}
