package util.test;

import org.junit.Before;
import org.junit.Test;

import protocol.CommandParser;
import protocol.command.Action;
import protocol.command.Command.Direction;
import protocol.command.Exit;
import util.exception.CommandForbiddenException;
import util.exception.CommandInvalidException;
import util.exception.CommandUnsupportedException;

public class CommandParserTest {

	CommandParser parserCS;
	CommandParser parserSC;
	
	@Before
	public void setUp() throws Exception {
		parserCS = new CommandParser(Direction.CLIENT_TO_SERVER);
		parserSC = new CommandParser(Direction.SERVER_TO_CLIENT);
	}

	
	@Test(expected = CommandForbiddenException.class)
	public void testForbiddedExceptionClientServer() 
			throws CommandUnsupportedException, CommandForbiddenException, CommandInvalidException {
		parserCS.parse(Action.START.toString() + " AART RICHARD");
	}
	
	@Test(expected = CommandForbiddenException.class)
	public void testForbiddedExceptionClientServer2() 
			throws CommandUnsupportedException, CommandForbiddenException, CommandInvalidException {
		parserCS.parse(Exit.LOST.toString());
	}
	
	
	@Test(expected = CommandForbiddenException.class)
	public void testForbiddenExceptionServerClient() 
			throws CommandForbiddenException, CommandUnsupportedException, CommandInvalidException {
		parserSC.parse(Action.CONNECT.toString() + " RICHARD");
	}
	

	@Test(expected = CommandInvalidException.class)
	public void testInvalidExceptionClientServer() 
			throws CommandUnsupportedException, CommandForbiddenException, CommandInvalidException {
		parserCS.parse(Action.CONNECT.toString());
	}
	
	
	@Test(expected = CommandInvalidException.class)
	public void testInvalidExceptionServerClient() 
			throws CommandUnsupportedException, CommandForbiddenException, CommandInvalidException {
		parserSC.parse(Exit.DRAW.toString() + " garbage");
	}
	
	
	@Test(expected = CommandUnsupportedException.class)
	public void testUnsupportedExceptionClientServer() 
			throws CommandUnsupportedException, CommandForbiddenException, CommandInvalidException {
		parserCS.parse("CONNECTA" + " Richard");
	}
	

	@Test(expected = CommandUnsupportedException.class)
	public void testUnsupportedExceptionServerClient() 
			throws CommandUnsupportedException, CommandForbiddenException, CommandInvalidException {
		parserCS.parse("Not existing command");
	}
	

}
