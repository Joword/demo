package org.example.demo.commons.model;

import lombok.Data;

import java.util.List;
@Data
public class ClinvarVo {
	private String variantId;
	private String pathogenicity;
	private String reviewStatus;
	private String reviewStatusStar;
	private String lastEvalulated;
	private String pmids;
	private int submitterNum;
	private List<ClinvarSubmission> submissions;

}
