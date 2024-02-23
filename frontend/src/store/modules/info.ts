import { defineStore } from "pinia";

export const useInformation = defineStore('info', {
    persist: {
        key: 'useInfoKey',
        storage: sessionStorage,
        paths: [ 'pdfParser', 'interpretation', 'acmgClassification', 'showHomeCard' ]
    },
    state: () => {
        return {
            cardSize: { size:0 },
            pdfParser: { name: 'chatpaper', value: 'Chat assistant', desc: '-Summary the given article<br />-Question about the article<br />-Question about ACMG guidelines<br />-Question about AIGI', img: './chat-paper.png' },
            interpretation: { name: 'interpretation', value: 'AI article parser', desc: '-NER<br />-ACMG rules auto detect<br />-Submit your evidences to database', img: './home-genome-interpretation.png' },
            acmgClassification: { name: 'classification', value: 'ACMG guideline interpretation', desc: '-2018 hearing loss guideline<br />-2015 ACMG general guideline', img: 'home-acmg-classification.png' },
            geneInfo: {symbol: '', geneName: '', chromosome: '', info: []},
            variantBasicInfo: {
                gene: '',
                variantId: '',
                transcript: '',
                chgvs: '',
                phgvs: '',
                classification: '',
                criteria: '',
                rsId: '',
                cytoband: '',
                chgvsDetail: '',
                phgvsDetail: ''
            },
            articleBasicInfo: {number: '', pmid: '', pmcid: '', source: '', sourceNumber: '', summary: ''},
            clinvarBasicInfo: {
                pathogenicity: '',
                reviewStar: 0,
                reviewStatus: '',
                clinvarId: '',
                consequence: '',
                pathAbbr: '',
                afTgp: '',
                afEsp: '',
                afExac: ''
            }
        }
    }
})