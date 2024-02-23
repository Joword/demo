<template>
	<n-card class="summary-info" :style="`--labels-color:${labels.backgroundColor}`">
		<template #header>
			<div class="summary-info-header" :style="{ backgroundColor: labels.backgroundColor }">
				<n-icon style="vertical-align: middle;" color="white" size="20" :component="TextSnippetOutlined"
					v-if="labels.value === 'Basic information'" />
				<n-icon style="vertical-align: middle;" color="white" size="20" :component="EarthOutline"
					v-if="labels.value === 'ACMG classification'" />
				<n-icon style="vertical-align: middle;" color="white" size="20" :component="FileAltRegular"
					v-if="labels.value === 'Article interpretation'" />
				<n-icon style="vertical-align: middle;" color="white" size="20" :component="Bot24Regular"
					v-if="labels.value === 'Softwares prediction'" />
				<n-icon style="vertical-align: middle;" color="white" size="20" :component="MedicationLiquidOutlined"
					v-if="labels.value === 'Clinical evidence'" />
				<n-icon style="vertical-align: middle;" color="white" size="20" :component="IncreaseLevel"
					v-if="labels.value === 'Population frequency'" />
				<span style="vertical-align: middle;padding-left: 2px;">{{ labels.value }}</span>
			</div>
		</template>
		<template #header-extra>
			<div class="summary-info-icon" :style="{ backgroundColor: labels.backgroundColor }">
				<n-icon style="vertical-align: middle;" color="white" size="25" />
			</div>
		</template>
		<template #default>
			<div class="summary-info-box">
				<div class="summary-info-content" v-show="labels.value === 'Basic information'">
					<div class="info-item">
						<span>VariantId: </span><span class="info-item-card2">{{ info.variantBasicInfo.variantId }}</span>
					</div>
					<div class="info-item">
						<span>Gene: </span><span class="info-item-card1">{{ info.variantBasicInfo.gene }}</span>
					</div>
					<div class="info-item">
						<span>Transcript: </span><span class="info-item-card2">{{ info.variantBasicInfo.transcript }}</span>
					</div>
					<div class="info-item">
						<span>chgvs: </span><span class="info-item-card2">{{ info.variantBasicInfo.chgvs }}</span>
					</div>
				</div>
				<div class="summary-info-content" v-show="labels.value === 'ACMG classification'">
					<span class="acmg-name" :style="{ color: pathogenicColor }">{{
						convertPathogenicity(info.variantBasicInfo.classification) }}</span>
					<div class="acgc-item">
						<span v-for="item, idx in criterias" :label="item.label" :key="idx" :value="item.value"
							:style="{ backgroundColor: item.color }">{{ item.value }}</span>
					</div>
				</div>
				<div class="summary-info-content" v-show="labels.value === 'Article interpretation'">
					<div class="article-item">
						<span class="item-num">{{ info.articleBasicInfo.number }}</span><span class="item-desc">were
							related</span>
					</div>
					<div class="article-item">
						<span class="item-num">{{ info.articleBasicInfo.sourceNumber }}</span><span class="item-desc">have been parsed</span>
					</div>
				</div>
				<div class="summary-info-content" v-show="labels.value === 'Softwares prediction'">
					<div class="soft-pred">
						<div class="article-item">
							<span class="pred-span pred-span-1">31</span><span>Deleterious</span><br />
							<span class="pred-span pred-span-2">9</span><span> VUS</span><br />
						</div>
						<div class="article-item"><span class="pred-span pred-span-3">1</span><span> Benign</span></div>
					</div>
				</div>
				<div class="summary-info-content" v-show="labels.value === 'Clinical evidence'">
					<div class="clinical-Id">
						<span>ClinVarId: </span><span>{{ info.clinvarBasicInfo.clinvarId }}</span>
					</div>
					<div class="clinical-evidence">
						<span>{{ info.clinvarBasicInfo.pathogenicity }}</span>
					</div>
					<div class="clinical-evidence evidence-status">
						<span style="margin-right: 8px;">Review status: </span>
						<n-rate :count="4" size="small" :value=info.clinvarBasicInfo.reviewStar readonly />
					</div>
				</div>
				<div class="summary-info-content" v-show="labels.value === 'Population frequency'">
					<div class="pop-freq">
						<span>Popmax filtering AF: </span><span>0.001301</span>
					</div>
					<div class="pop-freq">
						<span class="freq-tit">gnomAD</span><span></span>
					</div>
				</div>
			</div>
		</template>
	</n-card>
