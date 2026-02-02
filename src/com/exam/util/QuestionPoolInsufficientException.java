package com.exam.util;

public class QuestionPoolInsufficientException extends Exception{
	@Override
	public String toString() {
		return "INSUFFICIENT ACTIVE QUESTIONS";
	}

}
