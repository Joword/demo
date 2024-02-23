<template>
    <div class="clinical-evidence">
        <div class="clin-var">
            <span>ClinVar </span><span>(Version:05-Oct-2023)</span>
        </div>
        <div class="clinical-table">
            <table cellpadding="0" cellspacing="0">
                <thead>
                    <tr>
                        <th>Clinical Significance</th>
                        <th>Review Status</th>
                        <th>Last Evaluation</th>
                        <th>Number of Submitters</th>
                        <th>Publications</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                      <td style="color: #D43030; font-size: 14px; font-weight: bolder;">{{ clivarData.pathogenicity }}</td>
                        <td>
                          <n-rate :count="4" size="small" :value="Number(clivarData.reviewStatusStar)" readonly />
                            <div>{{clivarData.reviewStatus}}</div>
                        </td>
                        <td>{{ clivarData.lastEvalulated }}</td>
                        <td>{{ clivarData.submitterNum }}</td>
                        <td style="color: #1F5294;">
                            <a style="margin-right: 5px;" v-for="(i, inx) in clivarData.pmids?.split(',')" :key="inx" :href="'https://pubmed.ncbi.nlm.nih.gov/' + i" target="_blank">{{ i }},</a>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="clinical-table">
            <div class="sub-title">
                <div>{{ clivarData.submissions?.length }} Accessions, {{ clivarData.submissions?.reduce((pre: any, cur: any)=> {
                    return pre + cur.submissionNum
                }, 0) }} Submissions</div>
                <div>Show submissions<n-switch size="small" v-model:value="submissionSwitch" /></div>
            </div>
            <table cellpadding="0" cellspacing="0" class="submission-table">
                <thead>
                    <tr>
                        <th>Interpreted conditions</th>
                        <th>Interpretation</th>
                        <th>Number of submissons</th>
                        <th>Review Status</th>
                        <th>Condition record</th>
                    </tr>
                </thead>
                <tbody v-show="submissionSwitch">
                    <tr v-for="(item, index) in clivarData.submissions" :key="index">
                        <td style="font-weight: bold;">{{ item.condition }}</td>
                        <td style="color: #D43030;">{{ item.pathogenicity }}</td>
                        <td>{{ item.submissionNum }}</td>
                        <td>{{ item.reviewStatus }}</td>
                        <td><a :href="'https://www.ncbi.nlm.nih.gov/clinvar/' + item.rcv" target="_blank">{{ item.rcv }}</a></td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</template>

<script setup lang='ts'>
    import { ref, onMounted } from 'vue';
    import { useRoute } from "vue-router";
    import { getClinicalEvidence } from "@/api/request"

    const { variantId }  = useRoute().params;
    const clivarData = ref<any>({});
    const submissionSwitch = ref<boolean>(true);

    const getClivarData = ()=> {
        getClinicalEvidence(variantId as string).then(res=> {
            if(res.data.code === 1000) {
                clivarData.value = res.data.data;
            }
        })
    }

    onMounted(()=> {
        getClivarData();
    })

</script>

<style lang='scss' scoped>
    .clinical-evidence {
        text-align: left;
        font-size: 12px;
        .clin-var {
            border-bottom: 1px solid #C7C7C7;
            span:first-child {
                font-size: 14px;
                font-weight: bolder;
                color: #1F5294;
            }
            span:last-child {
                font-size: 12px;
                color: #A6A6A6;
            }
        }
        .clinical-table {
            margin-bottom: 20px;
            table {
                width: 100%;
                // text-align: left;
                font-size: 12px;
                table-layout:fixed;
                tr {
                    width: 100%;
                    th {
                        width: 20%;
                        padding-top: 10px;
                        color: #808080;
                        border-bottom: 1px solid #1F5294;
                    }
                    td {
                        width: 20%;
                        padding: 8px 0;
                        padding-right: 10px;
                        word-wrap: break-word;
                    }
                }
            }
            .submission-table {
                tr {
                    td {
                        border-bottom: 1px solid #C7C7C7;
                    }
                }
            }
            .sub-title {
                padding-bottom: 3px;
                border-bottom: 1px solid #C7C7C7;
                color: #A6A6A6;
                display: flex;
                div {
                    width: 20%;
                }
            }
        }
    }
</style>