import * as echarts from "echarts/core";
import { TooltipComponentOption, GridComponentOption, VisualMapComponentOption } from "echarts/components";
import { HeatmapSeriesOption } from "echarts/charts";

export type HomeCard = {
    img: string;
    title: string;
    desc: string;
}

export type CardSize = {
    width: string;
    height: string;
    size: number;
    name: string;
}

export type SwpTable = {
    key: number;
    category: string;
    tools: string;
    scores: string;
    thresthold: string;
    type: string;
    url: string;
}

export type Phenotype = {
    key: number;
    phenotype: string;
    inheritance: string;
    source: string[];
    url: string[];
    msg: string;
}

export type EChartsOption = echarts.ComposeOption<TooltipComponentOption | GridComponentOption | VisualMapComponentOption | HeatmapSeriesOption>;

export type HeatRows = {
    key: number;
    type: string,
    pathogenic: string;
    likelypathogenic: string;
    vus: string;
    likelybenign: string;
    benign: string;
    total: string;
}

export type PmidRows = {
    key: number;
    gene: string;
    variants: string;
    phenotype: string;
    chgvs: string;
    phgvs: string;
    phgvs1: string;
}

export type SimilarPubRows = {
    key: number;
    title: string;
    author: string;
    journal: string;
    url: string;
}

export type VariantInfo = {
    key: number;
    variant: string;
    relevance: string;
    criteria: string;
    submitter: string;
    comment: string;
    status: string;
}

export type VariantRows = {
    key: number;
    variant: string;
    chgvs: string;
    phgvs: string;
    consequence: string;
    criteria: string;
    classification: string;
}

export type RegisterInfo = {
    username: string;
    email: string;
    password: string;
    repassword: string;
    organization: string;
    emailCode: string;
}

export type validationCode = {
    isDisabled: boolean;
    text: string;
    status: string;
    isRequested: boolean;
    isShow: boolean;
}

export type emailCodeForm = {
    role: string;
    email: string;
}

export type loginForm = {
    username: string;
    password: string;
}

export type changePwdForm = {
    username: string;
    email: string;
    password: string;
    resetPassword: string;
}

export type changePassword = {
    password: string;
}

export type geneInfo = {
    gene: string;
    geneName: string;
    chromosome: string;
    hgncId: string;
    ncbiId: string;
    ensemblId: string;
    ucscId: string;
    omimId: string;
    uniprotId: string;
    orphanetId: string;
    variantId: string;
    chgvs: string;
    phgvs: string;
    consequence: string;
    criteria: string;
    classification: string;
    geneSummary: string;
}

export type variantBasicInfo = {
    gene: string;
    variantId: string;
    transcript: string;
    chgvs: string;
    phgvs: string;
    type: string;
}
export type BasicInfor = {
    gene: string,
    variantId: string,
    transcript: string,
    classification: string,
    criteria: string,
    chgvs: string,
    phgvs: string,
    rsId: string,
    cytoband: string,
    chgvsDetail: string,
    phgvsDetail: string
}

export type classfyInfo = {
    criteria: string;
    evidence: string;
    type: string;
    classification: string;
}

export type articleCriteria = {
    criteria: string;
    pmid: string;
    pmcid: string;
    type: string;
    number: string;
    source: string;
    sourceNumber: string;
    summary: string;
}

export type softwareItem = {
    type: string;
    pp3Strengh: string;
    bp4Strengh: string;

}

export type clinicalItem = {
    type: string;
    clinvarId: string;
    star: string;
    pathogenicity: string;
}

export type populationItem = {
    type: string;
}

export type arrayTag = {
    label: string;
    value: string;
    color: string;
}

export type pmidInfo = {
    pmid: string;
    title: string;
    journal: string;
    journalAbbr: string;
    author: string;
    year: string;
}

export type showPmidInfo = {
    title: string;
    author: string;
    year: string;
    pmid: string;
    cite: string;
    journal: string;
    abstract: string
}

export type criteriaTable = {
    pm2: string;
    pm2Strength: string;
    pm2Evidence: string;
    pm2Comment: string;
    bs2: string;
    bs2Strength: string;
    bs2Evidence: string;
    bs2Comment: string;
    bs1: string;
    bs1Strength: string;
    bs1Evidence: string;
    bs1Comment: string;
    ba1: string;
    ba1Strength: string;
    ba1Evidence: string;
    ba1Comment: string;
    pvs1: string;
    pvs1Strength: string;
    pvs1Evidence: string;
    pvs1Comment: string;
    pm1: string;
    pm1Strength: string;
    pm1Evidence: string;
    pm1Comment: string;
    pp3: string;
    pp3Strength: string;
    pp3Evidence: string;
    pp3Comment: string;
    bp4: string;
    bp4Strength: string;
    bp4Evidence: string;
    bp4Comment: string;
    pm4: string;
    pm4Strength: string;
    pm4Evidence: string;
    pm4Comment: string;
    bp3: string;
    bp3Strength: string;
    bp3Evidence: string;
    bp3Comment: string;
    bp7: string;
    bp7Strength: string;
    bp7Evidence: string;
    bp7Comment: string;
    ps1: string;
    ps1Strength: string;
    ps1Evidence: string;
    ps1Comment: string;
    pm5: string;
    pm5Strength: string;
    pm5Evidence: string;
    pm5Comment: string;
}

export type chatForm = {
    variantId: string;
    pmid: string;
}