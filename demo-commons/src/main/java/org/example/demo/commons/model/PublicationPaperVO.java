package org.example.demo.commons.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PublicationPaperVO {	
	private String variantId;
	private String title;
	private String relevance;
	private String firstAuthor;
	private String pmid;
	private String pmcid;
	private String doi;
	private String date;
	private String journal;
	private String journalabbr;
	private String citationNum;
	private String abstractText;
	private String type;
	private String url;
	private String remark;
	private List<EvidenceVo> evidences=new ArrayList<>();

}
