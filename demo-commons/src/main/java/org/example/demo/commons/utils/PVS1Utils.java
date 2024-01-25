package org.example.demo.commons.utils;

import org.springframework.stereotype.Component;

/**
 * @author Joword
 * @date: 2023/9/12 09:53
 * @version: 0.1.0
 * @description: pvs1 switch the abbr
 */
@Component
public class PVS1Utils {

    public static String getPVS1Description(String desc) {
        if ("NA".equals(desc) || null == desc) {
            return "PVS1 is not applicable for this variant type.";
        }
        if ("NF1".equals(desc)) {
            return "Nonsense or frameshift -> Predicted to undergo NMD -> Exon is present in biogically-relevent transcript(s) -> PVS1";
        }
        if ("NF2".equals(desc)) {
            return "Nonsense or frameshift -> Predicted to NMD -> Exon is absent in biogically-relevent transcript(s) -> Unmet";
        }
        if ("NF3".equals(desc)) {
            return "Nonsense or frameshift -> Not predicted to NMD -> Truncated/altered region is critical to protein function -> PVS1_Strong";
        }
        if ("NF4".equals(desc)) {
            return "Nonsense or frameshift -> Not predicted to NMD -> Role of region in protein function is unknown -> LoF variants in this exon are frequent in general population and/or exon is absent from biologically-relevant transcript(s) -> Unmet";
        }
        if ("NF5".equals(desc)) {
            return "Nonsense or frameshift -> Not predicted to NMD -> Role of region in protein function is unknown -> LoF variants in this exon are not frequent in general population and exon is present from biologically-relevant transcript(s) -> Variant removes >10% of protein -> PVS1_Strong";
        }
        if ("NF6".equals(desc)) {
            return "Nonsense or frameshift -> Not predicted to NMD -> Role of region in protein function is unknown -> LoF variants in this exon are not frequent in general population and exon is present from biologically-relevant transcript(s) -> Variant removes <10% of protein -> PVS1_Moderate";
        }
        if ("SS1".equals(desc)) {
            return "GT-AG 1,2 splice sites -> Exon skipping or use of a cryptic splice site disrupts reading frame and is predicted to undergo NMD -> Exon is present in biologically-relevant transcript(s) -> PVS1";
        }
        if ("SS2".equals(desc)) {
            return "GT-AG 1,2 splice sites -> Exon skipping or use of a cryptic splice site disrupts reading frame and is predicted to undergo NMD -> Exon is absent in biologically-relevant transcript(s) -> Unmet";
        }
        if ("SS3".equals(desc)) {
            return "GT-AG 1,2 splice sites -> Exon skipping or use of a cryptic splice site disrupts reading frame and is NOT predicted to undergo NMD -> Truncated/altered region is critical to protein function -> PVS1_Strong";
        }
        if ("SS4".equals(desc)) {
            return "GT-AG 1,2 splice sites -> Exon skipping or use of a cryptic splice site disrupts reading frame and is NOT predicted to undergo NMD -> Role of region in protein function is unknown -> LoF variants in this exon are frequent in the general population and/or exon is absent from biologically-relevant transcrip(s) -> Unmet";
        }
        if ("SS5".equals(desc)) {
            return "GT-AG 1,2 splice sites -> Exon skipping or use of a cryptic splice site disrupts reading frame and is NOT predicted to undergo NMD -> LoF variants in this exon are not frequent in the general population and exon is present in biologically-relevant trancript(s) -> Variant removes >10% of protein -> PVS1_Strong";
        }
        if ("SS6".equals(desc)) {
            return "GT-AG 1,2 splice sites -> Exon skipping or use of a cryptic splice site disrupts reading frame and is NOT predicted to undergo NMD -> LoF variants in this exon are not frequent in the general population and exon is present in biologically-relevant trancript(s) -> Variant removes <10% of protein -> PVS1_Moderate";
        }
        if ("SS7".equals(desc)) {
            return "GT-AG 1,2 splice sites -> Exon skipping or use of a cryptic splice site preserves reading frame -> Role of region in protein function is unknown -> LoF variants in this exon are frequent in the general population and/or exon is absent from biologically-relevant transcrip(s) -> Unmet";
        }
        if ("SS8".equals(desc)) {
            return "GT-AG 1,2 splice sites -> Exon skipping or use of a cryptic splice site preserves reading frame -> Role of region in protein function is unknown -> LoF variants in this exon are not frequent in the general population and exon is present in biologically-relevant trancript(s) -> Variant removes >10% of protein -> PVS1_Strong";
        }
        if ("SS9".equals(desc)) {
            return "GT-AG 1,2 splice sites -> Exon skipping or use of a cryptic splice site preserves reading frame -> Role of region in protein function is unknown -> LoF variants in this exon are not frequent in the general population and exon is present in biologically-relevant trancript(s) -> Variant removes <10% of protein -> PVS1_Moderate";
        }
        if ("SS10".equals(desc)) {
            return "GT-AG 1,2 splice sites -> Exon skipping or use of a cryptic splice site preserves reading frame -> Truncated/altered region is critical to protein function -> PVS1_Strong";
        }
        if ("DEL1".equals(desc)) {
            return "Deletion(single exon to full gene) -> Full gene deletion -> PVS1";
        }
        if ("DEL2".equals(desc)) {
            return "Deletion(single exon to full gene) -> Single to multi exon deletion - Disrupts reading frame and is predicted to undergo NMD -> Exon is present in biogically-relevent transcript(s) -> PVS1";
        }
        if ("DEL3".equals(desc)) {
            return "Deletion(single exon to full gene) -> Single to multi exon deletion - Disrupts reading frame and is predicted to undergo NMD -> Exon is absent in biogically-relevent transcript(s) -> Unmet";
        }
        if ("DEL4".equals(desc)) {
            return "Deletion(single exon to full gene) -> Single to multi exon deletion - Disrupts reading frame and is NOT predicted to undergo NMD -> Truncated/altered region is critical to protein function -> PVS1_Strong";
        }
        if ("DEL5".equals(desc)) {
            return "Deletion(single exon to full gene) -> Single to multi exon deletion - Disrupts reading frame and is NOT predicted to undergo NMD -> Role of region in protein function is unknown -> LoF variants in this exon are frequent in the general population and/or exon is absent from biologically-relevant transcript(s) -> Unmet";
        }
        if ("DEL6".equals(desc)) {
            return "Deletion(single exon to full gene) -> Single to multi exon deletion - Disrupts reading frame and is NOT predicted to undergo NMD -> Role of region in protein function is unknown -> Variant removes >10% of protein -> PVS1_Strong";
        }
        if ("DEL7".equals(desc)) {
            return "Deletion(single exon to full gene) -> Single to multi exon deletion - Disrupts reading frame and is NOT predicted to undergo NMD -> Role of region in protein function is unknown -> Variant removes <10% of protein -> PVS1_Moderate";
        }
        if ("DEL8".equals(desc)) {
            return "Deletion(single exon to full gene) -> Single to multi exon deletion - Preserves reading frame -> Truncated/altered region is critical to protein function -> PVS1_Strong";
        }
        if ("DEL9".equals(desc)) {
            return "Deletion(single exon to full gene) -> Single to multi exon deletion - Preserves reading frame -> Role of region in protein function is unknown -> LoF variants in this exon are frequent in the general population and/or exon is absent from biologically-relevant transcript(s) -> Unmet";
        }
        if ("DEL10".equals(desc)) {
            return "Deletion(single exon to full gene) -> Single to multi exon deletion - Preserves reading frame -> Role of region in protein function is unknown -> LoF variants in this exon are not frequent in the general population and exon is present in biologically-relevant transcript(s) ->Variant removes >10% of protein -> PVS1_Strong";
        }
        if ("DEL11".equals(desc)) {
            return "Deletion(single exon to full gene) -> Single to multi exon deletion - Preserves reading frame -> Role of region in protein function is unknown -> LoF variants in this exon are not frequent in the general population and exon is present in biologically-relevant transcript(s) ->Variant removes <10% of protein -> PVS1_Moderate";
        }
        if ("DUP1".equals(desc)) {
            return "Duplication(≥1 exon in size and must be completely contained within gene) -> Proven in tandem -> Reading frame disrupted and NMD predicted to occur -> PVS1";
        }
        if ("DUP2".equals(desc)) {
            return "Duplication(≥1 exon in size and must be completely contained within gene) -> Proven in tandem -> No or unknown impact on reading frame and NMD -> Unmet";
        }
        if ("DUP3".equals(desc)) {
            return "Duplication(≥1 exon in size and must be completely contained within gene) -> Presumed in tandem -> Reading frame presumed disrupted and NMD predicted to occur -> PVS1_Strong";
        }
        if ("DUP4".equals(desc)) {
            return "Duplication(≥1 exon in size and must be completely contained within gene) -> Presumed in tandem -> No or unknown impact on reading frame and NMD -> Unmet";
        }
        if ("DUP5".equals(desc)) {
            return "Duplication(≥1 exon in size and must be completely contained within gene) -> Proven not in tandem -> Unmet";
        }
        if ("IC1".equals(desc)) {
            return "Initiation Codon -> No known alternative start codon in other transcripts -> (>6) pathogenic variant(s) upstream of closest potential in-frame start codon -> PVS1";
        }
        if ("IC2".equals(desc)) {
            return "Initiation Codon -> No known alternative start codon in other transcripts -> (4~6) pathogenic variant(s) upstream of closest potential in-frame start codon -> PVS1_Strong";
        }
        if ("IC3".equals(desc)) {
            return "Initiation Codon -> No known alternative start codon in other transcripts -> (1~3) pathogenic variant(s) upstream of closest potential in-frame start codon -> PVS1_Moderate";
        }
        if ("IC4".equals(desc)) {
            return "Initiation Codon -> No known alternative start codon in other transcripts -> No pathogenic variant(s) upstream of closest potential in-frame start codon -> PVS1-Supp";
        }
        if ("IC5".equals(desc)) {
            return "Different functional transcript uses alternative start codon -> Unmet";
        }
        if ("IC0".equals(desc)) {
            return "No in-frame start codon";
        }
        if ("PTEN".equals(desc)) {
            return "PTEN disease specific criterion";
        }
        if ("CDH1".equals(desc)) {
            return "CDH1 disease specific criterion";
        }
        return desc;
    }
}
