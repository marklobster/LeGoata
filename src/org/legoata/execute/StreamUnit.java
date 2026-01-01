package org.legoata.execute;

import java.io.PrintStream;
import java.util.Scanner;

public class StreamUnit {
	
	private Scanner in;
	private PrintStream out;
	private PrintStream logger;

	public StreamUnit(Scanner in, PrintStream out, PrintStream logger) {
		this.in = in;
		this.out = out;
		this.logger = logger;
	}
	
	public Scanner getIn() {
		return in;
	}

	public PrintStream getOut() {
		return out;
	}

	public PrintStream getLogger() {
		return logger;
	}

}
