<template>
	<div class="summary" :style="`--labels-click:${summary.summaryEvent.boxShadow}`">
		<div style="min-height: 156px;display: flex;background-color: #235BA3;">
			<div style="min-width: 40px;"></div>
			<SummaryCard :data="basicInfo" @click="cardEvent(summary.info.name)"
				:class="summary.info.focused ? 'card-border' : 'card-unborder'" id="basicInfo"
				:style="isActive == summary.info.name ? 'transform: translate(0px, 8px);' : ''"></SummaryCard>
			<SummaryCard :data="classificationInfo" @click="cardEvent(summary.classify.name)"
				:class="summary.classify.focused ? 'card-border' : 'card-unborder'" id="classification"
				:style="isActive == summary.classify.name ? 'transform: translate(0px, 8px);' : ''"></SummaryCard>
			<SummaryCard :data="articleInfo" @click="cardEvent(summary.article.name)"
				:class="summary.article.focused ? 'card-border' : 'card-unborder'" id="article"
				:style="isActive == summary.article.name ? 'transform: translate(0px, 8px);' : ''"></SummaryCard>
			<SummaryCard :data="swpInfo" @click="cardEvent(summary.swp.name)"
				:class="summary.swp.focused ? 'card-border' : 'card-unborder'" id="swp"
				:style="isActive == summary.swp.name ? 'transform: translate(0px, 8px);' : ''"></SummaryCard>
			<SummaryCard :data="clinicalEvidence" @click="cardEvent(summary.clinvar.name)"
				:class="summary.clinvar.focused ? 'card-border' : 'card-unborder'" id="clinical"
				:style="isActive == summary.clinvar.name ? 'transform: translate(0px, 8px);' : ''"></SummaryCard>
			<SummaryCard :data="populationInfo" @click="cardEvent(summary.gnomAD.name)"
				:class="summary.gnomAD.focused ? 'card-border' : 'card-unborder'" id="gnomAD"
				:style="isActive == summary.gnomAD.name ? 'transform: translate(0px, 8px);' : ''"></SummaryCard>
			<div style="min-width: 40px;"></div>
		</div>
		<div style="margin-top: 20px;display: flex;">
			<div style="min-width: 40px;"></div>
			<div :id="summary.summaryEvent.show" v-if="summary.summaryEvent.show === 'Classification'">
				<!-- <CriteriaTable :variantId="classificationInfo" v-if="classificationInfo.criteria != ''"></CriteriaTable> -->
                <CriteriaTable v-if="classificationInfo.criteria != ''"></CriteriaTable>
			</div>
			<div :id="summary.summaryEvent.show" style="padding-bottom: 20px; width:100%" v-else-if="summary.summaryEvent.show === 'Basic information'">
				<VariantMessage :info="basicInfors"></VariantMessage>
			</div>
			<div :id="summary.summaryEvent.show" style="width: 100%;" v-else-if="summary.summaryEvent.show === 'Software predication'">
				<div class="software-chart">
					<div style="display: flex;">
						<div style="font-family: HanSansCN-Bold;margin-top: 80px;margin-right: 5px;">{{ t('commons.revel')
						}}:&nbsp;</div>
						<div>
							<LineBar :type="'revel'"></LineBar>
						</div>
					</div>
					<div style="display: flex;">
						<div style="font-family: HanSansCN-Bold;margin-top: 70px;margin-right: 5px;">{{ t('commons.blank')
						}}</div>
						<div>
							<LineBar :type="'regular'"></LineBar>
						</div>
					</div>
				</div>
				<div style="display: flex;">
					<!-- <div style="width:260px;"></div> -->
					<n-data-table :columns="predictionData.predictionColumns" :data="predictionData.predictionData"
						:bordered="false" />
					<!-- <div style="width:260px;"></div> -->
				</div>
			</div>
			<div :id="summary.summaryEvent.show" style="width: 100%;" v-else-if="summary.summaryEvent.show === 'Article interpretation'">
				<ArticleTable></ArticleTable>
			</div>
			<div v-else-if="summary.summaryEvent.show === 'Clinical evidence'">
				<ClinicalEvidence />
			</div>
			<div v-else-if="summary.summaryEvent.show === 'Population frequency'" style="width: 100%;">
				<GnomAd />
			</div>
			<div style="min-width: 40px;"></div>
		</div>
	</div>
