<template>
    <div id="pmid">
        <InfoBar type="article" :value="barValue"></InfoBar>
        <div class="pmid-content">
            <div class="cont-detail">
                <div class="detail-left">
                    <div class="type-switch">
                        <CheckBox :info="'gene'" style="margin-top: 0; width: 33.33%;" @switch="switchType"></CheckBox>
                        <CheckBox :info="'mutation'" style="margin-top: 0; width: 33.33%" @switch="switchType"></CheckBox>
                        <CheckBox :info="'disease'" style="margin-top: 0; width: 33.33%" @switch="switchType"></CheckBox>
                    </div>
                    <!-- <div class="type-table">
                        <div class="gene-list">
                            <div class="list-item" v-for="item in geneData" :key="item.name">
                                <div class="item-name">{{ item.name }}</div>
                                <div class="item-value">{{ item.value }}</div>
                            </div>
                        </div>
                    </div> -->
                    <div class="type-table">
                        <template v-if="showType.gene">
                            <div class="gene-list">
                                <div class="list-item" v-for="item in getGeneData.geneList" :key="item.mentions">
                                    <div class="item-name">{{ item.mentions }}</div>
                                    <div class="item-value">{{ item.mentionsNum }}</div>
                                </div>
                            </div>
                            <div class="gene-more">
                                <span  @click="moreGeneData('gene')">More</span>
                            </div>
                        </template>
                        <template v-if="showType.mutation">
                            <div class="gene-list">
                                <div class="list-item" v-for="item in getGeneData.variantList" :key="item.mentions">
                                    <div class="item-name">{{ item.mentions }}</div>
                                    <div class="item-value">{{ item.mentionsNum }}</div>
                                </div>
                            </div>
                            <div class="gene-more">
                                <span  @click="moreGeneData('mutation')">More</span>
                            </div>
                        </template>
                        <template v-if="showType.disease">
                            <div class="gene-list">
                                <div class="list-item" v-for="item in getGeneData.dieaseList" :key="item.mentions">
                                    <div class="item-name">{{ item.mentions }}</div>
                                    <div class="item-value">{{ item.mentionsNum }}</div>
                                </div>
                            </div>
                            <div class="gene-more">
                                <span  @click="moreGeneData('disease')">More</span>
                            </div>
                        </template>
                    </div>
                    <div class="search-progress">
                        <span class="tit-tip">Write a comment for PMID16460646</span>
                        <div class="search">
                            <n-input-group>
                                <n-input style="--n-border: 1px solid #00BAAD; --n-border-hover: 1px solid #0c7a43;
                                    --n-border-pressed: 1px solid #0c7a43;
                                    --n-border-focus: 1px solid #0c7a43;
                                    --n-border-disabled: 1px solid #0c7a43;" :style="{ width: '100%', borderColor: '#00BAAD',borderRadius: '10px' }" />
                                <n-button type="primary" style="background-color: #00BAAD; --n-border: 1px solid #00BAAD;
                                    --n-border-hover: 1px solid #0c7a43;
                                    --n-border-pressed: 1px solid #0c7a43;
                                    --n-border-focus: 1px solid #0c7a43;
                                    --n-border-disabled: 1px solid #0c7a43;" :style="{ borderRadius: '10px' }">
                                    Submit
                                </n-button>
                            </n-input-group>
                        </div>
                    </div>
                    <div class="search-progress">
                        <span class="tit-tip">This article has been submitted 2/14 times on AIGI.</span>
                        <div class="progress">
                            <n-progress
                                type="line"
                                :percentage="20"
                                color="#00BAAD"
                                style="width: 240px; border: 1px solid #00BAAD; border-radius: 20px;"
                                :indicator-placement="'inside'"
                                processing
                            />
                        </div>
                    </div>
                    <div class="mutation-table">
                        <Pmidmutation :data="getGeneData.tableInfo" />
                    </div>
                </div>
                <div class="detail-right">
                    <div class="paragraph-1">
                        <span>PMID16460646 | </span><span>PMC3525152</span>
                        <div class="desc-1">
                            Genomic analysis of a heterogeneous Mendelian phenotype: multiple novel alleles for inherited hearing loss in the Palestinian population 
                        </div>
                    </div>
                    <div class="paragraph-2">
                        <p>Tom Walsh et al.</p>
                        <p>2006-Jan | Journal:Human Genomics | 60 citatons</p>
                    </div>
                    <div class="paragraph-3">
                        <img src="https://img.js.design/assets/img/642bd0199b30b48c52035e05.png#0f03271acbc8daf494144176cb7760a5" alt="">
                    </div>
                </div>
            </div>
        </div>
    </div>  
</template>