</template>

<script setup lang="ts">
import { ref, reactive, watch, onMounted } from "vue";
import { useSummary, useInformation } from "@/store";
import { summaryInfo } from "@/types/summary";
import { arrayTag } from "@/types/info";
// import { useRoute } from "vue-router";
// import { getVariantId } from "@/api/request";

import { EarthOutline } from "@vicons/ionicons5";
import { FileAltRegular } from "@vicons/fa";
import { Bot24Regular } from "@vicons/fluent";
import { IncreaseLevel } from "@vicons/carbon";
import { TextSnippetOutlined, MedicationLiquidOutlined } from "@vicons/material";

// const route = useRoute();
const summary = useSummary();
const pathogenicColor = ref("");
const props = defineProps(['data']);
const info = useInformation();
const criterias = ref<arrayTag[]>([]);
const labels = reactive<summaryInfo>({ name: '', value: '', color: '', backgroundColor: '', index: [''], content: [''], extraLink: null, focused: false });

const doType = () => {
	switch (props.data.type) {
		case 'info':
			labels.value = summary.info.value;
			labels.backgroundColor = summary.info.color;
			labels.color = "white";
			labels.index = summary.info.index;
			labels.content = summary.info.content;
			labels.focused = summary.info.focused;
			break;
		case 'classification':
			labels.value = summary.classify.value;
			labels.backgroundColor = summary.classify.color;
			labels.color = "white";
			labels.index = summary.classify.index;
			labels.content = summary.classify.content;
			labels.focused = summary.classify.focused;
			break;

		case 'clinvar':
			labels.value = summary.clinvar.value;
			labels.backgroundColor = summary.clinvar.color;
			labels.color = "white";
			labels.index = summary.clinvar.index;
			labels.content = summary.clinvar.content;
			labels.focused = summary.clinvar.focused;
			break;
		case 'article':
			labels.value = summary.article.value;
			labels.backgroundColor = summary.article.color;
			labels.color = "white";
			labels.index = summary.article.index;
			labels.content = summary.article.content;
			labels.focused = summary.article.focused;
			break;
		case 'gnomAD':
			labels.value = summary.gnomAD.value;
			labels.backgroundColor = summary.gnomAD.color;
			labels.color = "white";
			labels.index = summary.gnomAD.index;
			labels.content = summary.gnomAD.content;
			labels.focused = summary.gnomAD.focused;
			break;
		case 'swp':
			labels.value = summary.swp.value;
			labels.backgroundColor = summary.swp.color;
			labels.color = "white";
			labels.index = summary.swp.index;
			labels.content = summary.swp.content;
			labels.focused = summary.swp.focused;
			break;
	}
};
const convertPathogenicity = (arg: string) => {
	switch (arg) {
		case "VUS":
			pathogenicColor.value = summary.pathogenicPalette.vus;
			return "VUS";
		case "P":
			pathogenicColor.value = summary.pathogenicPalette.p;
			return "Pathogenic";
		case "LP":
			pathogenicColor.value = summary.pathogenicPalette.lp;
			return "Likely Pathogenic";
		case "LB":
			pathogenicColor.value = summary.pathogenicPalette.lb;
			return "Likely Benign";
		case "B":
			pathogenicColor.value = summary.pathogenicPalette.b;
			return "Benign";
		case "Uncertain Significance":
			pathogenicColor.value = summary.pathogenicPalette.vus;
			return "VUS";
		case "Pathogenic":
			pathogenicColor.value = summary.pathogenicPalette.p;
			return "Pathogenic";
		case "Likely Pathogenic":
			pathogenicColor.value = summary.pathogenicPalette.lp;
			return "Likely Pathogenic";
		case "Likely Benign":
			pathogenicColor.value = summary.pathogenicPalette.lb;
			return "Likely Benign";
		case "Likely_Benign":
			pathogenicColor.value = summary.pathogenicPalette.lb;
			return "Likely Benign";
		case "Benign":
			pathogenicColor.value = summary.pathogenicPalette.b;
			return "Benign";
	}
};
const matchColor = (criteria: string) => {
	if (criteria.split("_").length > 1) {
		switch (criteria.split("_")[1]) {
			case 'P':
				return summary.criteriaPalette[criteria.split("_")[0].slice(0, 1).toLocaleLowerCase() + "p"]
			case 'M':
				return summary.criteriaPalette[criteria.split("_")[0].slice(0, 1).toLocaleLowerCase() + "m"]
			case 'S':
				return summary.criteriaPalette[criteria.split("_")[0].slice(0, 1).toLocaleLowerCase() + "s"]
			case 'Supporting':
				return summary.criteriaPalette[criteria.split("_")[0].slice(0, 1).toLocaleLowerCase() + "p"]
			case 'Moderate':
				return summary.criteriaPalette[criteria.split("_")[0].slice(0, 1).toLocaleLowerCase() + "m"]
			case 'Strong':
				return summary.criteriaPalette[criteria.split("_")[0].slice(0, 1).toLocaleLowerCase() + "s"]
		}
	} else {
		return summary.criteriaPalette[criteria.slice(0, 2).toLocaleLowerCase()]
	}
};
const filterCriteria = (e: string) => {
	if (e.split("_").length > 1) {
		switch (e.split("_")[0]) {
			case 'PS3':
				criterias.value.push({ label: 'PS3', value: 'PS3', color: matchColor(e) });
				break;
			case "PM2":
				criterias.value.push({ label: 'PM2', value: 'PM2', color: matchColor(e) });
				break;
			case "PM3":
				criterias.value.push({ label: 'PM3', value: 'PM3', color: matchColor(e) });
				break;
			case "PM5":
				criterias.value.push({ label: 'PM5', value: 'PM5', color: matchColor(e) });
				break;
			case "PP1":
				criterias.value.push({ label: 'PP1', value: 'PP1', color: matchColor(e) });
				break;
			case "PP3":
				criterias.value.push({ label: 'PP3', value: 'PP3', color: matchColor(e) });
				break;
			case "PP4":
				criterias.value.push({ label: 'PP4', value: 'PP4', color: matchColor(e) });
				break;
			case "BA1":
				criterias.value.push({ label: 'BA1', value: 'BA1', color: matchColor(e) });
				break;
			case "BS1":
				criterias.value.push({ label: 'BS1', value: 'BS1', color: matchColor(e) });
				break;
			case "BS2":
				criterias.value.push({ label: "BS2", value: 'BS2', color: matchColor(e) });
				break;
		}
	} else {
		switch (e) {
			case 'PS3':
				criterias.value.push({ label: 'PS3', value: 'PS3', color: matchColor('PS3') });
				break;
			case "PM2":
				criterias.value.push({ label: 'PM2', value: 'PM2', color: matchColor('PM2') });
				break;
			case "PM3":
				criterias.value.push({ label: 'PM3', value: 'PM3', color: matchColor('PM3') });
				break;
			case "PM5":
				criterias.value.push({ label: 'PM5', value: 'PM5', color: matchColor('PM5') });
				break;
			case "PP1":
				criterias.value.push({ label: 'PP1', value: 'PP1', color: matchColor('PP1') });
				break;
			case "PP3":
				criterias.value.push({ label: 'PP3', value: 'PP3', color: matchColor('PP3') });
				break;
			case "PP4":
				criterias.value.push({ label: 'PP4', value: 'PP4', color: matchColor('PP4') });
				break;
			case "BA1":
				criterias.value.push({ label: 'BA1', value: 'BA1', color: matchColor('BA1') });
				break;
			case "BS1":
				criterias.value.push({ label: 'BS1', value: 'BS1', color: matchColor('BS1') });
				break;
			case "BS2":
				criterias.value.push({ label: "BS2", value: 'BS2', color: matchColor('BS2') });
				break;
		}
	}
};
const showCriteria = () => {
    // console.log(info.variantBasicInfo.criteria)
	if (info.variantBasicInfo.criteria !== "") {
		if (info.variantBasicInfo.criteria.split(";").length > 1) {
			info.variantBasicInfo.criteria.split(";").forEach(e => { filterCriteria(e); });
			criterias.value = Array.from(new Set(criterias.value));
		} else {
			info.variantBasicInfo.criteria.split(", ").forEach(e => { filterCriteria(e); });
			criterias.value = Array.from(new Set(criterias.value));
		}
	} else {
		// const data = new FormData();
		// data.append("vd", route.params.variantId.toString());
		// getVariantId(data).then(res => {
        //     console.log(res)
		// 	if (res.data.code === 1000) {
        //         console.log(res.data.data)
		// 		info.variantBasicInfo.classification = res.data.data.classification;
		// 		info.variantBasicInfo.criteria = res.data.data.criteria;
		// 		if (info.variantBasicInfo.criteria.split(";").length > 1) {
		// 			info.variantBasicInfo.criteria.split(";").forEach(e => { filterCriteria(e); });
		// 			criterias.value = Array.from(new Set(criterias.value));
		// 		} else {
		// 			info.variantBasicInfo.criteria.split(", ").forEach(e => { filterCriteria(e); });
		// 			criterias.value = Array.from(new Set(criterias.value));
		// 		}
		// 	}
		// });
        
        if(props.data.type === 'classification') {
            watch(()=> props.data.criteria, ()=> {
                info.variantBasicInfo.classification = props.data.classification;
                info.variantBasicInfo.criteria = props.data.criteria;
                if (props.data.criteria.split(";").length > 1) {
                    props.data.criteria.split(";").forEach((e: any) => { filterCriteria(e); });
                    criterias.value = Array.from(new Set(criterias.value));
                } else {
                    props.data.criteria.split(", ").forEach((e: any) => { filterCriteria(e); });
                    criterias.value = Array.from(new Set(criterias.value));
                }
            })
        }
	}
};
onMounted(() => {
	doType();
	showCriteria();
})
</script>

