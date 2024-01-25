package org.example.demo.commons.model;

import lombok.Data;

@Data
public class ClinvarSubmission {
	
	private String condition;
	private String pathogenicity;
	private int submissionNum;
	private String reviewStatus;
	private String rcv;

}