<script setup lang="ts">
    import { ref, reactive, onMounted } from "vue";
    // import { useI18n } from "vue-i18n";
    import InfoBar from "@/components/submodules/infobar.vue";
    import CheckBox from "./submodules/checkbox.vue";
    // import Paper from "@/components/submodules/paper.vue";
    // import PmidTable from "@/components/submodules/pmidtable.vue";
    // import SimilarPublication from "@/components/submodules/similarpub.vue";
    // import VariantRelevance from "@/components/submodules/variantrelevance.vue";
    import Pmidmutation from './submodules/pmidMutation.vue';
    import { useRouter } from "vue-router";
    import { Message } from "@/utils/utils";
    import { getPmidAndGeneData } from '@/api/request';

    interface GeneDataType {
        pmid: string;
        geneList: Array<object | any>;
        tableInfo: Array<object | any>;
        variantList: Array<object | any>;
        dieaseList: Array<object | any>;
    }

    const router = useRouter();

    const barValue = ref("PMID16460646");

    const showType = ref({
        gene: true,
        mutation: true,
        disease: true
    })
    let getGeneData = reactive<GeneDataType>({
        pmid: '',
        geneList: [],
        tableInfo: [],
        variantList: [],
        dieaseList: [],
    })

    const getDataSet = () => {
        getPmidAndGeneData(router.currentRoute.value.params.pmid).then(res=> {
            console.log(res)
            if(res.data.code === 1000) {
                for(let i in getGeneData) {
                    if(i === 'geneList' || i === 'variantList' || i === 'dieaseList') {
                        getGeneData[i] = res.data.data[i].filter((v: any)=> {
                            if(v.mentions !== null) {
                                const len = v.mentions.split('|');
                                if(len.length > 1) {
                                    v.mentions = len[0];
                                    v.mentionsNum = len.length;
                                }else{
                                    v.mentionsNum = 1;
                                }
                                return v
                            }
                        })
                    }else{
                        getGeneData[i] = res.data.data[i];
                    }
                }
                console.log(getGeneData)
            }
        })
    }
    const switchType = (checkStatus: any) => {
        showType.value[checkStatus.type] = checkStatus.status; 
    }
    const moreGeneData = (type: string) => {
        let geneType = type === 'gene' ? 'geneList' : type === 'mutation' ? 'variantList' : type === 'disease' ? 'dieaseList' : '';
        if(getGeneData[geneType].length < 7) {
            Message.warning('No more data currently available');
        }else{

        }
    }
    onMounted(()=> {
        getDataSet();
    })
</script>

<style scoped lang="scss">
    .n-divider {
            color:#A6A6A6;
            font-family: HanSansCN-Bold;
        }
    #pmid {
        min-height: 500px;
    }
    #pmid-book {
        margin-top: 20px;
        display: flex;
    }
    #book-left {
        flex:6;
        min-height: 880px;
        border-top: 2px solid grey;
        border-left: 2px solid grey;
        border-bottom: 2px solid grey;
        border-top-left-radius: 8px;
        border-bottom-left-radius: 8px;
        box-shadow: 6px 0 10px -3px grey;
        border-top-right-radius: 3px;
        border-bottom-right-radius: 3px;
        padding: 10px 20px;
    }
    #book-right {
        flex:6;
        min-height: 880px;
        border-top: 2px solid grey;
        border-right: 2px solid grey;
        border-bottom: 2px solid grey;
        border-top-right-radius: 8px;
        border-bottom-right-radius: 8px;
        padding: 10px 20px;
    }
    .comment-button {
        border: 1px solid rgb(224, 224, 230);
        width: 85px;
        padding-top:8px;
        border-top-right-radius: 20px;
        border-bottom-right-radius: 20px;
        background-color: white;
        color: #225BA5;
        cursor: pointer;
    }
    .comment-button:hover {
        background-color: #225BA5;
        color: white;
        box-shadow: 0px 0px 5px #225BA5;
        border: 1px solid #225BA5;
    }
    .comment-input {
        text-align: left;
        width: 700px;
        border-top-left-radius: 20px;
        border-bottom-left-radius: 20px;
    }

    .pmid-header {
        line-height: 52px;
        background-color: #1F5294;
        border-top: 1px solid #A6A6A6;
        color: #FFFFFF;
    }
    .pad {
        padding: 16px;
    }
    .pmid-content {
        padding: 10px 20px 20px;
        display: flex;
        .cont-detail {
            border: 2px solid #FAF7F7;
            border-radius: 8px;
            display: flex;
            .detail-left {
                width: 50%;
                @extend .pad;
                // box-shadow: 3px 0 8px #000000;
                box-shadow: 8px 0 8px -8px rgba(0,0,0,.5);
                .type-switch {
                    display: flex;
                    text-align: left;
                    align-items: center;
                }
                .type-table {
                    margin-top: 10px;
                    // padding-right: 40px;
                    .gene-list {
                        width: 100%;
                        display: flex;
                        flex-wrap: wrap;
                        border-top: 1px solid #A6A6A6;
                        border-bottom: 1px solid #A6A6A6;
                        .list-item {
                            display: flex;
                            width: 33.3333%;
                            .item-name {
                                width: 70%;
                                text-align: left;
                            }
                            .item-value {
                                width: 30%;
                                text-align: left;
                            }
                        }
                    }
                }
                .gene-more {
                    text-align: right;
                    font-size: 14px;
                    color: #638BB8;
                    cursor: pointer;
                    margin-top: 2px;
                }
                .search-progress {
                    margin-top: 20px;
                    text-align: left;
                    .tit-tip {
                        color: #808080;
                        font-size: 12px;
                    }
                    .search {
                        margin-top: 2px;
                    }
                    .progress {
                        margin-top: 2px;
                    }
                }
                .mutation-table {
                    margin-top: 10px;
                }
            }
            .detail-right {
                width: 50%;
                @extend .pad;
                text-align: left;
                .paragraph-1 {
                    span {
                        font-size: 12px;
                        color: #7EA5CC;
                    }
                    .desc-1 {
                        font-size: 14px;
                        font-weight: bold;
                        line-height: 18px;
                    }
                }
                .paragraph-2 {
                    color: #808080;
                    font-size: 12px;
                    margin: 16px 0;
                }
                .paragraph-3 {
                    img {
                        width: 100%;
                        height: 360px;
                    }
                }
            }
        }
        .cont-chat {
            margin-left: 10px;
        }
    }

</style>