<style lang="scss">
.common-sty {
	text-overflow: ellipsis;
	white-space: nowrap;
	overflow: hidden;
}

.n-card {
	margin-top: 20px;
	width: 300px;
	height: 130px;
	margin-right: 5px;
	overflow: hidden;

}

.n-card>.n-card-header {
	padding: 0;
}

.n-card>.n-card__content {
	padding: 0;
}

.n-card.n-card--bordered {
	border-top-left-radius: 7px;
	border-top-right-radius: 7px;
	border-top-style: 1px solid var(--labels-color);
	border-left-style: 1px solid var(--labels-color);
	border-right-style: 1px solid var(--labels-color);
	border-bottom-color: white;
}

.summary-info {
	cursor: pointer;
	border-top-left-radius: 5px;
	border-top-right-radius: 5px;
}

.summary-info:hover {
	border-top-left-radius: 7px;
	border-top-right-radius: 7px;
	/* transform: translate(0px, 8px); */
}

.summary-info-header {
	height: 30px;
	border-top-left-radius: 5px;
	text-align: left;
	color: white;
	padding-left: 12px;
	font-size: 16px;
}

.summary-info-icon {
	height: 30px;
	border-top-right-radius: 5px;
}

.summary-info-box {
	width: 100%;
	height: 100px;
	display: flex;
	align-items: center;
}