</template>

<script setup lang="ts">
    import { ref, onMounted } from "vue";
    import { useSummary, useData, useInformation } from "@/store";
    import { useI18n } from "vue-i18n";
    import { getVariantId } from "@/api/request";
    import { useRoute, useRouter } from "vue-router";
    import { variantBasicInfo, BasicInfor, classfyInfo, articleCriteria, softwareItem, clinicalItem, populationItem } from "@/types/info";

    import SummaryCard from "./submodules/summaryCard.vue";
    import LineBar from "./submodules/progressbar.vue";
    import ArticleTable from "./submodules/articletable.vue";
    import CriteriaTable from "./submodules/criteriatable.vue";
    import VariantMessage from "./submodules/variantmessage.vue";
    import ClinicalEvidence from './submodules/clinicalEvidence.vue';
    import GnomAd from './gnomad.vue'

    const { t } = useI18n();
    const route = useRoute();
    const router = useRouter();
    const predictionData = useData();
    const summary = useSummary();
    const isActive = ref('');
    const variantInfo = useInformation();
    const variantId = ref(route.params.variantId.toString());
    const cardEvent = (arg: string) => {
        router.push({name: 'variant', params: { variantId: variantId.value }, query: { type: arg }})
        cardStatus(arg);
    };
    const cardStatus = (arg: string | any) => {
        isActive.value = arg;
        switch (arg) {
            case "info":
                summary.summaryEvent.boxShadow = summary.info.color;
                summary.summaryEvent.name = summary.info.name;
                summary.summaryEvent.show = summary.showTabs[summary.info.name as keyof typeof summary.showTabs];
                summary.info.focused = true;
                summary.classify.focused = false;
                summary.clinvar.focused = false;
                summary.swp.focused = false;
                summary.omim.focused = false;
                summary.gnomAD.focused = false;
                break;
            case "classification":
                summary.summaryEvent.boxShadow = summary.classify.color;
                summary.summaryEvent.name = summary.classify.name;
                summary.summaryEvent.show = summary.showTabs[summary.classify.name as keyof typeof summary.showTabs];
                summary.info.focused = false;
                summary.classify.focused = true;
                summary.clinvar.focused = false;
                summary.swp.focused = false;
                summary.omim.focused = false;
                summary.gnomAD.focused = false;
                break;
            case "clinvar":
                summary.summaryEvent.boxShadow = summary.clinvar.color;
                summary.summaryEvent.name = summary.clinvar.name;
                summary.summaryEvent.show = summary.showTabs[summary.clinvar.name as keyof typeof summary.showTabs];
                summary.info.focused = false;
                summary.classify.focused = false;
                summary.clinvar.focused = true;
                summary.swp.focused = false;
                summary.omim.focused = false;
                summary.gnomAD.focused = false;
                break;
            case "article":
                summary.summaryEvent.boxShadow = summary.article.color;
                summary.summaryEvent.name = summary.article.name;
                summary.summaryEvent.show = summary.showTabs[summary.article.name as keyof typeof summary.showTabs];
                summary.info.focused = false;
                summary.classify.focused = false;
                summary.clinvar.focused = false;
                summary.swp.focused = false;
                summary.article.focused = true;
                summary.gnomAD.focused = false;
                break;
            case "gnomAD":
                summary.summaryEvent.boxShadow = summary.gnomAD.color;
                summary.summaryEvent.name = summary.gnomAD.name;
                summary.summaryEvent.show = summary.showTabs[summary.gnomAD.name as keyof typeof summary.showTabs];
                summary.info.focused = false;
                summary.classify.focused = false;
                summary.clinvar.focused = false;
                summary.swp.focused = false;
                summary.omim.focused = false;
                summary.gnomAD.focused = true;
                break;
            case "swp":
                summary.summaryEvent.boxShadow = summary.swp.color;
                summary.summaryEvent.name = summary.swp.name;
                summary.summaryEvent.show = summary.showTabs[summary.swp.name as keyof typeof summary.showTabs];
                summary.info.focused = false;
                summary.classify.focused = false;
                summary.clinvar.focused = false;
                summary.swp.focused = true;
                summary.omim.focused = false;
                break;
        }
    }
    const basicInfo = ref<variantBasicInfo>({
        type: summary.info.name,
        variantId: '',
        gene: '',
        transcript: '',
        chgvs: '',
        phgvs: ''
    });
    const basicInfors = ref<BasicInfor>({
        gene: '',
        variantId: '',
        transcript: '',
        classification: '',
        criteria: '',
        chgvs: '',
        phgvs: '',
        rsId: '',
        cytoband: '',
        chgvsDetail: '',
        phgvsDetail: ''
    })
    const classificationInfo = ref<classfyInfo>({
        type: summary.classify.name,
        criteria: "",
        evidence: "",
        classification: ""
    });
    const articleInfo = ref<articleCriteria>({
        type: summary.article.name,
        criteria: "",
        pmid: "",
        pmcid: "",
        number: "",
        source: "",
        sourceNumber: "",
        summary: ""
    });
    const swpInfo = ref<softwareItem>({
        type: summary.swp.name,
        pp3Strengh: "",
        bp4Strengh: ""
    });
    const clinicalEvidence = ref<clinicalItem>({
        type: summary.clinvar.name,
        clinvarId: "",
        star: "",
        pathogenicity: ""
    });
    const populationInfo = ref<populationItem>({
        type: summary.gnomAD.name,
    });
    const getData = () => {
        // const data = new FormData();
        // data.append("vd", variantId.value);
        const data = {
            variant_id: '7-107315505-T-A',
            user_id: 22,
            genome_version: 'hg19',
            version: 'hearingloss'
        }
        getVariantId(data).then(res => {
            if (res.data.code === 1000) {
                basicInfo.value.variantId = res.data.data.variantId;
                basicInfo.value.gene = res.data.data.gene;
                basicInfo.value.transcript = res.data.data.transcript;
                basicInfo.value.chgvs = res.data.data.chgvs.split(":").length > 1 ? res.data.data.chgvs.split(":")[1] : res.data.data.chgvs;
                basicInfo.value.phgvs = res.data.data.phgvs.split(":").length > 1 ? res.data.data.phgvs.split(":")[1] : res.data.data.phgvs;
                basicInfors.value.gene = res.data.data.gene;
                basicInfors.value.variantId = res.data.data.variantId;
                basicInfors.value.transcript = res.data.data.transcript;
                basicInfors.value.classification = res.data.data.classification;
                basicInfors.value.criteria = res.data.data.criteria;
                basicInfors.value.chgvs = res.data.data.chgvs.split(":")[1];
                basicInfors.value.phgvs = res.data.data.phgvs.split(":")[1];
                basicInfors.value.rsId = res.data.data.rsId;
                basicInfors.value.cytoband = res.data.data.cytoband;
                basicInfors.value.chgvsDetail = res.data.data.chgvsSummary;
                basicInfors.value.phgvsDetail = res.data.data.phgvsSummary;
                classificationInfo.value.criteria = res.data.data.criteria;
                classificationInfo.value.classification = res.data.data.classification;
                variantInfo.variantBasicInfo.gene = res.data.data.gene;
                variantInfo.variantBasicInfo.variantId = res.data.data.variantId;
                variantInfo.variantBasicInfo.transcript = res.data.data.transcript;
                variantInfo.variantBasicInfo.classification = res.data.data.classification;
                variantInfo.variantBasicInfo.criteria = res.data.data.criteria;
                variantInfo.variantBasicInfo.chgvs = res.data.data.chgvs.split(":").length > 1 ? res.data.data.chgvs.split(":")[1] : res.data.data.chgvs;
                variantInfo.variantBasicInfo.phgvs = res.data.data.phgvs.split(":").length > 1 ? res.data.data.phgvs.split(":")[1] : res.data.data.phgvs;
                variantInfo.variantBasicInfo.rsId = res.data.data.rsId;
                variantInfo.variantBasicInfo.cytoband = res.data.data.cytoband;
                variantInfo.variantBasicInfo.chgvsDetail = res.data.data.chgvsSummary;
                variantInfo.variantBasicInfo.phgvsDetail = res.data.data.phgvsSummary;
                variantInfo.articleBasicInfo.number = res.data.data.publicationInfoVo.number;
                variantInfo.articleBasicInfo.pmid = res.data.data.publicationInfoVo.pmids;
                variantInfo.articleBasicInfo.pmcid = res.data.data.publicationInfoVo.pmcids;
                variantInfo.articleBasicInfo.source = res.data.data.publicationInfoVo.source;
                variantInfo.articleBasicInfo.sourceNumber = res.data.data.publicationInfoVo.sourceNumber;
                variantInfo.articleBasicInfo.summary = res.data.data.publicationInfoVo.summary;
                articleInfo.value.number = res.data.data.publicationInfoVo.number;
                articleInfo.value.pmid = res.data.data.publicationInfoVo.pmids;
                articleInfo.value.pmcid = res.data.data.publicationInfoVo.pmcids;
                articleInfo.value.source = res.data.data.publicationInfoVo.source;
                articleInfo.value.sourceNumber = res.data.data.publicationInfoVo.sourceNumber;
                articleInfo.value.summary = res.data.data.publicationInfoVo.summary;
                variantInfo.clinvarBasicInfo.clinvarId = res.data.data.clinvarObj.clinvarId;
                variantInfo.clinvarBasicInfo.consequence = res.data.data.clinvarObj.mutation;
                variantInfo.clinvarBasicInfo.pathogenicity = res.data.data.clinvarObj.pathogenicity;
                variantInfo.clinvarBasicInfo.pathAbbr = res.data.data.clinvarObj.pathAbbr;
                variantInfo.clinvarBasicInfo.reviewStar = parseInt(res.data.data.clinvarObj.reviewStatusStar);
                variantInfo.clinvarBasicInfo.reviewStatus = res.data.data.clinvarObj.reviewStatus;
                variantInfo.clinvarBasicInfo.afEsp = res.data.data.clinvarObj.afEsp;
                variantInfo.clinvarBasicInfo.afExac = res.data.data.clinvarObj.afExac;
                variantInfo.clinvarBasicInfo.afTgp = res.data.data.clinvarObj.afTgp;
            }
        });
    };

    onMounted(() => {
        cardStatus(route.query.type);
        getData();
    });

