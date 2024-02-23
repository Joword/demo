"""
@author Joword 
@time: 2024/02/01 15:13:07
@version: 1.0.0
@description: ACMG模型层
"""
from pydantic import BaseModel
from typing import Optional, Dict

class VariantInfo(BaseModel):
    variantId: str
    gene: str
    genomeVersion: str
    transcript: Optional[str]
    transcriptP: Optional[str]
    consequence: Optional[str]
    chgvs: Optional[str]
    phgvs: Optional[str]
    phgvs1: Optional[str]
    ghgvs: Optional[str]
    rsId: Optional[str]
    exon: Optional[str]
    intron: Optional[str]
    aminoAcid: Optional[str]
    classification: Optional[str]
    criteria: Optional[str]
    cytoband: Optional[str]
    clinvarObj: Optional[Dict]
    evidences: Optional[str]

class CriteriaInfo(BaseModel):
    rule: Optional[str]
    strength: Optional[str]
    category: Optional[str]
    evidence: Optional[str]
    evidences: Optional[str]
    summary: Optional[str]
    source: Optional[str]
    comment: Optional[str]
    status: Optional[str]
    afRace: Optional[str]
    af: Optional[str]
    inheritance: Optional[str]
    homozygot: Optional[str]
    revelScore: Optional[str]
    consequence: Optional[str]
    hotRegion: Optional[str]
    curatedRegion: Optional[str]
    domainRegion: Optional[str]
    impactSplicing: Optional[str]
    repeatedRegion: Optional[str]
    gerpScore: Optional[str]

class ChatpaperShowingInfo(BaseModel):
    p_hgvs: Optional[str]
    c_hgvs: Optional[str]
    g_hgvs: Optional[str]
    exon: Optional[str]
    create_time: Optional[str]
    update_time: Optional[str]
    genome_version: Optional[str]
    rs_id: Optional[str]
    ner_variantId: Optional[str]
    ner_gene: Optional[str]
    ner_disease: Optional[str]
    met_acmg: Optional[str]
    pathogenicity: Optional[str]
    summary_en: Optional[str]
    summary_cn: Optional[str]
    gene: Optional[str]
    acmgs: Optional[Dict]
    variant_id: Optional[str]
    
class UserInfo(BaseModel):
    user_id: Optional[str]
    email: Optional[str]
    username: Optional[str]
    password: Optional[str]
    firstname: Optional[str]
    lastname: Optional[str]
    fullname: Optional[str]
    organization: Optional[str]
    login_times: Optional[str]
    login_time: Optional[str]
    role: Optional[int]
    