.summary-info-content {
	text-align: left;
	padding-left: 12px;
	padding-right: 10px;
	font-size: 12px;
	// line-height: 22px;
	width: calc(100% - 22px);
	.info-item {
		@extend .common-sty;
		.info-item-card1 {
			font-size: 16px;
			color: #0A56AD;
			font-weight: bold;
		}

		.info-item-card2 {
			color: #383838;
			font-weight: bold;
		}
	}

	.acmg-name {
		font-size: 18px;
		font-weight: bolder;
		height: 100%;
	}

	.acgc-item {
		span {
			display: inline-block;
			width: 38px;
			height: 20px;
			text-align: center;
			margin-right: 5px;
			margin-bottom: 3px;
			border-radius: 3px;
			background: #FFC300;
			color: #FFFFFF;
		}
	}

	.article-item {
		@extend .common-sty;
		line-height: 20px;

		.item-num {
			font-size: 18px;
			font-weight: bolder;
			color: #00BAAD;
			margin-right: 6px;
		}

		.item-desc {
			font-size: 12px;
		}
	}

	.soft-pred {
		.pred-span {
			font-size: 18px;
			font-weight: bolder;
		}

		.pred-span-1 {
			color: #D43030;
		}

		.pred-span-2 {
			color: #808080;
		}

		.pred-span-3 {
			color: #43CF7C;
		}
	}

	.clinical-evidence {
		line-height: 20px;
		span:last-child {
			font-size: 14px;
			font-weight: bolder;
			color: #D43030;
		}
	}
    .evidence-status {
        display: flex;
        align-items: center;
    }

	.clinical-Id {
		span:last-child {
			font-size: 14px;
			font-weight: bolder;
			color: #383838;
		}
	}

	.pop-freq {
		line-height: 20px;
		span:last-child {
			font-size: 16px;
			font-weight: bolder;
			color: #A5D63F;
		}

		.freq-tit {
			color: #2068BA;
			font-weight: 700;
		}
	}
}</style>