</script>

<style lang="scss">
.n-input {
	text-align: left;
}

.n-tabs .n-tabs-nav.n-tabs-nav--card-type .n-tabs-tab {
	border: 1px solid var(--labels-click);
	box-shadow: 0 0 8px var(--labels-click);
}

.n-tabs .n-tabs-nav.n-tabs-nav--card-type .n-tabs-pad {
	border-bottom: 1px solid var(--labels-click);
	box-shadow: 0 0 8px var(--labels-click);
}

.n-tabs .n-tab-pane {
	min-height: 700px;
	border-left: 1px solid var(--labels-click);
	border-bottom: 1px solid var(--labels-click);
	border-right: 1px solid var(--labels-click);
	box-shadow: 0 0 8px var(--labels-click);
}

.n-tabs .n-tabs-nav.n-tabs-nav--card-type .n-tabs-tab {
	font-family: HanSansCN-Bold;
}

.n-tabs .n-tabs-nav.n-tabs-nav--card-type .n-tabs-tab.n-tabs-tab--active {
	color: var(--labels-click);
}

.summary {
	min-height: 700px;
}

.show-content {
	flex: 10;
	border: 1px solid var(--labels-click);
	box-shadow: 0 0 8px var(--labels-click);
	min-height: 750px;
	border-top-left-radius: 5px;
	border-top-right-radius: 5px;
}

.card-border {
	border-top-left-radius: 5px;
	border-top-right-radius: 5px;
	/* box-shadow: 0 0 8px var(--labels-click); */
}

.card-unborder {
	border-top-left-radius: 5px;
	border-top-right-radius: 5px;
}

.n-tabs .n-tab-pane {
	padding-top: 0;
}

.comment-thumbup:hover {
	color: skyblue
}

.comment-edit:hover {
	color: skyblue
}

.criteria-filtering {
	font-family: HanSansCN-Bold;
	color: black;
}

.criteria-filtering:hover {
	color: skyblue;
}

.n-tag:hover {
	background-color: #2080f0;
	border: 1px solid white;
	color: white;
}

.software-chart {
	width: 100%;
	display: flex;
	justify-content: space-around;
	margin: 10px 0 50px 0;
}
</style>
