export interface summaryInfo {
    name:string;
    value:string;
    color:null | string;
    backgroundColor:string;
    index:string[];
    content:string[];
    extraLink:null | string;
    focused:boolean;
}

export interface RulesTable {
    category: string;
    rule: string;
    evidence: string;
    source?: string;
    comment?:any;
}

export interface TypeTable{
    key: number;
    index: string;
    category: string;
    rule: string;
    strength: string;
    evidence: string;
    evidences: object | null;
    summary: string;
    source?: string;
    comment?: any;
    thumbup?: number;
    status: string[];
}

export interface ArticleTables {
    key: number;
    index: string;
    article: string;
    criteria?: string;
    strength?: string;
    evidence?: string;
    comment?: string;
    pmid: string;
    date?: string;
    impactFactor?: string;
    citation: string;
    journal?: string;
    author: string;
    thumbup: number;
}
